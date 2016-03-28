package de.piet.skywars.util;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
/**
 * Created by PeterH on 07.01.2016.
 */
public class ItemStackHelper {
    public static ItemStack createItemStack( Material material, int data, String name ) {
        ItemStack itemStack = null;
        if( data == 0 ) {
            itemStack = new ItemStack( material, 1 );
        } else {
            itemStack = new ItemStack( material, 1, ( short ) data );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( name );
        itemStack.setItemMeta( itemMeta );
        return itemStack;
    }
    public static ItemStack createItemStack( Material material, int data ) {
        ItemStack itemStack = null;
        if( data == 0 ) {
            itemStack = new ItemStack( material, 1 );
        } else {
            itemStack = new ItemStack( material, 1, ( short ) data );
        }
        return itemStack;
    }
    public static ItemStack createItemStack( Material material, int data, String name, int amount ) {
        ItemStack itemStack = new ItemStack( material, amount, ( short ) data );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( name );
        itemStack.setItemMeta( itemMeta );
        return itemStack;
    }
    public static ItemStack createItemStack( Material material, int data, String name, int amount, List<String> lore ) {
        ItemStack itemStack = null;
        if( data == 0 ) {
            itemStack = new ItemStack( material, 1 );
        } else {
            itemStack = new ItemStack( material, 1, ( short ) data );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore( lore );
        itemMeta.setDisplayName( name );
        itemStack.setItemMeta( itemMeta );
        return itemStack;
    }
    public static ItemStack getItem( Material material, String name, List<String> lore, int amount, int data, List<Enchantment> enchantments, List<Integer> level  ) {

        ItemStack itemStack = new ItemStack( material, amount, ( byte ) data );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( name );
        itemMeta.setLore( lore );
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant( enchantment, level.get( current ), true );

            current++;
        }
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, List<String> lore, int amount, List<Enchantment> enchantments, List<Integer> level ) {

        ItemStack itemStack = new ItemStack( material, amount );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant(enchantment, level.get(current), true);

            current++;
        }
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, int amount ) {

        ItemStack itemStack = new ItemStack( material, amount );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, int amount, List<Enchantment> enchantments, List<Integer> level  ) {

        ItemStack itemStack = new ItemStack( material, amount );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant(enchantment, level.get(current), true);

            current++;
        }

        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, int amount, List<Enchantment> enchantments, List<Integer> level, Color color  ) {

        ItemStack itemStack = new ItemStack( material, amount );
        LeatherArmorMeta itemMeta = ( LeatherArmorMeta ) itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant(enchantment, level.get(current), true);

            current++;
        }

        itemMeta.setColor( color );

        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, int amount, int data ) {

        ItemStack itemStack = new ItemStack( material, amount, ( byte ) data );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( name );
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, String name, int amount, int data, List<Enchantment> enchantments, List<Integer> level ) {

        ItemStack itemStack = new ItemStack( material, amount, ( byte ) data );
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant(enchantment, level.get(current), true);

            current++;
        }
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack getItem( Material material, int amount, int data, List<Enchantment> enchantments, List<Integer> level ) {

        ItemStack itemStack = null;
        if( data != 0 ) {
            itemStack = new ItemStack( material, amount, ( byte ) data );
        } else {
            itemStack = new ItemStack( material, amount );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        int current = 0;
        for( Enchantment enchantment : enchantments ) {

            itemMeta.addEnchant(enchantment, level.get(current), true);

            current++;
        }
        itemStack.setItemMeta( itemMeta );

        return itemStack;

    }

    public static ItemStack setName( ItemStack itemStack, String name ) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( name );
        itemStack.setItemMeta( itemMeta );
        return itemStack;
    }
}
