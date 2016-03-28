package de.piet.skywars.listener;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
/**
 * Created by PeterH on 21.01.2016.
 */
public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove( PlayerMoveEvent playerMoveEvent ) {
        if( GameManager.getGameState() == GameState.WARMUP ) {
            Player player = playerMoveEvent.getPlayer( );
            PlayerData playerData = PlayerManager.getPlayerData( player );
            if( playerData.getSkywarsTeam() == null || playerData.getSkywarsTeam().getSpawnLocation() == null ) return;
            Location loc = playerData.getSkywarsTeam().getSpawnLocation();
            Location to = playerMoveEvent.getTo();
            if( loc.getBlockX() != to.getBlockX() || loc.getBlockZ() != to.getBlockZ() ) {
                playerMoveEvent.setCancelled( true );
            }
        }
    }
}
