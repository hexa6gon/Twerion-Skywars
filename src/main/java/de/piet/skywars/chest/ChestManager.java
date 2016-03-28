package de.piet.skywars.chest;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.util.FlintUtil;
import de.piet.skywars.util.ItemStackHelper;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by PeterH on 20.01.2016.
 */
public class ChestManager {
    private static RandomCollection randomCollection, middleCollection;
    @Getter
    private static List<Inventory> addedInventories = new ArrayList<>(  );
    @Getter
    private static List<Location> placedChests = new ArrayList<>(  );
    private static Random random;
    public static void prepareChest( Inventory inventory, Player player ) {
        if( !addedInventories.contains( inventory ) ) {
            addedInventories.add( inventory );
            List<Integer> addedSlots = new ArrayList<>(  );
            List<Material> addedMaterials = new ArrayList<>(  );
            List<String> addedTypes = new ArrayList<>(  );
            int itemAmount;
            for( ;; ) {
                itemAmount = (int) ( Math.random() * 16 );
                if( itemAmount >= 8 ) break;
            }
            while( itemAmount >= 1 ) {
                itemAmount--;
                for( ;; ) {
                    int randomSlot = ( int ) ( Math.random() * 26 );
                    if( !addedSlots.contains( randomSlot ) ) {
                        addedSlots.add( randomSlot );
                        for( ;; ) {
                            ChestItem chestItem = ( ChestItem ) randomCollection.next();
                            ItemStack randomStack = chestItem.getItemStack();
                            if( !addedMaterials.contains( randomStack.getType() ) ) {
                                if( randomStack.getType().name().contains( "_" ) ) {
                                    String type = randomStack.getType().name().split( "_" )[1];
                                    if( addedTypes.contains( type ) ) {
                                        continue;
                                    }
                                    addedTypes.add( type );
                                }
                                addedMaterials.add( randomStack.getType() );
                                ItemStack setStack = randomStack.clone();
                                if( chestItem.getAmount() == 0 ) {
                                    int randomAmount;
                                    if( chestItem.getMinRandom() == 0 ) {
                                        randomAmount = random.nextInt( chestItem.getRandomAmount( ) );
                                    } else {
                                        randomAmount = random.nextInt(chestItem.getRandomAmount() - chestItem.getMinRandom() + 1) + chestItem.getMinRandom();
                                    }
                                    setStack.setAmount( randomAmount );
                                }
                                inventory.setItem( randomSlot, setStack );
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            PlayerManager.getPlayerData( player ).addOpenChest();
        }
    }
    public static void prepareMiddleChest( Inventory inventory, Player player ) {
        if( !addedInventories.contains( inventory ) ) {
            addedInventories.add( inventory );
            List<Integer> addedSlots = new ArrayList<>(  );
            List<Material> addedMaterials = new ArrayList<>(  );
            List<String> addedTypes = new ArrayList<>(  );
            int itemAmount;
            for( ;; ) {
                itemAmount = (int) ( Math.random() * 16 );
                if( itemAmount >= 12 ) break;
            }
            while( itemAmount >= 1 ) {
                itemAmount--;
                for( ;; ) {
                    int randomSlot = ( int ) ( Math.random() * 26 );
                    if( !addedSlots.contains( randomSlot ) ) {
                        addedSlots.add( randomSlot );
                        for( ;; ) {
                            ChestItem chestItem = ( ChestItem ) middleCollection.next();
                            ItemStack randomStack = chestItem.getItemStack();
                            if( !addedMaterials.contains( randomStack.getType() ) ) {
                                if( randomStack.getType().name().contains( "_" ) ) {
                                    String type = randomStack.getType().name().split( "_" )[1];
                                    if( addedTypes.contains( type ) ) {
                                        continue;
                                    }
                                    addedTypes.add( type );
                                }
                                addedMaterials.add( randomStack.getType() );
                                ItemStack setStack = randomStack.clone();
                                if( chestItem.getAmount() == 0 ) {
                                    int randomAmount;
                                    if( chestItem.getMinRandom() == 0 ) {
                                        randomAmount = random.nextInt( chestItem.getRandomAmount( ) );
                                    } else {
                                        randomAmount = random.nextInt(chestItem.getRandomAmount() - chestItem.getMinRandom() + 1) + chestItem.getMinRandom();
                                    }
                                    setStack.setAmount( randomAmount );
                                }
                                inventory.setItem( randomSlot, setStack );
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            PlayerManager.getPlayerData( player ).addOpenChest();
        }
    }
    public static void prepareItems() {
        random = new Random(  );
        randomCollection = new RandomCollection<ChestItem>();

        Potion heal1 = new Potion( PotionType.INSTANT_HEAL, 1 );
        heal1.setSplash( true );
        Potion heal2 = new Potion( PotionType.INSTANT_HEAL, 2 );
        heal2.setSplash( true );

        // Weight1
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_HELMET, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 0.5, new ChestItem( ItemStackHelper.createItemStack( Material.ENDER_PEARL, 0 ), 1, 0,  null, null, false ) );
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_AXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 1, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_PICKAXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 1 ), false ) );

        // Weight2
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND, 0 ), 0, 4, 1,  null, null, false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_HELMET, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_AXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_PICKAXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_PICKAXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 2, new ChestItem( heal1.toItemStack( 1 ), 0, 4, 1,  null, null, false ) );
        randomCollection.add( 2, new ChestItem( heal2.toItemStack( 1 ), 0, 4, 1,  null, null, false ) );

        //Weight3
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_INGOT, 0 ), 0, 10, 1, null, null, false ) );
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.TNT, 0 ), 0, 10, 2,  null, null, false ) );
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.WATER_BUCKET, 0 ), 1, 0, null, null, false ) );
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.LAVA_BUCKET, 0 ), 1, 0, null, null, false ) );
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.EXP_BOTTLE, 0 ), 0, 15, 3, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_HELMET, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLDEN_APPLE, 0 ), 1, 0,  null, null, false ) );
        //Weight6
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.COMPASS, 0 ), 1, 0, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.COOKED_BEEF, 0 ), 0, 10, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.COOKED_BEEF, 0 ), 0, 10, 5, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.FLINT, 0 ), 0, 5, 1, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STONE, 0 ), 0, 64, 30,  null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.BRICK, 0 ), 0, 64, 30, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GLASS, 0 ), 0, 64, 20,  null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WOOD, 0 ), 0, 64, 30, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STONE, 0 ), 0, 64, 30,  null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.BRICK, 0 ), 0, 64, 30, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GLASS, 0 ), 0, 64, 20,  null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WOOD, 0 ), 0, 64, 30, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WEB, 0 ), 0, 5, 2, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.PUMPKIN_PIE, 0 ), 0, 4, 2, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.PUMPKIN_PIE, 0 ), 0, 4, 2, null, null, false ) );

        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CAKE, 0 ), 1, 0, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.RAW_BEEF, 0 ), 0, 10, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STICK, 0 ), 0, 5, 1, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WOOD_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STONE_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_HELMET, 0 ), 1, 0, null, null, false ) );
        randomCollection.add( 6, new ChestItem( FlintUtil.getFlintAndSteel(), 1, 0, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_CHESTPLATE, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_LEGGINGS, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_BOOTS, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_HELMET, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_CHESTPLATE, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_LEGGINGS, 0 ), 1, 5, null, null, false ) );
        randomCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_BOOTS, 0 ), 1, 5, null, null, false ) );
        prepareMiddle();
    }
    public static void prepareMiddle() {
        Potion heal1 = new Potion( PotionType.INSTANT_HEAL, 1 );
        heal1.setSplash( true );
        Potion heal2 = new Potion( PotionType.INSTANT_HEAL, 2 );
        heal2.setSplash( true );
        middleCollection = new RandomCollection<ChestItem>();
        // Weight1
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_HELMET, 0 ), 1, 0, Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 0.5, new ChestItem( ItemStackHelper.createItemStack( Material.ENDER_PEARL, 0 ), 1, 0,  null, null, false ) );
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_AXE, 0 ), 1, 0,  Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 2, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND_PICKAXE, 0 ), 1, 0, Arrays.asList( Enchantment.DIG_SPEED ), Arrays.asList( 1 ), false ) );

        // Weight2
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.DIAMOND, 0 ), 0, 4, 1,  null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_HELMET, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        randomCollection.add( 3, new ChestItem( heal1.toItemStack( 1 ), 0, 4, 1,  null, null, false ) );
        randomCollection.add( 3, new ChestItem( heal2.toItemStack( 1 ), 0, 4, 1,  null, null, false ) );

        //Weight3
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.IRON_INGOT, 0 ), 0, 10, 1, null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.TNT, 0 ), 0, 10, 2,  null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.WATER_BUCKET, 0 ), 1, 0, null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.LAVA_BUCKET, 0 ), 1, 0, null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.EXP_BOTTLE, 0 ), 0, 15, 3, null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_HELMET, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_CHESTPLATE, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_LEGGINGS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_BOOTS, 0 ), 1, 0,  Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.GOLDEN_APPLE, 0 ), 1, 0,  null, null, false ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.SNOW_BALL, 0 ), 0, 16, 5,  null, null, true ) );
        middleCollection.add( 3, new ChestItem( ItemStackHelper.createItemStack( Material.EGG, 0 ), 0, 16, 5,  null, null, true ) );
        //Weight6
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.COMPASS, 0 ), 1, 0, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.COOKED_BEEF, 0 ), 0, 10, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.FLINT, 0 ), 0, 5, 1, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STONE, 0 ), 0, 64, 30,  null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.BRICK, 0 ), 0, 64, 30, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GLASS, 0 ), 0, 64, 20,  null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WOOD, 0 ), 0, 64, 30, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WEB, 0 ), 0, 5, 2, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.PUMPKIN_PIE, 0 ), 0, 4, 2, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CAKE, 0 ), 1, 0, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.RAW_BEEF, 0 ), 0, 10, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STICK, 0 ), 0, 5, 1, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.WOOD_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.GOLD_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.STONE_SWORD, 0 ), 1, 0,  Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 2 ), false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_HELMET, 0 ), 1, 0, null, null, false ) );
        randomCollection.add( 6, new ChestItem( FlintUtil.getFlintAndSteel( ), 1, 0, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_CHESTPLATE, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_LEGGINGS, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.LEATHER_BOOTS, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_HELMET, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_CHESTPLATE, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_LEGGINGS, 0 ), 1, 5, null, null, false ) );
        middleCollection.add( 6, new ChestItem( ItemStackHelper.createItemStack( Material.CHAINMAIL_BOOTS, 0 ), 1, 5, null, null, false ) );
    }
}
