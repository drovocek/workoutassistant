package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.repository.common.BaseRepository;

import java.util.UUID;

public interface TrainingSessionRepository extends BaseRepository<TrainingSession> {

    @Modifying
    @Transactional
    @Query("DELETE FROM training_session ts WHERE ts.id = :id")
    int delete(@Param(value = "id") UUID id);
}
