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

        // boolean ownerDisconnected = true;
        // for (Player player : lobbyState.getPlayers()) {
        //     if (player.isOwner()) {
        //         ownerDisconnected = false;
        //         break;
        //     }
        // }

        if (lobbyState.getPlayers().size() == 0) {
            StopUpdating();
            return;
        }

        // if (ownerDisconnected) {
        //     System.out.println("Lobby owner disconnected, killing lobby...");
        //     StopUpdating();
        //     try {
        //         gameEventService.ownerDisconnected(lobbyState);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        //     return;
        // }
        

        // If we are in the GameStage or BuyStage or ResultsStage then we subtract the timer.
        if (this.lobbyState.getStage() != StageState.LOBBY &&
            this.lobbyState.getStage() != StageState.BATTLESTAGE) {
            this.lobbyState.setStageTimer((float) (this.lobbyState.getStageTimer() - timeSinceLastFrame));
        }

        if (this.lobbyState.getStage() == StageState.BUYSTAGE) {
            boolean allReady = true;
            // iterate thru all players, if any player is not ready then return
            for (Player player : lobbyState.getPlayers()) {
                System.out.println(player.getName() + ": " + player.isReady());
                if (!player.isReady()) allReady = false;
            }

            // Success, all players are ready, start the battle stage
            if (allReady || this.lobbyState.getStageTimer() <= 0) {
                System.out.println("All players ready, starting battle stage!");
                LobbyStageService.GoToBattleStage(this.lobbyState);
                this.battleService.AssignOpponents(this.lobbyState);
                try {
                    gameEventService.battlesStarted(this.lobbyState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.lobbyState.getStage() == StageState.BATTLESTAGE) {
            if (this.battleService.checkAllBattlesDone(this.lobbyState)) {
                System.out.println("The battle stage is over!");
                LobbyStageService.EndBattleStage(this.lobbyState);
                gameEventService.resultsStageStarted(this.lobbyState);

                // this right here only supports 2 players
                for (Player player: lobbyState.getPlayers()) {
                    if (player.getLives() <= 0) {
                        gameEventService.gameOver(player);
                        StopUpdating();
                        return;
                    }
                }
            }
        }

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
    
    public void StopUpdating() {
        stillUpdating = false;
        if (this.updater != null && this.updater.isAlive()) {
            this.updater.interrupt(); // Interrupt sleep if any

            LobbyService.KillLobby(lobbyState);
        }
    }

    public void StartUpdating(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
        final double frameTime = 1_000_000_000.0 / 3.0; // nanoseconds per frame (30 fps)
        this.updater = new Thread(() -> {
            long lastTime = System.nanoTime();
            
            while (stillUpdating) {
                long now = System.nanoTime();

                long delta = now - lastTime;
                if (delta >= frameTime) {
                    lastTime = now;
                    Update(delta);
                }
            }

        });

        this.updater.setDaemon(true);
        this.updater.start();
    }
}
