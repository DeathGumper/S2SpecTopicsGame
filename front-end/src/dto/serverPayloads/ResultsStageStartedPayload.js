import { LobbyState } from '../../models/LobbyState.js';

export class ResultsStageStartedPayload {
    constructor(lobbyState) {
        this.lobbyState = lobbyState;
    }

    static fromDict(data) {
        return new ResultsStageStartedPayload(LobbyState.fromDict(data.lobbyState));
    }
}
