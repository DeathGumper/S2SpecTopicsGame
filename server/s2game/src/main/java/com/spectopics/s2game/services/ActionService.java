package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Battle;

public class ActionService {
    public static void CallAction(Battle battle, String act) {
        // TODO: jacobs code
        
        // TEMPORARY
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
