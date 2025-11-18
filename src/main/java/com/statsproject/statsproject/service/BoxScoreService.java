package com.statsproject.statsproject.service;

import com.statsproject.statsproject.entity.BoxScore;
import com.statsproject.statsproject.entity.Status;
import com.statsproject.statsproject.repository.BoxScoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BoxScoreService {
    private final BoxScoreRepository repo;
    public BoxScoreService(BoxScoreRepository repo) { this.repo = repo; }
    public BoxScore saveDraft(BoxScore b) {
        b.setStatus(Status.DRAFT);
        return repo.save(b);
    }
    public BoxScore submit(Long id, String username) {
        BoxScore b = repo.findById(id).orElseThrow();
        if (!username.equals(b.getCreatedBy())) throw new RuntimeException("Not owner");
                b.setStatus(Status.SUBMITTED);
        return repo.save(b);
    }
    public BoxScore approve(Long id) {
        BoxScore b = repo.findById(id).orElseThrow();
        b.setStatus(Status.APPROVED);
        return repo.save(b);
    }
    public List<BoxScore> findByGame(Long gameId) { return
            repo.findByGame_Id(gameId); }
    public List<BoxScore> findByUser(String username) { return
            repo.findByCreatedBy(username); }
}