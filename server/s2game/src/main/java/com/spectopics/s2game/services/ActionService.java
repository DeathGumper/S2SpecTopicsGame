package com.spectopics.s2game.services;

import java.lang.reflect.InvocationTargetException;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Battle;

public class ActionService {
    public static void CallAction(Battle battle, String act) {
        String[] parts = act.split("-");
        try {
            ActionService.class.getMethod(parts[0],Battle.class, String.class).invoke(null,battle,act);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error: " + e);
            return;
        }
    }

    public static void KillCreature(Battle battle, String act) {
        if (battle.GetBattleState() == BattleState.PLAYER1) {
            battle.NextP2Creature();
            battle.NextTurn();
        }

        else if (battle.GetBattleState() == BattleState.PLAYER2) {
            battle.NextP1Creature();
            battle.NextTurn();
        }
    }
}
