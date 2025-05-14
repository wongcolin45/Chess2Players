import {type JSX} from "react";
import {useGameStateStore} from "./store/ChessGameStore.ts";
import Square from "./Square.tsx";
import {DndContext, type DragEndEvent} from "@dnd-kit/core";
import {getPossibleMoves, sendMove} from "./API/webSocketClient.ts";
import {getPositionDTO} from "./utils.ts";
import type {PositionDTO} from "./dto.ts";
import {useDisplayStore} from "./store/DisplayStore.ts";




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

    // const activeId= useMemo(() => {
    //     if (selectedPiece === null) {
    //         return null;
    //     }
    //     return `${selectedPiece.row},${selectedPiece.col}`;
    // },[selectedPiece]);

    const {board} = useGameStateStore((s) => s.state);

    const role: string = useGameStateStore((s): string => s.role);

    const flipped: boolean = useDisplayStore((s): boolean => s.flipped);

    const renderBoard = () => {
        const boardPov: string[][] = (flipped) ? board.slice().reverse().map(row => [...row].reverse()) : board;

        return (
            <div className="board">
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
            {/*<DragOverlay>*/}
            {/*    {activeId ? (*/}
            {/*        <Piece*/}
            {/*            id={activeId}*/}
            {/*            value={state.board[getPositionDTO(activeId).row][getPositionDTO(activeId).col]}*/}
            {/*            selected={true}*/}
            {/*        />*/}
            {/*    ) : null}*/}
            {/*</DragOverlay>*/}
        </DndContext>
    )
}

export default Board