package de.piet.skywars.player;
import de.piet.cloud.spigotplugin.CloudSpigot;
import de.piet.skywars.kits.KitManager;
import de.piet.skywars.map.MapManager;
import de.piet.skywars.scoreboard.PacketScoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
/**
 * Created by PeterH on 24.01.2016.
 */
public class LobbyScoreboard {
    @Getter
    private Player player;
    @Getter
    private PacketScoreboard packetScoreboard;
    public LobbyScoreboard( Player player ) {
        this.player = player;
        this.packetScoreboard = new PacketScoreboard( player );
        packetScoreboard.remove();
        packetScoreboard.sendSidebar( "§eSkyWars" );
        packetScoreboard.setLine( 1, "§aKit§f:" );
        packetScoreboard.setLine( 2, "§1" );
        packetScoreboard.setLine( 4, "§aMap§f:" );
        packetScoreboard.setLine( 5, "§2" );
        packetScoreboard.setLine( 7, "§aSpieler§f:" );
        setKit();
        setMap();
        setPlayers();
    }
    public void setKit() {
        packetScoreboard.removeLine( 0 );
        if( !KitManager.getPlayerKits().containsKey( player ) ) {
            packetScoreboard.setLine( 0, "Nicht ausgewählt" );
        } else {
            packetScoreboard.setLine( 0, KitManager.getPlayerKits( ).get( player ).getName( ) );
        }
    }
    public void setMap() {
        packetScoreboard.removeLine( 3 );
        packetScoreboard.setLine( 3, MapManager.getConfigMap( ).getAnzeigename( ) );
    }
    public void setPlayers() {
        packetScoreboard.removeLine( 6 );
        packetScoreboard.setLine( 6, Bukkit.getOnlinePlayers( ).size() + "/" + CloudSpigot.getInstance().getConfigManager().getMaxPlayers() );
    }
}
