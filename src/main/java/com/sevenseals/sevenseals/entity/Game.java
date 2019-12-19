package com.sevenseals.sevenseals.entity;

import com.sevenseals.sevenseals.constant.GameStateEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Token> tokens;

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
        this.tokens = new ArrayList<>();
        this.field = new ArrayList<>();
    }
    public void init(){

        for (int i = 0; i < 3; i++) {
            this.tokens.add(new Token("Jaune", this));
            this.tokens.add(new Token("Bleu",this));
            this.tokens.add(new Token("Vert",this));
            this.tokens.add(new Token("Violet",this));
            this.tokens.add(new Token("Noir",this));
            this.tokens.add(new Token("Noir",this));
        }
    }
    public void takeToken(Token t){
        this.tokens.remove(t);
    }
    public void placeOnField(Card c){
        this.field.add(c);
        c.setField(this);
        c.setPlayer(null);
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

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Token> getTokens() {
        return tokens;
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
}
