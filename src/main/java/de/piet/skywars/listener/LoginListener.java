package de.piet.skywars.listener;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.util.PlayerRankData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
/**
 * Created by PeterH on 26.01.2016.
 */
public class LoginListener implements Listener {
    @EventHandler
    public void onLogin( PlayerLoginEvent event ) {

/*
        PlayerRankData playerRankData = CloudAPI.getRankAPI().getPlayerRankData( event.getPlayer().getName() );
        if( playerRankData.getAccess_level() < 80 ) {
            event.disallow( PlayerLoginEvent.Result.KICK_OTHER, "Dieser Server ist aktuell nur für Teammitglieder zugänglich!" );
        }

        */

    }
}
