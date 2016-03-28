package de.piet.skywars.listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
/**
 * Created by PeterH on 02.02.2016.
 */
public class LeavesDecayListener implements Listener {
    @EventHandler
    public void onLeavesDecay( LeavesDecayEvent event ) {
        event.setCancelled( true );
    }
}
