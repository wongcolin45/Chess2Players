
import PropTypes from "prop-types";
import {useEffect, useState} from "react";
import {getBoard, kingInCheck, promotePiece} from "./ChessAPI.js";

import PieceImage from "./PieceImage.jsx";

function SideBar(props) {



    function handleFlipClick() {
        props.setPov(prev => {
            if (prev === 'white') {
                return 'black';
            }
            return 'white';
        })
    }



    async function handlePromotionClick(type) {
        const result = await promotePiece(type);
        if (result) {
            const newGrid = await getBoard(props.pov);
            props.setGrid(newGrid);
        }

    }

    function renderPromotionSelector() {
        const types = ['knight', 'bishop', 'rook', 'queen'];
        let pieces;
        const style = {};
        if (props.turn === 'black') {
            pieces = ['♞', '♝', '♜', '♛'];
            style.backgroundColor = 'black';
        } else {
            pieces = ['♘', '♗', '♖', '♕'];
            style.backgroundColor = 'white';
        }
        style.backgroundColor = '#D9EFFF';
        //const pieces = (props.turn == 'white') ? ['♞', '♝', '♜', '♛'] : ['♘', '♗', '♖', '♕'];
        return (
            <div className={'promotion-selector'}>
                {
                    pieces.map((piece, index) => {
                        return (
                            <button key={index}
                                    style={style}
                                    onClick={() => handlePromotionClick(types[index])}>
                                <PieceImage key={piece+index} element={piece} stuff={style}/>
                            </button>
                        )
                    })
                }
            </div>
        )
    }

    return (
        <div className="SideBar">
            <h2 style={{textAlign: 'center'}}>Dashboard</h2>
            <h2>{`Turn: ${props.turn} to move`}</h2>
            {renderPromotionSelector()}
            <button className={'flip-board'}
                    onClick={handleFlipClick}>Flip Board</button>

        </div>
    )
}

export default SideBar;

SideBar.propTypes = {
    turn: PropTypes.string.isRequired,
    possibleMoves: PropTypes.array.isRequired,
    pov: PropTypes.string.isRequired,
    setPov: PropTypes.func.isRequired,
    gameOver: PropTypes.bool.isRequired,
    setGameOver: PropTypes.func.isRequired,
    grid: PropTypes.array.isRequired,
    setGrid: PropTypes.func.isRequired,
}