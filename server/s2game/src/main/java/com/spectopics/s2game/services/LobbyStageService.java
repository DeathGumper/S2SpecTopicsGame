package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class LobbyStageService {
    /**
     * Starts the game if the request maker is the lobby owner and the lobby is currently in the lobby stage. 
     * Changes the stage to the buy stage and sets the stage timer to the amount of time the buy stage lasts.
     * @param lobbyState
     * @param requestMaker
     * @return
     */
    public static boolean StartGame(LobbyState lobbyState, Player requestMaker) throws Exception{
        System.out.println(requestMaker.isOwner());
        // Return if not owner
        if (!requestMaker.isOwner()) {
            System.err.println("Player is not the owner.");
            return false;
        }

        // return if players not equal to 2 (for now we only support 2 player battles, so we need exactly 2 players to start the game)
        if (lobbyState.getPlayers().size() != 2) {
            System.err.println("There is not exactly 2 players in the lobby.");
            return false;
        }

        return GoToBuyStage(lobbyState);
        
    }

    public static boolean GoToBuyStage(LobbyState lobbyState) {

        // return if not in lobby stage or results stage
        if (lobbyState.getStage() != StageState.LOBBY && lobbyState.getStage() != StageState.RESULTSSTAGE) {
            System.err.println("The lobby is not in the LOBBY stage.");
            return false;
        }

        // Set to buy stage and set timer
        lobbyState.setStage(StageState.BUYSTAGE);
        lobbyState.setStageTimer(lobbyState.getLobbySettings().getBuyStageTimer());

        return true;
    }

    // The battle stage has ended and time to go show the results
    public static boolean EndBattleStage(LobbyState lobbyState) {
        if (lobbyState.getStage() != StageState.BATTLESTAGE) {
            return false;
        }

        lobbyState.setStage(StageState.RESULTSSTAGE);
        lobbyState.setStageTimer(lobbyState.getLobbySettings().getResultStageTimer());

        return true;
    }

    /**
     * Sets the player to ready if they are in the lobby, and the player is not null.
     * @param lobbyState
     * @param player
     */
    public static void ReadyUp(LobbyState lobbyState, Player player) {
        // return if player is null
        if (player == null) return;
        
        // Checks if this player is in the lobby
        if (!lobbyState.getPlayers().contains(player)) return;

        player.setReady(true);
    }

    /**
     * Checks if all players in the lobby are ready to go to the battlestage
     * @param lobbyState
     * @param players
     */

    public static boolean GoToBattleStage(LobbyState lobbyState) {
        lobbyState.setStage(StageState.BATTLESTAGE);

        for (Player player : lobbyState.getPlayers()) {
            player.setReady(false);
        }
        return true;
    }
}
