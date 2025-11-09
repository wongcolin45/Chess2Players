import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import React from "react";
import styles from './GameInfoPanel.module.css';


const GameInfoPanel: React.FC = () => {

    const state = useGameStateStore((s) => s.state);

    // const {whiteCaptures, blackCaptures} = state.capturedPieces

    const handleFlipBoardClick = (): void => useDisplayStore.getState().flipBoard();

    return (
        <div className={styles.gameInfoPanel}>

            <div className={styles.gameId}>
                <strong>{'Game ID: '}</strong>{useGameStateStore.getState().gameId}
            </div>

            <div className={styles.gameState}>
                <div><strong>{'In Check: '}</strong>{(state.kingInCheck) ? ' 😱' : '😌'}</div>
                <div><strong>{'Result: '}</strong>{state.gameResult.toLowerCase().replace('_', ' ')}</div>
            </div>

            <button className="flip-button" onClick={handleFlipBoardClick}>Flip Board</button>
        </div>
    );
};

export default GameInfoPanel;
