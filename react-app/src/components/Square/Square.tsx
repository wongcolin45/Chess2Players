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
        const isLastMove: boolean =  (lastMove != null) &&
            ((lastMove.from.row === row && lastMove.from.col == col) ||
             (lastMove.to.row === row && lastMove.to.col == col));

        const isSelectedPiece: boolean = selectedPiece !== null &&
            (selectedPiece.row == row && selectedPiece.col == col)

        if (isLastMove || isSelectedPiece) {
            return {
                position: 'relative',
                backgroundColor: '#EEE8AA'
            }
        }
        return {
            position: 'relative',
        }
    },[selectedPiece, lastMove])


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