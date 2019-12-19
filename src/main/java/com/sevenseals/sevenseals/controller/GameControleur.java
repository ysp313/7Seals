package com.sevenseals.sevenseals.controller;

import com.sevenseals.sevenseals.constant.GameStateEnum;
import com.sevenseals.sevenseals.entity.Card;
import com.sevenseals.sevenseals.entity.Game;
import com.sevenseals.sevenseals.entity.Player;
import com.sevenseals.sevenseals.entity.Token;
import com.sevenseals.sevenseals.repository.CardRepository;
import com.sevenseals.sevenseals.repository.GameRepository;
import com.sevenseals.sevenseals.repository.PlayerRepository;
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

    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private CardRepository cardRepository;

    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public String startGame(){
        return repository.findAll().toString();
    }


    @GetMapping("/game/create")
    public String createGameGetter(@RequestParam(name="userid") long userId, Model model){
        Player currentPlayer = playerRepository.getOne(userId);
        model.addAttribute("currentPlayer",currentPlayer);
        return "createGame";
    }

    @PostMapping("/game/create")
    public String createGame(@RequestParam(name="userid") long userId){
        Game game = new Game();
        Player currentPlayer = playerRepository.findById(userId).get();
        currentPlayer.setGame(game);
        game.setOwner(currentPlayer);
        game = repository.save(game);
        return "redirect:/game/"+ game.getId() + "?userid="+ userId;
    }

    @GetMapping("/game/{gameid}")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam(name="userid") long userId, Model model){
        Game currentGame = repository.getOne(gameId);
        model.addAttribute("game",currentGame);
        model.addAttribute("currentPlayer",playerRepository.getOne(userId));
        switch (currentGame.getState()){
            case WAITING_PHASE:
                return "waiting";
            case TOKEN_PHASE:
                return "tokenChoice";
            case PLAY_PHASE:
                model.addAttribute("selectionCard", new Card());
                return "game";
            case DONE:
                return "endGame";
        }
        return "game";
    }

    @PostMapping("/game/join")
    public String gameJoin(@RequestParam(name = "gameid") long gameId, @RequestParam(name = "userid") long userId){
        Game selectedGame = repository.getOne(gameId);
        Player currentPlayer = playerRepository.getOne(userId);
        currentPlayer.setGame(selectedGame);
        repository.save(selectedGame);
        return "redirect:/game/" + selectedGame.getId()+"?userid="+ userId;
    }


    @PostMapping("/game/{gameid}/play")
    public String gamePlay(@PathVariable(name = "gameid") long gameId, @RequestParam(name="userid") long userId, @ModelAttribute Card card){
        Game currentGame = repository.getOne(gameId);
        card = cardRepository.findById(card.getId()).get();
        card.setGame(currentGame);
        card.setPlayer(null);
        repository.save(currentGame);
        return "redirect:/game/" + currentGame.getId()+"?userid="+ userId;
    }

    @GetMapping("/game/{gameid}/start")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam(name="userid") long userId){
        Game currentGame = repository.getOne(gameId);
        if(currentGame.getState() == GameStateEnum.WAITING_PHASE) {
            List<Card> deck = new ArrayList<>();
            for (int i = 1; i <= currentGame.getPlayers().toArray().length * 3; i++) {
                deck.add(new Card(i, "Rouge"));
                deck.add(new Card(i, "Jaune"));
                deck.add(new Card(i, "Bleu"));
                deck.add(new Card(i, "Vert"));
                deck.add(new Card(i, "Violet"));
            }
            for (int i = 0; i < 3; i++) {
                new Token("Jaune").setGame(currentGame);
                new Token("Bleu").setGame(currentGame);
                new Token("Vert").setGame(currentGame);
                new Token("Violet").setGame(currentGame);
                new Token("Noir").setGame(currentGame);
                new Token("Noir").setGame(currentGame);
            }

            Random randGen = new Random();
            int decksize = deck.toArray().length;
            for (Player p : currentGame.getPlayers()) {
                System.out.println(p.getUsername() + "deck:" + deck.toArray().length);
                for (int i = 0; i < decksize / currentGame.getPlayers().toArray().length; i++) {
                    Card newCard = deck.remove(randGen.nextInt(deck.size()));
                    newCard.setPlayer(p);
                    newCard.setGame(currentGame);
                }
                for (Card c : p.getCard()) {
                    System.out.println(c.getColor() + " " + c.getValue());
                }
            }
            currentGame.setState(GameStateEnum.TOKEN_PHASE);
            repository.save(currentGame);
        }
        return "redirect:/game/" + currentGame.getId()+"?userid="+ userId;
    }

    @PostMapping("/game/{gameid}/taketoken")
    public String gameTakeToken(@PathVariable(name = "gameid") long gameId,@RequestParam(name = "userid") long userId, @ModelAttribute ArrayList<Token> tokenList){
        Game currentGame = repository.getOne(gameId);
        for(Player player : currentGame.getPlayers()){
            if(player.getId() == gameId){
                currentGame.getTokens().removeAll(tokenList);
                player.getTokens().addAll(tokenList);
            }
        }
        currentGame.setState(GameStateEnum.PLAY_PHASE);
        repository.save(currentGame);
        return "redirect:/game/" + currentGame.getId()+"?userid="+ userId;
    }
}