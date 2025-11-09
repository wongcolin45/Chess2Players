import React, {type CSSProperties, useMemo} from "react";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {promotePawn} from "../../API/webSocketClient.ts";
import {type DisplayState, useDisplayStore} from "../../store/DisplayStore.ts";
import styles from './PromotionSelection.module.css';

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

const whitePromotions: string[] = ['N', 'B', 'R', 'Q'];
const blackPromotions: string[] = ['n', 'b', 'r', 'q'];

const PromotionSelection: React.FC = () => {

    const role: string = useGameStateStore.getState().role;

    const pieces: string[] = (role == 'WHITE') ? whitePromotions : blackPromotions;

    const handleClick = (selection: string) => {
        promotePawn(selection);
    }

    const pieceSelection = (value: string, index: number) => {
        return (
            <img
                src={`https://assets-themes.chess.com/image/ejgfv/150/${tags.get(value)}.png`}
                alt="chess-piece"
                className={styles.pieceSelection}
                key={index}
                onClick={() => handleClick(value)}
            />
        );
    }

    const flipped: boolean = useDisplayStore((s: DisplayState): boolean => s.flipped);

    const style: CSSProperties = useMemo(() => {
        if (flipped && role === 'BLACK') {
            return {bottom: '100%'}
        }
        return {top: '100%'}
    }, [flipped, role]);

    return (
        <div className={styles.pieceSelectionContainer} style={style}>
            {pieces.map((piece: string, index: number) => {
                return <>{pieceSelection(piece, index)}</>;
            })}
        </div>
    )
}

export default PromotionSelection;