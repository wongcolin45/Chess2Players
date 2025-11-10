import React, {useState} from "react";
import {useGameStateStore} from "../../store/ChessGameStore.ts";
import {createGame, joinGame} from "../../API/restClient.ts";
import type {RoleAssignmentDTO} from "../../dto.ts";
import styles from './HomePage.module.css';
import {useNavigate} from "react-router-dom";

const HomePage: React.FC = () => {

    const [createdId, setCreatedId] = useState('');
    const [input, setInput] = useState('');
    const navigate = useNavigate();

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
            const {role, roleId} = data;
            useGameStateStore.getState().setRole(role);
            useGameStateStore.getState().setRoleId(roleId);
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
