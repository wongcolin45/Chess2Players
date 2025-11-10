import "../../polyfill.ts";
import {type JSX, useEffect} from "react";
import Board from "../Board/Board.tsx";
import {connectWebSocket, disconnectWebSocket} from "../../API/webSocketClient.ts";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import GameInfoPanel from "../GameInfoPanel/GameInfoPanel.tsx";
import styles from './GamePage.module.css'
import {useParams} from "react-router-dom";




const GamePage = (): JSX.Element => {

    const {gameId: urlGameId} = useParams<{gameId: string}>();
    const gameId: string = useGameStateStore(s => s.gameId);
    const role: string = useGameStateStore((s) => s.role);
    const setGameId = useGameStateStore(s => s.setGameId);

    useEffect(() => {
        if (urlGameId && urlGameId !== gameId) {
            setGameId(urlGameId)
        }
    }, [urlGameId, gameId, setGameId])


    useEffect((): void => {
        console.log('game id changed');
        if (!gameId) {
            return;
        }
        disconnectWebSocket();
        connectWebSocket();
    },[gameId, role]);


    if (!urlGameId) {
        return (
            <div className={styles.gameContainer}>
                <h3>Game Not Found</h3>
            </div>
        )
    }

    return (
        <div className={styles.gamePage}>

            {/*<h1>Game not found</h1>*/}
            <div className={styles.gameContainer}>
                <Board/>
                <GameInfoPanel/>
            </div>
        </div>
    )
}

export default GamePage;

