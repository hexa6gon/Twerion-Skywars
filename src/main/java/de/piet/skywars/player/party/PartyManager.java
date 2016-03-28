package de.piet.skywars.player.party;
import de.piet.cloudsystem.packets.types.PartyMemberPacket;
import de.piet.skywars.SkyWars;
import de.piet.skywars.config.SkywarsConfig;
import de.piet.skywars.team.SkywarsTeam;
import de.piet.skywars.team.TeamManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by PeterH on 23.01.2016.
 */
public class PartyManager {
    @Getter
    private static HashMap<String,PartyMemberPacket> partyMembers = new HashMap<>(  );
    public static void receivePartyPacket( PartyMemberPacket partyMemberPacket ) {
        List<Player> onlinePlayers = new ArrayList<>(  );
        for( String member : partyMemberPacket.getMembers() ) {
            Player player = Bukkit.getServer( ).getPlayer( member );
            if( player != null ) {
                onlinePlayers.add( player );
            }
            partyMembers.put( member.toLowerCase(), partyMemberPacket );
        }
        if( onlinePlayers.size() == partyMemberPacket.getMembers().size() ) {
            allPlayersOnline( onlinePlayers );
        }
    }
    public static void allPlayersOnline( List<Player> onlinePartyPlayers ) {
        SkywarsTeam skywarsTeam = null;
        for( int id : TeamManager.getTeams().keySet() ) {
            SkywarsTeam getTeam = TeamManager.getTeams().get( id );
            int space = SkywarsConfig.getProTeam() - getTeam.getMembers( ).size();
            if( space >= onlinePartyPlayers.size() ) {
                skywarsTeam = getTeam;
                break;
            }
        }
        for( Player player : onlinePartyPlayers ) {
            partyMembers.remove( player.getName().toLowerCase() );
            if( skywarsTeam != null ) {
                skywarsTeam.playerJoin( player );
                player.sendMessage( SkyWars.getPrefix() + "§aDeine Party ist in einem Team!" );
            } else {
                player.sendMessage( SkyWars.getPrefix( ) + "§4Es konnte kein Team für die Party gefunden werden!" );
            }
        }
    }
}
