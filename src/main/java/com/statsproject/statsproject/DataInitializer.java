package com.statsproject.statsproject;

import com.statsproject.statsproject.entity.*;
import com.statsproject.statsproject.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Set;
@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;
    private final GameRepository gameRepo;
    private final BoxScoreRepository boxScoreRepo;
    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo,
                           PasswordEncoder encoder, TeamRepository teamRepo,
                           PlayerRepository playerRepo, GameRepository
                                   gameRepo, BoxScoreRepository boxScoreRepo) {
        this.roleRepo = roleRepo; this.userRepo = userRepo; this.encoder =
                encoder;
        this.teamRepo = teamRepo; this.playerRepo = playerRepo; this.gameRepo =
                gameRepo;
        this.boxScoreRepo = boxScoreRepo;
    }
    @Override
    public void run(String... args) {
        // roles
        Role rUser = new Role(); rUser.setName("ROLE_USER");
        Role rAdmin = new Role(); rAdmin.setName("ROLE_ADMIN");
        roleRepo.save(rUser); roleRepo.save(rAdmin);

        // users
        User admin = new User(); admin.setUsername("admin");
        admin.setPassword(encoder.encode("adminpass"));
        admin.setRoles(Set.of(rAdmin, rUser)); userRepo.save(admin);
        User user = new User(); user.setUsername("user");
        user.setPassword(encoder.encode("userpass"));
        user.setRoles(Set.of(rUser)); userRepo.save(user);

        // sample teams
        Team t1 = new Team(); t1.setName("CSM Oradea"); t1.setCity("Oradea");
        Team t2 = new Team(); t2.setName("U-BT Cluj Napoca"); t2.setCity("Cluj Napoca");
        Team t3 = new Team(); t3.setName("CS Valcea 1924"); t3.setCity("Ramnicu Valcea");
        Team t4 = new Team(); t4.setName("CSU Sibiu"); t4.setCity("Sibiu");
        teamRepo.save(t1); teamRepo.save(t2); teamRepo.save(t3); teamRepo.save(t4);

        // sample players
        Player p1 = new Player(); p1.setFirstName("Kris");
        p1.setLastName("Richard"); p1.setNumber(0); p1.setPosition("G"); p1.setTeam(t1);
        Player p2 = new Player(); p2.setFirstName("Khalif");
        p2.setLastName("Young"); p2.setNumber(13); p2.setPosition("C"); p2.setTeam(t1);
        Player p3 = new Player(); p3.setFirstName("Darron");
        p3.setLastName("Russel"); p3.setNumber(33); p3.setPosition("G"); p3.setTeam(t2);
        Player p4 = new Player(); p4.setFirstName("Titus");
        p4.setLastName("Nicoara"); p4.setNumber(10); p4.setPosition("F"); p4.setTeam(t3);
        Player p5 = new Player(); p5.setFirstName("Monyea");
        p5.setLastName("Pratt"); p5.setNumber(1); p5.setPosition("SF"); p5.setTeam(t3);

        playerRepo.save(p1); playerRepo.save(p2); playerRepo.save(p3); playerRepo.save(p4); playerRepo.save(p5);

        // sample game
        Game g1 = new Game(); g1.setDateTime(LocalDateTime.now().minusDays(2));
        g1.setHomeTeam(t1); g1.setAwayTeam(t2);
        g1.setHomeScore(102); g1.setAwayScore(98);
        Game g2 = new Game(); g2.setDateTime(LocalDateTime.now().minusDays(5));
        g2.setHomeTeam(t2); g2.setAwayTeam(t3);
        g2.setHomeScore(77); g2.setAwayScore(70);
        Game g3 = new Game(); g3.setDateTime(LocalDateTime.now().minusDays(7));
        g3.setHomeTeam(t3); g3.setAwayTeam(t1);
        g3.setHomeScore(81); g3.setAwayScore(93);
        Game g4 = new Game(); g4.setDateTime(LocalDateTime.now().minusDays(11));
        g4.setHomeTeam(t4); g4.setAwayTeam(t2);
        g4.setHomeScore(66); g4.setAwayScore(85);
        Game g5 = new Game(); g5.setDateTime(LocalDateTime.now().minusDays(13));
        g5.setHomeTeam(t3); g5.setAwayTeam(t4);
        g5.setHomeScore(80); g5.setAwayScore(77);
        gameRepo.save(g1); gameRepo.save(g2); gameRepo.save(g3); gameRepo.save(g4); gameRepo.save(g5);

        // sample boxscores
        BoxScore bs1 = new BoxScore();
        bs1.setGame(g1);
        bs1.setPlayer(p1);
        bs1.setPoints(35);
        bs1.setRebounds(8);
        bs1.setAssists(6);

        BoxScore bs2 = new BoxScore();
        bs2.setGame(g1);
        bs2.setPlayer(p3);
        bs2.setPoints(30);
        bs2.setRebounds(4);
        bs2.setAssists(11);

        BoxScore bs3 = new BoxScore();
        bs3.setGame(g2);
        bs3.setPlayer(p4);
        bs3.setPoints(11);
        bs3.setRebounds(9);
        bs3.setAssists(2);

        BoxScore bs4 = new BoxScore();
        bs4.setGame(g3);
        bs4.setPlayer(p2);
        bs4.setPoints(16);
        bs4.setRebounds(12);
        bs4.setAssists(3);

        BoxScore bs5 = new BoxScore();
        bs5.setGame(g4);
        bs5.setPlayer(p3);
        bs5.setPoints(19);
        bs5.setRebounds(5);
        bs5.setAssists(7);

        BoxScore bs6 = new BoxScore();
        bs6.setGame(g5);
        bs6.setPlayer(p5);
        bs6.setPoints(22);
        bs6.setRebounds(10);
        bs6.setAssists(12);

// you must save using repository
        boxScoreRepo.save(bs1);
        boxScoreRepo.save(bs2);
        boxScoreRepo.save(bs3);
        boxScoreRepo.save(bs4);
        boxScoreRepo.save(bs5);
        boxScoreRepo.save(bs6);

    }
}
