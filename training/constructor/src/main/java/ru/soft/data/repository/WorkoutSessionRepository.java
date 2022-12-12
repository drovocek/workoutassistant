package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutSession;

import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkModification;

public interface WorkoutSessionRepository extends CrudRepository<WorkoutSession, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM WorkoutSession ws WHERE ws.id = :id")
    int delete(@Param(value = "id") UUID id);

    default void deleteExisted(UUID id) {
        checkModification(delete(id), id);
    }
}
