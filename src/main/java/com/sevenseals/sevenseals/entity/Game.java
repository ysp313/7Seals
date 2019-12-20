package com.sevenseals.sevenseals.entity;

import com.sevenseals.sevenseals.constant.GameStateEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Card> field;

    private int startPos;

    private int currentPos;

    private GameStateEnum state;

    @OneToOne
    private Player owner;


    public Game() {
        this.state = GameStateEnum.WAITING_PHASE;
        this.players = new ArrayList<>();
        this.field = new ArrayList<>();
    }
    public void placeOnField(Card c){
        this.field.add(c);
        c.setField(this);
        c.setPlayer(null);
        if (this.currentPos == this.players.toArray().length-1)
            this.checkWinner();
        else
            this.currentPos++;

    }

    private void checkWinner() {
        int pos = (this.startPos + getWinnerPos()) % this.players.toArray().length;
        this.players.get(pos).addScore(1);
        this.currentPos = 0;
        this.startPos = pos;
        
        for(Card card:this.field){
            card.setField(null);
            card = null;
        }
        this.field = new ArrayList<>();
    }

    private int getWinnerPos(){
        Card winner = this.field.get(0);
        for(Card c: this.field){
            if(c.getColor() == "Rouge" && winner.getColor() != "Rouge"){
                winner = c;
            }else
            if(c.getColor() == "Rouge" && winner.getColor() == "Rouge"){
                if(c.getValue()>winner.getValue()){
                    winner = c;
                }
            }else{
                if(winner.getColor() != "Rouge"){
                    if(c.getValue()>winner.getValue()){
                        winner = c;
                    }
                }
            }
        }
        return this.field.indexOf(winner);
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public GameStateEnum getState() {
        return state;
    }

    public void setState(GameStateEnum state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }


    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getField() {
        return field;
    }

    public void setField(List<Card> field) {
        this.field = field;
    }

    public void setPlayer(List<Player> players) {
        this.players = players;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner= owner;
        owner.setGame(this);
    }
	public void addPlayer(Player player) {
        this.players.add(player);
	}

    public Player getPlayerToPlay() {
        return this.players.get((startPos + currentPos)%this.players.toArray().length);
    }
	public String getPlayableColor() {
        Card first = this.field.get(0);
        String color = first.getColor();
        for(Card c : this.field){
            if(c.getColor() == "Rouge"){
                return "Rouge";
            }
        }
		return color;
	}
}
