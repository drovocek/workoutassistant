package ru.soft.web.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.soft.data.model.Exercise;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.web.to.ExerciseTo;

import java.util.UUID;

import static ru.soft.web.utils.ExerciseUtils.createNewFromTo;
import static ru.soft.web.utils.ExerciseUtils.toTo;
import static ru.soft.web.utils.ValidationUtil.checkNew;
import static ru.soft.web.utils.ValidationUtil.checkNotNew;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = ExerciseRestController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ExerciseRestController {

    static final String REST_URL = "/api/exercises";

    private final ExerciseRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseTo> get(@NotNull @PathVariable UUID id) {
        log.info("get by id={}", id);
        Exercise existed = this.repository.getExisted(id);
        return ResponseEntity.ok(toTo(existed));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@NotNull @PathVariable UUID id) {
        log.info("delete {}", id);
        this.repository.deleteExisted(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTo> add(@Valid @RequestBody ExerciseTo exerciseTo) {
        log.info("add {}", exerciseTo);
        checkNew(exerciseTo);
        Exercise exercise = createNewFromTo(exerciseTo);
        Exercise created = this.repository.save(exercise);
        return ResponseEntity.ok(toTo(created));
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Valid ExerciseTo exerciseTo) {
        log.info("update by {}", exerciseTo);
        checkNotNew(exerciseTo);
        this.repository.getExisted(exerciseTo.id());
        Exercise exercise = createNewFromTo(exerciseTo);
        this.repository.save(exercise);
    }
}
