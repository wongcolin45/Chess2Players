import axios from 'axios';
import {useGameStateStore} from "../store/ChessGameStore.ts";
import type {RoleAssignmentDTO} from "../dto.ts";
import {setPlayerId} from "../store/playerIdStore.ts";

export const BASE_URL =
    import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

async function createGame(): Promise<string> {
    try {
        const res = await axios.post(`${BASE_URL}/api/create-game`);
        console.log('Create Game got '+res.data);
        setPlayerId(res.data.roleId);
        return res.data;
    } catch (err) {
        console.error(`Unable to createGame: ${err}`);
        throw err;
    }
}

async function joinGame(gameId: string): Promise<RoleAssignmentDTO> {
    try {
        const res = await axios.post(`${BASE_URL}/api/join-game/${gameId}`);
        console.log('Setting game id to '+gameId)
        useGameStateStore.getState().setGameId(gameId);
        setPlayerId(res.data.roleId);
        return res.data;
    } catch (err) {
        console.error(`Unable to createGame: ${err}`);
        throw err;
    }
}


export {
    createGame,
    joinGame,
}