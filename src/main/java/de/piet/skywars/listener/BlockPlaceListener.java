package de.piet.skywars.listener;
import de.piet.skywars.chest.ChestManager;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
/**
 * Created by PeterH on 24.01.2016.
 */
public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() == GameState.INGAME ) {
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) return;
            PlayerManager.getPlayerData( player ).addPlacedBlock();
            if( event.getBlock().getType() == Material.CHEST ) {
                ChestManager.getPlacedChests().add( event.getBlock().getLocation() );
            }
        }
    }
}
