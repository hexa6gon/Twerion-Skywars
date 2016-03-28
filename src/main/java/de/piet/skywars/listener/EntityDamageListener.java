package de.piet.skywars.listener;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
/**
 * Created by PeterH on 18.01.2016.
 */
public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage( EntityDamageEvent event ) {
        if( GameManager.getGameState() != GameState.INGAME ) {
            event.setCancelled( true );
            return;
        }
        if( event.getEntity() instanceof Player ) {
            if( GameManager.isPeace() ) {
                event.setCancelled( true );
                return;
            }
            Player player = ( Player ) event.getEntity();
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
                return;
            }
        }
    }
}
