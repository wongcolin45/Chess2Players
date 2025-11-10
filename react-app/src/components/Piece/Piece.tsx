import { useDraggable } from '@dnd-kit/core';
import React, {useMemo} from "react";
import styles from './Piece.module.css';

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

interface PieceProps {
    value: string;
    id: string;
    selected: boolean;
}

const Piece = ({value, id, selected}: PieceProps) => {

    const { attributes, listeners, setNodeRef, transform } = useDraggable({id: id});

    const style: React.CSSProperties = useMemo(() => {
        const defaultStyle: React.CSSProperties = {
            transform: transform
                ? `translate3d(${transform.x}px, ${transform.y}px, 0)`
                : undefined,
            touchAction: 'none', // for mobile compatibility
            position: 'relative',
            backgroundColor: 'transparent',
            zIndex: 100
        };
        if (!selected) {
            return defaultStyle;
        }
        return {...defaultStyle, zIndex: 200};
    },[transform, selected])

    const link: string = (`https://assets-themes.chess.com/image/ejgfv/150/${tags.get(value)}.png`)



    return (
        <img
            ref={setNodeRef}
            src={link}
            alt="chess-piece"
            className={styles.piece}
            style={style}
            {...listeners}
            {...attributes}
        />
    );
}

export default Piece;

