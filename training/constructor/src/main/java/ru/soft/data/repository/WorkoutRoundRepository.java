package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.WorkoutRound;

import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkModification;

public interface WorkoutRoundRepository extends CrudRepository<WorkoutRound, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM WorkoutRound wr WHERE wr.id = :id")
    int delete(@Param(value = "id") UUID id);

    default void deleteExisted(UUID id) {
        checkModification(delete(id), id);
    }
}
