package de.piet.skywars.game;
import de.piet.cloud.api.CloudAPI;
import de.piet.cloud.api.types.CloudServerState;
import de.piet.cloud.api.util.Title;
import de.piet.cloud.spigotplugin.CloudSpigot;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.map.MapManager;
import de.piet.skywars.map.SkyWarsMap;
import de.piet.skywars.player.LobbyScoreboardManager;
import de.piet.skywars.player.PlayerData;
import de.piet.skywars.player.PlayerManager;
import de.piet.skywars.spectator.SpectatorManager;
import de.piet.skywars.spectator.SpectatorScoreboard;
import de.piet.skywars.team.SkywarsTeam;
import de.piet.skywars.team.TeamManager;
import de.piet.skywars.util.Locations;
import de.piet.skywars.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.Iterator;

/**
 * Created by PeterH on 07.01.2016.
 */
public class GameManager {
    @Getter
    @Setter
    private static GameState gameState = GameState.LOBBY;
    @Getter
    @Setter
    private static int lobbyCount = 60;
    private static int restartCount = 15;
    @Getter
    private static int mapCount = 3;
    private static int toSendMessage = 10;
    @Getter
    private static SkyWarsMap skyWarsMap;
    @Setter
    @Getter
    private static long startTime;
    @Getter
    private static boolean peace = true;
    private static int peaceTime = 30;
    public static void startTimer() {
        Bukkit.getServer( ).getScheduler().runTaskTimer( SkyWars.getInstance( ), ( ) -> {
            Locations.getLobbyLocation().getWorld().setThundering( false );
            Locations.getLobbyLocation().getWorld().setStorm( false );
            if( Locations.getLobbyLocation().getWorld().getTime() > 8000 ) {
                Locations.getLobbyLocation().getWorld().setTime( 0 );
            }
            if( gameState == GameState.LOBBY ) {
                if( Bukkit.getServer().getOnlinePlayers().size() >= SkywarsConfig.getToStart() ) {
                    if( lobbyCount > 20 && Bukkit.getServer().getOnlinePlayers().size() == CloudSpigot.getInstance().getConfigManager().getMaxPlayers() ) {
                        lobbyCount = 20;
                    }
                    for( Player player : Bukkit.getOnlinePlayers() ) {
                        player.setLevel( lobbyCount );
                        player.setExp( lobbyCount * 0.0166666666666667F );
                        if( lobbyCount <= 3 ) {
                            player.playSound( player.getLocation(), Sound.NOTE_BASS, 40, 1 );
                        }
                    }
                    if( lobbyCount == 0 ) {
                        startGame( );
                    } else {
                        if( lobbyCount == 60 || lobbyCount == 45 || lobbyCount == 30 || lobbyCount == 20 || lobbyCount == 15 || lobbyCount == 10 || lobbyCount <= 5 ) {
                            if( lobbyCount == 1 ) {
                                MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Das Spiel startet in §a" + lobbyCount + " Sekunde" );
                            } else {
                                MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Das Spiel startet in §a" + lobbyCount + " Sekunden" );
                            }
                            if( lobbyCount == 10 || lobbyCount == 30 ) {
                                sendTitle();
                                sendMapInfo( );
                            }
                        }
                        lobbyCount--;
                    }
                } else {
                    lobbyCount = 60;
                    for( Player player : Bukkit.getOnlinePlayers() ) {
                        player.setExp( 0 );
                        player.setLevel( 0 );
                    }
                    if( toSendMessage == 0 ) {
                        toSendMessage = 10;
                        int needed = SkywarsConfig.getToStart() - Bukkit.getOnlinePlayers().size();
                        if( needed == 1 ) {
                            MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Es fehlt noch §a1 Spieler §7damit der Countdown beginnt!" );
                        } else {
                            MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Es fehlen noch §a" + needed + " Spieler §7damit der Countdown beginnt!" );
                        }
                    } else {
                        toSendMessage--;
                    }
                }
            } else if( gameState == GameState.WARMUP ) {
                updateScoreboardTime();
                if( mapCount == 0 ) {
                    gameState = GameState.INGAME;
                    for( Player player : Bukkit.getOnlinePlayers() ) {
                        player.setLevel( 0 );
                        player.setExp( 0 );
                        player.playSound( player.getLocation( ), Sound.NOTE_PLING, 40, 1 );
                        player.sendMessage( SkyWars.getPrefix() + "§7Das Spiel beginnt!" );
                    }
                } else {
                    for( Player player : Bukkit.getOnlinePlayers() ) {
                        player.setLevel( mapCount );
                        player.setExp( mapCount * 0.3333333333333333F );
                        player.playSound( player.getLocation(), Sound.NOTE_BASS, 40, 1 );
                        if( mapCount == 1 ) {
                            player.sendMessage( SkyWars.getPrefix() + "§7Die Runde beginnt in §a" + 1 + " Sekunde" );
                        } else {
                            player.sendMessage( SkyWars.getPrefix() + "§7Die Runde beginnt in §a" + mapCount + " Sekunden" );
                        }
                    }
                    ChatColor chatColor = null;
                    if( mapCount == 3 ) {
                        chatColor = ChatColor.RED;
                    } else if( mapCount == 2 ) {
                        chatColor = ChatColor.YELLOW;
                    } else if( mapCount == 1 ) {
                        chatColor = ChatColor.GREEN;
                    }
                    Title title = new Title( chatColor + "" + mapCount, "", 0, 1, 1 );
                    Bukkit.getOnlinePlayers( ).forEach( title::send );
                    mapCount--;
                }
            } else if( gameState == GameState.INGAME ) {
                if( Bukkit.getOnlinePlayers().size() == 0 ) {
                    Bukkit.getServer().shutdown();
                }
                /*
                Iterator<Player> playerIterator = PlayerManager.getPlayerDatas().keySet().iterator();
                while( playerIterator.hasNext() ) {
                    Player player = playerIterator.next();
                    if( !player.isOnline() ) {
                        TeamManager.playerDeath( player );
                        playerIterator.remove();
                    }
                }
                */
                updateScoreboardTime();
                SpectatorScoreboard.updateTime();
                getSkyWarsMap().getWorld().setStorm( false );
                getSkyWarsMap().getWorld().setThundering( false );
                if( isPeace() ) {
                    if( peaceTime == 0 ) {
                        peace = false;
                        for( Player player : Bukkit.getOnlinePlayers() ) {
                            player.playSound( player.getLocation( ), Sound.NOTE_PLING, 40, 1 );
                            player.sendMessage( SkyWars.getPrefix() + "§7Die §aSchutzzeit §7ist vorbei!" );
                        }
                    } else {
                        if( peaceTime == 30 || peaceTime == 20 || peaceTime == 10 || peaceTime <= 3 ) {
                            if( peaceTime == 1 ) {
                                MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Die §aSchutzzeit §7endet in §a" + 1 + " Sekunde" );
                            } else {
                                MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§7Die §aSchutzzeit §7endet in §a" + peaceTime + " Sekunden" );
                            }
                        }
                        peaceTime--;
                    }
                }
            } else if( gameState == GameState.END ) {
                for( Player player : Bukkit.getOnlinePlayers() ) {
                    player.setLevel( restartCount );
                    player.setExp( restartCount * 0.0666666666666667F );
                }
                if( restartCount == 0 ) {
                    for( Player player : Bukkit.getOnlinePlayers() ) {
                        player.sendMessage( SkyWars.getPrefix() + "§cDer Server stoppt jetzt!" );
                        player.kickPlayer( "lobby" );
                    }
                    try {
                        Thread.sleep( 3000 );
                    } catch ( InterruptedException e ) {
                        e.printStackTrace( );
                    }
                    Bukkit.shutdown();
                } else {
                    if( restartCount == 15 || restartCount == 10 || restartCount < 5 ) {
                        String sendMessage = null;
                        if( restartCount == 1 ) {
                            sendMessage = SkyWars.getPrefix() + "§cDer Server stoppt in §e1 Sekunde";
                        } else {
                            sendMessage = SkyWars.getPrefix() + "§cDer Server stoppt in §e" + restartCount + " Sekunden";
                        }
                        MessageUtil.sendMessageToAll( sendMessage );
                    }
                    restartCount--;
                }
            }
        }, 20L, 20L );
    }
    public static void sendTitle() {
        Title title = new Title( "§e§lSkyWars", "§a" + MapManager.getConfigMap().getAnzeigename(), 0, 3, 2 );
        Bukkit.getOnlinePlayers( ).forEach( title::send );
    }
    public static void sendMapInfo() {
        MessageUtil.sendMessageToAll( SkyWars.getPrefix() + "§aMap: §6" + MapManager.getConfigMap().getAnzeigename() + " §aErbaut von: §6" + MapManager.getConfigMap().getCreator() );
    }
    public static void updateScoreboardTime() {
        for( Player player : PlayerManager.getPlayerDatas().keySet() ) {
            PlayerData playerData = PlayerManager.getPlayerData( player );
            if( playerData.getPlayerScoreboard() != null ) {
                ScoreboardManager.updateTime( playerData );
            }
            double distance = 0;
            Player findPlayer = null;
            for( Player onlinePlayer : Bukkit.getOnlinePlayers() ) {
                if( onlinePlayer.equals( player ) ) continue;
                PlayerData onlineData = PlayerManager.getPlayerData( onlinePlayer );
                if( onlineData == null ) continue;
                if( playerData.getSkywarsTeam().equals( onlineData.getSkywarsTeam() ) ) continue;
                if( findPlayer == null || onlinePlayer.getLocation().distance( findPlayer.getLocation() ) < distance ) {
                    if( findPlayer != null ) {
                        distance = onlinePlayer.getLocation( ).distance( findPlayer.getLocation( ) );
                    }
                    findPlayer = onlinePlayer;
                }
            }
            if( findPlayer != null ) {
                player.setCompassTarget( findPlayer.getLocation() );
            }
        }
    }
    public static void endGame() {
        if( TeamManager.getAliveTeams().size() == 1 ) {
            setGameState( GameState.END );
            CloudAPI.getServerAPI().setScoreboard( null );
            SkywarsTeam winnerTeam = TeamManager.getAliveTeams().get( 0 );
            for( Player player : winnerTeam.getMembers() ) {
                if( CloudAPI.getNickAPI().isNicked( player ) ) {
                    MessageUtil.sendMessageToAll( SkyWars.getPrefix( ) + "§a" + CloudAPI.getNickAPI().getNickName( player ) + " §7hat das Spiel gewonnen!" );
                } else {
                    MessageUtil.sendMessageToAll( SkyWars.getPrefix( ) + CloudAPI.getRankAPI().getPlayerRankData( player.getName() ).getRankColor() + player.getName() + " §7hat das Spiel gewonnen!" );
                }
                PlayerData playerData = PlayerManager.getPlayerData( player );
                playerData.addWin();
                playerData.saveStats();
            }
            for( Player player : SpectatorManager.getSpectatorPlayers() ) {
                player.removePotionEffect( PotionEffectType.INVISIBILITY );
                for( Player online : Bukkit.getOnlinePlayers() ) {
                    online.showPlayer( player );
                }
            }
        }
    }
    public static String getTime( long time ) {

        long diff = System.currentTimeMillis() - time;
        diff = diff / 1000;

        int minuten = 0;
        int sekunden = 0;

        if( diff >= 60 ) {
            minuten = ( int ) diff / 60;
            diff -= minuten * 60;
        }

        sekunden = ( int ) diff;

        String minRet = String.valueOf( minuten );
        String secRet = String.valueOf( sekunden );

        if(minuten < 10 ) {
            minRet = "0" + minRet;
        }

        if( sekunden < 10 ) {
            secRet = "0" + secRet;
        }


        return minRet + ":" + secRet;

    }
    public static void startGame() {
        LobbyScoreboardManager.removeAllBoards();
        for( Player player : Bukkit.getOnlinePlayers() ) {
            player.playSound( player.getLocation(), Sound.NOTE_PLING, 40, 1 );
        }
        setStartTime( System.currentTimeMillis() );
        setGameState( GameState.WARMUP );
        // Load Map
        for( Player player : Bukkit.getOnlinePlayers() ) {
            for( Player player2 : Bukkit.getOnlinePlayers() ) {
                player.hidePlayer( player2 );
                player.showPlayer( player2 );
            }
        }
        skyWarsMap = MapManager.getConfigMap().getMap();
        CloudAPI.getServerAPI().changeServer( CloudServerState.INGAME, getSkyWarsMap().getAnzeigeName() + " " + SkywarsConfig.getType() );
        Bukkit.getOnlinePlayers( ).forEach( PlayerManager::startGameFor );
        CloudAPI.getServerAPI( ).setScoreboard( ScoreboardManager.getScoreboard( ) );
        MessageUtil.sendMessageToAll( SkyWars.getPrefix( ) + "§aAlle Spieler wurden auf die Karte teleportiert!" );
        SpectatorManager.updateSpectatorInventory( );
        CloudAPI.getServerAPI().setKick( false );
    }
}
