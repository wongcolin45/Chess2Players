import PropTypes from "prop-types";
import {getBoard, movePiece, selectPiece, getTurn, promotionAvailable} from "./ChessAPI.js";
import Piece from "./Piece.jsx";


function Square(props) {


    const rows = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];

    const notation = calculateNotation();

    function calculateNotation() {
        if (props.pov === 'black') {
            return rows[7 - props.colIndex] + (props.rowIndex+1);
        }
        return rows[props.colIndex] + (8 - props.rowIndex);
    }

    async function handleSelection(notation) {
        if (props.element === ' ') {
            props.setPossibleMoves([]);
            return;
        }
        const result = await selectPiece(notation);
        if (result.length >= 0) {
            const newBoard = await getBoard(props.pov);
            props.setGrid(newBoard);
            props.setSelection(notation);
            props.setPossibleMoves(result);
        }else {
            props.setPossibleMoves([]);
        }

    }

    async function handleMove(notation) {
        const result = await movePiece(notation);
        if (result) {
            const newBoard = await getBoard(props.pov);
            props.setGrid(newBoard);
            props.setSelection(null);
            const nextTurn = await getTurn();
            props.setTurn(nextTurn);
            props.setPossibleMoves([]);
            // check for promotion
            const canPromote = await promotionAvailable();
            props.setPromotion(canPromote);
        } else {
            props.setPossibleMoves([]);
            await handleSelection(notation);
        }
    }

    async function handleSquareClick() {
        if (props.selection === null) {
            await handleSelection(notation);
        } else if (props.selection === notation) {
            props.setSelection(null);
            props.setPossibleMoves([]);
        } else {
            await handleMove(notation);
        }
    }

    function getColor() {
        const style = {};
        if (props.selection === notation) {
            style.backgroundColor = '#F6F669';
        }
        if (props.rowIndex % 2 + props.colIndex % 2 === 0 || (props.rowIndex+props.colIndex) % 2 === 0) {
            style.backgroundColor = '#D9EFFF';
        } else {
            style.backgroundColor = '#3B72A6';
        }
        if (props.possibleMoves.some(move => move === notation)) {
            if (props.element === ' ') {
                style.backgroundImage = 'radial-gradient(circle, rgba(128, 128, 128, 0.4) 20%, transparent 20%)';
                style.backgroundSize = '100px 100px'
            } else {
                style.backgroundImage = 'radial-gradient(circle, transparent 40%, rgba(128, 128, 128, 0.6) 42%, transparent 45%)';
                style.backgroundSize = '150px 150px'; // Adjust size for the hollow circle
            }
            style.backgroundPosition = 'center';
            style.backgroundRepeat = 'no-repeat';

        }

        return style;
    }

    return (
        <button key={props.element + props.colIndex}
                className='square'
                style={getColor()}
                onClick={handleSquareClick}>
           <Piece element={props.element} stuff={getColor()}></Piece>
        </button>
    )
}

export default Square;

Square.propTypes = {
    element: PropTypes.string.isRequired,
    rowIndex: PropTypes.number.isRequired,
    colIndex: PropTypes.number.isRequired,
    setGrid: PropTypes.func.isRequired,
    selection: PropTypes.string,
    setSelection: PropTypes.func.isRequired,
    setTurn: PropTypes.func.isRequired,
    pov: PropTypes.string.isRequired,
    possibleMoves: PropTypes.array.isRequired,
    setPossibleMoves: PropTypes.func.isRequired,
    setPromotion: PropTypes.func.isRequired,
}