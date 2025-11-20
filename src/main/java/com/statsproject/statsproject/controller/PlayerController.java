package com.statsproject.statsproject.controller;

import com.statsproject.statsproject.entity.Player;
import com.statsproject.statsproject.entity.Team;
import com.statsproject.statsproject.repository.PlayerRepository;
import com.statsproject.statsproject.repository.TeamRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createForm(Model m) {
        m.addAttribute("player", new Player());
        m.addAttribute("teams", teamRepo.findAll());
        return "players/form";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@ModelAttribute Player player,
                       @RequestParam Long teamId) {

        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        player.setTeam(team);
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
