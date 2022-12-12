package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutPlan;

import java.util.UUID;

public interface WorkoutPlanRepository extends BaseRepository<WorkoutPlan> {

    @Modifying
    @Transactional
    @Query("DELETE FROM workout_plan wp WHERE wp.id = :id")
    int delete(@Param(value = "id") UUID id);
}
