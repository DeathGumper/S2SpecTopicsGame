import { Player } from './Player.js';
import { LobbySettings } from './LobbySettings.js';

import { ALLSTAGES } from '../helpers/constants.jsx';

export class LobbyState {
    constructor(name, id, players, stage, stageTimer, lobbySettings) {
        this.name = name;
        this.id = id;
        this.players = players;
        this.stage = stage;
        this.stageTimer = stageTimer;
        this.lobbySettings = lobbySettings;
    }

    static fromDict(data) {
        return new LobbyState(
            data.name,
            data.id,
            data.players ? data.players.map(p => Player.fromDict(p)) : [],
            data.stage,
            data.stageTimer,
            data.lobbySettings ? LobbySettings.fromDict(data.lobbySettings) : null
        );
    }
}
