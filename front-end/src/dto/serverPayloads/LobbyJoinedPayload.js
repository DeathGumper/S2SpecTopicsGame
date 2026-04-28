import { LobbyState } from '../../models/LobbyState.js';

export class LobbyJoinedPayload {
    constructor(lobbyState, playerId) {
        this.lobbyState = lobbyState;
        this.playerId = playerId;
    }

    static fromDict(data) {
        return new LobbyJoinedPayload(
            LobbyState.fromDict(data.lobbyState),
            data.playerId
        );
    }
}
