package de.piet.skywars.listener;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.util.PlayerRankData;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.player.LobbyScoreboard;
import de.piet.skywars.player.LobbyScoreboardManager;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.team.TeamManager;
import de.piet.skywars.util.MessageUtil;
import de.piet.skywars.util.PlayerDeathManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
/**
 * Created by PeterH on 12.01.2016.
 */
public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
        event.setQuitMessage( "" );
        if( GameManager.getGameState() == GameState.LOBBY ) {
            PlayerRankData playerRank = CloudAPI.getRankAPI().getPlayerRankData( player.getName( ) );
            if( !CloudAPI.getNickAPI( ).isNicked( player ) ) {
                MessageUtil.sendMessageToAll( "§7« " + playerRank.getRankColor( ) + player.getName( ) + " §chat das Spiel verlassen!" );
            } else {
                MessageUtil.sendMessageToAll( "§7« " + "§a" + CloudAPI.getNickAPI( ).getNickName( player ) + " §chat das Spiel verlassen!" );
            }
            TeamManager.removePlayerFromTeams( player );
            LobbyScoreboardManager.removeBoard( player );
            LobbyScoreboardManager.updateAllPlayers();
        } else if( GameManager.getGameState() == GameState.INGAME || GameManager.getGameState() == GameState.WARMUP ) {
            if( PlayerManager.getPlayerDatas().containsKey( player ) ) {
                /*
                if( GameManager.getGameState() == GameState.INGAME ) {
                    player.damage( 20 );
                } else {
                    TeamManager.playerDeath( player );
                    if( CloudAPI.getNickAPI().isNicked( player ) ) {
                        Bukkit.broadcastMessage( SkyWars.getPrefix() + "§8" + CloudAPI.getNickAPI().getNickName( player ) + " §7hat das Spiel verlassen!" );
                    } else {
                        PlayerRankData playerRank = CloudAPI.getRankAPI().getPlayerRankData( player.getName( ) );
                        Bukkit.broadcastMessage( SkyWars.getPrefix() + playerRank.getRankColor() + player.getName() +  " §7hat das Spiel verlassen!" );
                    }
                }
                */
                /*
                TeamManager.playerDeath( player );
                if( CloudAPI.getNickAPI().isNicked( player ) ) {
                    Bukkit.broadcastMessage( SkyWars.getPrefix() + "§8" + CloudAPI.getNickAPI().getNickName( player ) + " §7hat das Spiel verlassen!" );
                } else {
                    PlayerRankData playerRank = CloudAPI.getRankAPI().getPlayerRankData( player.getName( ) );
                    Bukkit.broadcastMessage( SkyWars.getPrefix() + playerRank.getRankColor() + player.getName() +  " §7hat das Spiel verlassen!" );
                }
                */
                PlayerDeathManager.playerDeath( player, true );
            } else {
                System.out.println( player.getName() + " leaved as spectator!" );
                SpectatorManager.getSpectatorPlayers( ).remove( player );
            }
        }
        SpectatorManager.playerQuit( player );
        KitManager.playerQuit( player );
    }
}
