package de.piet.skywars.spectator;
import com.mojang.authlib.GameProfile;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.util.ItemStackHelper;
import de.piet.skywars.util.SkullChanger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * Created by PeterH on 07.01.2016.
 */
public class SpectatorManager {
    @Getter
    private static List<Player> spectatorPlayers = new ArrayList<>(  );
    @Getter
    private static Inventory spectatorInventory;
    public static void updateSpectatorInventory() {
        int size = 0;
        if ( PlayerManager.getPlayerDatas( ).size( ) <= 9 ) {
            size = 9;
        } else if ( PlayerManager.getPlayerDatas( ).size( ) <= 18 ) {
            size = 18;
        } else if ( PlayerManager.getPlayerDatas( ).size( ) <= 27 ) {
            size = 27;
        } else if ( PlayerManager.getPlayerDatas( ).size( ) <= 36 ) {
            size = 36;
        } else {
            size = 54;
        }
        if ( spectatorInventory == null || size != spectatorInventory.getSize( ) ) {
            Inventory newInventory = Bukkit.getServer( ).createInventory( null, size, "§6Teleporter" );
            spectatorInventory = newInventory;
        }
        int currentSlot = 0;
        HashMap<Integer, ItemStack> addedItems = new HashMap<>( );
        for ( Player player : PlayerManager.getPlayerDatas( ).keySet( ) ) {
            if( currentSlot > 53 ) {
                break;
            }
            CraftPlayer entityPlayer = ( CraftPlayer ) player;
            GameProfile gameProfile = entityPlayer.getProfile();
            ItemStack skull = null;
            if( gameProfile.getProperties().containsKey( "textures" ) ) {
                skull = SkullChanger.getSkull( player );
            } else {
                skull = new ItemStack( Material.SKULL_ITEM, 1, ( short ) 3 );
            }
            ItemMeta skullMeta = skull.getItemMeta();
            skullMeta.setDisplayName( CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor() +  player.getName() );
            skull.setItemMeta( skullMeta );
            spectatorInventory.setItem( currentSlot, skull );
            addedItems.put( currentSlot, skull );
            currentSlot++;
        }
        int currentCheckSlot = 0;
        for( ItemStack content : spectatorInventory.getContents( ) ) {
            if( addedItems.containsKey( currentCheckSlot ) ) {
                if( content == null || !addedItems.get( currentCheckSlot ).equals( content ) ) {
                    spectatorInventory.setItem( currentCheckSlot, addedItems.get( currentCheckSlot ) );
                }
            } else {
                spectatorInventory.setItem( currentCheckSlot, null );
            }
            currentCheckSlot++;
        }
    }
    public static void spectatorPlayer( Player player, boolean teleport ) {
        if( !spectatorPlayers.contains( player ) ) {
            spectatorPlayers.add( player );
        }
        PlayerManager.resetPlayer( player );
        player.spigot( ).setCollidesWithEntities( false );
        ScoreboardManager.getSpectatorTeam().addEntry( player.getName( ) );
        player.setGameMode( GameMode.ADVENTURE );
        player.setAllowFlight( true );
        player.setFlying( true );
        player.addPotionEffect( new PotionEffect( PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1 ) );
        if( teleport ) {
            player.teleport( GameManager.getSkyWarsMap( ).getWorld( ).getSpawnLocation( ) );
        }
        if( !ScoreboardManager.getSpectatorTeam().hasEntry( player.getName() ) ) {
            ScoreboardManager.getSpectatorTeam().addEntry( player.getName() );
        }
        if( CloudAPI.getNickAPI().isNicked( player ) ) {
            if ( !ScoreboardManager.getSpectatorTeam( ).hasEntry( CloudAPI.getNickAPI().getNickName( player ) ) ) {
                ScoreboardManager.getSpectatorTeam( ).addEntry( CloudAPI.getNickAPI().getNickName( player ) );
            }
        }
        for( Player onlinePlayer : Bukkit.getServer( ).getOnlinePlayers() ) {
            if( PlayerManager.getPlayerDatas().get( onlinePlayer ) == null ) continue;
            onlinePlayer.hidePlayer( player );
        }
        for( Player spectatorPlayer : spectatorPlayers ) {
            if( spectatorPlayer == player ) continue;
            player.showPlayer( spectatorPlayer );
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask( SkyWars.getInstance( ), ( ) -> {
            if ( !player.isOnline( ) ) return;
            player.getInventory( ).setItem( 0, SpectateItems.COMPASS.get( ) );
            player.getInventory( ).setItem( 4, SpectateItems.NEXT_ROUND.get( ) );
            player.getInventory( ).setItem( 8, SpectateItems.HUB.get( ) );
        }, 10L );
        player.sendMessage( SkyWars.getPrefix( ) + "§7Du bist nun §6Zuschauer" );
        SpectatorScoreboard.setupSpectatorScoreboard( player );
    }
    public static void playerQuit( Player player ) {
        SpectatorScoreboard.getScoreboards().remove( player );
    }
    public enum SpectateItems {
        COMPASS( ItemStackHelper.createItemStack( Material.COMPASS, 0, "§6Teleporter §7§o<Rechtsklick>" ) ),
        HUB( ItemStackHelper.createItemStack( Material.WATCH, 0, "§6Zurück in die Lobby §7§o<Rechtsklick>" ) ),
        NEXT_ROUND( ItemStackHelper.createItemStack( Material.PAPER, 0, "§aNächste Runde §6SW" + SkywarsConfig.getType(), 1, Arrays.asList( "§7Rechtsklick um einen neuen",
                "§7SkyWars Server §6SW" + SkywarsConfig.getType( ) + " §7zu",
                "§7betreten." ) ) );
        ItemStack itemStack;
        SpectateItems( ItemStack itemStack ) {
            this.itemStack = itemStack;
        }
        public ItemStack get() {
            return itemStack;
        }
    }
}
