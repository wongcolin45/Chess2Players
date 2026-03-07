import {type JSX} from "react";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import Square from "../Square/Square.tsx";
import {DndContext, type DragEndEvent} from "@dnd-kit/core";
import {getPossibleMoves, sendMove} from "../../API/webSocketClient.ts";
import {getPositionDTO, isOwnPiece} from "../../utils.ts";
import type {PositionDTO} from "../../dto.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import styles from './Board.module.css';


const Board = (): JSX.Element => {

    const handleDragStart = (event: DragEndEvent): void => {
        const id: string = String(event.active.id);
        const pos: PositionDTO = getPositionDTO(id);
        const pieceValue: string = useGameStateStore.getState().state.board[pos.row][pos.col];
        const role: string = useDisplayStore.getState().role;
        if (!isOwnPiece(pieceValue, role)) return;
        getPossibleMoves(id);
        useGameStateStore.getState().updateSelectedPiece(pos);
    };

    const handleDragEnd = (event: DragEndEvent): void => {
        useGameStateStore.getState().updatePossibleMoves([]);
        useGameStateStore.getState().clearSelectedPiece();
        const { active, over } = event;
        if (!over || !active) return;
        const fromDTO: PositionDTO = getPositionDTO(active.id.toString());
        const pieceValue: string = useGameStateStore.getState().state.board[fromDTO.row][fromDTO.col];
        const role: string = useDisplayStore.getState().role;
        if (!isOwnPiece(pieceValue, role)) return;
        const toDTO: PositionDTO = getPositionDTO(over.id.toString());
        sendMove(fromDTO, toDTO);
    };

    const board = useGameStateStore((s) => s.state.board);

    const flipped: boolean = useDisplayStore((s): boolean => s.flipped);

    const renderBoard = () => {
        const boardPov: string[][] = (flipped) ? board.slice().reverse().map(row => [...row].reverse()) : board;

        return  (
            <div className={styles.board}>
                {boardPov.flatMap((row: string[], rowIndex: number) =>
                    row.map((square: string, colIndex: number): JSX.Element => {
                        let actualRow: number = rowIndex;
                        let actualCol: number = colIndex;
                        if (flipped) {
                            actualRow = 7 - actualRow;
                            actualCol = 7 - actualCol;
                        }
                        const rankLabel = colIndex === 0 ? String(8 - actualRow) : undefined;
                        const fileLabel = rowIndex === 7 ? String.fromCharCode(97 + actualCol) : undefined;
                        return (
                            <Square
                                key={`${rowIndex}-${colIndex}`}
                                row={actualRow} col={actualCol}
                                value={square}
                                rankLabel={rankLabel}
                                fileLabel={fileLabel}
                            />
                        )
                    })
                )}
            </div>
        )
    }

    return (
        <DndContext onDragStart={handleDragStart} onDragEnd={handleDragEnd}>
            {renderBoard()}
        </DndContext>
    )
}

export default Board