package com.statsproject.statsproject.controller;


import com.statsproject.statsproject.entity.Player;
import com.statsproject.statsproject.entity.Team;
import com.statsproject.statsproject.repository.PlayerRepository;
import com.statsproject.statsproject.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamRepository repo;
    public TeamController(TeamRepository repo, PlayerRepository playerRepo) { this.repo = repo;
        this.playerRepo = playerRepo;
    }
    private final PlayerRepository playerRepo;
    @GetMapping
    public String list(Model m) {
        m.addAttribute("teams", repo.findAll());
        return "teams/list";
    }
    @GetMapping("/create")
    public String createForm(Model m) {
        m.addAttribute("team", new Team());
        return "teams/form";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute Team team) {
        repo.save(team);
        return "redirect:/teams";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model m) {
        repo.findById(id).ifPresent(t -> m.addAttribute("team", t));
        return "teams/form";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // First delete players that reference this team to avoid FK violation
        List<Player> players = playerRepo.findByTeam_Id(id);
        if (!players.isEmpty()) {
            playerRepo.deleteAll(players);
        }
        repo.deleteById(id);
        return "redirect:/teams";
    }
}
