package de.piet.skywars.player.party;
import de.piet.cloud.spigotplugin.api.PacketGetter;
import de.piet.cloudsystem.packets.PacketType;
import de.piet.cloudsystem.packets.types.InitPacket;
import de.piet.cloudsystem.packets.types.PartyMemberPacket;
import io.netty.channel.Channel;
/**
 * Created by PeterH on 23.01.2016.
 */
public class PartyPacketListener extends PacketGetter {
    @Override
    public void receivePacket ( InitPacket initPacket ) {
        if( initPacket.getKey().equals( PacketType.PARTY_MEMBER_PACKET.getKey() ) ) {
            PartyMemberPacket partyMemberPacket = ( PartyMemberPacket ) initPacket.getValue();
            PartyManager.receivePartyPacket( partyMemberPacket );
        }
    }

    @Override
    public void channelActive ( Channel channel ) {

    }
}
