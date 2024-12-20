
const base = 'http://localhost:8080/';

async function startGame() {
    try {
        const url = base + 'start';
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
           throw new Error(response.statusText);
        }
        return await response.json();
    } catch (e) {
        console.error(e);
    }
}

async function selectPiece(notation) {
    try {
        const url = base + 'select/' + notation;
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.json();
        return data;
        // eslint-disable-next-line no-unused-vars
    } catch (e) {
        return []
    }
}

async function request(route, method) {
    try {
        const url = base + route;
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.json();
        return data;
    } catch (e) {
        console.error(e);
    }
}

async function movePiece(notation) {
    try {
        const url = base + 'move/' + notation;
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return true;
    } catch (e) {
        return false
    }
}

async function getBoard(color) {
    try {
        const url = base + 'board/'+color;
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.json();
        // console.log('get board called ');
        // console.log(data);
        return data;
    } catch (e) {
        console.error(e);
    }
}

async function kingInCheck(color) {
    try {
        const url = base + 'king/'+color;
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.json();
        return data;
    } catch (e) {
        console.error(e);
    }
}

async function getTurn() {
    try {
        const url = base + 'turn';
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.text();
        return data;
    } catch (e) {
        console.error(e);
    }
}

async function isGameOver() {
    try {
        const url = base + 'gameOver';
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.json();
        return data;
    } catch (e) {
        console.error(e);
    }
}

async function getGameResult() {
    try {
        const url = base + 'result';
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        const data = await response.text();
        return data;
    } catch (e) {
        console.error(e);
    }
}



async function promotePiece(type) {
    try {
        const url = base + 'promote/'+type;
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return await response.json();
    } catch (e) {
        return false;
    }
}










export {
        startGame,
        selectPiece,
        movePiece,
        getBoard,
        getTurn,
        kingInCheck,
        isGameOver,
        getGameResult,
        promotePiece};