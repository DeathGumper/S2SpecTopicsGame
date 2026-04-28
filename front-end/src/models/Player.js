import { Creature } from './Creature.js';

export class Player {
    constructor(name, id, lives, creatures, activeCreatureIndex, ready, owner, opponentId) {
        this.name = name;
        this.id = id;
        this.lives = lives;
        this.creatures = creatures;
        this.activeCreatureIndex = activeCreatureIndex;
        this.ready = ready;
        this.owner = owner;
        this.opponentId = opponentId;
    }

    static fromDict(data) {
        const player = new Player(
            data.name,
            data.id,
            data.lives,
            data.creatures ? data.creatures.map(c => c ? Creature.fromDict(c) : null) : [],
            data.activeCreatureIndex,
            data.ready,
            data.owner,
            data.opponentId
        );
        console.log(data);
        player.battleState = data.battleState;
        return player;
    }
}
