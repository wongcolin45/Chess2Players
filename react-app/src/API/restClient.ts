import axios from 'axios';
import {useGameStateStore} from "../store/ChessGameStore.ts";
import type {RoleAssignmentDTO} from "../dto.ts";

// export const BASE_URL = 'http://localhost:8080';

export const BASE_URL = 'https://chessapi-2jxt.onrender.com';

async function createGame(): Promise<RoleAssignmentDTO> {
    try {
        const res = await axios.post(`${BASE_URL}/api/create-game`);
        console.log('Create Game got '+res.data)
        return res.data;
    } catch (err) {
        console.error(`Unable to createGame: ${err}`);
        throw err;
    }
}

async function joinGame(gameId: string): Promise<object> {
    try {
        const res = await axios.post(`${BASE_URL}/api/join-game/${gameId}`);
        console.log('Setting game id to '+gameId)
        useGameStateStore.getState().setGameId(gameId);
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