package de.piet.skywars.team;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.game.ScoreboardManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by PeterH on 07.01.2016.
 */
public class SkywarsTeam {
    @Getter
    private int number;
    @Getter
    private List<Player> members = new ArrayList<>(  );
    @Getter
    @Setter
    private Location spawnLocation;
    public SkywarsTeam( int number ) {
        this.number = number;
        int viewNumber = number;
        viewNumber++;
        Team team = ScoreboardManager.getScoreboard().registerNewTeam( "team_" + number );
        team.setAllowFriendlyFire( false );
        team.setPrefix( "T#" + viewNumber + " | " );
        ScoreboardManager.getTeams().put( this, team );
    }
    public void playerJoin( Player player ) {
        if( members.contains( player ) ) {
            player.sendMessage( SkyWars.getPrefix() + "§cDu bist bereits in diesem Team!" );
            return;
        }
        if( members.size() >= SkywarsConfig.getProTeam() ) {
            player.sendMessage( SkyWars.getPrefix() + "§cDieses Team ist bereits voll!" );
            return;
        }
        TeamManager.removePlayerFromTeams( player );
        members.add( player );
        int viewNumber = number;
        viewNumber++;
        player.sendMessage( SkyWars.getPrefix( ) + "§7Du bist nun in Team §a#" + viewNumber );
        TeamManager.updateSelectInventory();
    }
}
