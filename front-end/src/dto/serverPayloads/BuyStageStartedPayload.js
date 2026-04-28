import { LobbyState } from '../../models/LobbyState.js';

export class BuyStageStartedPayload {
    constructor(lobbyState) {
        this.lobbyState = lobbyState;
    }

    static fromDict(data) {
        return new BuyStageStartedPayload(LobbyState.fromDict(data.lobbyState));
    }
}
