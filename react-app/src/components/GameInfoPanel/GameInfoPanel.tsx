import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import React, {useState} from "react";
import styles from './GameInfoPanel.module.css';
import {useNavigate} from "react-router-dom";
import {clearRoleId} from "../../store/playerIdStore.ts";


const GameInfoPanel: React.FC = () => {

    const role = useDisplayStore(s => s.role);
    const navigate = useNavigate();
    const [copied, setCopied] = useState(false);

    const gameId = useGameStateStore(s => s.gameId);
    const turn = useGameStateStore(s => s.state.turn);
    const gameOver = useGameStateStore(s => s.state.gameOver);
    const gameResult = useGameStateStore(s => s.state.gameResult);
    const inCheck = useGameStateStore(s => s.state.kingInCheck);

    const handleNewGameClick = (): void => {
        clearRoleId(gameId);
        navigate('/');
    };

    const handleCopyGameId = () => {
        navigator.clipboard.writeText(`${window.location.origin}/game/${gameId}`);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
    };

    const getGameResultLabel = (): string => {
        switch (gameResult) {
            case 'WHITE_CHECKMATE': return 'Black wins by checkmate';
            case 'BLACK_CHECKMATE': return 'White wins by checkmate';
            case 'STALEMATE_FORCED': return 'Draw — Stalemate';
            default: return 'Game Over';
        }
    };

    const isMyTurn = role === turn;
    const turnColor = turn === 'WHITE' ? 'White' : 'Black';

    return (
        <div className={styles.gameInfoPanel}>
            <div className={styles.card}>
                <div className={styles.label}>Invite a friend</div>
                <div className={styles.gameIdRow}>
                    <span className={styles.gameIdText}>{`${window.location.origin}/game/${gameId}`}</span>
                    <button className={styles.copyButton} onClick={handleCopyGameId}>
                        {copied ? '✓' : 'Copy link'}
                    </button>
                </div>
            </div>

            <div className={styles.card}>
                <div className={styles.label}>Playing as</div>
                <div className={styles.roleDisplay}>
                    <span className={role === 'WHITE' ? styles.whitePiece : styles.blackPiece}>
                        {role === 'WHITE' ? '♔' : '♚'}
                    </span>
                    <span className={styles.roleName}>{role === 'WHITE' ? 'White' : 'Black'}</span>
                </div>
            </div>

            {gameOver ? (
                <div className={`${styles.card} ${styles.gameOverCard}`}>
                    <div className={styles.gameOverLabel}>Game Over</div>
                    <div className={styles.gameOverResult}>{getGameResultLabel()}</div>
                </div>
            ) : (
                <div className={`${styles.card} ${styles.turnCard} ${isMyTurn ? styles.myTurn : ''}`}>
                    <div className={styles.turnColor}>{turnColor} to move</div>
                    <div className={styles.turnLabel}>
                        {inCheck ? '⚠ Check!' : isMyTurn ? 'Your turn' : "Opponent's turn"}
                    </div>
                </div>
            )}

            <button className={styles.newGameButton} onClick={handleNewGameClick}>
                Start New Game
            </button>
        </div>
    );
};

export default GameInfoPanel;
