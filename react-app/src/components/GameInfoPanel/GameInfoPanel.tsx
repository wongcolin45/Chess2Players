import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import React from "react";
import styles from './GameInfoPanel.module.css';
import {useNavigate} from "react-router-dom";
import {clearRoleId} from "../../store/playerIdStore.ts";


const GameInfoPanel: React.FC = () => {

    const role = useDisplayStore(s => s.role);

    const flipBoard = useDisplayStore(s => s.flipBoard);

    const navigate = useNavigate();

    const handleNewGameClick = (): void => {
        clearRoleId();
        navigate('/');
        flipBoard();
    }

    return (
        <div className={styles.gameInfoPanel}>
            <div className={styles.gameId}>
                <strong>{'Game ID: '}</strong>{useGameStateStore.getState().gameId}
            </div>
            <div className={styles.gameId}>
                <strong>{`Role: ${role}`}</strong>{}
            </div>
            {/*<button className={styles.flipButton} onClick={handleFlipBoardClick}>Flip Board</button>*/}
            <button className={styles.newGameButton} onClick={handleNewGameClick}>Start New Game</button>
        </div>
    );
};

export default GameInfoPanel;
