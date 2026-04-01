package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class GameUpdaterService {
    private Thread updater;
    private volatile boolean stillUpdating = true;
    private LobbyState lobbyState;

    private GameEventService gameEventService;

    public GameUpdaterService() {
        gameEventService = SpringContext.getBean(GameEventService.class);
    }

    // Called 30 times per second
    private void Update(long nanosSinceLastFrame) {
        double timeSinceLastFrame = nanosSinceLastFrame/1_000_000_000.0;

        // If we are in the GameStage or BuyStage or ResultsStage then we subtract the timer.
        if (this.lobbyState.getStage() != StageState.LOBBY) {
            this.lobbyState.setStageTimer((float) (this.lobbyState.getStageTimer() - timeSinceLastFrame)); // Subtract the amount of time since the last frame
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
                this.lobbyState.setBattles(BattleService.MakeBattles(this.lobbyState));
                try {
                    gameEventService.battlesStarted(this.lobbyState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.lobbyState.getStage() == StageState.BATTLESTAGE) {
            // Temporary, this accounts for only 1 battle ongoing at a time
            if (this.lobbyState.getBattles().get(0).isBattleDone()) {
                System.out.println("The battle stage is over!");
                LobbyStageService.EndBattleStage(this.lobbyState);
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

        System.out.println(this.lobbyState.getStageTimer() + " players: " + lobbyState.getPlayers().size());


        if (lobbyState.getPlayers().size() == 0) {
            StopUpdating();
        }
    }
    
    public void StopUpdating() {
        stillUpdating = false;
        if (this.updater != null && this.updater.isAlive()) {
            this.updater.interrupt(); // Interrupt sleep if any
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
