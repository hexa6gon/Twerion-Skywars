package de.piet.skywars.map;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by PeterH on 06.01.2016.
 */
public class SkyWarsMap {
    @Getter
    private String name, anzeigeName, creator;
    @Getter
    @Setter
    private World world;
    @Getter
    private List<Location> middleChests = new ArrayList<>(  );
    @Getter
    private List<Location> spawnLocations = new ArrayList<>(  );
    @Getter
    private ItemStack itemStack;
    public SkyWarsMap( String name, String anzeigeName, String creator, ItemStack itemStack ) {
        this.name = name;
        this.anzeigeName = anzeigeName;
        this.creator = creator;
        this.itemStack = itemStack;
    }
}
