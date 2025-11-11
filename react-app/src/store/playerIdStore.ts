

const KEY = 'playerId';


function getRoleId(): string | null {
    return localStorage.getItem(KEY);
}
function setRoleId(id: string) {
    localStorage.setItem(KEY, id);
}
function clearRoleId() {
    localStorage.removeItem(KEY);
}

export {
    getRoleId,
    setRoleId,
    clearRoleId
}

