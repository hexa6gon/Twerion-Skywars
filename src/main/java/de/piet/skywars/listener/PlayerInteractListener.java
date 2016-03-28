package de.piet.skywars.listener;
import de.piet.cloud.spigotplugin.CloudSpigot;
import de.piet.cloud.spigotplugin.util.Api;
import de.piet.cloudsystem.packets.CommandType;
import de.piet.cloudsystem.packets.PacketType;
import de.piet.cloudsystem.packets.types.CommandPacket;
import de.piet.skywars.SkyWars;
import de.piet.skywars.chest.ChestManager;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.kits.PlayerKitData;
import de.piet.skywars.map.MapManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.team.TeamManager;
import de.piet.skywars.util.FlintUtil;
import de.piet.skywars.util.Items;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 13.01.2016.
 */
public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Player player = event.getPlayer();
        if( GameManager.getGameState() == GameState.LOBBY ) {
            event.setCancelled( true );
            ItemStack hand = player.getItemInHand();
            if( hand != null ) {
                if( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
                    if( hand.equals( Items.LobbyItems.KITS.get() ) ) {
                        if( !KitManager.getKitDatas().containsKey( player ) ) {
                            player.sendMessage( SkyWars.getPrefix( ) + "§cBitte warte kurz, deine Kits werden geladen!" );
                            PlayerKitData playerKitData = new PlayerKitData( player );
                            KitManager.getKitDatas().put( player, playerKitData );
                            playerKitData.loadBuyedKits();
                        } else {
                            PlayerKitData playerKitData = KitManager.getKitDatas().get( player );
                            if( playerKitData.isLoaded() ) {
                                player.openInventory( playerKitData.getKitInventory() );
                            } else {
                                player.sendMessage( SkyWars.getPrefix( ) + "§cDeine Kits werden noch geladen..." );
                            }
                        }
                    } else if( hand.equals( Items.LobbyItems.TEAM.get() ) ) {
                        player.openInventory( TeamManager.getSelectInventory() );
                    } else if( hand.equals( Items.LobbyItems.FORCE.get() ) ) {
                        player.openInventory( MapManager.getForceInventory() );
                    }
                }
            }
        } else if( GameManager.getGameState() == GameState.INGAME ) {
            ItemStack hand = player.getItemInHand();
            if( SpectatorManager.getSpectatorPlayers().contains( player ) ) {
                event.setCancelled( true );
                if( hand != null ) {
                    if( event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR ) {
                        if( hand.getType() == Material.COMPASS ) {
                            player.openInventory( SpectatorManager.getSpectatorInventory() );
                        } else if( hand.getType() == Material.PAPER ) {
                            CloudSpigot.getInstance( ).getApi( ).sendPacket( CloudSpigot.getInstance( ).getPacketHelper( ).preparePacket( PacketType.COMMAND_PACKET, new CommandPacket( player.getName( ), CommandType.PLAY, new String[]{ "SW" + SkywarsConfig.getType( ) } ) ), Api.PacketReceiver.WRAPPER );
                        } else if( hand.getType() == Material.WATCH ) {
                            player.kickPlayer( "lobby" );
                        }
                    }
                }
                return;
            }
            if( hand != null && hand.getType() == Material.FLINT_AND_STEEL ) {
                short durability = FlintUtil.getFlintAndSteel( ).getDurability( );
                if( hand.getDurability() > durability ) {
                    hand.setDurability( FlintUtil.getFlintAndSteel( ).getDurability( ) );
                }
            }
            if( ( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) && hand != null && hand.getType() == Material.MUSHROOM_SOUP ) {
                if( player.getHealth( ) < 20 ) {
                    PlayerData playerData = PlayerManager.getPlayerData( player );
                    if( playerData.getSkyWarsKit().equals( KitManager.getKits().get( "soup" ) ) ) {
                        player.getItemInHand( ).setType( Material.BOWL );
                        LivingEntity livingEntity = player;
                        double newHealth = player.getHealth( ) + 7;
                        if ( newHealth > 20 ) {
                            newHealth = 20;
                        }
                        livingEntity.setHealth( newHealth );
                    }
                }
            }
            if( event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK ) {
                if( event.getClickedBlock().getType() == Material.CHEST && !ChestManager.getPlacedChests().contains( event.getClickedBlock().getLocation() ) ) {
                    Chest chest = ( Chest ) event.getClickedBlock().getState();
                    if( !GameManager.getSkyWarsMap().getMiddleChests().contains( event.getClickedBlock().getLocation() ) ) {
                        ChestManager.prepareChest( chest.getBlockInventory( ), player );
                    } else {
                        ChestManager.prepareMiddleChest( chest.getBlockInventory( ), player );
                    }
                }
            }
        } else if( GameManager.getGameState() == GameState.END ) {
            event.setCancelled( true );
            ItemStack hand = player.getItemInHand();
            if( event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR ) {
                if( hand.getType() == Material.PAPER ) {
                    CloudSpigot.getInstance( ).getApi( ).sendPacket( CloudSpigot.getInstance( ).getPacketHelper( ).preparePacket( PacketType.COMMAND_PACKET, new CommandPacket( player.getName( ), CommandType.PLAY, new String[]{ "SW" + SkywarsConfig.getType( ) } ) ), Api.PacketReceiver.WRAPPER );
                } else if( hand.getType() == Material.WATCH ) {
                    player.kickPlayer( "lobby" );
                }
            }
        } else {
            event.setCancelled( true );
        }
    }
}
