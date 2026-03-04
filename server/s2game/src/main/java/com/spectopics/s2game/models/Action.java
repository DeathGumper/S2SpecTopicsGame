package com.spectopics.s2game.models;

public class Action {
    int startTurn = -1;
    int currentTurn = 0;
    int endTurn = -1;

    public void DamageOverTime(Creature other, int damage, int turns) {
        startTurn = currentTurn;
        endTurn = currentTurn + turns;
        DamageOverTime(other, damage);
    }

    public void DamageOverTime(Creature other, int damage) {
        if (currentTurn >= endTurn) {
            other.getStats().AdjustHealth(-1 * (damage / (endTurn - startTurn)));
        }
    }

    public void HealOverTime(Creature other, int damage, int turns) {
        startTurn = currentTurn;
        endTurn = currentTurn + turns;
        HealOverTime(other, damage);
    }

    public void HealOverTime(Creature other, int damage) {
        if (currentTurn >= endTurn) {
            other.getStats().AdjustHealth((damage / (endTurn - startTurn)));
        }
    }

}
