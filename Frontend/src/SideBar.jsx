
import PropTypes from "prop-types";
import {useEffect, useState} from "react";
import {getBoard, getTurn, kingInCheck, promotePiece} from "./ChessAPI.js";

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
        await promotePiece(type);
        const nextTurn = await getTurn();
        props.setTurn(nextTurn);
        props.setPromotion(false);

    }

    function renderPromotionSelector() {
        if (!props.promotion) {
            return <></>
        }
        const types = ['knight', 'bishop', 'rook', 'queen'];
        let pieces;
        const style = {};
        if (props.turn === 'white') {
            pieces = ['♞', '♝', '♜', '♛'];
        } else {
            pieces = ['♘', '♗', '♖', '♕'];
        }
        style.backgroundColor = '#D9EFFF';

        return (
            <>
                <h2>Promotion Available</h2>
                <div className={'promotion-selector'}>
                    {

                        pieces.map((piece, index) => {
                            return (
                                <button key={index}
                                        style={style}
                                        onClick={() => handlePromotionClick(types[index])}>
                                    <PieceImage key={piece + index} element={piece} stuff={style}/>
                                </button>
                            )
                        })
                    }
                </div>
            </>
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
    setTurn: PropTypes.func.isRequired,
    possibleMoves: PropTypes.array.isRequired,
    pov: PropTypes.string.isRequired,
    setPov: PropTypes.func.isRequired,
    gameOver: PropTypes.bool.isRequired,
    setGameOver: PropTypes.func.isRequired,
    grid: PropTypes.array.isRequired,
    setGrid: PropTypes.func.isRequired,
    promotion: PropTypes.bool.isRequired,
    setPromotion: PropTypes.func.isRequired,
}