package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.List;

import com.spectopics.s2game.models.Battle;
import com.spectopics.s2game.models.Player;

public class BattleService {

    public List<Battle> MakeBattles(List<Player> players) {
        List<Battle> battles = new ArrayList<Battle>();
        
        // currently only supports 2 players, so just make 1 battle with the 2 players. In the future we can add support for more players and multiple battles.
        battles.add(new Battle(players.get(0), players.get(1)));

        return battles;
    }
}
