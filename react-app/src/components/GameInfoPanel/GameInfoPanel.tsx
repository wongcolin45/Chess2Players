import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import React from "react";
import styles from './GameInfoPanel.module.css';
import {useNavigate} from "react-router-dom";
import {clearPlayerId} from "../../store/playerIdStore.ts";


const GameInfoPanel: React.FC = () => {

    // const state = useGameStateStore((s) => s.state);

    // const {whiteCaptures, blackCaptures} = state.capturedPieces

    const navigate = useNavigate();

    const handleFlipBoardClick = (): void => useDisplayStore.getState().flipBoard();

    const handleNewGameClick = (): void => {
        clearPlayerId();
        navigate('/');
    }

    return (
        <div className={styles.gameInfoPanel}>
            <div className={styles.gameId}>
                <strong>{'Game ID: '}</strong>{useGameStateStore.getState().gameId}
            </div>
            <button className={styles.flipButton} onClick={handleFlipBoardClick}>Flip Board</button>
            <button className={styles.newGameButton} onClick={handleNewGameClick}>Start New Game</button>
        </div>
    );
};

export default GameInfoPanel;
