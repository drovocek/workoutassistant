package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.Exercise;

import java.util.UUID;

public interface ExerciseRepository extends BaseRepository<Exercise> {

    @Modifying
    @Transactional
    @Query("DELETE FROM exercise e WHERE e.id = :id")
    int delete(@Param(value = "id") UUID id);
}
