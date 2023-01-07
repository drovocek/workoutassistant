package ru.soft.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.common.api.ResourceApiController;
import ru.soft.common.data.HasId;
import ru.soft.common.security.JwtUser;
import ru.soft.service.BaseApiService;

import java.util.List;
import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkNew;
import static ru.soft.web.utils.ValidationUtil.checkNotNew;

@Slf4j
abstract class AbstractApiController<TO extends HasId> implements ResourceApiController<TO> {

    @Autowired
    protected BaseApiService<TO> service;

    @Override
    public TO get(UUID id, JwtUser user) {
        log.info("get by id={} for user={}", id, user);
        return this.service.get(id);
    }

    @Override
    public List<TO> getAll(JwtUser user) {
        log.info("get all for user={}", user);
        return this.service.getAll();
    }

    @Override
    public Page<TO> getPage(Pageable pageable, JwtUser user) {
        log.info("get page number={}, size={} for user={}",
                pageable.getPageNumber(), pageable.getPageSize(), user);
        return this.service.getPage(pageable);
    }

    @Override
    public long count(JwtUser user) {
        log.info("count for user={}", user);
        return this.service.count();
    }

    @Override
    public void delete(UUID id, JwtUser user) {
        log.info("delete {} for user={}", id, user);
        this.service.delete(id);
    }

    @Override
    public TO add(TO to, JwtUser user) {
        log.info("add {} for user={}", to, user);
        checkNew(to);
        return this.service.add(to);
    }

    @Valid
    @Override
    @Transactional
    public void update(TO to, JwtUser user) {
        log.info("update by {} for user={}", to, user);
        checkNotNew(to);
        this.service.update(to);
    }
}
