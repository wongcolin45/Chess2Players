import {type JSX} from "react";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import Square from "../Square/Square.tsx";
import {DndContext, type DragEndEvent} from "@dnd-kit/core";
import {getPossibleMoves, sendMove} from "../../API/webSocketClient.ts";
import {getPositionDTO} from "../../utils.ts";
import type {PositionDTO} from "../../dto.ts";
import {useDisplayStore} from "../../store/DisplayStore.ts";
import styles from './Board.module.css';


const Board = (): JSX.Element => {

    const handleDragStart = (event: DragEndEvent): void => {
        const id: string = String(event.active.id);
        getPossibleMoves(id);
        const pos: PositionDTO = getPositionDTO(id);
        useGameStateStore.getState().updateSelectedPiece(pos);
    }

    const handleDragEnd = (event: DragEndEvent): void => {
        useGameStateStore.getState().updatePossibleMoves([]);
        useGameStateStore.getState().clearSelectedPiece();
        const { active, over } = event;
        if (!over || !active) return;
        const fromDTO: PositionDTO = getPositionDTO(active.id.toString());
        const toDTO: PositionDTO = getPositionDTO(over.id.toString());
        sendMove(fromDTO, toDTO);
    };

    const board = useGameStateStore((s) => s.state.board);

    const role: string = useGameStateStore((s): string => s.role);

    const flipped: boolean = useDisplayStore((s): boolean => s.flipped);

    const renderBoard = () => {
        const boardPov: string[][] = (flipped) ? board.slice().reverse().map(row => [...row].reverse()) : board;

        return  (
            <div className={styles.board}>
                {boardPov.flatMap((row: string[], rowIndex: number) =>
                    row.map((square: string, colIndex: number): JSX.Element => {
                        let actualRow: number = rowIndex;
                        let actualCol: number = colIndex;
                        if (role === 'BLACK') {
                            actualRow = 7 - actualRow;
                            actualCol = 7 - actualCol;
                        }
                        return (
                            <Square
                                key={`${rowIndex}-${colIndex}`}
                                row={actualRow} col={actualCol}
                                value={square}
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