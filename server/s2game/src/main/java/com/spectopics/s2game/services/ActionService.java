package com.spectopics.s2game.services;

import java.lang.reflect.InvocationTargetException;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Battle;
import com.spectopics.s2game.models.Player;

public class ActionService {
    public static boolean CallAction(Battle battle, String act) {
        String[] parts = act.split("-");
        try {
            return ActionService.class.getMethod(parts[0],Battle.class, String.class).invoke(null,battle,act);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    public static boolean KillCreature(Battle battle, String act) {
        if (battle.GetBattleState() == BattleState.PLAYER1) {
            battle.NextP2Creature();
            battle.NextTurn();
        }

        else if (battle.GetBattleState() == BattleState.PLAYER2) {
            battle.NextP1Creature();
            battle.NextTurn();
        }
        return true;
    }

    public static boolean DamageEnemy(Battle battle, String act) {
        //Get Player
        Player currentPlayer;
        Player enemyPlayer;
        if (battle.GetBattleState() == BattleState.PLAYER1) {
            currentPlayer = battle.GetPlayer1();
            enemyPlayer = battle.GetPlayer2();
        } else if (battle.GetBattleState() == BattleState.PLAYER2) {
            currentPlayer = battle.GetPlayer2();
            enemyPlayer = battle.GetPlayer1();
        }

        //Damage Calculation
        float movePow = Float.parseFloat(act.split("-")[1]);
        float userAtk = currentPlayer.GetActiveCreature().GetStats().getStrength();
        float enemyDef = enemyPlayer.GetActiveCreature().GetStats().getDefense();
        float crit;
        if ((int) (Math.random() * 24) == 0) {
            crit = 1.5;
        } else {
            crit = 1;
        }
        float rand = ((int) (Math.random() * 15) + 86) * 0.01;

        float dmg = (((22 * movePow * userAtk / enemyDef) / 50) + 2) * crit * rand;
        enemyPlayer.GetActiveCreature().GetStats().AdjustHealth(-dmg);

        if (enemyPlayer.GetActiveCreature().GetStats().getHealth() <= 0) {
            if (battle.GetBattleState() == BattleState.PLAYER1) {
                battle.NextP2Creature();
                battle.NextTurn();
            } else if (battle.GetBattleState() == BattleState.PLAYER2) {
                battle.NextP1Creature();
                battle.NextTurn();
            }
        }

        return true;
    }
}
