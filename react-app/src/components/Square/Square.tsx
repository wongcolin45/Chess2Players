import {useMemo, type JSX, useState, useEffect, type CSSProperties} from "react";
import { useDroppable } from '@dnd-kit/core';
import Piece from "../Piece/Piece.tsx";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import type {PositionDTO} from "../../dto.ts";
import PromotionSelection from "../PromotionSelection/PromotionSelection.tsx";
import type {State} from "../../store/ChessGameStore.ts";
import styles from './Square.module.css';
import {useDisplayStore} from "../../store/DisplayStore.ts";
import {isSamePosition} from "../../utils.ts";

interface SquareProps {
    row: number;
    col: number;
    value: string;
}


const Square = ({row, col, value}: SquareProps): JSX.Element => {

    const [style, setStyle] = useState<CSSProperties | undefined>(undefined);

    const possibleMoves = useGameStateStore(s => s.possibleMoves);

    const selectedPiece = useGameStateStore(s => s.selectedPiece);

    const lastMove = useGameStateStore(s => s.state.lastMove);

    const inCheck = useGameStateStore(s => s.state.kingInCheck);

    const kingPosition = useGameStateStore(s => s.state.kingPosition);

    const flipped = useDisplayStore(s=>s.flipped);

    const getStyle = () => {
        const isPossibleMove: boolean = possibleMoves.some((move: PositionDTO): boolean => {
            return row === move.row && col === move.col;
        });
        if (!isPossibleMove) {
            return undefined;
        }
        if (value === ' ') {
            return {
                width: '30%',
                height: '30%',
                backgroundColor: 'gray',
                borderRadius: '50%',
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                opacity: '40%'
            } as CSSProperties;
        }
        return {
            width: '90%',
            height: '90%',
            border: '5px solid gray',
            borderRadius: '50%',
            backgroundColor: 'transparent',
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            opacity: '40%'
        } as CSSProperties;
    }

    useEffect((): void => {
        const newStyles: CSSProperties | undefined = getStyle();
        setStyle(newStyles);
    },[possibleMoves])

    // could pull out is over
    const { setNodeRef } = useDroppable({
        id: `${row},${col}`
    });

    const className: string = useMemo(() => {
        if ((row+col) % 2 != 0) {
            return styles.coloredSquare
        }
        return styles.blankSquare
    },[row,col])

    const getSquareStyle: CSSProperties = useMemo(() => {
        let actualRow = row
        let actualCol = col;
        if (flipped) {
            actualRow = 7 - row;
            actualCol = 7-col;
        }
        const actualPosition = {
            row: actualRow, col: actualCol
        }
        const isLastMoveFrom: boolean = (lastMove != null) && isSamePosition(lastMove.from, actualPosition);
        const isLastMoveTo: boolean = (lastMove != null) && isSamePosition(lastMove.to, actualPosition);
        const isSelectedPiece: boolean = selectedPiece !== null && (selectedPiece.row == row && selectedPiece.col == col);
        const isKingInCheck: boolean = inCheck && kingPosition.row == actualRow && kingPosition.col == actualCol;
        const style: CSSProperties = { position: 'relative' };

        if (isKingInCheck) {
            style.backgroundColor = '#FF6B6B';
        } else if (isLastMoveFrom || isSelectedPiece) {
            style.backgroundColor = '#EEE8AA';
        } else if (isLastMoveTo) {
            style.backgroundColor = '#E6D98A';
        }
        return style;
    },[selectedPiece, lastMove, kingPosition, inCheck, flipped, row, col])




    const renderContents = () => {
        const state: State = useGameStateStore.getState().state;
        const {pawnToPromote} = state;
        const isTurn: boolean = useGameStateStore.getState().role === state.turn;
        if (pawnToPromote.row === row && pawnToPromote.col === col && isTurn) {
            return (
                <>
                    <PromotionSelection/>
                    {(value !== ' ') && <Piece value={value} id={`${row},${col}`} selected={true} />}
                </>

            )
        }
        return (
            <>
                {style && <div style={style}></div>}
                {(value !== ' ') && <Piece value={value} id={`${row},${col}`} selected={true} />}
            </>
        )
    }

    return (
        <div ref={setNodeRef} className={className}  style={getSquareStyle}>
            {renderContents()}
        </div>
    )
}

export default Square