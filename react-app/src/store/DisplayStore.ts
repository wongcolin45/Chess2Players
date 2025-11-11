import { create } from 'zustand'


export interface DisplayState {
    role: string;
    setRole: (role: string) => void;
    flipped: boolean;
    flipBoard: () => void;
}

const useDisplayStore = create<DisplayState>((set) => ({
    flipped: false,
    flipBoard: (): void =>
        set((state) => ({ flipped: !state.flipped })),
    role: 'WHITE',
    setRole: (role) => set({role})
}));


export {
    useDisplayStore,
}