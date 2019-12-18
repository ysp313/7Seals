package com.sevenseals.sevenseals.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Token> tokens;

    private List<Card> field;

    private int startPos;

    public Game() {
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
}
