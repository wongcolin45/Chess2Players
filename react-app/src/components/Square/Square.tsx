import {useMemo, type JSX, useState, useEffect, type CSSProperties} from "react";
import { useDroppable } from '@dnd-kit/core';
import Piece from "../Piece/Piece.tsx";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import type {PositionDTO} from "../../dto.ts";
import PromotionSelection from "../PromotionSelection/PromotionSelection.tsx";
import type {State} from "../../store/ChessGameStore.ts";
import styles from './Square.module.css';

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
        const isLastMoveFrom: boolean = (lastMove != null) && (lastMove.from.row === row && lastMove.from.col == col);
        const isLastMoveTo: boolean = (lastMove != null) && (lastMove.to.row === row && lastMove.to.col == col);
        const isSelectedPiece: boolean = selectedPiece !== null && (selectedPiece.row == row && selectedPiece.col == col)
        const isKingInCheck: boolean = inCheck && kingPosition.row == row && kingPosition.col == col;
        const style: CSSProperties = { position: 'relative' };

        if (isKingInCheck) {
            style.backgroundColor = '#FF6B6B';
        } else if (isLastMoveFrom || isSelectedPiece) {
            style.backgroundColor = '#EEE8AA';
        } else if (isLastMoveTo) {
            style.backgroundColor = '#E6D98A';
        }
        return style;
    },[selectedPiece, lastMove, kingPosition, inCheck])


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