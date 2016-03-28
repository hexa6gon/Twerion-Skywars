package de.piet.skywars;
import com.google.common.reflect.ClassPath;
import de.piet.cloud.spigotplugin.CloudSpigot;
import de.piet.cloudsystem.packets.types.CloudServerCallBack;
import de.piet.skywars.commands.start;
import de.piet.skywars.commands.swadmin;
import de.piet.skywars.game.StartupManager;
import de.piet.skywars.player.party.PartyPacketListener;
import de.piet.skywars.util.Locations;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
/**
 * Created by PeterH on 06.01.2016.
 */
public class SkyWars extends JavaPlugin {
    @Getter
    private static SkyWars instance;
    @Getter
    private static String prefix = "§8[§eSkyWars§8] ";
    @Getter
    private static String databasePrefix = "skywars_";
    @Getter
    private static String statsPrefix = "sw_";
    @Getter
    @Setter
    private static boolean testModus = false;
    @Override
    public void onEnable ( ) {
        instance = this;
        // Register Listeners
        PluginManager pluginManager = getServer().getPluginManager();
        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( getClassLoader() ).getTopLevelClasses( "de.piet.skywars.listener" ) ) {
                Class clazz = Class.forName( classInfo.getName() );

                if ( Listener.class.isAssignableFrom( clazz ) ) {
                    pluginManager.registerEvents( (Listener) clazz.newInstance(), this );
                }
            }
        } catch ( IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
            e.printStackTrace();
        }
        // StartUP Skywars
        Locations.initLocs( );
        StartupManager.startupSkywars();

        // Register Commands
        getCommand( "swadmin" ).setExecutor( new swadmin() );
        getCommand( "start" ).setExecutor( new start() );

        // Register Party Packet listener
        CloudSpigot.getInstance().getWrapperClient().addGetter( new PartyPacketListener() );
    }
}
