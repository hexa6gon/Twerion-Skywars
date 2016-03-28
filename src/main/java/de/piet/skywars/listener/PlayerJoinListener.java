package de.piet.skywars.listener;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.util.PlayerRankData;
import de.piet.cloudsystem.packets.types.PartyMemberPacket;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.LobbyScoreboard;
import de.piet.skywars.player.LobbyScoreboardManager;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.player.party.PartyManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.util.Items;
import de.piet.skywars.util.Locations;
import de.piet.skywars.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by PeterH on 11.01.2016.
 */
public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        Player player = event.getPlayer();
        event.setJoinMessage( "" );
        PlayerManager.resetPlayer( player );
        PlayerRankData playerRank = CloudAPI.getRankAPI().getPlayerRankData( player.getName( ) );
        if( GameManager.getGameState() == GameState.LOBBY ) {
            player.setGameMode( GameMode.ADVENTURE );
            if( CloudAPI.getNickAPI().isNicked( player ) ) {
                MessageUtil.sendMessageToAll( "§7» " + "§a" + CloudAPI.getNickAPI( ).getNickName( player ) + " §ahat das Spiel betreten! §7[§e" + Bukkit.getOnlinePlayers( ).size( ) + "§8/§e" + CloudAPI.getServerAPI( ).getServerMaxPlayers( ) + "§7]" );
            } else {
                MessageUtil.sendMessageToAll( "§7» " + playerRank.getRankColor( ) + player.getName( ) + " §ahat das Spiel betreten! §7[§e" + Bukkit.getOnlinePlayers( ).size( ) + "§8/§e" + CloudAPI.getServerAPI( ).getServerMaxPlayers( ) + "§7]" );
            }
            if( playerRank.getAccess_level( ) >= 70 ) {
                player.sendMessage( SkyWars.getPrefix( ) + "§7Da du " + playerRank.getRankColor( ) + playerRank.getRankName( ) + " §7bist, kannst du §6Maps forcen §7und §6/start §7ausführen!" );
            }
            if( SkywarsConfig.getProTeam( ) != 1 ) {
                player.getInventory().setItem( 0, Items.LobbyItems.TEAM.get( ) );
                player.getInventory().setItem( 1, Items.LobbyItems.KITS.get() );
                if( playerRank.getAccess_level( ) >= 70 ) {
                    player.getInventory().setItem( 2, Items.LobbyItems.FORCE.get() );
                }
            } else {
                player.getInventory().setItem( 0, Items.LobbyItems.KITS.get( ) );
                if( playerRank.getAccess_level( ) >= 70 ) {
                    player.getInventory().setItem( 1, Items.LobbyItems.FORCE.get() );
                }
            }
            if( PartyManager.getPartyMembers( ).containsKey( player.getName( ).toLowerCase( ) ) ) {
                PartyMemberPacket partyMemberPacket = PartyManager.getPartyMembers().get( player.getName().toLowerCase() );
                List<Player> onlinePartyPlayers = new ArrayList<>(  );
                for( String member : partyMemberPacket.getMembers() ) {
                    Player getPlayer = Bukkit.getServer().getPlayer( member );
                    if( getPlayer != null ) {
                        onlinePartyPlayers.add( getPlayer );
                    }
                }
                if( onlinePartyPlayers.size( ) == partyMemberPacket.getMembers().size() ) {
                    PartyManager.allPlayersOnline( onlinePartyPlayers );
                }
            }
            LobbyScoreboardManager.createBoard( player );
            LobbyScoreboardManager.updateAllPlayers();
        } else if( GameManager.getGameState() == GameState.INGAME || GameManager.getGameState() == GameState.WARMUP ) {
            SpectatorManager.spectatorPlayer( player, true );
            return;
        } else if( GameManager.getGameState() == GameState.END ) {
            player.sendMessage( SkyWars.getPrefix() + "§cDas Spiel ist bereits vorbei, der Server stoppt gleich!" );
        }
        player.teleport( Locations.getLobbyLocation( ) );
    }
}
