package de.piet.skywars.listener;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/**
 * Created by PeterH on 23.01.2016.
 */
public class EntityTargetLivingEntityListener implements Listener {
    @EventHandler
    public void onEntityTargetLivingEntityEvent( EntityTargetLivingEntityEvent event ) {
        if( event.getTarget() instanceof Player ) {
            Player player = ( Player ) event.getTarget();
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
            }
        }
    }
}
