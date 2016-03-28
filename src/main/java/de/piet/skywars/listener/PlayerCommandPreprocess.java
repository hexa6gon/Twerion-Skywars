package de.piet.skywars.listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/**
 * Created by PeterH on 31.01.2016.
 */
public class PlayerCommandPreprocess implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess( PlayerCommandPreprocessEvent event ) {
        if( ! ( event.getMessage().equalsIgnoreCase( "/start" ) || event.getMessage().equalsIgnoreCase( "/stats" )  || event.getMessage().startsWith( "/stats" )  || event.getPlayer().isOp() || event.getMessage().equalsIgnoreCase( "/reports" )) ) {
            event.setCancelled( true );
            event.getPlayer().sendMessage( "§8[§6Twerion§8] §cDieser Befehl existiert nicht!" );
        }
    }
}
