package com.statsproject.statsproject.controller;


import com.statsproject.statsproject.entity.Game;
import com.statsproject.statsproject.entity.Player;
import com.statsproject.statsproject.entity.Team;
import com.statsproject.statsproject.repository.GameRepository;
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
    private final PlayerRepository playerRepo;
    private final GameRepository gameRepo;
    public TeamController(TeamRepository repo, PlayerRepository playerRepo, GameRepository gameRepo) {
        this.repo = repo;
        this.playerRepo = playerRepo;
        this.gameRepo = gameRepo;
    }

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

        // 1) Delete players of this team
        List<Player> players = playerRepo.findByTeam_Id(id);
        if (!players.isEmpty()) {
            playerRepo.deleteAll(players);
        }

        // 2) Delete games where this team is either home or away
        List<Game> games = gameRepo.findByHomeTeam_IdOrAwayTeam_Id(id, id);
        if (!games.isEmpty()) {
            gameRepo.deleteAll(games);
        }

        // 3) Delete the team itself
        repo.deleteById(id);

        return "redirect:/teams";
    }

}
