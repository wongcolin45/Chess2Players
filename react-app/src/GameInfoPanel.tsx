import {useGameStateStore} from "./store/ChessGameStore.ts";
import {useDisplayStore} from "./store/DisplayStore.ts";
import React from "react";

const tags: Map<string, string> = new Map([
    ['P', 'wp'],
    ['N', 'wn'],
    ['B', 'wb'],
    ['R', 'wr'],
    ['Q', 'wq'],
    ['K', 'wk'],
    ['p', 'bp'],
    ['n', 'bn'],
    ['b', 'bb'],
    ['r', 'br'],
    ['q', 'bq'],
    ['k', 'bk'],
]);

const GameInfoPanel: React.FC = () => {

    const state = useGameStateStore((s) => s.state);

    const {whiteCaptures, blackCaptures} = state.capturedPieces

    const handleFlipBoardClick = (): void => useDisplayStore.getState().flipBoard();

    const renderCapturedPieces = (capturedPieces: string[]) => {
        return (
            <>
                {capturedPieces.map((piece: string, index: number) => {
                    return (
                            <img
                            src={`https://assets-themes.chess.com/image/ejgfv/150/${tags.get(piece)}.png`}
                            alt="chess-piece"
                            key={index}
                            />
                    )
                })}
            </>
        )

    }

    return (
        <div className="game-info-panel">
            <div className="game-id">
                <strong>{'Game ID: '}</strong>{useGameStateStore.getState().gameId}
            </div>


            <div className="game-state">
                <div><strong>{'Turn: '}</strong>{(state.turn==='WHITE') ? '♔' : '♚'}</div>
                <div><strong>{'In Check: '}</strong>{(state.kingInCheck) ? ' 😱' : '😌'}</div>
                <div><strong>{'Result: '}</strong>{state.gameResult.toLowerCase().replace('_', ' ')}</div>
            </div>

            {/* Captured Pieces */}
            <div className="captured-pieces">
                <div className="captured-section">
                    <strong>{'White Captures: '}</strong>
                    <br></br>
                    {renderCapturedPieces(whiteCaptures)}
                </div>
                <div className="captured-section">
                    <strong>{'Black Captures: '}</strong>
                    <br></br>
                    {renderCapturedPieces(blackCaptures)}
                </div>
            </div>

            <button className="flip-button" onClick={handleFlipBoardClick}>Flip Board</button>
        </div>
    );
};

export default GameInfoPanel;
