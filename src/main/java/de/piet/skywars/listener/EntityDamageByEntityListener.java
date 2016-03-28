package de.piet.skywars.listener;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
/**
 * Created by PeterH on 18.01.2016.
 */
public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity( EntityDamageByEntityEvent event ) {
        if( GameManager.getGameState() != GameState.INGAME ) {
            event.setCancelled( true );
            return;
        }
        if( event.getDamager( ) instanceof Player ) {
            Player player = ( Player ) event.getDamager();
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
                return;
            }
        }
        if( event.getEntity( ) instanceof Player ) {
            if( GameManager.isPeace() ) {
                event.setCancelled( true );
                return;
            }
            Player player = ( Player ) event.getEntity();
            PlayerData playerData = PlayerManager.getPlayerData( player );
            if( playerData == null ) {
                event.setCancelled( true );
                return;
            }
            if( event.getDamager( ) instanceof Player ) {
                Player damager = ( Player ) event.getDamager( );
                PlayerData damagerData = PlayerManager.getPlayerData( damager );
                if( damagerData == null ) {
                    event.setCancelled( true );
                    return;
                }
                if( damagerData.getSkywarsTeam() != null && playerData.getSkywarsTeam() != null && damagerData.getSkywarsTeam().equals( playerData.getSkywarsTeam() ) ) {
                    event.setCancelled( true );
                    return;
                }
                playerData.receiveDamageFrom( damager, false );
            } else if( event.getDamager() instanceof Projectile ) {
                Projectile projectile = ( Projectile ) event.getDamager();
                if( projectile.getShooter() instanceof Player ) {
                    Player damager = ( Player ) projectile.getShooter();
                    PlayerData damagerData = PlayerManager.getPlayerData( damager );
                    if( damagerData == null ) {
                        event.setCancelled( true );
                        return;
                    }
                    if( SpectatorManager.getSpectatorPlayers().contains( damager ) ) {
                        event.setCancelled( true );
                        return;
                    }
                    if( damagerData.getSkywarsTeam() != null && playerData.getSkywarsTeam() != null && damagerData.getSkywarsTeam().equals( playerData.getSkywarsTeam() ) ) {
                        event.setCancelled( true );
                        return;
                    }
                    playerData.receiveDamageFrom( damager, true );
                }
            }
        }
    }
}
