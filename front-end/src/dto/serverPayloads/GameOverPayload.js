
export class GameOverPayload {
    constructor(result) {
        this.result = result
    }

    static fromDict(data) {
        return new GameOverPayload(data.result);
    }
}
