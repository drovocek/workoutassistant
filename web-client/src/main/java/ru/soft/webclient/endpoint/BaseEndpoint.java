package ru.soft.webclient.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.EndpointExposed;
import dev.hilla.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.soft.common.api.ApiController;
import ru.soft.common.api.WebApi;
import ru.soft.common.data.HasId;

import java.util.List;
import java.util.UUID;

@Slf4j
@AnonymousAllowed
@EndpointExposed
public abstract class BaseEndpoint<TO extends HasId> implements WebApi<TO> {

    @Autowired
    protected ApiController<TO> controller;

    @Nonnull
    @Override
    public Page<@Nonnull TO> getPage(Pageable pageable) {
        log.info("get page number:{}, size:{} ", pageable.getPageNumber(), pageable.getPageSize());
        return this.controller.getPage(pageable);
    }

    @Nonnull
    @Override
    public List<@Nonnull TO> getAll() {
        log.info("get all");
        return this.controller.getAll();
    }

    @Override
    public TO get(@Nonnull UUID id) {
        log.info("get by id={}", id);
        return this.controller.get(id);
    }

    @Override
    public void update(@Nonnull TO to) {
        log.info("update by {}", to);
        this.controller.update(to);
    }

    @Nonnull
    @Override
    public TO add(@Nonnull TO to) {
        log.info("update by {}", to);
        return this.controller.add(to);
    }

    @Override
    public void delete(@Nonnull UUID id) {
        log.info("delete {}", id);
        this.controller.delete(id);
    }

    @Override
    public long count() {
        log.info("count");
        return this.controller.count();
    }
}
