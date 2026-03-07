import "../../polyfill.ts";
import {type JSX, useEffect, useState} from "react";
import Board from "../Board/Board.tsx";
import {connectWebSocket, disconnectWebSocket} from "../../API/webSocketClient.ts";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import GameInfoPanel from "../GameInfoPanel/GameInfoPanel.tsx";
import styles from './GamePage.module.css'
import {useParams} from "react-router-dom";
import {joinGame, getGameRole} from "../../API/restClient.ts";
import {getRoleId, clearRoleId} from "../../store/playerIdStore.ts";
import type {RoleAssignmentDTO} from "../../dto.ts";


const GamePage = (): JSX.Element => {

    const {gameId: urlGameId} = useParams<{gameId: string}>();
    const gameId: string = useGameStateStore(s => s.gameId);
    const setGameId = useGameStateStore(s => s.setGameId);
    const gameLoaded = useGameStateStore(s => s.gameLoaded);
    const setRole = useDisplayStore(s => s.setRole);
    const flipped = useDisplayStore(s => s.flipped);
    const flipBoard = useDisplayStore(s => s.flipBoard);

    const [joined, setJoined] = useState<boolean>(!!getRoleId(urlGameId ?? ''));
    const [roleLoading, setRoleLoading] = useState<boolean>(!!getRoleId(urlGameId ?? ''));
    const [joining, setJoining] = useState(false);
    const [joinError, setJoinError] = useState('');

    useEffect(() => {
        if (urlGameId && urlGameId !== gameId) {
            setGameId(urlGameId);
        }
    }, [urlGameId, gameId, setGameId]);

    // Restore role and board orientation for returning players
    useEffect(() => {
        if (!urlGameId || !joined) {
            setRoleLoading(false);
            return;
        }
        const storedRoleId = getRoleId(urlGameId);
        if (!storedRoleId) {
            setRoleLoading(false);
            return;
        }
        getGameRole(urlGameId, storedRoleId)
            .then((data) => {
                setRole(data.role);
                const shouldBeFlipped = data.role === 'BLACK';
                if (shouldBeFlipped !== useDisplayStore.getState().flipped) {
                    flipBoard();
                }
            })
            .catch(() => {
                clearRoleId(urlGameId);
                setJoined(false);
            })
            .finally(() => setRoleLoading(false));
    }, [urlGameId]);

    useEffect((): void => {
        if (!gameId || !joined || roleLoading) {
            return;
        }
        disconnectWebSocket();
        connectWebSocket();
    }, [gameId, joined, roleLoading]);

    if (!urlGameId) {
        return (
            <div className={styles.gameContainer}>
                <h3>Game Not Found</h3>
            </div>
        );
    }

    if (roleLoading) {
        return (
            <div className={styles.joinScreen}>
                <div className={styles.joinCard}>
                    <div className={styles.joinIcon}>♟</div>
                    <p className={styles.joinSubtitle}>Reconnecting…</p>
                </div>
            </div>
        );
    }

    if (joined && !gameLoaded) {
        return (
            <div className={styles.joinScreen}>
                <div className={styles.joinCard}>
                    <div className={styles.spinner}></div>
                    <p className={styles.joinSubtitle}>Connecting to game…</p>
                </div>
            </div>
        );
    }

    if (!joined) {
        const handleJoin = async () => {
            setJoining(true);
            setJoinError('');
            try {
                const data: RoleAssignmentDTO = await joinGame(urlGameId);
                setRole(data.role);
                if ((data.role === 'WHITE' && flipped) || (data.role === 'BLACK' && !flipped)) {
                    flipBoard();
                }
                setJoined(true);
            } catch {
                setJoinError('Failed to join. The game may not exist.');
                setJoining(false);
            }
        };

        return (
            <div className={styles.joinScreen}>
                <div className={styles.joinCard}>
                    <div className={styles.joinIcon}>♟</div>
                    <h2 className={styles.joinTitle}>You've been invited to play chess</h2>
                    <p className={styles.joinSubtitle}>You'll be assigned the available color.</p>
                    <button className={styles.joinButton} onClick={handleJoin} disabled={joining}>
                        {joining ? 'Joining…' : 'Join Game'}
                    </button>
                    {joinError && <p className={styles.joinError}>{joinError}</p>}
                </div>
            </div>
        );
    }

    return (
        <div className={styles.gamePage}>
            <div className={styles.gameContainer}>
                <Board/>
                <GameInfoPanel/>
            </div>
        </div>
    );
}

export default GamePage;

