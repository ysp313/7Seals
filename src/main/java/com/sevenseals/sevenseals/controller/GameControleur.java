package com.sevenseals.sevenseals.controller;


import com.sevenseals.sevenseals.entity.Card;
import com.sevenseals.sevenseals.entity.Token;
import com.sevenseals.sevenseals.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameControleur{

    @Autowired
    private GameRepository repository;

    @GetMapping("/")
    @ResponseBody
    public String gameStart(){
        return repository.findAll().toString();
    }

    @GetMapping("/game/{gameid}")
    public String gameStart(@PathVariable(name = "gameid") long gameId){
        return "";
    }

    @GetMapping("/game/{gameid}/join")
    public String gameJoin(@PathVariable(name = "gameid") long gameId, @RequestParam String username){
        return "";
    }


    @PostMapping("/game/{gameid}/play")
    public String gamePlay(@PathVariable(name = "gameid") long gameId, @RequestParam Card card){
        return "";
    }

    @PostMapping("/game/{gameid}/start")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam String username){
        //distribuer les cartes

        //mes cartes
        return "";
    }

    @PostMapping("/game/{gameid}/taketoken")
    public String gameTakeToken(@PathVariable(name = "gameid") long gameId, @ModelAttribute List<Token> tokenList){

        return "redirect:/";
    }



}