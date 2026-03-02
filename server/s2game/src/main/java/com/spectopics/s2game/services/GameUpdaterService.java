package com.spectopics.s2game.services;

import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.models.LobbyState;

public class GameUpdaterService {
    private Thread updater;
    private LobbyState lobbyState;

    // Called 30 times per second
    private void Update(long nanosSinceLastFrame) {
        double timeSinceLastFrame = nanosSinceLastFrame/1_000_000_000.0;

        // if (lobbyState.getPlayers().size() == 0) {
        //     StopUpdating();
        //     LobbyService.KillLobby(lobbyState);
        // }

        // If we are in the GameStage or BuyStage then we subtract the timer.
        if (this.lobbyState.getStage() != StageState.LOBBY) {
            this.lobbyState.setStageTimer((float) (this.lobbyState.getStageTimer() - timeSinceLastFrame)); // Subtract the amount of time since the last frame
            if (LobbyStageService.CheckAllReady(this.lobbyState, this.lobbyState.getPlayers())) {
                System.out.println("All players ready, starting battle stage!");
                BattleService.MakeBattles(this.lobbyState);

                
            }
        }

        System.out.println(this.lobbyState.getStageTimer() + " players: " + lobbyState.getPlayers().size());
    }
    
    public void StopUpdating() {
        if (this.updater != null && this.updater.isAlive()) {
            this.updater.interrupt(); // Interrupt sleep if any
            try {
                this.updater.join(); // Wait for thread to finish cleanly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void StartUpdating(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
        final double frameTime = 1_000_000_000.0 / 3.0; // nanoseconds per frame (30 fps)
        this.updater = new Thread(() -> {
            long lastTime = System.nanoTime();
            
            while (true) {
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
