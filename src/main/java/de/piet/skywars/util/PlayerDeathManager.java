package de.piet.skywars.util;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.util.Title;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 31.01.2016.
 */
public class PlayerDeathManager {
    public static void playerDeath( Player player, boolean quit ) {
        PlayerData playerData = PlayerManager.getPlayerData( player );
        Player killer = playerData.getLastHitted();
        if( killer != null && PlayerManager.getPlayerDatas().containsKey( killer ) ) {
            PlayerData killerData = PlayerManager.getPlayerData( killer );
            String colorPlayer, namePlayer, colorKiller, nameKiller;
            if( CloudAPI.getNickAPI( ).isNicked( player ) ) {
                colorPlayer = "§a";
                namePlayer = CloudAPI.getNickAPI().getNickName( player );
            } else {
                colorPlayer = CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor();
                namePlayer = player.getName();
            }
            if( CloudAPI.getNickAPI().isNicked( killer ) ) {
                colorKiller = "§a";
                nameKiller = CloudAPI.getNickAPI().getNickName( killer );
            } else {
                colorKiller = CloudAPI.getRankAPI().getPlayerRankData( killer.getName() ).getRankColor();
                nameKiller = killer.getName();
            }
            Bukkit.broadcastMessage( SkyWars.getPrefix( ) + colorPlayer + namePlayer + " §7wurde von " + colorKiller + nameKiller + " §7getötet!" );
            killer.playSound( killer.getLocation( ), Sound.LEVEL_UP, 40, 1 );
            if( playerData.isProjectileHit() ) {
                killerData.addProjectile();
            }
            killerData.addKill( );
            ScoreboardManager.updateKills( killer );
        } else {
            if( CloudAPI.getNickAPI().isNicked( player ) ) {
                Bukkit.broadcastMessage( SkyWars.getPrefix( ) + "§a" + CloudAPI.getNickAPI( ).getNickName( player ) + " §7ist gestorben!" );
            } else {
                Bukkit.broadcastMessage( SkyWars.getPrefix() + CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor() + player.getName() + " §7ist gestorben!" );
            }
        }
        playerData.addDeath( );
        playerData.saveStats( );
        TeamManager.playerDeath( player );
        if( quit ) {
            for( ItemStack itemStack : player.getInventory().getContents() ) {
                if( itemStack == null || itemStack.getType() == Material.AIR ) continue;
                player.getWorld().dropItemNaturally( player.getLocation(), itemStack );
            }
            for( ItemStack itemStack : player.getInventory().getArmorContents() ) {
                if( itemStack == null || itemStack.getType() == Material.AIR ) continue;
                player.getWorld().dropItemNaturally( player.getLocation(), itemStack );
            }
            return;
        }
        if( player.getLocation( ).getBlockY() <= 0 ) {
            SpectatorManager.spectatorPlayer( player, true );
        } else {
            SpectatorManager.spectatorPlayer( player, false );
        }
        new Title( "§cDu bist gestorben", "", 0, 2, 1 ).send( player );
    }
}
