package com.sevenseals.sevenseals.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="player", cascade = CascadeType.ALL)
    private List<Card> card;


    @Column
    private int plis;

    private int score;
    private String username;
    @Transient
    private List<Card> playableCards;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Player() {
        this.card = new ArrayList<>();
        this.playableCards = new ArrayList<>();

    }

    public Game getGame() {
        return game;
    }



    public void setGame(Game game) {
        this.game = game;
        this.game.addPlayer(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCard() {
        return card;
    }
    public List<Card> getPlayableCards(){
        String color = this.game.getPlayableColor();
        System.out.println("BOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM"+color);
        if(color==null)
            return this.card;
        for(Card c: this.card){
            if(color == c.getColor() || c.getColor() == "Rouge")
            playableCards.add(c);
        }
        if(playableCards.size()== 0){
            return this.card;
        }
        return playableCards;
    }
    public void setCard(List<Card> hand) {
        this.card = hand;
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

    public void addCard(Card c){
        this.card.add(c);
        c.setGame(game);
        c.setPlayer(this);
    }
    public int getPlis() {
        return plis;
    }

    public void setPlis(int plis) {
        this.plis = plis;
    }

    public void addScore(int i) {
        this.score += i;
    }
}
