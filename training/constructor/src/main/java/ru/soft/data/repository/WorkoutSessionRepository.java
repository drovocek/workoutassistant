package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutSession;

import java.util.UUID;

public interface WorkoutSessionRepository extends BaseRepository<WorkoutSession> {

    @Modifying
    @Transactional
    @Query("DELETE FROM workout_session ws WHERE ws.id = :id")
    int delete(@Param(value = "id") UUID id);
}
