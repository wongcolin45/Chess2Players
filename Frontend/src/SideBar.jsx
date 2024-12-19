
import PropTypes from "prop-types";
import {useEffect, useState} from "react";
import {kingInCheck} from "./ChessAPI.js";

function SideBar(props) {

    const [whiteCheck, setWhiteCheck] = useState(false);
    const [blackCheck, setBlackCheck] = useState(false);


    function handleFlipClick() {
        props.setPov(prev => {
            if (prev === 'white') {
                return 'black';
            }
            return 'white';
        })
    }

    useEffect(() => {
        kingInCheck('white').then(result => {
            setWhiteCheck(result);
        });
        kingInCheck('black').then(result => {
            setBlackCheck(result);
        })
    },[props.turn])

    function renderChecks() {
        return (
            <>
                <h2>{`White in check: ${whiteCheck}`}</h2>
                <h2>{`Black in check: ${blackCheck}`}</h2>
            </>
        )
    }

    return (
        <div className="SideBar">
            <h2 style={{textAlign: 'center'}}>Dashboard</h2>
            <h2>{`Turn: ${props.turn} to move`}</h2>
            <button className={'flip-board'}
                    onClick={handleFlipClick}>Flip Board</button>
        </div>
    )
}

export default SideBar;

SideBar.propTypes = {
    turn: PropTypes.string.isRequired,
    possibleMoves: PropTypes.array.isRequired,
    setPov: PropTypes.func.isRequired,
    gameOver: PropTypes.bool.isRequired,
    setGameOver: PropTypes.func.isRequired,
}