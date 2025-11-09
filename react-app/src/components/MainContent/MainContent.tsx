import "../../polyfill.ts";
import {type JSX, useEffect} from "react";
import Board from "../Board/Board.tsx";
import {connectWebSocket} from "../../API/webSocketClient.ts";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import HomePage from "../HomePage/HomePage.tsx";
import GameInfoPanel from "../GameInfoPanel/GameInfoPanel.tsx";
import styles from './MainContent.module.css'

const MainContent = (): JSX.Element => {

    const gameId: string = useGameStateStore.getState().gameId; // thins doesnt rerender
    const role: string = useGameStateStore((s) => s.role);

    useEffect((): void => {
        if (gameId !== '' && role !== '') {
            connectWebSocket();
        }
    },[gameId, role]);

    const renderContents = () => {
        if (gameId === '' && role === '') {
            return <HomePage/>;
        }
        return <Board/>
    }

    return (
        <div className={styles.gameContainer}>
            {renderContents()}
            {(gameId !== '') && <GameInfoPanel/>}
        </div>
    )
}

export default MainContent;

