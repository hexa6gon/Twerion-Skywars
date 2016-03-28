package de.piet.skywars.kits;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import de.piet.skywars.game.ScoreboardManager;
import de.piet.skywars.player.LobbyScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.util.FlintUtil;
import de.piet.skywars.util.ItemStackHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;
/**
 * Created by PeterH on 12.01.2016.
 */
public class KitManager {
    @Getter
    private static HashMap<Player,PlayerKitData> kitDatas = new HashMap<>(  );
    @Getter
    private static HashMap<String,SkyWarsKit> kits = new HashMap<>(  );
    @Getter
    private static HashMap<Player,SkyWarsKit> playerKits = new HashMap<>(  );
    @Getter
    @Setter
    private static SkyWarsKit defaultKit;
    public static void setupKits() {
        // Standard Kit
        SkyWarsKit defaultKit = new SkyWarsKit( "Standard Kit", "standard", ItemStackHelper.createItemStack( Material.STONE_AXE, 0, "§6Standard Kit" ), 0 );
        kits.put( defaultKit.getKey(), defaultKit );
        setDefaultKit( defaultKit );
        defaultKit.getDescription().add( "1x Stein Spitzhacke" );
        defaultKit.getDescription().add( "1x Stein Axt" );
        defaultKit.getDescription().add( "1x Stein Schaufel" );
        defaultKit.getAddItems( ).add( new ItemStack( Material.STONE_PICKAXE, 1 ) );
        defaultKit.getAddItems().add( new ItemStack( Material.STONE_AXE, 1 ) );
        defaultKit.getAddItems().add( new ItemStack( Material.STONE_SPADE, 1 ) );
        defaultKit.buildViewStack();

        // Maurer
        SkyWarsKit maurer = new SkyWarsKit( "Maurer", "maurer", ItemStackHelper.createItemStack( Material.BRICK, 0, "§6Maurer" ), 1000 );
        kits.put( maurer.getKey( ), maurer );
        maurer.getDescription().add( "3x 64 Ziegelsteine" );
        maurer.getAddItems( ).add( new ItemStack( Material.BRICK, 64 ) );
        maurer.getAddItems( ).add( new ItemStack( Material.BRICK, 64 ) );
        maurer.getAddItems( ).add( new ItemStack( Material.BRICK, 64 ) );
        maurer.buildViewStack();

        // Grillmeister
        SkyWarsKit grillmeister = new SkyWarsKit( "Grillmeister", "grillmeister", ItemStackHelper.createItemStack( Material.COOKED_BEEF, 0, "§6Grillmeister" ), 1000 );
        kits.put( grillmeister.getKey( ), grillmeister );
        grillmeister.getDescription().add( "32x Gebratenes Fleisch" );
        grillmeister.getDescription().add( "32x Gebratene Hähnchen" );
        grillmeister.getDescription().add( "12x Öfen" );
        grillmeister.getDescription().add( "1x Feuerzeug" );
        grillmeister.getAddItems( ).add( new ItemStack( Material.COOKED_BEEF, 32 ) );
        grillmeister.getAddItems( ).add( new ItemStack( Material.COOKED_CHICKEN, 32 ) );
        grillmeister.getAddItems( ).add( new ItemStack( Material.FURNACE, 12 ) );
        grillmeister.getAddItems( ).add( new ItemStack( Material.FLINT_AND_STEEL, 12 ) );
        grillmeister.buildViewStack();

        // Pirat
        SkyWarsKit pirat = new SkyWarsKit( "Pirat", "pirat", ItemStackHelper.createItemStack( Material.FIREBALL, 0, "§6Pirat" ), 1000 );
        kits.put( pirat.getKey( ), pirat );
        pirat.getDescription().add( "1x Goldschwert mit Schärfe 3" );
        pirat.getDescription().add( "3x Dispenser" );
        pirat.getDescription().add( "32x Feuerball" );
        pirat.getDescription().add( "3x Hebel" );
        pirat.getAddItems( ).add( ItemStackHelper.getItem( Material.GOLD_SWORD, 1, 0, Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 3 ) ) );
        pirat.getAddItems( ).add( new ItemStack( Material.DISPENSER, 3 ) );
        pirat.getAddItems( ).add( new ItemStack( Material.FIREBALL, 32 ) );
        pirat.getAddItems( ).add( new ItemStack( Material.LEVER, 3 ) );
        pirat.buildViewStack();

