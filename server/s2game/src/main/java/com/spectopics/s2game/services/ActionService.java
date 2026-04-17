package com.spectopics.s2game.services;

import java.lang.reflect.InvocationTargetException;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Battle;
import com.spectopics.s2game.models.Player;

public class ActionService {
    public static boolean CallAction(Battle battle, String act) {
        System.out.println("Calling action by " + (battle.getState() == BattleState.PLAYER1 ? battle.getPlayer1().getName() : battle.getPlayer2().getName()) + ": " + act + " for battle: " + battle.getPlayer1().getName() + " vs " + battle.getPlayer2().getName());

        String[] parts = act.split("-");
        try {
            boolean status = (boolean) ActionService.class.getMethod(parts[0],Battle.class, String.class).invoke(null,battle,act);
            return status;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    public static boolean KillCreature(Battle battle, String act) {
        if (battle.getState() == BattleState.PLAYER1) {
            battle.NextP2Creature();
            battle.NextTurn();
        }

        else if (battle.getState() == BattleState.PLAYER2) {
            battle.NextP1Creature();
            battle.NextTurn();
        }
        return true;
    }

    public static boolean DamageEnemy(Battle battle, String act) {
        //Get Player
        Player currentPlayer = null;
        Player enemyPlayer = null;
        if (battle.getState() == BattleState.PLAYER1) {
            currentPlayer = battle.getPlayer1();
            enemyPlayer = battle.getPlayer2();
        } else if (battle.getState() == BattleState.PLAYER2) {
            currentPlayer = battle.getPlayer2();
            enemyPlayer = battle.getPlayer1();
        }

        if (currentPlayer == null || enemyPlayer == null) {
            System.out.println("Error: One of the players is null");
            return false;
        }

        //Damage Calculation
        float movePow = Float.parseFloat(act.split("-")[1]);
        float userAtk = currentPlayer.GetActiveCreature().getStats().getStrength();
        float enemyDef = enemyPlayer.GetActiveCreature().getStats().getDefense();
        float crit;
        if ((int) (Math.random() * 24) == 0) {
            crit = 1.5f;
        } else {
            crit = 1.0f;
        }
        float rand = ((int) (Math.random() * 15) + 86) * 0.01f;

        float dmg = (((22 * movePow * userAtk / enemyDef) / 50) + 2) * crit * rand;
        enemyPlayer.GetActiveCreature().getStats().AdjustHealth(-dmg);

        if (enemyPlayer.GetActiveCreature().getStats().getHealth() <= 0) {
            if (battle.getState() == BattleState.PLAYER1) {
                battle.NextP2Creature();
                battle.NextTurn();
            } else if (battle.getState() == BattleState.PLAYER2) {
                battle.NextP1Creature();
                battle.NextTurn();
            }
        }

        return true;
    }
}
