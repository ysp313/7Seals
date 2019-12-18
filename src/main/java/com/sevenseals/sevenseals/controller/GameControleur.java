package com.sevenseals.sevenseals.controller;


import com.sevenseals.sevenseals.entity.Card;
import com.sevenseals.sevenseals.entity.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameControleur{

    @GetMapping("/")
    public String gameStart(){

    }

    @GetMapping("/game/{gameid}")
    public String gameStart(@PathVariable(name = "gameid") long gameId){

    }

    @GetMapping("/game/{gameid}/join")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam String username){

    }


    @PostMapping("/game/{gameid}/play")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam Card card){

    }

    @PostMapping("/game/{gameid}/start")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @RequestParam String username){
        //distribuer les cartes

        return ""//mes cartes
    }

    @PostMapping("/game/{gameid}/taketoken")
    public String gameStart(@PathVariable(name = "gameid") long gameId, @ModelAttribute List<Token> tokenList){

        return "redirect:/";
    }



}