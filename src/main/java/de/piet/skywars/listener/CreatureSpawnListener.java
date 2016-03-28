package de.piet.skywars.listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
/**
 * Created by PeterH on 22.01.2016.
 */
public class CreatureSpawnListener implements Listener {
    @EventHandler
    public void onCreatureSpawn( CreatureSpawnEvent event ) {
        if( event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG ) {
            event.setCancelled( true );
        }
    }
}
