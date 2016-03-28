package de.piet.skywars.player;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.kits.PlayerKitData;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.team.SkywarsTeam;
import de.piet.skywars.team.TeamManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
/**
 * Created by PeterH on 07.01.2016.
 */
public class PlayerManager {
    @Getter
    private static HashMap<Player,PlayerData> playerDatas = new HashMap<>(  );
    public static PlayerData getPlayerData( Player player ) {
        return playerDatas.get( player );
    }
    public static void startGameFor( Player player ) {
        PlayerManager.resetPlayer( player );
        SkywarsTeam getTeam = TeamManager.getPlayersTeam( player );
        if( getTeam == null ) {
            for( int slot : TeamManager.getTeams().keySet() ) {
                SkywarsTeam forTeam = TeamManager.getTeams().get( slot );
                if( forTeam.getMembers().size() < SkywarsConfig.getProTeam() ) {
                    getTeam = forTeam;
                    break;
                }
            }
            if( getTeam == null ) {
                SpectatorManager.spectatorPlayer( player, true );
                player.sendMessage( SkyWars.getPrefix() + "§cEs konnte kein freies Team für dich gefunden werden!" );
                return;
            } else {
                getTeam.playerJoin( player );
            }
        } else {
            player.sendRawMessage( SkyWars.getPrefix() + "§7Du bist in Team §a" + getTeam.getNumber() );
        }
        int teleportCount = getTeam.getNumber();
        if( GameManager.getSkyWarsMap().getSpawnLocations().size() >= getTeam.getNumber() ) {
            PlayerData playerData = new PlayerData( player, getTeam );
            if( KitManager.getKitDatas().containsKey( player ) && KitManager.getPlayerKits().containsKey( player ) ) {
                    playerData.setSkyWarsKit( KitManager.getPlayerKits().get( player ) );
                    player.sendMessage( SkyWars.getPrefix() + "§7Du spielst mit dem Kit §a" + playerData.getSkyWarsKit().getName() );
            } else {
                KitManager.useLastSelectedKit( player );
            }
            if( !TeamManager.getAliveTeams().contains( getTeam ) ) {
                TeamManager.getAliveTeams().add( getTeam );
            }
            playerDatas.put( player, playerData );
            player.spigot().setCollidesWithEntities( true );
            playerData.addPlayedGame( );
            getTeam.setSpawnLocation( GameManager.getSkyWarsMap( ).getSpawnLocations( ).get( teleportCount ) );
            player.teleport( GameManager.getSkyWarsMap( ).getSpawnLocations( ).get( teleportCount ) );
            ScoreboardManager.getTeams().get( getTeam ).addEntry( player.getName() );
            if( CloudAPI.getNickAPI().isNicked( player ) ) {
                ScoreboardManager.getTeams().get( getTeam ).addEntry( CloudAPI.getNickAPI().getNickName( player ) );
            }
            if( playerData.getSkyWarsKit() != null ) {
                ScoreboardManager.setupScoreboard( playerData );
                playerData.getSkyWarsKit().giveItems( player );
            }
            if( playerData.getSkyWarsKit() != null && !playerData.getSkyWarsKit().equals( KitManager.getDefaultKit() ) ) {
                CloudAPI.getDatabaseAPI().savePlayerDatabaseValue( player.getName(), SkyWars.getDatabasePrefix() + "lastkit", playerData.getSkyWarsKit().getKey() );
            }
            player.setScoreboard( ScoreboardManager.getScoreboard() );
        } else {
            player.sendMessage( SkyWars.getPrefix( ) + "§cFür dein Team konnte kein SpawnPunkt gefunden werden!" );
            SpectatorManager.spectatorPlayer( player, true );
        }
        player.sendMessage( SkyWars.getPrefix() + "§cTeamen mit anderen Teams ist verboten und wird mit einem Ban bestraft!" );
    }
    public static void resetPlayer( Player player ) {
        player.setGameMode( GameMode.SURVIVAL );
        player.getInventory().clear( );
        player.getInventory().setArmorContents( null );
        player.setHealth( 20 );
        player.setFoodLevel( 20 );
        player.setExp( 0 );
        player.setAllowFlight( false );
        for( PotionEffect potionEffect : player.getActivePotionEffects() ) {
            player.removePotionEffect( potionEffect.getType() );
        }
    }
}
