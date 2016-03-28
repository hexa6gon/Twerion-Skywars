package de.piet.skywars.listener;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.spigotplugin.permissions.listener.CloudChatEvent;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Created by PeterH on 24.01.2016.
 */
public class CloudChatListener implements Listener {
    @EventHandler
    public void onCloudChat( CloudChatEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() == GameState.INGAME || GameManager.getGameState() == GameState.WARMUP ) {
            event.setCancelled( true );
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                spectatorChat( player, event.getRawMessage() );
                return;
            }
            if( event.getRawMessage().startsWith( "@all " ) ) {
                globalChat( player, event.getRawMessage().split( "@all " )[1] );
            } else if( event.getRawMessage().startsWith( "@all" ) ) {
                globalChat( player, event.getRawMessage().split( "@all" )[1] );
            } else if( event.getRawMessage().startsWith( "@a " ) ) {
                globalChat( player, event.getRawMessage().split( "@a " )[1] );
            } else if( event.getRawMessage().startsWith( "@a" ) ) {
                globalChat( player, event.getRawMessage().split( "@a" )[1] );
            } else {
                teamChat( player, event.getRawMessage() );
            }
        }
    }
    public void spectatorChat( Player player, String message ) {
        String sMSG = "§7[§4X§7] " + CloudAPI.getNickAPI().getNickName( player ) + "§7: §8" + message;
        for( Player spec : SpectatorManager.getSpectatorPlayers() ) {
            spec.sendMessage( sMSG );
        }
    }
    public void teamChat( Player player, String message ) {
        String sMSG;
        if( CloudAPI.getNickAPI().isNicked( player ) ) {
            sMSG = "§7[§6§oTeam§7] §a" + CloudAPI.getNickAPI().getNickName( player ) + ": §f" + message;
        } else {
            sMSG = "§7[§6§oTeam§7] " + CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor() + player.getName() + "§8: §f" + message;
        }
        for( Player teamPlayer : PlayerManager.getPlayerData( player ).getSkywarsTeam().getMembers( ) ) {
            teamPlayer.sendMessage( sMSG );
        }
    }
    public void globalChat( Player player, String message ) {
        String sMSG;
        if( CloudAPI.getNickAPI().isNicked( player ) ) {
            sMSG = "§8[§7§o@all§8] §a" + CloudAPI.getNickAPI().getNickName( player ) + ": §f" + message;
        } else {
            sMSG = "§8[§7§o@all§8] " + CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor() + player.getName() + "§8: §f" + message;
        }
        for( Player onlinePlayer : Bukkit.getOnlinePlayers( ) ) {
            onlinePlayer.sendMessage( sMSG );
        }
    }
}
