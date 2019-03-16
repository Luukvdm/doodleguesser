package restauthshared.model;

import fontysmultipurposelibrary.dataacces.Entity;

import java.util.Date;

public class Token extends Entity {

    private String token;
    private Date creationDate;
    private int timeToLive;
    private long playerId;

    public Token(String token, Date creationDate, int timeToLive, long playerId) {
        this.token = token;
        this.creationDate = creationDate;
        this.timeToLive = timeToLive;
        this.playerId = playerId;
    }

    public Token() {

    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
