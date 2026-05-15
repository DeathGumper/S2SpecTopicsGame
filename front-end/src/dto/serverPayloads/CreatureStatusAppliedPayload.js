import { Creature } from "../../models/Creature";

export class CreatureStatusAppliedPayload {

    constructor(creature, statusName) {
        this.creature = creature
        this.statusName = statusName
    }

    static fromDict(data) {
        return new CreatureStatusAppliedPayload(
            Creature.fromDict(data.creature),
            data.statusName
        );
    }   
}