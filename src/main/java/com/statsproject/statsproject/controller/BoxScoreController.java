package com.statsproject.statsproject.controller;

import com.statsproject.statsproject.entity.BoxScore;
import com.statsproject.statsproject.repository.BoxScoreRepository;
import com.statsproject.statsproject.repository.GameRepository;
import com.statsproject.statsproject.repository.PlayerRepository;
import com.statsproject.statsproject.service.BoxScoreService;
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
    public BoxScoreController(BoxScoreRepository repo, GameRepository gameRepo,
                              PlayerRepository playerRepo, BoxScoreService service) {
        this.repo = repo; this.gameRepo = gameRepo; this.playerRepo =
                playerRepo; this.service = service;
    }
    @GetMapping
    public String list(Model m) {
        m.addAttribute("boxscores", repo.findAll());
        return "boxscores/list";
    }
    @GetMapping("/create")
    public String createForm(Model m) {
        m.addAttribute("boxscore", new BoxScore());
        m.addAttribute("games", gameRepo.findAll());
        m.addAttribute("players", playerRepo.findAll());
        return "boxscores/form";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoxScore boxScore, Principal principal) {
        boxScore.setCreatedBy(principal.getName());
        service.saveDraft(boxScore);
        return "redirect:/boxscores";
    }

    @PostMapping("/{id}/submit")
    public String submit(@PathVariable Long id, Principal principal) {
        service.submit(id, principal.getName());
        return "redirect:/boxscores";
    }
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        service.approve(id);
        return "redirect:/boxscores";
    }
}
