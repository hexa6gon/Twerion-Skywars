package de.piet.skywars.listener;
import de.piet.skywars.util.PlayerDeathManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
/**
 * Created by PeterH on 15.01.2016.
 */
public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        Player player = event.getEntity();
        event.setDeathMessage( "" );
        if( event.getEntity().getLocation().getBlockY( ) <= 0 ) {
            event.getDrops().clear();
            event.setDroppedExp( 0 );
        }
        PlayerDeathManager.playerDeath( player, false );
    }
}
