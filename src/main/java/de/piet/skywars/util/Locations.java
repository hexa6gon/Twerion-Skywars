package de.piet.skywars.util;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
/**
 * Created by PeterH on 11.01.2016.
 */
public class Locations {
    @Getter
    private static Location lobbyLocation;
    public static void initLocs() {
        lobbyLocation = new Location( Bukkit.getServer( ).getWorld( "lobby" ), 52.5, 5, 19.5 );
    }
}
