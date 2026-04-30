import { Creature } from "../../models/Creature";

export class CreatureStunnedPayload {

    constructor(creature, damage) {
        this.creature = creature
    }

    static fromDict(data) {
        return new CreatureStatusAppliedPayload(
            Creature.fromDict(data.creature)
        );
    }   
}