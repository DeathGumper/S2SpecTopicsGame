export class LobbySettings {
    constructor(buyStageTimer, turnTimer, maxPlayers) {
        this.buyStageTimer = buyStageTimer;
        this.turnTimer = turnTimer;
        this.maxPlayers = maxPlayers;
    }

    static fromDict(data) {
        return new LobbySettings(data.buyStageTimer, data.turnTimer, data.maxPlayers);
    }
}
