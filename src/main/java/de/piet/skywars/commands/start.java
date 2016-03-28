package de.piet.skywars.commands;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.util.PlayerRankData;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Created by PeterH on 22.01.2016.
 */
public class start implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender commandSender, Command command, String s, String[] strings ) {
        Player player = ( Player ) commandSender;
        PlayerRankData playerRankData = CloudAPI.getRankAPI().getPlayerRankData( player.getName() );
        if( !(playerRankData.getAccess_level() >= 70 ) ) {
            player.sendMessage( SkyWars.getPrefix() + "§cDazu hast du keine Berechtigung!" );
            return true;
        }
        if( GameManager.getGameState() == GameState.LOBBY ) {
            player.sendMessage( SkyWars.getPrefix() + "§aDas Spiel wird gestartet!" );
            GameManager.startGame();
        } else {
            player.sendMessage( SkyWars.getPrefix() + "§cDas Spiel kann nur in der Lobby Phase gestartet werden!" );
        }
        return true;
    }
}
