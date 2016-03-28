package de.piet.skywars.listener;
import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
/**
 * Created by PeterH on 22.01.2016.
 */
public class EnchantListener implements Listener {
    private ItemStack lapis;
    public EnchantListener( ) {
        Dye dye = new Dye(  );
        dye.setColor( DyeColor.BLUE );
        lapis = dye.toItemStack();
        lapis.setAmount( 3 );
    }
    @EventHandler
    public void enchantItemEvent(EnchantItemEvent e) {
            e.getInventory().setItem( 1, this.lapis );
    }
    @EventHandler
    public void openInventoryEvent(InventoryOpenEvent e) {
        if (e.getInventory() instanceof EnchantingInventory ) {
                e.getInventory().setItem( 1, this.lapis );
        }
    }
    @EventHandler
    public void closeInventoryEvent(InventoryCloseEvent e) {
        if (e.getInventory() instanceof EnchantingInventory) {
                e.getInventory().setItem(1, null);
        }
    }
}
