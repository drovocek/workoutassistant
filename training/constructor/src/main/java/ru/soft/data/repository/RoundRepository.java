package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import ru.soft.data.model.Round;
import ru.soft.data.repository.common.BaseRepository;

import java.util.UUID;

public interface RoundRepository extends BaseRepository<Round> {

    @Modifying
    @Query("DELETE FROM round r WHERE r.id = :id")
    int delete(@Param(value = "id") UUID id);
}
