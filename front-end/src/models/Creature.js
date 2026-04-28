import { Stats } from './Stats.js';

export class Creature {
    constructor(name, stats, totalSpeed, abilities) {
        this.name = name;
        this.stats = stats;
        this.totalSpeed = totalSpeed;
        this.abilities = abilities;
    }

    static fromDict(data) {
        return new Creature(
            data.name,
            data.stats ? Stats.fromDict(data.stats) : null,
            data.totalSpeed,
            data.abilities
        );
    }
}
