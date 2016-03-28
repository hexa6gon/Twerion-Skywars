package de.piet.skywars.game;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.chest.ChestManager;
import de.piet.skywars.config.MapLoader;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.map.MapManager;
import de.piet.skywars.team.TeamManager;
/**
 * Created by PeterH on 06.01.2016.
 */
public class StartupManager {
    public static void startupSkywars() {
        // Config
        SkywarsConfig.loadConfig( );
        // Kits
        KitManager.setupKits( );
        // Chests
        ChestManager.prepareItems( );
        // Scoreboard
        ScoreboardManager.initScoreboards( );
        // Load Maps
        MapLoader.loadMaps( );
        // Create Teams
        TeamManager.createTeams( );
        // Select Map
        MapManager.selectRandomMap( );
        // Start Timer
        GameManager.startTimer();
        CloudAPI.getServerAPI().setKick( true );
    }
}
