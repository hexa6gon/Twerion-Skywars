package de.piet.skywars.commands;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Created by PeterH on 20.01.2016.
 */
public class swadmin implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender commandSender, Command command, String s, String[] strings ) {
        if( strings[0].equalsIgnoreCase( "tostart" ) ) {
            SkywarsConfig.setToStart( Integer.valueOf( strings[1] ) );
        } else if( strings[0].equalsIgnoreCase( "count" ) ) {
            GameManager.setLobbyCount( Integer.valueOf( strings[1] ) );
        } else if( strings[0].equalsIgnoreCase( "stats" ) ) {
            if( SkyWars.isTestModus() ) {
                SkyWars.setTestModus( false );
                commandSender.sendMessage( "Deaktiviert" );
            } else {
                SkyWars.setTestModus( true );
                commandSender.sendMessage( "Aktiviert" );
            }
        } else if( strings[0].equalsIgnoreCase( "pd" ) ) {
            for( Player player : PlayerManager.getPlayerDatas().keySet() ) {
                commandSender.sendMessage( player.getName() );
            }
        }
        return true;
    }
}
