import {type JSX, useEffect} from 'react';
import {useGameStateStore} from "./store/ChessGameStore.ts";
import {useDisplayStore} from "./store/DisplayStore.ts";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./components/HomePage/HomePage.tsx";
import GamePage from "./components/GamePage/GamePage.tsx";

function App(): JSX.Element {

    const role: string = useGameStateStore((s) => s.role);

    useEffect((): void => {
        if (role === 'BLACK') {
            useDisplayStore.getState().flipBoard();
        }
    }, [role]);

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/game/:gameId" element={<GamePage/>} />
                <Route path="/" element={<HomePage/>} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
