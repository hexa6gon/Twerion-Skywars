package de.piet.skywars.kits;
import de.piet.skywars.SkyWars;
import de.piet.skywars.util.Items;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by PeterH on 12.01.2016.
 */
public class SkyWarsKit {
    @Getter
    private String name, key;
    @Getter
    private ItemStack viewStack;
    @Getter
    private List<String> description = new ArrayList<>(  );
    @Getter
    private List<ItemStack> addItems = new ArrayList<>(  );
    @Getter
    private int price;
    public SkyWarsKit( String name, String key, ItemStack viewStack, int price ) {
        this.name = name;
        this.key = key;
        this.viewStack = viewStack;
        this.price = price;
    }
    public void buildViewStack() {
        ItemMeta itemMeta = getViewStack().getItemMeta( );
        List<String> newLore = new ArrayList<>(  );
        newLore.add( "§eAusrüstung:" );
        for( String s : description ) {
            newLore.add( "§7" + s );
        }
        itemMeta.setLore( newLore );
        viewStack.setItemMeta( itemMeta );
    }
    public void openBuyInventory( Player player, ItemStack clicked ) {
        PlayerKitData playerKitData = KitManager.getKitDatas().get( player );
        if( playerKitData.getCoins() == -1 ) {
            player.sendMessage( SkyWars.getPrefix() + "§cFehler: Deine Coins wurden noch nicht geladen!" );
            return;
        }
        Inventory inventory = Bukkit.getServer( ).createInventory( null, 9, "§aKit kaufen?" );
        player.openInventory( inventory );
        clicked = clicked.clone();
        ItemMeta itemMeta = clicked.getItemMeta();
        itemMeta.setLore( null );
        clicked.setItemMeta( itemMeta );
        inventory.setItem( 0, clicked );

        ItemStack info = Items.ShopItems.INFO.get().clone();
        ItemMeta infoM = info.getItemMeta();
        infoM.setLore( description );
        info.setItemMeta( infoM );
        inventory.setItem( 1, info );

        ItemStack buy = Items.ShopItems.BUY.get().clone();
        ItemMeta buyM = buy.getItemMeta();
        if( playerKitData.getCoins() >= getPrice() ) {
            buyM.setDisplayName( "§aDu kannst dieses Kit für §e" + getPrice() + " Coins §akaufen!" );
        } else {
            int need = getPrice() - playerKitData.getCoins();
            buyM.setDisplayName( "§cDir fehlen §e" + need + " Coins §cum dieses Kit kaufen zu können!" );
            buyM.setLore( Arrays.asList( "§7So bekommst du Coins:", "§8- §aMinigames auf Twerion spielen", "§8- §aVoten /vote", "§8- §aIm Onlineshop unter shop.twerion.net") );
        }
        buy.setItemMeta( buyM );
        inventory.setItem( 7, buy );
        inventory.setItem( 8, Items.ShopItems.BACK.get() );

    }
    public void giveItems( Player player ) {
        for( ItemStack itemStack : addItems ) {
            player.getInventory().addItem( itemStack );
        }
    }
}
