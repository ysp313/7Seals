package com.sevenseals.sevenseals.controller;

import com.sevenseals.sevenseals.entity.Player;
import com.sevenseals.sevenseals.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerController {
    @Autowired
    private PlayerRepository repository;

    @GetMapping("/user/create")
    public String addUser(Model model){
        model.addAttribute("newUser", new Player());
        return "userCreate";
    }

    @PostMapping("/user/create")
    public String addUser(@ModelAttribute Player player, Model model){
        Player newPlayer = repository.save(player);
        return "redirect:/game/create?userid="+newPlayer.getId();
    }
}
