import whitePawn from './pieces/white-pawn.png';
import whiteKnight from './pieces/white-knight.png';
import whiteBishop from './pieces/white-bishop.png';
import whiteRook from './pieces/white-rook.png';
import whiteQueen from './pieces/white-queen.png';
import whiteKing from './pieces/white-king.png';
import blackPawn from './pieces/black-pawn.png';
import blackKnight from './pieces/black-knight.png';
import blackBishop from './pieces/black-bishop.png';
import blackRook from './pieces/black-rook.png';
import blackQueen from './pieces/black-queen.png';
import blackKing from './pieces/black-king.png';
import PropTypes from "prop-types";
const pieceDictionary = {
    '♟' : whitePawn,
    '♞' : whiteKnight,
    '♝' : whiteBishop,
    '♜' : whiteRook,
    '♛' : whiteQueen,
    '♚' : whiteKing,
    '♙' : blackPawn,
    '♘' : blackKnight,
    '♗' : blackBishop,
    '♖' : blackRook,
    '♕' : blackQueen,
    '♔' : blackKing
}

function PieceImage(props) {

    return <img src={pieceDictionary[props.element]} style={props.stuff}></img>
}


export default PieceImage;

PieceImage.propTypes = {
    element: PropTypes.string,
    stuff: PropTypes.object,
}