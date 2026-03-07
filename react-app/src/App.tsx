import {type JSX} from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./components/HomePage/HomePage.tsx";
import GamePage from "./components/GamePage/GamePage.tsx";

function App(): JSX.Element {

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
