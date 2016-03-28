package de.piet.skywars.listener;
import de.piet.cloud.api.util.PlayerNickEvent;
import de.piet.cloud.api.util.PlayerUnnickEvent;
import de.piet.cloud.api.util.PlayerUnnickedEvent;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
/**
 * Created by PeterH on 24.01.2016.
 */
public class NickListener implements Listener {
    @EventHandler
    public void onPlayerNick( PlayerNickEvent event ) {
        Player player = event.getPlayer();
        Bukkit.getScheduler( ).runTask( SkyWars.getInstance( ), ( ) -> {
        if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
            for( Player getPlayer : PlayerManager.getPlayerDatas().keySet() ) {
                getPlayer.hidePlayer( player );
            }
        } else if( PlayerManager.getPlayerDatas().containsKey( player ) ){
            PlayerData playerData = PlayerManager.getPlayerData( player );
            ScoreboardManager.getTeams().get( playerData.getSkywarsTeam() ).addEntry( event.getNickName() );
        }
        } );
    }
    @EventHandler
    public void onPlayerUnnick( PlayerUnnickEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() == GameState.LOBBY || GameManager.getGameState() == GameState.END ) return;
        Bukkit.getScheduler( ).runTask( SkyWars.getInstance( ), ( ) -> {
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                for( Player getPlayer : PlayerManager.getPlayerDatas().keySet() ) {
                    getPlayer.hidePlayer( player );
                }
            } else {
                PlayerData playerData = PlayerManager.getPlayerData( player );
                ScoreboardManager.getTeams().get( playerData.getSkywarsTeam() ).removeEntry( event.getNickName( ) );
            }
        } );
    }
    @EventHandler
    public void onPlayerUnniked( PlayerUnnickedEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() == GameState.LOBBY || GameManager.getGameState() == GameState.END ) return;
        Bukkit.getScheduler( ).runTask( SkyWars.getInstance( ), ( ) -> {
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                for( Player getPlayer : PlayerManager.getPlayerDatas().keySet() ) {
                    getPlayer.hidePlayer( player );
                }
            }
        } );
    }
}
