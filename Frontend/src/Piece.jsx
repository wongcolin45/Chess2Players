import { memo } from "react";
import PropTypes from "prop-types";

const tags = {
    '♟' : 'wp',
    '♞' :'wn',
    '♝' :'wb',
    '♜' : 'wr',
    '♛' : 'wq',
    '♚' : 'wk',
    '♙' : 'bp',
    '♘' : 'bn',
    '♗' : 'bb',
    '♖' : 'br',
    '♕' : 'bq',
    '♔' : 'bk'
}

const Piece = memo(function Piece(props) {
    if (props.element === ' ') {
        return <></>
    }
    const link = (`https://assets-themes.chess.com/image/ejgfv/150/${tags[props.element]}.png`)
    return <img src={link}
                style={props.stuff}
                width={90}
                height={90}
    ></img>
});

export default Piece;

Piece.propTypes = {
    element: PropTypes.string,
    stuff: PropTypes.object,
}