package com.statsproject.statsproject.controller;

import com.statsproject.statsproject.entity.Player;
import com.statsproject.statsproject.repository.PlayerRepository;
import com.statsproject.statsproject.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerRepository playerRepo;
    private final TeamRepository teamRepo;
    public PlayerController(PlayerRepository playerRepo, TeamRepository
            teamRepo) { this.playerRepo = playerRepo; this.teamRepo = teamRepo; }
    @GetMapping
    public String list(Model m) {
        m.addAttribute("players", playerRepo.findAll());
        return "players/list";
    }
    @GetMapping("/create")
    public String createForm(Model m) {
        m.addAttribute("player", new Player());
        m.addAttribute("teams", teamRepo.findAll());
        return "players/form";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute Player player, @RequestParam(required = false) Long teamId) {
        if (teamId != null) {
            teamRepo.findById(teamId).ifPresent(player::setTeam);
        } else {
            player.setTeam(null);
        }
        playerRepo.save(player);
        return "redirect:/players";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model m) {
        playerRepo.findById(id).ifPresent(p -> m.addAttribute("player", p));
        m.addAttribute("teams", teamRepo.findAll());
        return "players/form";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        playerRepo.deleteById(id);
        return "redirect:/players";
    }
}
