package com.statsproject.statsproject.controller;

import com.statsproject.statsproject.entity.Game;
import com.statsproject.statsproject.entity.Team;
import com.statsproject.statsproject.repository.GameRepository;
import com.statsproject.statsproject.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    // LIST GAMES
    @GetMapping
    public String list(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        return "games/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("teams", teamRepository.findAll());
        return "games/form";
    }

    // SAVE GAME
    @PostMapping("/save")
    public String save(
            @ModelAttribute Game game,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam("homeTeamId") Long homeTeamId,
            @RequestParam("awayTeamId") Long awayTeamId
    ) {

        Team home = teamRepository.findById(homeTeamId).orElseThrow();
        Team away = teamRepository.findById(awayTeamId).orElseThrow();

        game.setDateTime(dateTime);
        game.setHomeTeam(home);
        game.setAwayTeam(away);

        gameRepository.save(game);
        return "redirect:/games";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        gameRepository.deleteById(id);
        return "redirect:/games";
    }
}
