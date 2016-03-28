package de.piet.skywars.player;
import de.piet.cloud.api.CloudAPI;
import de.piet.skywars.SkyWars;
import de.piet.skywars.game.GameManager;
import de.piet.skywars.kits.SkyWarsKit;
import de.piet.skywars.player.stats.Coins;
import de.piet.skywars.player.stats.Points;
import de.piet.skywars.scoreboard.PlayerScoreboard;
import de.piet.skywars.team.SkywarsTeam;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
/**
 * Created by PeterH on 07.01.2016.
 */
public class PlayerData {
    @Getter
    private Player player;
    @Getter
    private SkywarsTeam skywarsTeam;
    @Getter
    @Setter
    private SkyWarsKit skyWarsKit;
    private Player lastHitted;
    private long hittedTime;
    @Getter
    private int kills, deaths, open_chests, points, wins, played_games, placed_blocks, projectile_kills;
    @Getter
    @Setter
    private PlayerScoreboard playerScoreboard;
    private boolean saved = false;
    @Getter
    private boolean projectileHit = false;
    public PlayerData( Player player, SkywarsTeam skywarsTeam ) {
        this.player = player;
        this.skywarsTeam = skywarsTeam;
    }
    public void receiveDamageFrom( Player player, boolean projectileHit ) {
        this.lastHitted = player;
        this.hittedTime = System.currentTimeMillis() + 30000;
        this.projectileHit = projectileHit;
    }
    public Player getLastHitted() {
        if( lastHitted != null ) {
            if( hittedTime > System.currentTimeMillis() ) {
                return lastHitted;
            }
        }
        return null;
    }
    public void addKill() {
        kills ++;
        points += Points.KILL.getPoints();
        int coins = Coins.KILL.getCoins();
        CloudAPI.getCoinsAPI( ).addCoins( player.getName(), coins );
    }
    public void addPlayedGame() {
        played_games++;
    }
    public void addOpenChest() {
        open_chests++;
    }
    public void addPlacedBlock() {
        placed_blocks++;
    }
    public void addProjectile() {
        projectile_kills++;
    }
    public void addDeath() {
        deaths++;
    }
    public void addWin() {
        wins++;
        points += Points.WIN.getPoints();
        int coins = Coins.WIN.getCoins();
        CloudAPI.getCoinsAPI().addCoins( player.getName(), coins );
    }
    public void saveStats() {
        player.sendMessage( "§7-= §aStatistiken der Runde §7=-" );
        player.sendMessage( "§eDauer der Runde: §7" + GameManager.getTime( GameManager.getStartTime( ) ) );
        player.sendMessage( "§eKills: §7" + kills + " §eDavon durch Projektile: §7" + projectile_kills );
        player.sendMessage( "§eTode: §7" + deaths );
        player.sendMessage( "§eGeöffnete Kisten: §7" + open_chests );
        player.sendMessage( "§ePlatzierte Blöcke: §7" + placed_blocks );
        player.sendMessage( "§ePunkte: §7" + points );
        if( SkyWars.isTestModus( ) ) return;
        if( saved ) return;
        saved = true;
        if( kills != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName(), SkyWars.getStatsPrefix() + "kills", kills );
        }
        if( deaths != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName(), SkyWars.getStatsPrefix() + "deaths", deaths );
        }
        if( points != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName(), SkyWars.getStatsPrefix() + "points", points );
        }
        if( wins != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName(), SkyWars.getStatsPrefix() + "wins", wins );
        }
        if( played_games != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName(), SkyWars.getStatsPrefix() + "played_games", played_games );
        }
        if( open_chests != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName( ), SkyWars.getStatsPrefix() + "open_chests", open_chests );
        }
        if( projectile_kills != 0 ) {
            CloudAPI.getStatsAPI().addPlayerStat( player.getName( ), SkyWars.getStatsPrefix() + "projectile_kills", projectile_kills );
        }
    }
}
