package ru.soft.common.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.soft.common.data.HasId;

import java.util.List;
import java.util.UUID;

public interface ApiController<TO extends HasId> {

    @GetMapping("/{id}")
    TO get(@PathVariable @NotNull UUID id);

    @GetMapping("/all")
    List<TO> getAll();

    @GetMapping
    Page<TO> getPage(Pageable pageable);

    @GetMapping("/count")
    long count();

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable @NotNull UUID id);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    TO add(@RequestBody @Valid TO to);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody @Valid TO to);
}
