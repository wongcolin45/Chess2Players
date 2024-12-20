import Board from "./Board.jsx";
import SideBar from "./SideBar.jsx";
import {useEffect, useState} from "react";
import {isGameOver, getGameResult, startGame, getBoard} from "./ChessAPI.js";


function App() {

    const [turn, setTurn] = useState('white');
    const [possibleMoves, setPossibleMoves] = useState([]);
    const [pov, setPov] = useState('white');
    const [gameOver, setGameOver] = useState(false);
    const [gameResult, setGameResult] = useState("In progress");

    const [grid, setGrid] = useState([]);

    useEffect(() => {
        console.log('pov changed to'+pov);
    })

    useEffect(() => {
        async function start() {
            await startGame();
            const board = await getBoard(pov);
            setGrid(board);
        }
        start();

    },[])


    useEffect(() => {
        isGameOver().then(result => {
            setGameOver(result);
        });
        getGameResult().then(result => {
            setGameResult(result);
        })

        getBoard(pov).then(board => {
            console.log('new board update');
            console.log(board);
            setGrid(board);
        })

    },[turn])




    return (
        <div className='main'>
            <h1>{`Game Result: ${gameResult}`}</h1>
            <div className='app'>
                <Board setTurn={setTurn}
                       possibleMoves={possibleMoves}
                       setPossibleMoves={setPossibleMoves}
                       grid={grid}
                       setGrid={setGrid}
                       pov={pov}></Board>
                <SideBar turn={turn}
                         setTurn={setTurn}
                         possibleMoves={possibleMoves}
                         pov={pov}
                         setPov={setPov}
                         gameOver={gameOver}
                         setGameOver={setGameOver}
                         grid={grid}
                         setGrid={setGrid}
                         gameResult={gameResult}/>

            </div>
        </div>
    )
}

export default App
