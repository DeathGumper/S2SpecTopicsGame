package com.spectopics.s2game.services;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Player;

@Service
public class ActionService {

    private final BattleService battleService;
    private final GameEventService gameEventService;

    // Constructor injection (recommended)
    public ActionService(BattleService battleService, GameEventService gameEventService) {
        this.battleService = battleService;
        this.gameEventService = gameEventService;
    }

    public boolean CallAction(Player player, String act) {
        System.out.println("Calling action by " + player.getName() + ": " + act + " vs " + player.getOpponent().getName());

        if (player.getBattleState() != BattleState.MY_TURN) {
            System.out.println("Error: Not player's turn");
            return false;
        }

        String[] actions = act.split("\\|");
        if (actions.length == 0) {
            actions = new String[]{act};
        }

        for (String action : actions) {
            String[] parts = action.split("-");
            try {
                boolean status = (boolean) this.getClass()
                        .getMethod(parts[0], Player.class, String.class)
                        .invoke(this, player, action);

                if (!status) {
                    System.out.println("Error: Action " + parts[0] + " failed");
                    return false;
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error: " + e);
                return false;
            }
        }

        battleService.NextTurn(player);
        return true;
    }

    public boolean DamageEnemy(Player player, String act) {
        Player opponent = player.getOpponent();

        if (opponent == null) {
            System.out.println("Error: opponent is null");
            return false;
        }

        float movePow = Float.parseFloat(act.split("-")[1]);
        float userAtk = player.GetActiveCreature().getStats().getStrength();
        float enemyDef = opponent.GetActiveCreature().getStats().getDefense();

        float crit = ((int) (Math.random() * 24) == 0) ? 1.5f : 1.0f;
        float rand = ((int) (Math.random() * 15) + 86) * 0.01f;

        float poisonMultiplier = opponent.GetActiveCreature().getPoisonDamageMultiplier();

        float dmg = (((22 * movePow * userAtk / enemyDef) / 50) + 2) * crit * rand * poisonMultiplier;
        opponent.GetActiveCreature().getStats().AdjustHealth(-dmg);

        return true;
    }

    public boolean Heal(Player player, String act) {
        float healAmount = Float.parseFloat(act.split("-")[1]);
        player.GetActiveCreature().getStats().AdjustHealth(healAmount);
        return true;
    }

    public boolean Stun(Player player, String act) {
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);

        opponent.GetActiveCreature().getStats().setStun(
            opponent.GetActiveCreature().getStats().getStun() + effectPower
        );
        
        this.gameEventService.creatureStunApplied(player, opponent.GetActiveCreature());

        return true;
    }

    public boolean Ignite(Player player, String act) {
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);

        opponent.GetActiveCreature().getStats().setBurn(
            opponent.GetActiveCreature().getStats().getBurn() + effectPower
        );

        this.gameEventService.creatureIgnited(player, opponent.GetActiveCreature());

        return true;
    }

    public boolean Poison(Player player, String act) {
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);

        opponent.GetActiveCreature().getStats().setPoison(
            opponent.GetActiveCreature().getStats().getPoison() + effectPower
        );



        return true;
    }
}