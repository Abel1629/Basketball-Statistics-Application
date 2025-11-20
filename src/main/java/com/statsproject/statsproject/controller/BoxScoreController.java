package com.statsproject.statsproject.controller;

import com.statsproject.statsproject.entity.BoxScore;
import com.statsproject.statsproject.entity.Game;
import com.statsproject.statsproject.entity.Player;
import com.statsproject.statsproject.repository.BoxScoreRepository;
import com.statsproject.statsproject.repository.GameRepository;
import com.statsproject.statsproject.repository.PlayerRepository;
import com.statsproject.statsproject.service.BoxScoreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/boxscores")
public class BoxScoreController {

    private final BoxScoreRepository repo;
    private final GameRepository gameRepo;
    private final PlayerRepository playerRepo;
    private final BoxScoreService service;

    public BoxScoreController(BoxScoreRepository repo,
                              GameRepository gameRepo,
                              PlayerRepository playerRepo,
                              BoxScoreService service) {

        this.repo = repo;
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
        this.service = service;
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("boxscores", repo.findAll());
        return "boxscores/list";
    }

    // USER or ADMIN may create a draft
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/create")
    public String createForm(Model m) {
        m.addAttribute("boxscore", new BoxScore());
        m.addAttribute("games", gameRepo.findAll());
        m.addAttribute("players", playerRepo.findAll());
        return "boxscores/form";
    }

    // USER or ADMIN may save a draft
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/save")
    public String save(@ModelAttribute BoxScore boxScore,
                       @RequestParam Long gameId,
                       @RequestParam Long playerId,
                       Principal principal) {

        Game game = gameRepo.findById(gameId).orElseThrow();
        Player player = playerRepo.findById(playerId).orElseThrow();

        boxScore.setGame(game);
        boxScore.setPlayer(player);

        // IMPORTANT: save as draft + record creator username
        service.saveDraft(boxScore, principal.getName());

        return "redirect:/boxscores";
    }

    // Only the owner can submit
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/submit")
    public String submit(@PathVariable Long id, Principal principal) {
        service.submit(id, principal.getName());
        return "redirect:/boxscores";
    }

    // Only ADMIN can approve
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        service.approve(id);
        return "redirect:/boxscores";
    }
}
