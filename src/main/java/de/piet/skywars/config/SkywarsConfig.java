package de.piet.skywars.config;
import de.piet.skywars.SkyWars;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
/**
 * Created by PeterH on 06.01.2016.
 */
public class SkywarsConfig {
    @Getter
    @Setter
    private static int proTeam, toStart, teams;
    @Getter
    private static String type;
    public static void loadConfig() {
        File file = new File( SkyWars.getInstance().getDataFolder(), "config.yml" );
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration( file );
        yamlConfiguration.addDefault( "proTeam", 0 );
        yamlConfiguration.addDefault( "toStart", 0 );
        yamlConfiguration.addDefault( "teams", 0 );
        yamlConfiguration.addDefault( "type", "0x0" );
        yamlConfiguration.options().copyDefaults( true );
        try {
            yamlConfiguration.save( file );
        }
        catch( Exception exc ) {
            exc.printStackTrace();
        }
        proTeam = yamlConfiguration.getInt( "proTeam" );
        toStart = yamlConfiguration.getInt( "toStart" );
        teams = yamlConfiguration.getInt( "teams" );
        type = yamlConfiguration.getString( "type" );
    }
}