        // Angler
        SkyWarsKit angler = new SkyWarsKit( "Angler", "angler", ItemStackHelper.createItemStack( Material.FISHING_ROD, 0, "§6Angler" ), 1000 );
        kits.put( angler.getKey( ), angler );
        angler.getDescription( ).add( "1x Angel mit Haltbarkeit 10 und Glück des Meeres 1" );
        angler.getDescription( ).add( "32x Gebratener Fisch" );
        angler.getAddItems( ).add( ItemStackHelper.getItem( Material.FISHING_ROD, 1, 0, Arrays.asList( Enchantment.DURABILITY, Enchantment.LUCK ), Arrays.asList( 10, 1 ) ) );
        angler.getAddItems( ).add( new ItemStack( Material.COOKED_FISH, 32 ) );
        angler.buildViewStack();

        // Landwirt
        SkyWarsKit landwirt = new SkyWarsKit( "Landwirt", "landwirt", ItemStackHelper.createItemStack( Material.HAY_BLOCK, 0, "§6Landwirt" ), 1000 );
        kits.put( landwirt.getKey( ), landwirt );
        landwirt.getDescription( ).add( "64x Heuballen" );
        landwirt.getDescription( ).add( "1x Eisenspitzhacke mit Schärfe 2 und Haltbarkeit 10" );
        landwirt.getDescription( ).add( "32x Fackeln" );
        landwirt.getDescription( ).add( "32x Brote" );
        landwirt.getAddItems( ).add( new ItemStack( Material.HAY_BLOCK, 64 ) );
        landwirt.getAddItems( ).add( ItemStackHelper.getItem( Material.IRON_PICKAXE, 1, 0, Arrays.asList( Enchantment.DAMAGE_ALL, Enchantment.DURABILITY ), Arrays.asList( 2, 10 ) ) );
        landwirt.getAddItems( ).add( new ItemStack( Material.TORCH, 32 ) );
        landwirt.getAddItems( ).add( new ItemStack( Material.BREAD, 32 ) );
        landwirt.buildViewStack();

        // Crafter
        SkyWarsKit crafter = new SkyWarsKit( "Crafter", "crafter", ItemStackHelper.createItemStack( Material.WORKBENCH, 0, "§6Crafter" ), 2000 );
        kits.put( crafter.getKey( ), crafter );
        crafter.getDescription( ).add( "64x Werkbank" );
        crafter.getDescription( ).add( "32x Amboss" );
        crafter.getDescription( ).add( "64x Sticks" );
        crafter.getDescription( ).add( "5x Kohle" );
        crafter.getDescription( ).add( "5x Eisen" );
        crafter.getDescription( ).add( "5x Diamanten" );
        crafter.getAddItems( ).add( new ItemStack( Material.WORKBENCH, 64 ) );
        crafter.getAddItems( ).add( new ItemStack( Material.ANVIL, 32 ) );
        crafter.getAddItems( ).add( new ItemStack( Material.STICK, 64 ) );
        crafter.getAddItems( ).add( new ItemStack( Material.COAL, 5 ) );
        crafter.getAddItems( ).add( new ItemStack( Material.IRON_INGOT, 5 ) );
        crafter.getAddItems( ).add( new ItemStack( Material.DIAMOND, 5 ) );
        crafter.buildViewStack();

        // Wolf
        SkyWarsKit wolf = new SkyWarsKit( "Wolf", "wolf", ItemStackHelper.createItemStack( Material.BONE, 0, "§6Wolf" ), 2000 );
        kits.put( wolf.getKey( ), wolf );
        wolf.getDescription( ).add( "32x Knochen" );
        wolf.getDescription( ).add( "12x Wolf Spawnei" );
        wolf.getAddItems( ).add( new ItemStack( Material.BONE, 32 ) );
        wolf.getAddItems( ).add( new ItemStack( Material.MONSTER_EGG, 12, ( short ) 95 ) );
        wolf.buildViewStack();

