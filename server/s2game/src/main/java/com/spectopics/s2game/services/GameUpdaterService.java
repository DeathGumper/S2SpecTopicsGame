package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class GameUpdaterService {
    private Thread updater;
    private volatile boolean stillUpdating = true;
    private LobbyState lobbyState;

    private GameEventService gameEventService;
    private BattleService battleService;

    public GameUpdaterService() {
        
        this.battleService = SpringContext.getBean(BattleService.class);
        this.gameEventService = SpringContext.getBean(GameEventService.class);
    }

    // Called 30 times per second
    private void Update(long nanosSinceLastFrame) {
        double timeSinceLastFrame = nanosSinceLastFrame/1_000_000_000.0;

        // No more ppl! Stop the updater and kill the lobby.
        if (lobbyState.getPlayers().size() == 0) {
            StopUpdating();
            return;
        }
        

        // If we are in the GameStage or BuyStage or ResultsStage then we subtract the timer.
        if (this.lobbyState.getStage() != StageState.LOBBY &&
            this.lobbyState.getStage() != StageState.BATTLESTAGE) {
            this.lobbyState.setStageTimer((float) (this.lobbyState.getStageTimer() - timeSinceLastFrame));
        }

        // Check if the buystage is over, and if it is start the battle stage.
        if (this.lobbyState.getStage() == StageState.BUYSTAGE) {
            boolean allReady = true;
            // iterate thru all players, if any player is not ready then return
            for (Player player : lobbyState.getPlayers()) {
                if (!player.isReady()) allReady = false;
            }

            // Success, all players are ready, start the battle stage
            if (allReady || this.lobbyState.getStageTimer() <= 0) {
                LobbyStageService.GoToBattleStage(this.lobbyState);
                this.battleService.AssignOpponents(this.lobbyState);
                try {
                    gameEventService.battlesStarted(this.lobbyState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Check if the battle stage is over, and if it is start the results stage.
        if (this.lobbyState.getStage() == StageState.BATTLESTAGE) {
            if (this.battleService.checkAllBattlesDone(this.lobbyState)) {
                LobbyStageService.EndBattleStage(this.lobbyState);
                gameEventService.resultsStageStarted(this.lobbyState);

                // this right here only supports 2 players
                for (Player player: lobbyState.getPlayers()) {
                    // Did player loose all lives?
                    if (player.getLives() <= 0) {
                        gameEventService.gameOver(player);
                        StopUpdating();
                        return;
                    }
                }
            }
        }

        // Check if the results stage is over, and if it is start the buy stage.
        if (this.lobbyState.getStage() == StageState.RESULTSSTAGE) {
            if (this.lobbyState.getStageTimer() <= 0) {
                LobbyStageService.GoToBuyStage(this.lobbyState);
                try {
                    gameEventService.buyStageStarted(lobbyState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Stop the updater, called when the lobby is killed.
    public void StopUpdating() {
        stillUpdating = false;
        if (this.updater != null && this.updater.isAlive()) {
            this.updater.interrupt(); // Interrupt sleep if any

            LobbyService.KillLobby(lobbyState);
        }
    }


    // Start the loop! Called when lobby owner starts the game.
    public void StartUpdating(LobbyState lobbyState) {
        // Stores the lobby state
        this.lobbyState = lobbyState;
        final double frameTime = 1_000_000_000.0 / 3.0; // nanoseconds per frame (30 fps)

        // Async thread!
        this.updater = new Thread(() -> {

            // Track delta time
            long lastTime = System.nanoTime();
            
            while (stillUpdating) {
                long now = System.nanoTime();

                long delta = now - lastTime;
                if (delta >= frameTime) {
                    lastTime = now;

                    // Calls the update function with the delta time.
                    Update(delta);
                }
            }

        });

        // Starts the thread.
        this.updater.setDaemon(true);
        this.updater.start();
    }
}
