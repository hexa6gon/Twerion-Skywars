package de.piet.skywars.game;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.scoreboard.PlayerScoreboard;
import de.piet.skywars.team.SkywarsTeam;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
/**
 * Created by PeterH on 08.01.2016.
 */
public class ScoreboardManager {
    @Getter
    private static Scoreboard scoreboard;
    @Getter
    private static Team spectatorTeam;
    @Getter
    private static HashMap<SkywarsTeam,Team> teams = new HashMap<>(  );
    public static void initScoreboards() {
        scoreboard = Bukkit.getServer( ).getScoreboardManager().getNewScoreboard();
        spectatorTeam = getScoreboard().registerNewTeam( "spectator" );
        spectatorTeam.setPrefix( "§7" );
        spectatorTeam.setCanSeeFriendlyInvisibles( true );
    }
    public static void setupScoreboard( PlayerData playerData ) {
        PlayerScoreboard playerScoreboard = new PlayerScoreboard( playerData.getPlayer() );
        playerData.setPlayerScoreboard( playerScoreboard );
        playerScoreboard.setup( );
        //playerScoreboard.sendTitle();
        playerScoreboard.setLine( 0, playerData.getSkyWarsKit( ).getName( ) );
        playerScoreboard.setLine( 1, "§aKit§f:" );
        playerScoreboard.setLine( 2, "§1" );

        updatePlayers( playerData.getPlayer( ) );
        playerScoreboard.setLine( 4, "§aSpieler§f:" );
        playerScoreboard.setLine( 5, "§2" );

        updateKills( playerData.getPlayer( ) );
        playerScoreboard.setLine( 7, "§aKills§f:" );
        playerScoreboard.setLine( 8, "§3" );

        playerScoreboard.setLine( 9, GameManager.getSkyWarsMap().getAnzeigeName() );
        playerScoreboard.setLine( 10, "§aMap§f:" );
        playerScoreboard.setLine( 11, "§4" );
        playerScoreboard.setLine( 13, "§aSpielzeit§f:" );
        /*
        if( SkywarsConfig.getProTeam() > 1 ) {
            playerScoreboard.setLine( 14, "§5" );
            playerScoreboard.setLine( 16, "§aTeam§f:" );
            updateMate( playerData.getPlayer() );
        }
        */
    }
    public static void updateTime( PlayerData playerData ) {
        PlayerScoreboard playerScoreboard = playerData.getPlayerScoreboard();
        playerScoreboard.setLine( 12, "§3§f" + GameManager.getTime( GameManager.getStartTime( ) ) );
    }
    public static void updateKills( Player player ) {
        PlayerData playerData = PlayerManager.getPlayerData( player );
        PlayerScoreboard playerScoreboard = playerData.getPlayerScoreboard();
        playerScoreboard.setLine( 6, "§2§f" + playerData.getKills( ) );
    }
    public static void updatePlayers( Player player ) {
        PlayerScoreboard playerScoreboard = PlayerManager.getPlayerData( player ).getPlayerScoreboard( );
        playerScoreboard.setLine( 3, "§1§f" + PlayerManager.getPlayerDatas( ).size( ) );
    }
    /*
    public static void updateMate( Player player ) {
            PlayerData playerData = PlayerManager.getPlayerData( player );
            PlayerScoreboard playerScoreboard = playerData.getPlayerScoreboard( );
            int view = playerData.getSkywarsTeam().getNumber();
            view++;
            playerScoreboard.setLine( 15, "#" + view );
    }
    */
}
