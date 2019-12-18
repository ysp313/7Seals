package com.sevenseals.sevenseals.controller;


import com.sevenseals.sevenseals.entity.Card;
import com.sevenseals.sevenseals.entity.Game;
import com.sevenseals.sevenseals.entity.Player;
import com.sevenseals.sevenseals.entity.Token;
import com.sevenseals.sevenseals.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameControleur{
    @Autowired
    private GameRepository repository;

    @GetMapping("/")
    @ResponseBody
    public String startGame(){
        return repository.findAll().toString();
    }


    @GetMapping("/game/create")
    @ResponseBody
    public String createGameGetter(@ModelAttribute Player p){
        return "createGame";
    }

    @PostMapping("/game/create")
    public String createGame(@ModelAttribute Player p){
        Game game = new Game();
        game.getPlayers().add(p);
        repository.save(game);
        return "redirect:/game/"+ game.getId();
    }

    @GetMapping("/game/{gameid}")
    public String gameStart(@PathVariable(name = "gameid") long gameId, Model model){
        model.addAttribute("game",repository.getOne(gameId));
        return "game";
    }

    @GetMapping("/game/join")
    public String gameJoin(@PathVariable(name = "gameid") long gameId, @ModelAttribute Player p){
        Game selectedGame = repository.getOne(gameId);
        selectedGame.getPlayers().add(p);
        repository.save(selectedGame);
        return "redirect:/game/" + selectedGame.getId();
    }


    @PostMapping("/game/{gameid}/play")
    public String gamePlay(@PathVariable(name = "gameid") long gameId, @RequestParam Card card){
        Game currentGame = repository.getOne(gameId);
        currentGame.getField().add(card);
        repository.save(currentGame);
        return "";
    }

    @PostMapping("/game/{gameid}/start")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam String username){
        Game currentGame = repository.getOne(gameId);
        List<Card> deck = new ArrayList<>();
        for(int i = 0; i < currentGame.getPlayers().size() * 3;i++){
            deck.add(new Card(i, "Rouge"));
            deck.add(new Card(i, "Jaune"));
            deck.add(new Card(i, "Bleu"));
            deck.add(new Card(i, "Vert"));
            deck.add(new Card(i, "Violet"));
            if(i%3 == 0) // le nomebre de carte d'une couleur divisé par 3
                currentGame.getTokens().add(new Token("Rouge"));
        }
        for(int i=0; i < currentGame.getPlayers().size() * 3 / 5; i++) {
            currentGame.getTokens().add(new Token("Jaune"));
            currentGame.getTokens().add(new Token("Bleu"));
            currentGame.getTokens().add(new Token("Vert"));
            currentGame.getTokens().add(new Token("Violet"));
            currentGame.getTokens().add(new Token("Noir"));
            currentGame.getTokens().add(new Token("Noir"));
        }

        Random randGen = new Random();
        for(Player p : currentGame.getPlayers()){
            for(int i = 0; i < currentGame.getPlayers().size() * 3;i++){
                p.getHand().add(deck.remove(randGen.nextInt(deck.size())));
            }
        }
        repository.save(currentGame);
        return "";
    }

    @PostMapping("/game/{gameid}/taketoken")
    public String gameTakeToken(@PathVariable(name = "gameid") long gameId,@PathVariable long playerId, @ModelAttribute List<Token> tokenList){
        Game currentGame = repository.getOne(gameId);
        for(Player player : currentGame.getPlayers()){
            if(player.getId() == gameId){
                currentGame.getTokens().removeAll(tokenList);
                player.getTokens().addAll(tokenList);
            }
        }
        repository.save(currentGame);
        return "redirect:/";
    }
}