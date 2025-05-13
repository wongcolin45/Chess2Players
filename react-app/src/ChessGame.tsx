
import "./polyfill"; // ✅ Must be before sockjs-client is ever imported

import {type JSX, useEffect} from "react";
import Board from "./Board.tsx";
import {connectWebSocket} from "./API/webSocketClient.ts";
import {useGameStateStore} from "./store/ChessGameStore.ts";
import SelectGame from "./SelectGame.tsx";



const ChessGame = (): JSX.Element => {

    const gameId: string = useGameStateStore.getState().gameId; // thins doesnt rerender
    const role: string = useGameStateStore((s) => s.role);

    useEffect((): void => {
        if (gameId !== '' && role !== '') {
            connectWebSocket();
        }
    },[gameId, role]);

    const renderContents = () => {
        console.log(`gameId ${gameId}, role ${role}`);
        if (gameId === '' && role === '') {
            return <SelectGame/>;
        }
        return <Board/>
    }

    return (
        <div className='game'>
            {renderContents()}
        </div>


    )
}

export default ChessGame;

