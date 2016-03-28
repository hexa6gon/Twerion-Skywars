package de.piet.skywars.listener;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
/**
 * Created by PeterH on 21.01.2016.
 */
public class ItemListener implements Listener {
    @EventHandler
    public void onItemDrop( PlayerDropItemEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() != GameState.INGAME ) {
            event.setCancelled( true );
            return;
        }
        if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
            event.setCancelled( true );
        }
    }
    @EventHandler
    public void onItemPickup( PlayerPickupItemEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() != GameState.INGAME ) {
            event.setCancelled( true );
            return;
        }
        if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
            event.setCancelled( true );
        }
    }
}
