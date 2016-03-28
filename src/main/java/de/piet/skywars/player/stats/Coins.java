package de.piet.skywars.player.stats;
/**
 * Created by PeterH on 21.01.2016.
 */
public enum Coins {
    KILL( 10 ), WIN( 25 );
    public int coins;
    Coins( int coins ) {
        this.coins = coins;
    }
    public int getCoins() {
        return coins;
    }
}
