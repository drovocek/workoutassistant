package ru.soft.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.soft.data.BaseEntity;
import ru.soft.data.HasId;
import ru.soft.data.repository.BaseRepository;
import ru.soft.web.to.mapper.TOMapper;

import java.util.UUID;

import static ru.soft.utils.ValidationUtil.checkNew;
import static ru.soft.utils.ValidationUtil.checkNotNew;

@Slf4j
abstract class BaseApiController<T extends BaseEntity, TO extends HasId> {

    protected abstract BaseRepository<T> getRepository();

    protected abstract TOMapper<T, TO> getMapper();

    @GetMapping("/{id}")
    protected ResponseEntity<TO> get(@NotNull @PathVariable UUID id) {
        log.info("get by id={}", id);
        T existed = getRepository().getExisted(id);
        return ResponseEntity.ok(getMapper().toTo(existed));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    protected void delete(@NotNull @PathVariable UUID id) {
        log.info("delete {}", id);
        getRepository().deleteExisted(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<TO> add(@Valid @RequestBody TO to) {
        log.info("add {}", to);
        checkNew(to);
        T forCreate = getMapper().fromTo(to);
        T created = getRepository().save(forCreate);
        return ResponseEntity.ok(getMapper().toTo(created));
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    protected void update(@RequestBody @Valid TO to) {
        log.info("update by {}", to);
        checkNotNew(to);
        getRepository().getExisted(to.id());
        T forUpdate = getMapper().fromTo(to);
        getRepository().save(forUpdate);
    }
}
