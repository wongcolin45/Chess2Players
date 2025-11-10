// playerIdStore.ts
const KEY = 'playerId';


function getPlayerId(): string | null {
    return localStorage.getItem(KEY);
}
function setPlayerId(id: string) {
    localStorage.setItem(KEY, id);
}
function clearPlayerId() {
    localStorage.removeItem(KEY);
}

export {
    getPlayerId,
    setPlayerId,
    clearPlayerId
}

