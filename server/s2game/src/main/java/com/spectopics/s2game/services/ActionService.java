package com.spectopics.s2game.services;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.Player;

@Service
public class ActionService {
    // This class calls all the actions.
    private final BattleService battleService;
    private final GameEventService gameEventService;

    // Constructor injection (recommended)
    public ActionService(BattleService battleService, GameEventService gameEventService) {
        this.battleService = battleService;
        this.gameEventService = gameEventService;
    }

    public boolean CallAction(Player player, String act) {

        if (player.getBattleState() != BattleState.MY_TURN) {
            System.out.println("Error: Not player's turn");
            return false;
        }

        // Breaks the action chunks by |.
        String[] actions = act.split("\\|");
        if (actions.length == 0) {
            // If there are no |, it will just be one action, so manually assign it.
            actions = new String[]{act};
        }

        // Does each of the aciton chunks
        for (String action : actions) {
            // Breaks the action chunks into smaller commands and values.
            // First one is the command, second is the value.
            String[] parts = action.split("-");
            try {
                // calls the action by using the string, a little funky but works.
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

        // Action was called, now your turn opponent!
        battleService.NextTurn(player);
        return true;
    }

    public boolean DamageEnemy(Player player, String act) {
        // Just a flat damage, there is calculations to it but it just flat damages.
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

    public boolean DamagePlayer(Player player, String act) {
        // Damages the player itself, used for self damage moves.
        // There is a formula, but still just flat damage.

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

        float poisonMultiplier = player.GetActiveCreature().getPoisonDamageMultiplier();

        float dmg = (((22 * movePow * userAtk / enemyDef) / 50) + 2) * crit * rand * poisonMultiplier;
        player.GetActiveCreature().getStats().AdjustHealth(-dmg);

        return true;
    }

    public boolean Heal(Player player, String act) {
        // Raises players hp.
        float healAmount = Float.parseFloat(act.split("-")[1]);
        player.GetActiveCreature().getStats().AdjustHealth(healAmount);
        return true;
    }

    public boolean Stun(Player player, String act) {
        // Adds a stun stack to the opponent, has stun chance, see creature class for more info.
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);

        opponent.GetActiveCreature().getStats().setStun(
            opponent.GetActiveCreature().getStats().getStun() + effectPower
        );
        
        this.gameEventService.creatureStunApplied(player, opponent.GetActiveCreature());

        return true;
    }

    public boolean SelfStun(Player player, String act) {
        // Stun yourself! Same thing as Stun but for self.
        float effectPower = Float.parseFloat(act.split("-")[1]);

        player.GetActiveCreature().getStats().setStun(
            player.GetActiveCreature().getStats().getStun() + effectPower
        );
        
        this.gameEventService.creatureStunned(player, player.GetActiveCreature());

        return true;
    }

    public boolean Ignite(Player player, String act) {
        // Light the enemy on fire, and then goes and burns each turn, see creature class for more info on burn.
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
        // Adds poison stacks, amplifies damage taken, see creature class for more info on poison.
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);

        opponent.GetActiveCreature().getStats().setPoison(
            opponent.GetActiveCreature().getStats().getPoison() + effectPower
        );

        return true;
    }

    public boolean WeakenEnemy(Player player, String act) {
        // Lowers damage of the enemy, just flat reduction to strength.
        Player opponent = player.getOpponent();
        if (opponent == null) return false;

        float effectPower = Float.parseFloat(act.split("-")[1]);
        opponent.GetActiveCreature().getStats().AdjustStrength(-effectPower);

        return true;
    }
}