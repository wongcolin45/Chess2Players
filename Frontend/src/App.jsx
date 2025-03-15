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
    const [promotion, setPromotion] = useState(false);

    // is game started:
    const [gameStarted, setGameStarted] = useState(false);

    const [grid, setGrid] = useState([]);



    useEffect(() => {
        async function start() {
            await startGame();
            const board = await getBoard(pov);
            setGrid(board);
        }
        start();
        setGameStarted(true);
    },[])


    useEffect(() => {
        isGameOver().then(result => {
            if (result) {
                getGameResult().then(result => {
                    setGameResult(result);
                })
            }
            setGameOver(result);
        });
        getBoard(pov).then(board => {
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
                       pov={pov}
                       setPromotion={setPromotion}></Board>
                <SideBar turn={turn}
                         setTurn={setTurn}
                         possibleMoves={possibleMoves}
                         pov={pov}
                         setPov={setPov}
                         gameOver={gameOver}
                         setGameOver={setGameOver}
                         grid={grid}
                         setGrid={setGrid}
                         gameResult={gameResult}
                         promotion={promotion}
                         setPromotion={setPromotion}></SideBar>

            </div>
        </div>
    )
}

export default App
