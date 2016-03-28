package de.piet.skywars.listener;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.types.CoinsAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.kits.PlayerKitData;
import de.piet.skywars.kits.SkyWarsKit;
import de.piet.skywars.map.ConfigMap;
import de.piet.skywars.map.MapManager;
import de.piet.skywars.player.LobbyScoreboardManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.team.TeamManager;
import de.piet.skywars.util.FlintUtil;
import de.piet.skywars.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 14.01.2016.
 */
public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        Player player = ( Player ) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if( GameManager.getGameState() == GameState.LOBBY ) {
            event.setCancelled( true );
            if( event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null ) {
                if( event.getClickedInventory().getTitle().equals( "§6SkyWars Kits" ) ) {
                    PlayerKitData playerKitData = KitManager.getKitDatas().get( player );
                    if( current != null ) {
                        SkyWarsKit skyWarsKit = KitManager.getKitByItemStack( current );
                        if( skyWarsKit != null ) {
                            if( !playerKitData.getBuyedKits().contains( skyWarsKit ) ) {
                                skyWarsKit.openBuyInventory( player, current );
                            } else {
                                player.closeInventory( );
                                KitManager.selectKit( player, skyWarsKit );
                            }
                        }
                    }
                } else if( event.getClickedInventory().getTitle().equals( "§6Team Auswahl" ) ) {
                    int slot = event.getSlot();
                    if( TeamManager.getTeams().containsKey( slot ) ) {
                        TeamManager.getTeams().get( slot ).playerJoin( player );
                    }
                } else if( event.getClickedInventory().getTitle().equals( "§aKit kaufen?" ) ) {
                    if( current == null || ( current.getType() != Material.EMERALD_BLOCK && !current.equals( Items.ShopItems.BACK.get() ) ) ) return;
                    PlayerKitData playerKitData = KitManager.getKitDatas().get( player );
                    if( current.equals( Items.ShopItems.BACK.get() ) ) {
                        player.openInventory( playerKitData.getKitInventory() );
                        return;
                    }
                    SkyWarsKit skyWarsKit = KitManager.getKitByItemStack( event.getClickedInventory( ).getItem( 0 ) );
                    if( skyWarsKit == null ) return;
                    if( playerKitData.getCoins() >= skyWarsKit.getPrice() ) {
                        playerKitData.removeCoins( skyWarsKit.getPrice() );
                        CloudAPI.getCoinsAPI().removeCoins( player.getName(), skyWarsKit.getPrice() );
                        player.openInventory( playerKitData.getKitInventory( ) );
                        playerKitData.getBuyedKits().add( skyWarsKit );
                        playerKitData.saveBuyedKits( );
                        player.playSound( player.getLocation( ), Sound.LEVEL_UP, 40, 1 );
                        KitManager.selectKit( player, skyWarsKit );
                        playerKitData.buildKitInventory( false );
                        player.sendMessage( SkyWars.getPrefix() + "§aDu hast das Kit §6" + skyWarsKit.getName() + " §aerfolgreich gekauft!" );
                    } else {
                        player.sendMessage( SkyWars.getPrefix( ) + "§cDu hast nicht genügend Coins für dieses Kit!" );
                    }
                } else if( event.getClickedInventory().getTitle().equalsIgnoreCase( "§6Forcemap" ) ) {
                    ConfigMap configMap = MapManager.getForceMaps().get( event.getSlot() );
                    if( configMap != null ) {
                        MapManager.setConfigMap( configMap );
                        player.sendMessage( SkyWars.getPrefix( ) + "§7Die Map §6" + configMap.getAnzeigename( ) + " §7wird gespielt!" );
                        MapManager.updateServerState( );
                        LobbyScoreboardManager.updateAllMap();
                    }
                }
            }
        } else if( GameManager.getGameState() == GameState.INGAME ) {
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
                if( event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null && event.getClickedInventory().getTitle().equalsIgnoreCase( "§6Teleporter" ) ) {
                    if( current != null ) {
                        if( current.getType() == Material.SKULL_ITEM ) {
                            String name = ChatColor.stripColor( current.getItemMeta().getDisplayName() );
                            Player getPlayer = Bukkit.getPlayer( name );
                            if( getPlayer == null ) return;
                            player.teleport( getPlayer );
                            player.closeInventory();
                        }
                    }
                }
            }
        }
        if( GameManager.getGameState() == GameState.INGAME || GameManager.getGameState() == GameState.WARMUP ) {
            if( current != null && current.getType() == Material.FLINT_AND_STEEL ) {
                short durability = FlintUtil.getFlintAndSteel().getDurability( );
                if( current.getDurability() > durability ) {
                    current.setDurability( FlintUtil.getFlintAndSteel( ).getDurability( ) );
                }
            }
        }
    }
}
