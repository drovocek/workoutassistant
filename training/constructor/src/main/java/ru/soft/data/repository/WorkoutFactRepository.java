package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutFact;

import java.util.UUID;

public interface WorkoutFactRepository extends BaseRepository<WorkoutFact> {

    @Modifying
    @Transactional
    @Query("DELETE FROM workout_fact wf WHERE wf.id = :id")
    int delete(@Param(value = "id") UUID id);
}
