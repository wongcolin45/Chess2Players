
import {type JSX, useEffect} from 'react';


import MainContent from "./components/MainContent/MainContent.tsx";
import {useGameStateStore} from "./store/ChessGameStore.ts";
import {useDisplayStore} from "./store/DisplayStore.ts";







function App(): JSX.Element {

    const role: string = useGameStateStore((s) => s.role);

    useEffect((): void => {
        if (role === 'BLACK') {
            useDisplayStore.getState().flipBoard();
        }
    }, [role]);

    return (
        <div className="App">
            <MainContent />
        </div>
    );
}

export default App;
