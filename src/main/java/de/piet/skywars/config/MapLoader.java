package de.piet.skywars.config;
import de.piet.skywars.SkyWars;
import de.piet.skywars.map.ConfigMap;
import de.piet.skywars.map.MapManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
/**
 * Created by PeterH on 06.01.2016.
 */
public class MapLoader {
    public static void loadMaps() {
        File mapsFile = new File( SkyWars.getInstance().getDataFolder(), "maps.yml" );
        YamlConfiguration mapsConfiguration = YamlConfiguration.loadConfiguration( mapsFile );
        List<String> mapsList = mapsConfiguration.getStringList( "maps" );
        for( String map : mapsList ) {
            File mapFile = new File( SkyWars.getInstance().getDataFolder() + "/maps", map + ".yml" );
            YamlConfiguration mapConfiguration = YamlConfiguration.loadConfiguration( mapFile );
            ConfigMap configMap = new ConfigMap( mapConfiguration.getString( "name" ), mapConfiguration.getString( "anzeigename" ), mapConfiguration.getString( "creator" ), mapConfiguration.getString( "world" ), mapConfiguration.getString( "itemstack" ) );
            for( String spawnString : mapConfiguration.getStringList( "spawns" ) ) {
                configMap.getSpawns().add( spawnString );
            }
            for( String location : mapConfiguration.getStringList( "middlechests" ) ) {
                configMap.getMiddlechests().add( location );
            }
            MapManager.getSkyWarsMaps().add( configMap );
        }

    }
}
