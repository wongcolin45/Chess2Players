import axios from 'axios';
import {useGameStateStore} from "../store/ChessGameStore.ts";
import type {RoleAssignmentDTO} from "../dto.ts";
import {setRoleId} from "../store/playerIdStore.ts";

export const BASE_URL =
    import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

async function createGame(): Promise<string> {
    try {
        const res = await axios.post(`${BASE_URL}/api/create-game`);
        return res.data;
    } catch (err) {
        console.error(`Unable to createGame: ${err}`);
        throw err;
    }
}

async function joinGame(gameId: string, color?: string): Promise<RoleAssignmentDTO> {
    try {
        const params = color ? `?color=${color}` : '';
        const res = await axios.post(`${BASE_URL}/api/join-game/${gameId}${params}`);
        useGameStateStore.getState().setGameId(gameId);
        setRoleId(gameId, res.data.roleId);
        return res.data;
    } catch (err) {
        console.error(`Unable to join game: ${err}`);
        throw err;
    }
}

async function getGameRole(gameId: string, roleId: string): Promise<RoleAssignmentDTO> {
    try {
        const res = await axios.get(`${BASE_URL}/api/game/${gameId}/${roleId}`);
        return res.data;
    } catch (err) {
        console.error(`Unable to get game role: ${err}`);
        throw err;
    }
}


export {
    createGame,
    joinGame,
    getGameRole
}