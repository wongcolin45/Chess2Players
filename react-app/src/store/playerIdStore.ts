
// Stores a map of gameId → roleId so multiple concurrent games are supported
const KEY = 'playerIds';

function getMap(): Record<string, string> {
    try {
        return JSON.parse(localStorage.getItem(KEY) ?? '{}');
    } catch {
        return {};
    }
}

function getRoleId(gameId: string): string | null {
    return getMap()[gameId] ?? null;
}

function setRoleId(gameId: string, id: string): void {
    const map = getMap();
    map[gameId] = id;
    localStorage.setItem(KEY, JSON.stringify(map));
}

function clearRoleId(gameId: string): void {
    const map = getMap();
    delete map[gameId];
    localStorage.setItem(KEY, JSON.stringify(map));
}

export {
    getRoleId,
    setRoleId,
    clearRoleId,
}
