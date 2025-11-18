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
    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo,
                           PasswordEncoder encoder, TeamRepository teamRepo,
                           PlayerRepository playerRepo, GameRepository
                                   gameRepo) {
        this.roleRepo = roleRepo; this.userRepo = userRepo; this.encoder =
                encoder;
        this.teamRepo = teamRepo; this.playerRepo = playerRepo; this.gameRepo =
                gameRepo;
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
// sample teams and players
        Team t1 = new Team(); t1.setName("OKC Thunder"); t1.setCity("Oklahoma City");
        Team t2 = new Team(); t2.setName("LA Lakers"); t2.setCity("Los Angeles");
        teamRepo.save(t1); teamRepo.save(t2);
        Player p1 = new Player(); p1.setFirstName("Shai");
        p1.setLastName("Gilgeous-Alexander"); p1.setNumber(4); p1.setPosition("G"); p1.setTeam(t1);
        Player p2 = new Player(); p2.setFirstName("Chat");
        p2.setLastName("Holmgren"); p2.setNumber(12); p2.setPosition("C"); p2.setTeam(t1);
        Player p3 = new Player(); p3.setFirstName("Le-Bron");
        p3.setLastName("James"); p3.setNumber(33); p3.setPosition("F"); p3.setTeam(t2);
        playerRepo.save(p1); playerRepo.save(p2); playerRepo.save(p3);
// sample game
        Game g = new Game(); g.setDateTime(LocalDateTime.now().minusDays(2));
        g.setHomeTeam(t1); g.setAwayTeam(t2);
        g.setHomeScore(102); g.setAwayScore(98);
        gameRepo.save(g);
    }
}
