package de.piet.skywars.util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
/**
 * Created by PeterH on 07.01.2016.
 */
public class MessageUtil {
    public static void sendMessageToAll( String message ) {
        for( Player player : Bukkit.getOnlinePlayers( ) ) {
            player.sendMessage( message );
        }
    }
}
