export class Stats {
    constructor(maxHealth, health, strength, defense, dexterity, speed, accuracy, stun, burn, poison) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.dexterity = dexterity;
        this.speed = speed;
        this.accuracy = accuracy;
        this.stun = stun;
        this.burn = burn;
        this.poison = poison;
    }

    static fromDict(data) {
        return new Stats(
            data.maxHealth,
            data.health,
            data.strength,
            data.defense,
            data.dexterity,
            data.speed,
            data.accuracy,
            data.stun,
            data.burn,
            data.poison
        );
    }
}
