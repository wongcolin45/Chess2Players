import Board from "./Board.jsx";
import SideBar from "./SideBar.jsx";
import {useEffect, useState} from "react";
import {isGameOver, getGameResult} from "./ChessAPI.js";


function App() {

    const [turn, setTurn] = useState('white');
    const [possibleMoves, setPossibleMoves] = useState([]);
    const [pov, setPov] = useState('white');
    const [gameOver, setGameOver] = useState(false);
    const [gameResult, setGameResult] = useState("In progress");



    useEffect(() => {
        isGameOver().then(result => {
            setGameOver(result);
        });
        getGameResult().then(result => {
            setGameResult(result);
        })
    },[turn])




    return (
        <div className='main'>
            <h1>{`Game Result: ${gameResult}`}</h1>
            <div className='app'>
                <Board setTurn={setTurn}
                       possibleMoves={possibleMoves}
                       setPossibleMoves={setPossibleMoves}
                       pov={pov}></Board>
                <SideBar turn={turn}
                         possibleMoves={possibleMoves}
                         setPov={setPov}
                         gameOver={gameOver}
                         setGameOver={setGameOver}
                         gameResult={gameResult}/>
            </div>
        </div>
    )
}

export default App