        // Sprengmeister
        SkyWarsKit sprengmeister = new SkyWarsKit( "Sprengmeister", "sprengmeister", ItemStackHelper.createItemStack( Material.TNT, 0, "§6Sprengmeister" ), 2000 );
        kits.put( sprengmeister.getKey( ), sprengmeister );
        sprengmeister.getDescription( ).add( "5x Creeper Spawnei" );
        sprengmeister.getDescription( ).add( "16x TNT" );
        sprengmeister.getDescription( ).add( "16x Redstone Fackel" );
        sprengmeister.getDescription( ).add( "1x Lederbrustpanzer mit Explosionsschutz 10 und Schutz 2" );
        sprengmeister.getAddItems( ).add( new ItemStack( Material.MONSTER_EGG, 5, ( short ) 50 ) );
        sprengmeister.getAddItems( ).add( new ItemStack( Material.TNT, 16 ) );
        sprengmeister.getAddItems( ).add( new ItemStack( Material.REDSTONE_TORCH_ON, 8 ) );
        sprengmeister.getAddItems( ).add( ItemStackHelper.getItem( Material.LEATHER_CHESTPLATE, 1, 0, Arrays.asList( Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 10, 2 ) ) );
        sprengmeister.buildViewStack();

        // Bauarbeiter
        SkyWarsKit bauarbeiter = new SkyWarsKit( "Bauarbeiter", "bauarbeiter", ItemStackHelper.createItemStack( Material.DIAMOND_PICKAXE, 0, "§6Bauarbeiter" ), 2000 );
        kits.put( bauarbeiter.getKey( ), bauarbeiter );
        bauarbeiter.getDescription( ).add( "1x Diamant Spitzhacke mit Effizienz 10 und Haltbarkeit 10" );
        bauarbeiter.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_PICKAXE, 1, 0, Arrays.asList( Enchantment.DIG_SPEED, Enchantment.DURABILITY ), Arrays.asList( 10, 10 ) ) );
        bauarbeiter.buildViewStack();

        // Schneemann
        SkyWarsKit schneemann = new SkyWarsKit( "Schneemann", "schneemann", ItemStackHelper.createItemStack( Material.SNOW_BALL, 0, "§6Schneemann" ), 2000 );
        kits.put( schneemann.getKey( ), schneemann );
        schneemann.getDescription( ).add( "5x Schneeblock" );
        schneemann.getDescription( ).add( "1x Eisenschaufel mit Haltbarkeit 10" );
        schneemann.getAddItems( ).add( new ItemStack( Material.SNOW_BLOCK, 5 ) );
        schneemann.getAddItems( ).add( ItemStackHelper.getItem( Material.IRON_SPADE, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        schneemann.buildViewStack();

        // Hexenmeister
        Potion poison = new Potion( PotionType.POISON, 2 );
        poison.setSplash( true );
        Potion reg = new Potion( PotionType.REGEN, 1 );
        SkyWarsKit hexenmeister = new SkyWarsKit( "Hexenmeister", "hexenmeister", ItemStackHelper.createItemStack( Material.CAULDRON_ITEM, 0, "§6Hexenmeister" ), 2000 );
        kits.put( hexenmeister.getKey( ), hexenmeister );
        hexenmeister.getDescription( ).add( "1x Werfbarer Gift Trank Vergiftung 2" );
        hexenmeister.getDescription( ).add( "1x Regeneration 1 Trank" );
        hexenmeister.getAddItems( ).add( poison.toItemStack( 1 ) );
        hexenmeister.getAddItems( ).add( reg.toItemStack( 1 ) );
        hexenmeister.buildViewStack();

        // Magier
        Potion strength = new Potion( PotionType.STRENGTH, 1 );
        Potion slowness = new Potion( PotionType.SLOWNESS, 1 );
        slowness.setSplash( true );
        Potion weakness = new Potion( PotionType.WEAKNESS, 1 );
        weakness.setSplash( true );
        SkyWarsKit magier = new SkyWarsKit( "Magier", "magier", ItemStackHelper.createItemStack( Material.BLAZE_POWDER, 0, "§6Magier" ), 2000 );
        kits.put( magier.getKey( ), magier );
        magier.getDescription( ).add( "1x Goldene Karotte" );
        magier.getDescription( ).add( "1x Trank der Stärke 1" );
        magier.getDescription( ).add( "1x Trank der Regeneration 1" );
        magier.getDescription( ).add( "1x Werfbarer Trank der Langsamkeit 1" );
        magier.getDescription( ).add( "1x Werfbarer Trank der Schwäche 1" );
        magier.getAddItems( ).add( new ItemStack( Material.GOLDEN_CARROT, 1 ) );
        magier.getAddItems().add( strength.toItemStack( 1 ) );
        magier.getAddItems().add( reg.toItemStack( 1 ) );
        magier.getAddItems().add( slowness.toItemStack( 1 ) );
        magier.getAddItems( ).add( weakness.toItemStack( 1 ) );
        magier.buildViewStack();

        // Enchanter Kit
        SkyWarsKit enchanter = new SkyWarsKit( "Zauberer", "enchanter", ItemStackHelper.createItemStack( Material.ENCHANTMENT_TABLE, 0, "§6Zauberer" ), 3000 );
        kits.put( enchanter.getKey( ), enchanter );
        enchanter.getDescription( ).add( "1x Zaubertisch" );
        enchanter.getDescription( ).add( "32x Erfahrungsfläschchen" );
        enchanter.getAddItems( ).add( new ItemStack( Material.ENCHANTMENT_TABLE, 1 ) );
        enchanter.getAddItems().add( new ItemStack( Material.EXP_BOTTLE, 32 ) );
        enchanter.buildViewStack();

        // Tank
        Potion fire = new Potion( PotionType.FIRE_RESISTANCE, 1 );
        SkyWarsKit tank = new SkyWarsKit( "Tank", "tank", ItemStackHelper.createItemStack( Material.IRON_CHESTPLATE, 0, "§6Tank" ), 3000 );
        kits.put( tank.getKey( ), tank );
        tank.getDescription().add( "1x Diamant Helm mit Haltbarkeit 10" );
        tank.getDescription().add( "1x Eisen Brustplatte mit Haltbarkeit 10" );
        tank.getDescription().add( "1x Diamant Hose mit Haltbarkeit 10" );
        tank.getDescription().add( "1x Diamant Schuhe mit Haltbarkeit 10" );
        tank.getDescription( ).add( "1x Feuerresistenz Trank" );
        tank.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_HELMET, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        tank.getAddItems( ).add( ItemStackHelper.getItem( Material.IRON_CHESTPLATE, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        tank.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_LEGGINGS, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        tank.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_BOOTS, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        tank.getAddItems( ).add( fire.toItemStack( 1 ) );
        tank.buildViewStack();

        // Assassine
        SkyWarsKit assassine = new SkyWarsKit( "Assassine", "assassine", ItemStackHelper.createItemStack( Material.DIAMOND_SWORD, 0, "§6Assassine" ), 3000 );
        kits.put( assassine.getKey( ), assassine );
        assassine.getDescription( ).add( "1x Diamant Schwert mit Sharpness 1 und Haltbarkeit 10" );
        assassine.getDescription( ).add( "1x Diamant Schuhe mit Federfall 2 und Haltbarkeit 10" );
        assassine.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_SWORD, 1, 0, Arrays.asList( Enchantment.DAMAGE_ALL ), Arrays.asList( 1 ) ) );
        assassine.getAddItems( ).add( ItemStackHelper.getItem( Material.DIAMOND_BOOTS, 1, 0, Arrays.asList( Enchantment.PROTECTION_FALL ), Arrays.asList( 2 ) ) );
        assassine.buildViewStack();

        // Knüppel
        SkyWarsKit knueppel = new SkyWarsKit( "Knüppel", "knuepel", ItemStackHelper.createItemStack( Material.STICK, 0, "§6Knüppel" ), 3000 );
        kits.put( knueppel.getKey( ), knueppel );
        knueppel.getDescription( ).add( "1x Knüppel mit Rückstoß 1" );
        knueppel.getAddItems( ).add( ItemStackHelper.getItem( Material.STICK, 1, 0, Arrays.asList( Enchantment.KNOCKBACK ), Arrays.asList( 1 ) ) );
        knueppel.buildViewStack();

        // Enderman
        SkyWarsKit enderman = new SkyWarsKit( "Enderman", "enderman", ItemStackHelper.createItemStack( Material.ENDER_PEARL, 0, "§6Endermann" ), 3000 );
        kits.put( enderman.getKey( ), enderman );
        enderman.getDescription( ).add( "1x Enderperle" );
        enderman.getAddItems( ).add( new ItemStack( Material.ENDER_PEARL, 1 ) );
        enderman.buildViewStack();

        // Dagobert Duck
        SkyWarsKit dagobert = new SkyWarsKit( "Goldrausch", "goldrausch", ItemStackHelper.createItemStack( Material.GOLD_BLOCK, 0, "§6Goldrausch" ), 3000 );
        kits.put( dagobert.getKey( ), dagobert );
        dagobert.getDescription( ).add( "64x Goldblöcke" );
        dagobert.getDescription( ).add( "1x Goldener Apfel" );
        dagobert.getDescription( ).add( "1x Goldene Spitzhacke" );
        dagobert.getDescription( ).add( "1x Gold Brustpanzer mit Schutz 1" );
        dagobert.getDescription( ).add( "1x Gold Schuhe mit Schutz 1" );
        dagobert.getDescription( ).add( "1x Goldene Karotte" );
        dagobert.getAddItems( ).add( new ItemStack( Material.GOLD_BLOCK, 64 ) );
        dagobert.getAddItems( ).add( new ItemStack( Material.GOLDEN_APPLE, 1 ) );
        dagobert.getAddItems().add( ItemStackHelper.getItem( Material.GOLD_PICKAXE, 1, 0, Arrays.asList( Enchantment.DURABILITY ), Arrays.asList( 10 ) ) );
        dagobert.getAddItems().add( ItemStackHelper.getItem( Material.GOLD_CHESTPLATE, 1, 0, Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ) ) );
        dagobert.getAddItems().add( ItemStackHelper.getItem( Material.GOLD_BOOTS, 1, 0, Arrays.asList( Enchantment.PROTECTION_ENVIRONMENTAL ), Arrays.asList( 1 ) ) );
        dagobert.getAddItems( ).add( new ItemStack( Material.GOLDEN_CARROT, 1 ) );
        dagobert.buildViewStack();

        // Soup
        SkyWarsKit soup = new SkyWarsKit( "Soup", "soup", ItemStackHelper.createItemStack( Material.MUSHROOM_SOUP, 0, "§6Soup" ), 3000 );
        kits.put( soup.getKey( ), soup );
        soup.getDescription( ).add( "64x rote Pilzblöcke" );
        soup.getDescription( ).add( "5x Schüsseln (Maximal 5 Suppen im Inventar)" );
        soup.getDescription( ).add( "1x Pilzkuh Spawnei" );
        soup.getAddItems( ).add( new ItemStack( Material.HUGE_MUSHROOM_2, 64 ) );
        soup.getAddItems( ).add( new ItemStack( Material.BOWL, 5 ) );
        soup.getAddItems( ).add( new ItemStack( Material.MONSTER_EGG, 1, ( short ) 96 ) );
        soup.buildViewStack();

        // Pyro
        SkyWarsKit pyro = new SkyWarsKit( "Pyro", "pyro", ItemStackHelper.createItemStack( Material.FLINT_AND_STEEL, 0, "§6Pyro" ), 3000 );
        kits.put( pyro.getKey( ), pyro );
        pyro.getDescription( ).add( "1x Goldschwert mit Schärfe 3 und Feuer Aspect 2" );
        pyro.getDescription( ).add( "1x Feuerzeug" );
        pyro.getDescription( ).add( "2x Lavaeimer" );
        pyro.getDescription( ).add( "1x Trank der Feuerresistenz" );
        pyro.getAddItems( ).add( ItemStackHelper.getItem( Material.GOLD_SWORD, 1, 0, Arrays.asList( Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT ), Arrays.asList( 3, 2 ) ) );
        pyro.getAddItems( ).add( FlintUtil.getFlintAndSteel( ) );
        pyro.getAddItems( ).add( new ItemStack( Material.LAVA_BUCKET, 1 ) );
        pyro.getAddItems( ).add( new ItemStack( Material.LAVA_BUCKET, 1 ) );
        pyro.getAddItems().add( fire.toItemStack( 1 ) );
        pyro.buildViewStack();

        // MLG
        SkyWarsKit mlg = new SkyWarsKit( "MLG", "mlg", ItemStackHelper.createItemStack( Material.WEB, 0, "§6MLG" ), 4000 );
        kits.put( mlg.getKey( ), mlg );
        mlg.getDescription( ).add( "3x Boote" );
        mlg.getDescription( ).add( "16x Spinnweben" );
        mlg.getDescription( ).add( "2x Wassereimer" );
        mlg.getDescription( ).add( "24x TNT" );
        mlg.getDescription( ).add( "1x Feuerzeug" );
        mlg.getDescription( ).add( "6x Holzdruckplatte" );
        mlg.getAddItems( ).add( new ItemStack( Material.BOAT, 1 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.BOAT, 1 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.BOAT, 1 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.WEB, 16 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.WATER_BUCKET, 1 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.WATER_BUCKET, 1 ) );
        mlg.getAddItems( ).add( new ItemStack( Material.TNT, 24 ) );
        mlg.getAddItems( ).add( FlintUtil.getFlintAndSteel() );
        mlg.getAddItems( ).add( new ItemStack( Material.WOOD_PLATE, 6 ) );
        mlg.buildViewStack();

    }
    public static SkyWarsKit getKitByItemStack( ItemStack itemStack ) {
        for( String key : kits.keySet() ) {
            SkyWarsKit skyWarsKit = kits.get( key );
            if( skyWarsKit.getViewStack().getType().equals( itemStack.getType() ) ) {
                return skyWarsKit;
            }
        }
        return null;
    }
    public static void selectKit( Player player, SkyWarsKit skyWarsKit ) {
        if( playerKits.containsKey( player ) && playerKits.get( player ).equals( skyWarsKit ) ) {
            player.sendMessage( SkyWars.getPrefix() + "§cDu hast dieses Kit bereits ausgewählt!" );
            return;
        }
        player.sendMessage( SkyWars.getPrefix() + "§7Du hast das Kit §a" + skyWarsKit.getName() + " §7ausgewählt!" );
        player.playSound( player.getLocation(), Sound.CHEST_CLOSE, 40, 10 );
        if( GameManager.getGameState( ) == GameState.INGAME || GameManager.getGameState() == GameState.WARMUP ) {
            PlayerData playerData = PlayerManager.getPlayerData( player );
            playerData.setSkyWarsKit( skyWarsKit );
            skyWarsKit.giveItems( player );
            ScoreboardManager.setupScoreboard( playerData );
        } else {
            playerKits.put( player, skyWarsKit );
            player.getInventory().setItem( 8, skyWarsKit.getViewStack( ) );
            LobbyScoreboardManager.updateKit( player );
        }
    }
    public static void useRandomKit( Player player ) {
        player.sendMessage( SkyWars.getPrefix() + "§7Dir wird ein zufälliges Kit zugewiesen!" );
        if( kitDatas.containsKey( player ) ) {
            PlayerKitData playerKitData = kitDatas.get( player );
            List<SkyWarsKit> buyedKits = playerKitData.getBuyedKits();
            if( buyedKits.size() != 0 ) {
                int random = Math.abs( new Random( ).nextInt( ) ) % buyedKits.size( );
                selectKit( player, buyedKits.get( random ) );
            } else {
                selectKit( player, defaultKit );
            }
        } else {
            CloudAPI.getDatabaseAPI().getPlayerDatabaseValue( player.getName( ), SkyWars.getDatabasePrefix( ) + "buyedkits", o -> {
                String kits1 = ( String ) o;
                if ( !kits1.isEmpty( ) ) {
                    Bukkit.getServer( ).getScheduler( ).runTask( SkyWars.getInstance( ), ( ) -> {
                        List<String> kitList = Arrays.asList( kits1.split( "," ) );
                        int random = Math.abs( new Random( ).nextInt( ) ) % kitList.size( );
                        selectKit( player, kits.get( kitList.get( random ) ) );
                    } );
                } else {
                    Bukkit.getServer( ).getScheduler( ).runTask( SkyWars.getInstance( ), ( ) -> selectKit( player, defaultKit ) );
                }
            } );
            return;
        }
    }
    public static void playerQuit( Player player ) {
        playerKits.remove( player );
        kitDatas.remove( player );
    }
    public static void useLastSelectedKit( Player player ) {
        CloudAPI.getDatabaseAPI().getPlayerDatabaseValue( player.getName( ), SkyWars.getDatabasePrefix( ) + "lastkit", o -> {
            String lastKit = ( String ) o;
            if( !lastKit.isEmpty() ) {
                Bukkit.getServer().getScheduler().runTask( SkyWars.getInstance( ), ( ) -> {
                    player.sendMessage( SkyWars.getPrefix( ) + "§7Dir wird dein zuletzt verwendetes Kit zugewiesen!" );
                    SkyWarsKit skyWarsKit = kits.get( lastKit );
                    selectKit( player, skyWarsKit );
                } );
            } else {
                useRandomKit( player );
            }
        } );
    }
}
