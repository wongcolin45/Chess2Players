import "./polyfill";
import {type JSX, useEffect} from "react";
import Board from "./Board.tsx";
import {connectWebSocket} from "./API/webSocketClient.ts";
import {useGameStateStore} from "./store/ChessGameStore.ts";
import SelectGame from "./SelectGame.tsx";
import GameInfoPanel from "./GameInfoPanel.tsx";


const ChessGame = (): JSX.Element => {

    const gameId: string = useGameStateStore.getState().gameId; // thins doesnt rerender
    const role: string = useGameStateStore((s) => s.role);
    const lastMove = useGameStateStore((s => s.state.lastMove));
    console.log('last Move is ' + JSON.stringify(lastMove, null, 2));

    useEffect((): void => {
        if (gameId !== '' && role !== '') {
            connectWebSocket();
        }
    },[gameId, role]);

    const renderContents = () => {
        if (gameId === '' && role === '') {
            return <SelectGame/>;
        }
        return <Board/>
    }

    return (
        <div className='game-container'>
            {renderContents()}
            {(gameId !== '') && <GameInfoPanel/>}
        </div>
    )
}

export default ChessGame;

