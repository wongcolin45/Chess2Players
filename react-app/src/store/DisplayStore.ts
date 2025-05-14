import { create } from 'zustand'


export interface DisplayState {
    flipped: boolean;
    flipBoard: () => void;
}

const useDisplayStore = create<DisplayState>((set) => ({
    flipped: false,
    flipBoard: (): void =>
        set((state) => ({ flipped: !state.flipped }))
}));


export {
    useDisplayStore,
}