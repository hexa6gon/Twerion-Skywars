package de.piet.skywars.listener;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
/**
 * Created by PeterH on 07.02.2016.
 */
public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        Player player = event.getPlayer();
        if( event.getBlock().getType( ) == Material.SNOW_BLOCK || event.getBlock().getType() == Material.SNOW ) {
            if ( GameManager.getGameState( ) == GameState.INGAME ) {
                PlayerData playerData = PlayerManager.getPlayerData( player );
                if( playerData == null ) return;
                if( !playerData.getSkyWarsKit().equals( KitManager.getKits().get( "schneemann" ) ) ) {
                    event.setCancelled( true );
                    event.getBlock().setType( Material.AIR );
                }
            }
        }
    }
}
