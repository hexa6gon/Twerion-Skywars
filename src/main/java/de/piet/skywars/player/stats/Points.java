package de.piet.skywars.player.stats;
/**
 * Created by PeterH on 21.01.2016.
 */
public enum Points {
    KILL( 5 ), WIN( 15 );
    public int points;
    Points( int points ) {
        this.points = points;
    }
    public int getPoints() {
        return points;
    }
}
