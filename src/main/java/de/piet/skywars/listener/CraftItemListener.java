package de.piet.skywars.listener;
import de.piet.skywars.util.FlintUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
/**
 * Created by PeterH on 23.01.2016.
 */
public class CraftItemListener implements Listener {
    @EventHandler
    public void onCraftItem( CraftItemEvent craftItemEvent ) {
        if( craftItemEvent.getCurrentItem( ).getType() == Material.FLINT_AND_STEEL ) {
            craftItemEvent.setCurrentItem( FlintUtil.getFlintAndSteel() );
        }
    }
}
