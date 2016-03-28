package de.piet.skywars.chest;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
/**
 * Created by PeterH on 20.01.2016.
 */
public class ChestItem {
    @Getter
    private ItemStack itemStack;
    @Getter
    private int amount, randomAmount, minRandom;
    @Getter
    private List<Enchantment> enchantments;
    @Getter
    private List<Integer> levels;
    @Getter
    private boolean onlyMiddle;
    public ChestItem( ItemStack itemStack, int amount, int randomAmount, List<Enchantment> enchantments, List<Integer> levels, boolean onlyMiddle ) {
        this.itemStack = itemStack;
        this.amount = amount;
        this.randomAmount = randomAmount;
        this.enchantments = enchantments;
        this.levels = levels;
        this.onlyMiddle = onlyMiddle;
        if( enchantments != null && levels != null ) {
            ItemMeta itemMeta = itemStack.getItemMeta( );
            int current = 0;
            for ( Enchantment enchantment : enchantments ) {

                itemMeta.addEnchant( enchantment, levels.get( current ), true );

                current++;
            }
            itemStack.setItemMeta( itemMeta );
        }
    }
    public ChestItem( ItemStack itemStack, int amount, int randomAmount, int minRandom, List<Enchantment> enchantments, List<Integer> levels, boolean onlyMiddle ) {
        this.itemStack = itemStack;
        this.amount = amount;
        this.randomAmount = randomAmount;
        this.enchantments = enchantments;
        this.levels = levels;
        this.onlyMiddle = onlyMiddle;
        this.minRandom = minRandom;
        if( enchantments != null && levels != null ) {
            ItemMeta itemMeta = itemStack.getItemMeta( );
            int current = 0;
            for ( Enchantment enchantment : enchantments ) {

                itemMeta.addEnchant( enchantment, levels.get( current ), true );

                current++;
            }
            itemStack.setItemMeta( itemMeta );
        }
    }
}
