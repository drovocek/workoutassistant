package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutResult;

import java.util.UUID;

public interface WorkoutResultRepository extends BaseRepository<WorkoutResult> {

    @Modifying
    @Transactional
    @Query("DELETE FROM workout_result wr WHERE wr.id = :id")
    int delete(@Param(value = "id") UUID id);
}
