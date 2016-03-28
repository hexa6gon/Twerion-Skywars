package de.piet.skywars.listener;
import de.piet.skywars.SkyWars;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 18.02.2016.
 */
public class PlayerInteractEntityListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity( PlayerInteractEntityEvent event ) {
        Player player = event.getPlayer( );
        if( event.getRightClicked().getType().equals( EntityType.MUSHROOM_COW ) && player.getItemInHand() != null && player.getItemInHand().getType().equals( Material.BOWL )) {
            PlayerData playerData = PlayerManager.getPlayerData( player );
            if( playerData.getSkyWarsKit().equals( KitManager.getKits().get( "soup" ) ) ) {
                int amount = 0;
                for( ItemStack content : player.getInventory().getContents() ) {
                    if( content != null && content.getType().equals( Material.MUSHROOM_SOUP ) ) {
                        amount++;
                    }
                }
                if( amount >= 5 ) {
                    player.sendMessage( SkyWars.getPrefix() + "§cDu kannst maximal 5 Suppen in deinem Inventar haben!" );
                    event.setCancelled( true );
                    player.updateInventory();
                }
            } else {
                event.setCancelled( true );
                player.sendMessage( SkyWars.getPrefix() + "§cDas ist nur mit dem Soup Kit möglich!" );
                event.setCancelled( true );
                player.updateInventory();
            }
        }
    }
}
