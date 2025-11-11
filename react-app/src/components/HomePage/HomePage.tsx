import React, {useState} from "react";
import {createGame, joinGame} from "../../API/restClient.ts";
import type {RoleAssignmentDTO} from "../../dto.ts";
import styles from './HomePage.module.css';
import {type NavigateFunction, useNavigate} from "react-router-dom";
import { useDisplayStore } from "../../store/DisplayStore.ts";

const HomePage: React.FC = () => {

    const [createdId, setCreatedId] = useState('');
    const [input, setInput] = useState('');
    const navigate: NavigateFunction = useNavigate();

    const setRole: (role: string) => void = useDisplayStore(s => s.setRole);

    const flipped: boolean = useDisplayStore(s => s.flipped);

    const flipBoard: () => void = useDisplayStore(s => s.flipBoard);

    const handleCreateGameClick = async () => {
        try {
            const gameId: string = await createGame();
            setCreatedId(gameId);
            console.log('set created id to '+createdId);
        } catch (err) {
            console.error(`Error creating game: ${err}`);
        }
    }

    const handleJoinGameClick = async () => {
        try {
            const data: RoleAssignmentDTO = await joinGame(input);
            setRole(data.role);
            if ((data.role === 'WHITE' && flipped) || (data.role === 'BLACK' && !flipped)) {
                flipBoard();
            }
            navigate(`/game/${input.trim()}`);
        } catch (err) {
            console.error(`Error joining game: ${err}`);
        }
    }

    return (
        <div className={styles.selectGame}>
            <h1>2 Player Chess </h1>
            <input type="text" value={createdId} placeholder="Enter game name" />
            <button onClick={handleCreateGameClick}>Create Game</button>
            <input type="text"
                   value={input}
                   onChange={(e) => setInput(e.target.value)}
                   placeholder="Enter Game ID" />
            <button onClick={handleJoinGameClick}>Join Game</button>
        </div>
    );
};

export default HomePage;
