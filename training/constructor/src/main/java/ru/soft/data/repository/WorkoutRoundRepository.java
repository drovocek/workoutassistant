package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutRound;

import java.util.UUID;

public interface WorkoutRoundRepository extends BaseRepository<WorkoutRound> {

    @Modifying
    @Transactional
    @Query("DELETE FROM workout_round wr WHERE wr.id = :id")
    int delete(@Param(value = "id") UUID id);
}
