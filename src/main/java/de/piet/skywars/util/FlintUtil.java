package de.piet.skywars.util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
/**
 * Created by PeterH on 23.01.2016.
 */
public class FlintUtil {
    public static void changeFlint( ItemStack itemStack ) {
        itemStack.setDurability( ( short ) 32 );
    }
    public static ItemStack getFlintAndSteel() {
        ItemStack itemStack = new ItemStack( Material.FLINT_AND_STEEL, 1 );
        itemStack.setDurability( ( short ) 32 );
        return itemStack;
    }
}
