package com.statsproject.statsproject.service;

import com.statsproject.statsproject.entity.BoxScore;
import com.statsproject.statsproject.entity.Status;
import com.statsproject.statsproject.repository.BoxScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxScoreService {

    private final BoxScoreRepository repo;

    public BoxScoreService(BoxScoreRepository repo) {
        this.repo = repo;
    }

    // SAVE AS DRAFT + RECORD CREATOR
    public BoxScore saveDraft(BoxScore b, String username) {

        b.setStatus(Status.DRAFT);
        b.setCreatedBy(username);

        return repo.save(b);
    }

    // ONLY OWNER CAN SUBMIT A DRAFT
    public BoxScore submit(Long id, String username) {
        BoxScore b = repo.findById(id).orElseThrow();

        if (b.getStatus() == null)
            b.setStatus(Status.DRAFT);

        if (!username.equals(b.getCreatedBy()))
            throw new RuntimeException("Not owner");

        b.setStatus(Status.SUBMITTED);
        return repo.save(b);
    }

    // ONLY ADMIN CAN APPROVE
    public BoxScore approve(Long id) {
        BoxScore b = repo.findById(id).orElseThrow();

        if (b.getStatus() == null)
            b.setStatus(Status.DRAFT);

        b.setStatus(Status.APPROVED);
        return repo.save(b);
    }

    public List<BoxScore> findByGame(Long gameId) {
        return repo.findByGame_Id(gameId);
    }

    public List<BoxScore> findByUser(String username) {
        return repo.findByCreatedBy(username);
    }
}
