import {useEffect, useState} from "react";
import {startGame, getBoard} from './ChessAPI.js';
import Square from "./Square.jsx";

import PropTypes from "prop-types";

function Board(props) {

    const [selection, setSelection] = useState(null);



    useEffect(() => {
        getBoard(props.pov).then(board => {
            props.setGrid(board);
        })
    },[props.pov])

    function renderRow(row, rowIndex) {
        return (
            <div className='row' key={row + rowIndex}>

                {
                    row.map((element, colIndex) => {
                        return (
                            <Square element={element}
                                    rowIndex={rowIndex}
                                    colIndex={colIndex}
                                    setGrid={props.setGrid}
                                    selection={selection}
                                    setSelection={setSelection}
                                    setTurn={props.setTurn}
                                    possibleMoves={props.possibleMoves}
                                    setPossibleMoves={props.setPossibleMoves}
                                    setPromotion={props.setPromotion}
                                    pov={props.pov}
                                    key={colIndex}/>
                        )
                    })
                }
            </div>
        )
    }

    //const cols = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];

    function renderBoard() {
        return (
            <>
                {
                    props.grid.map((row, rowIndex)=> {
                        return renderRow(row, rowIndex);
                    })
                }
            </>
        )
    }




    return (
        <>

            <div className="board">
                {renderBoard()}
            </div>

        </>
    )
}

export default Board;

Board.propTypes = {
    setTurn: PropTypes.func.isRequired,
    possibleMoves: PropTypes.array.isRequired,
    setPossibleMoves: PropTypes.func.isRequired,
    pov: PropTypes.string.isRequired,
    grid: PropTypes.array.isRequired,
    setGrid: PropTypes.func.isRequired,
    setPromotion: PropTypes.func.isRequired,
}