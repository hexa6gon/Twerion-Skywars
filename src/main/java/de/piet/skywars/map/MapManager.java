package de.piet.skywars.map;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.types.CloudServerState;
import de.piet.skywars.config.SkywarsConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * Created by PeterH on 06.01.2016.
 */
public class MapManager {
    @Getter
    private static List<ConfigMap> skyWarsMaps = new ArrayList<>(  );
    @Getter
    private static HashMap<Integer,ConfigMap> forceMaps = new HashMap<>(  );
    @Getter
    @Setter
    private static ConfigMap configMap;
    @Getter
    private static Inventory forceInventory;
    public static void createForceMapInventory() {
        forceInventory = Bukkit.getServer( ).createInventory( null, 54, "§6Forcemap" );
        int count = 0;
        for( ConfigMap configMap : skyWarsMaps ) {
            forceInventory.setItem( count, configMap.getItemStack() );
            forceMaps.put( count, configMap );
            count++;
        }
    }
    public static void updateServerState() {
        CloudAPI.getServerAPI().changeServer( CloudServerState.LOBBY, configMap.getAnzeigename() + " " + SkywarsConfig.getType() );
    }
    public static void selectRandomMap() {
        createForceMapInventory( );
        int random = Math.abs(new Random(  ).nextInt()) % skyWarsMaps.size();
        System.out.println( "Random: " + random );
        ConfigMap configMap = skyWarsMaps.get( random );
        setConfigMap( configMap );
        updateServerState();
    }
}
