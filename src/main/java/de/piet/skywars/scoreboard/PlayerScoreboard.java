package de.piet.skywars.scoreboard;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
/**
 * Created by PeterH on 21.01.2016.
 */
public class PlayerScoreboard {
    @Getter
    private Player player;
    @Getter
    private HashMap<Integer,String> lines = new HashMap<>(  );
    private PacketScoreboard packetScoreboard;
    public PlayerScoreboard( Player player ) {
        this.player = player;
        this.packetScoreboard = new PacketScoreboard( player );
    }
    public void sendTitle( ) {
        packetScoreboard.sendSidebar( "§eSkyWars" );
    }
    public void setup( ) {
        packetScoreboard.remove( );
        sendTitle();
        for( int score : lines.keySet() ) {
            String line = lines.get( score );
            packetScoreboard.setLine( score, line );
        }
    }
    public void remove() {
        packetScoreboard.remove();
    }
    public void setLine( int line, String text ) {
        if( lines.containsKey( line ) ) {
            packetScoreboard.removeLine( line );
        }
        lines.put( line, text );
        packetScoreboard.setLine( line, text );
    }
}
