package de.piet.skywars.kits;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.types.Callback;
import de.piet.skywars.SkyWars;
import de.piet.skywars.util.GlowUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by PeterH on 12.01.2016.
 */
public class PlayerKitData {
    @Getter
    private Player player;
    @Getter
    private List<SkyWarsKit> buyedKits = new ArrayList<>(  );
    @Getter
    private Inventory kitInventory;
    @Getter
    private String lastUsedKit;
    @Getter
    private boolean loaded = false;
    @Getter
    private int coins = -1;
    @Getter
    private boolean freeKits = false;
    public PlayerKitData( Player player ) {
        this.player = player;
        kitInventory = Bukkit.getServer( ).createInventory( null, 27, "§6SkyWars Kits" );
        buyedKits.add( KitManager.getDefaultKit() );

        if( CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getAccess_level() >= 70 ) {
            freeKits = true;
        }

    }
    public void removeCoins( int coins ) {
        this.coins -= coins;
    }
    public void buildKitInventory( boolean open ) {
        int current = 0;
        loaded = true;
        if( open ) {
            player.openInventory( kitInventory );
        }
        for( String kitKey : KitManager.getKits().keySet() ) {
            SkyWarsKit skyWarsKit = KitManager.getKits().get( kitKey );
            ItemStack itemStack = skyWarsKit.getViewStack( ).clone( );
            ItemMeta itemMeta = itemStack.getItemMeta( );
            List<String> lore = itemMeta.getLore();
            if( buyedKits.contains( skyWarsKit ) ) {
                lore.add( "§aGekauft" );
            } else {
                lore.add( "§cNicht gekauft" );
                lore.add( "§6Preis: §a" + skyWarsKit.getPrice() + " Coins" );
            }
            itemMeta.setLore( lore );
            itemStack.setItemMeta( itemMeta );
            if( buyedKits.contains( skyWarsKit ) ) {
                itemStack = GlowUtil.addGlowEffect( itemStack );
            }
            kitInventory.setItem( current, itemStack );
            current++;
        }
    }
    public void saveBuyedKits() {
        if( isFreeKits() ) return;
        if( buyedKits.isEmpty( ) ) return;
        String saveString = "";
        int current = 0;
        for( SkyWarsKit skyWarsKit : buyedKits ) {
            if( skyWarsKit.equals( KitManager.getDefaultKit() ) ) continue;
            current++;
            saveString += skyWarsKit.getKey();
            if( current != buyedKits.size()-1 ) {
                saveString += ",";
            }
        }
        if( !saveString.isEmpty() ) {
            CloudAPI.getDatabaseAPI().savePlayerDatabaseValue( player.getName(), SkyWars.getDatabasePrefix() + "buyedkits", saveString );
        }
    }
    public void loadBuyedKits() {
        CloudAPI.getCoinsAPI().getCoins( getPlayer( ).getName( ), o -> {
            int coins = ( int ) o;
            this.coins = coins;
        } );
        CloudAPI.getDatabaseAPI().getPlayerDatabaseValue( getPlayer( ).getName( ), SkyWars.getDatabasePrefix( ) + "buyedkits", o -> {
            String buyedString = ( String ) o;
            if( isFreeKits() ) {
                for( String key : KitManager.getKits().keySet() ) {
                    SkyWarsKit skyWarsKit = KitManager.getKits().get( key );
                    buyedKits.add( skyWarsKit );
                }
            } else {
                if ( !buyedString.isEmpty( ) ) {
                    for ( String name : buyedString.split( "," ) ) {
                        buyedKits.add( KitManager.getKits( ).get( name ) );
                    }
                }
            }
            buildKitInventory( true );
        } );
    }
}
