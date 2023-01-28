package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import ru.soft.data.model.Workout;
import ru.soft.data.repository.common.BaseRepository;

import java.util.UUID;

public interface WorkoutRepository extends BaseRepository<Workout> {

    @Modifying
    @Query("DELETE FROM workout w WHERE w.id = :id")
    int delete(@Param(value = "id") UUID id);
}
