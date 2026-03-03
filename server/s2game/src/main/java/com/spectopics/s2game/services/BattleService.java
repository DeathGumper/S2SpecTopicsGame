package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.List;

import com.spectopics.s2game.models.Battle;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class BattleService {
    /**
     * Makes all the battles
     * @param players
     * @return
     */
    public static List<Battle> MakeBattles(LobbyState lobbyState) {
        List<Player> players = lobbyState.getPlayers();
        List<Battle> battles = new ArrayList<Battle>();
        
        // currently only supports 2 players, so just make 1 battle with the 2 players. In the future we can add support for more players and multiple battles.
        battles.add(new Battle(players.get(0), players.get(1)));

        lobbyState.setBattles(battles);
        return battles;
    }

    /**
     * Checks if all battles in the list are done.
     * @param battles
     * @return
     */
    public static boolean checkAllBattlesDone(List<Battle> battles) {
        for (Battle battle : battles) {
            if (!battle.isBattleDone()) {
                return false;
            }
        }
        return true;
    }

    public static Battle getBattleByPlayerId(List<Battle> battles, String playerId) {
        for (Battle battle : battles) {
            // Check if either the player 1 or player 2's id = player id
            if (battle.getPlayer1().getId().equals(playerId) || battle.getPlayer2().getId().equals(playerId)) {
                return battle;
            }
        }

        return null;
    }
}
