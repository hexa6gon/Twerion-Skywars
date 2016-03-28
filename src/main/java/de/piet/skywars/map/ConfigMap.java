package de.piet.skywars.map;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by PeterH on 06.01.2016.
 */
public class ConfigMap {
    @Getter
    private String name, anzeigename, creator, worldname, itemData;
    @Getter
    private List<String> spawns = new ArrayList<>(  );
    @Getter
    private List<String> middlechests = new ArrayList<>(  );
    @Getter
    private ItemStack itemStack;
    public ConfigMap( String name, String anzeigename, String creator, String worldname, String itemData ) {
        this.name = name;
        this.anzeigename = anzeigename;
        this.creator = creator;
        this.worldname = worldname;
        this.itemData = itemData;
        ItemStack itemStack = null;
        if( this.itemStack == null ) {
            itemStack = new ItemStack( Material.PAPER, 1 );
        } else {
            String material = this.itemData.split( ";" )[0];
            int data = Integer.valueOf( this.itemData.split( ";" )[1] );
            if( data == 0 ) {
                itemStack = new ItemStack( Material.getMaterial( Integer.valueOf( material ) ), 1 );
            } else {
                itemStack = new ItemStack( Material.getMaterial( Integer.valueOf( material ) ), 1, ( short ) data );
            }
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName( ChatColor.GOLD + getAnzeigename() );
        itemStack.setItemMeta( itemMeta );
        this.itemStack = itemStack;
    }
    public SkyWarsMap getMap() {
        SkyWarsMap skyWarsMap = new SkyWarsMap( name, anzeigename, creator, itemStack );
        WorldCreator worldCreator = new WorldCreator( getWorldname() );
        worldCreator.generator( "CleanroomGenerator:." );
        Bukkit.getServer( ).createWorld( worldCreator );
        World world = Bukkit.getServer().getWorld( getWorldname() );
        world.setGameRuleValue( "randomTickSpeed", "3" );
        world.setGameRuleValue( "doTileDrops", "true" );
        world.setThundering( false );
        world.setStorm( false );
        world.setTime( 0 );
        skyWarsMap.setWorld( world );
        for( String ss : spawns ) {
            String[] split = ss.split( ";" );
            skyWarsMap.getSpawnLocations().add( new Location( world, Integer.parseInt( split[0] ) + 0.5, Integer.parseInt( split[1] ) + 1, Integer.parseInt( split[2] ) + 0.5 ) );
        }
        for( String ss : middlechests ) {
            String[] split = ss.split( ";" );
            skyWarsMap.getMiddleChests().add( new Location( world, Integer.parseInt( split[0] ), Integer.parseInt( split[1] ), Integer.parseInt( split[2] ) ) );
        }
        return skyWarsMap;
    }
}
