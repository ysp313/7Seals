package com.sevenseals.sevenseals.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="player", cascade = CascadeType.ALL)
    private List<Card> card;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Token> tokens;

    private int score;
    private String username;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getHand() {
        return card;
    }

    public void setHand(List<Card> hand) {
        this.card = hand;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
