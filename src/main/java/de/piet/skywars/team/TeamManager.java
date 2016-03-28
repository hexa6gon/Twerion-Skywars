package de.piet.skywars.team;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.spectator.SpectatorScoreboard;
import de.piet.skywars.util.ItemStackHelper;
import de.piet.skywars.util.MessageUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * Created by PeterH on 07.01.2016.
 */
public class TeamManager {
    @Getter
    private static HashMap<Integer,SkywarsTeam> teams = new HashMap<>(  );
    @Getter
    private static List<SkywarsTeam> aliveTeams = new ArrayList<>(  );
    @Getter
    private static Inventory selectInventory;
    public static SkywarsTeam getPlayersTeam( Player player ) {
        for( int slot : teams.keySet() ) {
            SkywarsTeam skywarsTeam = teams.get( slot );
            for( Player teamPlayer : skywarsTeam.getMembers() ) {
                if( teamPlayer.equals( player ) ) {
                    return skywarsTeam;
                }
            }
        }
        return null;
    }
    public static void createTeams() {
        for( int i = 0; i < SkywarsConfig.getTeams(); i++ ) {
            teams.put( i, new SkywarsTeam( i ) );
        }
        updateSelectInventory();
    }
    public static void updateSelectInventory() {
        if( selectInventory == null ) {
            int size = 0;
            if( teams.size() <= 9 ) {
                size = 9;
            } else if( teams.size() <= 18 ) {
                size = 18;
            } else if( size <= 27 ) {
                size = 27;
            } else if( size <= 36 ) {
                size = 36;
            } else if( size <= 45 ) {
                size = 45;
            } else {
                size = 54;
            }
            selectInventory = Bukkit.getServer( ).createInventory( null, size, "§6Team Auswahl" );
        }
        for( int slot : teams.keySet() ) {
            SkywarsTeam skywarsTeam = teams.get( slot );
            ItemStack setStack = null;
            if( skywarsTeam.getMembers().size() >= SkywarsConfig.getProTeam() ) {
                setStack = SelectItem.FULL.get();
            } else {
                setStack = SelectItem.CANJOIN.get();
            }
            ItemMeta itemMeta = setStack.getItemMeta();
            int teamNumber = slot;
            teamNumber++;
            itemMeta.setDisplayName( "Team #" + teamNumber );
            List<String> lore = new ArrayList<>(  );
            lore.add( skywarsTeam.getMembers( ).size( ) + "/" + SkywarsConfig.getProTeam( ) );
            lore.add( "§7----------------" );
            for( Player member : skywarsTeam.getMembers() ) {
                if( CloudAPI.getNickAPI().isNicked( member ) ) {
                    lore.add( "§a" + CloudAPI.getNickAPI().getNickName( member ) );
                } else {
                    lore.add( CloudAPI.getRankAPI().getPlayerRankData( member.getName() ).getRankColor() + member.getName() );
                }
            }
            itemMeta.setLore( lore );
            setStack.setItemMeta( itemMeta );
            selectInventory.setItem( slot, setStack );
        }
    }
    public static void playerDeath( Player player ) {
        ScoreboardManager.getTeams().get( PlayerManager.getPlayerData( player ).getSkywarsTeam( ) ).removeEntry( player.getName( ) );
        PlayerData playerData = PlayerManager.getPlayerData( player );
        if( playerData != null ) {
            if( playerData.getPlayerScoreboard() != null ) {
                playerData.getPlayerScoreboard( ).remove( );
            }
        }
        PlayerManager.getPlayerDatas().remove( player );
        removePlayerFromTeams( player );
        checkAliveTeams();
        SpectatorManager.updateSpectatorInventory( );
        SpectatorScoreboard.updatePlayers( );
        try {
            //Bukkit.getOnlinePlayers( ).forEach( ScoreboardManager::updatePlayers );
            PlayerManager.getPlayerDatas( ).keySet( ).forEach( ScoreboardManager::updatePlayers );
        }
        catch( Exception exc ) {}
    }
    public static void removePlayerFromTeams( Player player ) {
        for( int id : teams.keySet() ) {
            SkywarsTeam skywarsTeam = teams.get( id );
            if( skywarsTeam.getMembers().contains( player ) ) {
                skywarsTeam.getMembers().remove( player );
            }
        }
        for( SkywarsTeam skywarsTeam : aliveTeams ) {
            if( skywarsTeam.getMembers().contains( player ) ) {
                skywarsTeam.getMembers().remove( player );
            }
        }
    }
    public static void checkAliveTeams() {
        Iterator<SkywarsTeam> iterator = aliveTeams.iterator();
        while( iterator.hasNext() ) {
            SkywarsTeam skywarsTeam = iterator.next();
            if( skywarsTeam.getMembers().size() == 0 ) {
                //MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Das Team §6" + skywarsTeam.getNumber() + " §7ist ausgeschieden!" );
                iterator.remove();
            }
        }
        if( aliveTeams.size() == 1 ) {
            // End Game
            GameManager.endGame();
        }
    }
    public enum SelectItem {
        CANJOIN( ItemStackHelper.createItemStack( Material.STAINED_GLASS, 13, "" ) ),
        FULL( ItemStackHelper.createItemStack( Material.STAINED_GLASS, 14, "" ) );
        public ItemStack itemStack;
        SelectItem( ItemStack itemStack ) {
            this.itemStack = itemStack;
        }
        public ItemStack get() {
            return itemStack;
        }
    }
}
