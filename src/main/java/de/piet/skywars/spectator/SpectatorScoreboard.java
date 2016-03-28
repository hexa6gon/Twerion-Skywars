package de.piet.skywars.spectator;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.scoreboard.PacketScoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
/**
 * Created by PeterH on 23.01.2016.
 */
public class SpectatorScoreboard {
    @Getter
    private static HashMap<Player,PacketScoreboard> scoreboards = new HashMap<>(  );
    public static void setupSpectatorScoreboard( Player player ) {
        PacketScoreboard packetScoreboard = new PacketScoreboard( player );
        packetScoreboard.remove();
        Bukkit.getServer( ).getScheduler().scheduleSyncDelayedTask( SkyWars.getInstance( ), ( ) -> {
        scoreboards.put( player, packetScoreboard );
        packetScoreboard.sendSidebar( "§eSkyWars" );
        packetScoreboard.setLine( 0, "§1§f" + PlayerManager.getPlayerDatas( ).size( ) );
        packetScoreboard.setLine( 1, "§aSpieler§f:" );
        packetScoreboard.setLine( 2, "§2" );
        packetScoreboard.setLine( 3, GameManager.getSkyWarsMap().getAnzeigeName() );
        packetScoreboard.setLine( 4, "§aMap§f:" );
        packetScoreboard.setLine( 5, "§3" );
        packetScoreboard.setLine( 6, GameManager.getTime( GameManager.getStartTime() ) );
        packetScoreboard.setLine( 7, "§aSpielzeit§f:" );
        }, 10L );
    }
    public static void updateTime() {
        for( Player player : scoreboards.keySet() ) {
            PacketScoreboard packetScoreboard = scoreboards.get( player );
            packetScoreboard.removeLine( 6 );
            packetScoreboard.setLine( 6, GameManager.getTime( GameManager.getStartTime() ) );
        }
    }
    public static void updatePlayers() {
        for( Player player : scoreboards.keySet() ) {
            PacketScoreboard packetScoreboard = scoreboards.get( player );
            packetScoreboard.removeLine( 0 );
            packetScoreboard.setLine( 0, "§1§f" + PlayerManager.getPlayerDatas().size() );
        }
    }
}
