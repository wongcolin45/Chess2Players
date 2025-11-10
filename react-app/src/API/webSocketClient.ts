// websocketClient.ts
import SockJS from "sockjs-client";
import { Client, type IFrame} from "@stomp/stompjs";
import { useGameStateStore } from "../store/ChessGameStore.ts";
import {getPositionDTO} from "../utils.ts";
import type {PieceSelectionDTO, PositionDTO} from "../dto.ts";
import {BASE_URL} from "./restClient.ts";
import {getPlayerId} from "../store/playerIdStore.ts";

let client: Client | null = null;

export function connectWebSocket(): void {
    if (client?.active) return; // Prevent multiple connections

    const socket = new SockJS(`${BASE_URL}/ws`);

    client = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        onConnect: (): void => {
            if ( useGameStateStore.getState().gameId === '') {
                console.log('Game id not set cannot connect to websocket')
                return;
            }
            console.log('Connection to websocket success!');
            // use game id in paths
            const gameId: string = useGameStateStore.getState().gameId;
            client?.publish({
                destination: `/app/request-state/${gameId}`,
                body: "{}"
            });
            // subscribe to getting game state
            client?.subscribe(`/topic/gameState/${gameId}`, (msg): void => {
                const data = JSON.parse(msg.body);
                useGameStateStore.getState().updateState(data);
            });
            const roleId: string = useGameStateStore.getState().roleId;
            // subscribe to getting possible moves
            client?.subscribe(`/topic/possible-moves/${gameId}/${roleId}`, (msg): void => {
                const data = JSON.parse(msg.body);
                useGameStateStore.getState().updatePossibleMoves(data.possibleMoves);
            })
        },
        onStompError: (frame: IFrame): void => {
            console.error("STOMP error", frame);
        },
    });
    client.activate();
}

export function sendMove(fromDTO: PositionDTO, toDTO: PositionDTO): void {
    if (!client?.connected) {
        console.warn("WebSocket not connected yet.");
        return;
    }

    const moveDTO = {
        from: fromDTO,
        to: toDTO,
    };

    const gameId: string = useGameStateStore.getState().gameId;

    client.publish({
        destination: `/app/move-piece/${gameId}/${getPlayerId()}`,
        body: JSON.stringify(moveDTO),
    });
}

export function getPossibleMoves(id: string): void {
    if (!client?.connected) {
        console.warn("WebSocket not connected yet.");
        return;
    }
    const PositionDTO: PositionDTO = getPositionDTO(id);
    const gameId: string = useGameStateStore.getState().gameId;
    const roleId: string = useGameStateStore.getState().roleId;
    client.publish({
        destination: `/app/possible-moves/${gameId}/${roleId}`,
        body: JSON.stringify(PositionDTO),
    })
}

export function promotePawn(piece: string): void {
    if (!client?.connected) {
        console.warn("WebSocket not connected yet.");
        return;
    }
    const pieceSelection: PieceSelectionDTO = {piece: piece}
    const gameId: string = useGameStateStore.getState().gameId;
    // const roleId: string = useGameStateStore.getState().roleId;
    client.publish({
        destination: `/app/promote-pawn/${gameId}`,
        body: JSON.stringify(pieceSelection),
    })
}
