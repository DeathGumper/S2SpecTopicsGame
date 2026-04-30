import { Creature } from "../../models/Creature";

export class CreatureBurnedPayload {

    constructor(creature, damage) {
        this.creature = creature
        this.damage = damage
    }

    static fromDict(data) {
        return new CreatureStatusAppliedPayload(
            Creature.fromDict(data.creature),
            data.damage
        );
    }   
}