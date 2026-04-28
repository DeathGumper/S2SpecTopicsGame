import { LobbyState } from '../../models/LobbyState.js';

export class BattlesStartedPayload {
    constructor(lobbyState) {
        this.lobbyState = lobbyState;
    }

    static fromDict(data) {
        return new BattlesStartedPayload(LobbyState.fromDict(data.lobbyState));
    }
}
