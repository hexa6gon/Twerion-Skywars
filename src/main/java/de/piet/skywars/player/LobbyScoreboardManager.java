package de.piet.skywars.player;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
/**
 * Created by PeterH on 24.01.2016.
 */
public class LobbyScoreboardManager {
    @Getter
    private static HashMap<Player,LobbyScoreboard> scoreboards = new HashMap<>(  );
    public static void createBoard( Player player ) {
        scoreboards.put( player, new LobbyScoreboard( player ) );
    }
    public static void removeBoard( Player player ) {
        scoreboards.remove( player );
    }
    public static void updateKit( Player player ) {
        if( !scoreboards.containsKey( player ) ) return;
        scoreboards.get( player ).setKit();
    }
    public static void updateAllPlayers() {
        for( Player player : scoreboards.keySet() ) {
            scoreboards.get( player ).setPlayers();
        }
    }
    public static void updateAllMap() {
        for( Player player : scoreboards.keySet() ) {
            scoreboards.get( player ).setMap( );
        }
    }
    public static void removeAllBoards() {
        scoreboards.clear();
    }
}
