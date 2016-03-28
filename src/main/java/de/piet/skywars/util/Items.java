package de.piet.skywars.util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 10.01.2016.
 */
public class Items {
    public enum LobbyItems {
        KITS( ItemStackHelper.createItemStack( Material.CHEST, 0, "§6Kit Auswahl" ) ),
        TEAM( ItemStackHelper.createItemStack( Material.BED, 0, "§6Team Auswahl" ) ),
        FORCE( ItemStackHelper.createItemStack( Material.PAPER, 0, "§6Map forcen" ) );
        ItemStack itemStack;
        LobbyItems( ItemStack itemStack ) {
            this.itemStack = itemStack;
        }
        public ItemStack get() {
            return itemStack;
        }
    }
    public enum ShopItems {
        INFO( ItemStackHelper.createItemStack( Material.BOOK, 0, "§6Kit Information" ) ),
        BUY( ItemStackHelper.createItemStack( Material.EMERALD_BLOCK, 0, "§aKit kaufen" ) ),
        BACK( ItemStackHelper.createItemStack( Material.IRON_DOOR, 0, "§7Zurück" ) );
        ItemStack itemStack;
        ShopItems( ItemStack itemStack ) {
            this.itemStack = itemStack;
        }
        public ItemStack get() {
            return itemStack;
        }
    }
}
