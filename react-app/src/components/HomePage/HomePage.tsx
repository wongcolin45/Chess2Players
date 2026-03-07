import React, {useState} from "react";
import {createGame, joinGame} from "../../API/restClient.ts";
import type {RoleAssignmentDTO} from "../../dto.ts";
import styles from './HomePage.module.css';
import {type NavigateFunction, useNavigate} from "react-router-dom";
import { useDisplayStore } from "../../store/DisplayStore.ts";

const HomePage: React.FC = () => {

    const [input, setInput] = useState('');
    const [createdGameId, setCreatedGameId] = useState('');
    const [copied, setCopied] = useState(false);
    const [error, setError] = useState('');
    const [selectedColor, setSelectedColor] = useState<'WHITE' | 'BLACK' | null>(null);
    const [loading, setLoading] = useState(false);

    const navigate: NavigateFunction = useNavigate();
    const setRole = useDisplayStore(s => s.setRole);
    const flipped = useDisplayStore(s => s.flipped);
    const flipBoard = useDisplayStore(s => s.flipBoard);

    const doJoin = async (gameId: string, color?: string) => {
        const data: RoleAssignmentDTO = await joinGame(gameId, color);
        setRole(data.role);
        if ((data.role === 'WHITE' && flipped) || (data.role === 'BLACK' && !flipped)) {
            flipBoard();
        }
        navigate(`/game/${gameId.trim()}`);
    };

    const handleCreateGameClick = async () => {
        try {
            setError('');
            if (!selectedColor) {
                setError('Please choose a color before creating a game.');
                return;
            }
            setLoading(true);
            const gameId: string = await createGame();
            setCreatedGameId(gameId);
            await doJoin(gameId, selectedColor);
        } catch (err) {
            setError('Failed to create game. Is the server running?');
            setLoading(false);
        }
    };

    const extractGameId = (value: string): string => {
        try {
            const url = new URL(value.trim());
            const parts = url.pathname.split('/');
            const idx = parts.indexOf('game');
            if (idx !== -1 && parts[idx + 1]) return parts[idx + 1];
        } catch {
            // not a URL — treat as raw game ID
        }
        return value.trim();
    };

    const handleJoinGameClick = async () => {
        try {
            setError('');
            if (!input.trim()) {
                setError('Please enter a game link or ID.');
                return;
            }
            setLoading(true);
            await doJoin(extractGameId(input));
        } catch (err) {
            setError('Failed to join game. Check the link or ID and try again.');
            setLoading(false);
        }
    };

    const handleCopy = () => {
        navigator.clipboard.writeText(`${window.location.origin}/game/${createdGameId}`);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
    };

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>♟ Chess</h1>
            <p className={styles.subtitle}>2-player online chess</p>

            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>Create a New Game</h2>
                <p className={styles.cardHint}>Choose your color, then share the invite link with your opponent.</p>
                <div className={styles.colorPicker}>
                    <button
                        className={`${styles.colorOption} ${styles.whiteOption} ${selectedColor === 'WHITE' ? styles.colorSelected : ''}`}
                        onClick={() => setSelectedColor('WHITE')}
                    >
                        <span className={styles.colorPiece}>♔</span>
                        <span>White</span>
                    </button>
                    <button
                        className={`${styles.colorOption} ${styles.blackOption} ${selectedColor === 'BLACK' ? styles.colorSelected : ''}`}
                        onClick={() => setSelectedColor('BLACK')}
                    >
                        <span className={styles.colorPiece}>♚</span>
                        <span>Black</span>
                    </button>
                </div>
                {createdGameId && (
                    <div className={styles.gameIdDisplay}>
                        <span className={styles.gameIdText}>{`${window.location.origin}/game/${createdGameId}`}</span>
                        <button className={styles.copyButton} onClick={handleCopy}>
                            {copied ? '✓ Copied' : 'Copy link'}
                        </button>
                    </div>
                )}
                <button className={styles.primaryButton} onClick={handleCreateGameClick} disabled={loading}>
                    {loading ? 'Creating…' : 'Create Game'}
                </button>
            </div>

            <div className={styles.divider}>or</div>

            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>Join a Game</h2>
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleJoinGameClick()}
                    placeholder="Paste invite link or game ID"
                />
                <button className={styles.secondaryButton} onClick={handleJoinGameClick} disabled={loading}>
                    {loading ? 'Joining…' : 'Join Game'}
                </button>
            </div>

            {error && <div className={styles.error}>{error}</div>}
        </div>
    );
};

export default HomePage;
