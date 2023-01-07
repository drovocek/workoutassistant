package ru.soft.common.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.soft.common.data.HasId;
import ru.soft.common.security.JwtUser;

import java.util.List;
import java.util.UUID;

public interface ResourceApiController<TO extends HasId> {

    @GetMapping("/{id}")
    TO get(@PathVariable @NotNull UUID id, JwtUser user);

    @GetMapping("/all")
    List<TO> getAll(JwtUser user);

    @GetMapping
    Page<TO> getPage(Pageable pageable, JwtUser user);

    @GetMapping("/count")
    long count(JwtUser user);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable @NotNull UUID id, JwtUser user);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    TO add(@RequestBody @Valid TO to, JwtUser user);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody @Valid TO to, JwtUser user);
}
