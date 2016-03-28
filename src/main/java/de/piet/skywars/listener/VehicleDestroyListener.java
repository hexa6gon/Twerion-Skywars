package de.piet.skywars.listener;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
/**
 * Created by PeterH on 23.01.2016.
 */
public class VehicleDestroyListener implements Listener {
    @EventHandler
    public void onVehicleDestroy( VehicleDestroyEvent event ) {
        if( event.getAttacker() instanceof Player ) {
            Player player = ( Player ) event.getAttacker();
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
            }
        }
    }
}
