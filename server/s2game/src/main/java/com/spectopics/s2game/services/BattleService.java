package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.BattleState;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BattleService {

    private final GameEventService gameEventService;

    public BattleService(GameEventService gameEventService) {
        // Assign a value to the game event service, using spring bean dependency injection.
        this.gameEventService = gameEventService;
    }

    public void AssignOpponents(LobbyState lobbyState) {
        List<Player> players = lobbyState.getPlayers();
        // Currently only supports 2 players
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        // They are fighting each other

        p1.setOpponent(p2);
        p2.setOpponent(p1);

        // Fail cases, this is impossible tho.
        if (p1.getCreatures().length == 0) {
            System.out.println(p1.getName() + " has no creatures, automatically loses!");
            p1.setBattleState(BattleState.LOST);
            p2.setBattleState(BattleState.WON);
        } else if (p2.getCreatures().length == 0) {
            System.out.println(p2.getName() + " has no creatures, automatically loses!");
            p1.setBattleState(BattleState.WON);
            p2.setBattleState(BattleState.LOST);
        } else {
            // p1 always goes first, maybe make a coin flip later
            p1.setBattleState(BattleState.MY_TURN);
            p2.setBattleState(BattleState.OPPONENT_TURN);

            p1.setActiveCreatureIndex(0);
            p2.setActiveCreatureIndex(0);
        }
    }

    public boolean checkAllBattlesDone(LobbyState lobbyState) {
        // Itterate thru
        for (Player player : lobbyState.getPlayers()) {
            BattleState state = player.getBattleState();

            // Are they still battling?
            if (state != BattleState.WON && state != BattleState.LOST) {
                return false;
            }
        }

        // All battles are done!
        return true;
    }

    public void RemoveLives(LobbyState lobbyState) {
        // Remove lives from players that lost
        for (Player player : lobbyState.getPlayers()) {
            if (player.getBattleState() == BattleState.LOST) {
                player.setLives(player.getLives() - 1);
                System.out.println(player.getName() + " lost a life! Lives left: " + player.getLives());
            }
        }
    }

    public void NextTurn(Player player) {
        // This should be broken up into more functions, but for now it does a lot.
        Player opponent = player.getOpponent();

        // This is post player turn but before the next turn starts.

        // Post players turn...
        // Reduce the poison stacks by 1 at the end of the players turn.
        if (player.GetActiveCreature().getStats().getPoison() > 0) {
            player.GetActiveCreature().getStats().setPoison(player.GetActiveCreature().getStats().getPoison() - 1);
            System.out.println(player.getName() + " is poisoned and now has " + player.GetActiveCreature().getStats().getPoison() + " poison stacks left!");
        }

        // Take burn damage
        float dmg = player.GetActiveCreature().takeBurn();
        if (dmg > 0) {
            gameEventService.creatureBurned(player, player.GetActiveCreature(), dmg);
        }

        // TODO: BUG, when creature dies from burn, it doesnt go to the next creature!

        // Start of opponents turn...
        // Check if the opponents active creatures is still alive after the turn.
        if (opponent.GetActiveCreature().getStats().getHealth() <= 0) {
            System.out.println(opponent.getName() + "'s active creature fainted!");
            if (opponent.NextCreature()) {
                System.out.println(opponent.getName() + " sends out " + opponent.GetActiveCreature().getName() + "!");
                // Still the players turn
                return;
            } else {
                System.out.println(opponent.getName() + " has no more creatures to send out and loses!");
                opponent.setBattleState(BattleState.LOST);
                player.setBattleState(BattleState.WON);
                return;
            }
        }

        // Check if the opponents active creature is stunned and skip their turn if so.
        if (opponent.GetActiveCreature().checkStun()) {
            System.out.println(opponent.getName() + "'s active creature is stunned and loses their turn!");
            gameEventService.creatureStunned(opponent, opponent.GetActiveCreature());
            // Still the players turn
            return;
        } 
        // sets the next turn
        player.setBattleState(BattleState.OPPONENT_TURN);
        opponent.setBattleState(BattleState.MY_TURN);
        return;
    }
}
