
import { create } from 'zustand'
import type {PositionDTO} from "../dto.ts";




export interface State {
    turn: string;
    kingInCheck: boolean;
    gameOver: boolean;
    gameResult: string;
    board: string[][];
}

export interface GameState {
    gameId: string;
    setGameId: (id: string) => void;
    roleId: string;
    setRoleId: (id: string) => void;
    role: string;
    setRole: (role: string) => void;
    state: State;
    updateState: (state: State) => void;
    possibleMoves: PositionDTO[];
    updatePossibleMoves: (newMoves: PositionDTO[]) => void;
    selectedPiece: PositionDTO | null;
    clearSelectedPiece: () => void;
    updateSelectedPiece: (pos: PositionDTO) => void;
}

const useGameStateStore = create<GameState>((set) => ({
    gameId: '',
    setGameId: (id: string): void => set({ gameId: id}),
    roleId: '',
    setRoleId: (id: string): void => set({ roleId: id }),
    role: '',
    setRole: (role: string): void => set({ role: role }),
    state: {
        turn: 'white',
        kingInCheck: false,
        gameOver: false,
        gameResult: '',
        board: Array(8).fill(Array(8).fill(" ")),
    },
    updateState: (newState: State): void => set({ state: newState }),
    possibleMoves: [],
    updatePossibleMoves: (newMoves: PositionDTO[]): void => set({ possibleMoves: newMoves }),
    selectedPiece: null,
    clearSelectedPiece: (): void => set({ selectedPiece: null }),
    updateSelectedPiece: (pos: PositionDTO): void => set({ selectedPiece: pos })
}));



export {
    useGameStateStore,
}
