package ru.soft.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.common.api.ApiController;
import ru.soft.common.data.HasId;
import ru.soft.service.BaseApiService;

import java.util.List;
import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkNew;
import static ru.soft.web.utils.ValidationUtil.checkNotNew;

@Slf4j
abstract class AbstractApiController<TO extends HasId> implements ApiController<TO> {

    @Autowired
    protected BaseApiService<TO> service;

    @Override
    public TO get(UUID id) {
        log.info("get by id={}", id);
        return this.service.get(id);
    }

    @Override
    public List<TO> getAll() {
        log.info("get all");
        return this.service.getAll();
    }

    @Override
    public Page<TO> getPage(Pageable pageable) {
        log.info("get page number:{}, size:{} ", pageable.getPageNumber(), pageable.getPageSize());
        return this.service.getPage(pageable);
    }

    @Override
    public long count() {
        log.info("count");
        return this.service.count();
    }

    @Override
    public void delete(UUID id) {
        log.info("delete {}", id);
        this.service.delete(id);
    }

    @Override
    public TO add(TO to) {
        log.info("add {}", to);
        checkNew(to);
        return this.service.add(to);
    }

    @Valid
    @Override
    @Transactional
    public void update(TO to) {
        log.info("update by {}", to);
        checkNotNew(to);
        this.service.update(to);
    }
}
