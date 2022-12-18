package ru.soft.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.common.api.ApiController;
import ru.soft.data.BaseEntity;
import ru.soft.common.data.HasId;
import ru.soft.data.repository.BaseRepository;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

import static ru.soft.utils.ValidationUtil.checkNew;
import static ru.soft.utils.ValidationUtil.checkNotNew;

@Slf4j
abstract class AbstractApiController<T extends BaseEntity, TO extends HasId> implements ApiController<TO> {

    protected abstract BaseRepository<T> getRepository();

    protected abstract TOMapper<T, TO> getMapper();

    @Override
    public TO get(UUID id) {
        log.info("get by id={}", id);
        T existed = getRepository().getExisted(id);
        return getMapper().toTo(existed);
    }

    @Override
    public List<TO> getAll() {
        log.info("get all");
        List<T> all = getRepository().findAll();
        return all.stream()
                .map(t -> getMapper().toTo(t))
                .toList();
    }

    @Override
    public Page<TO> getPage(Pageable pageable) {
        log.info("get page number:{}, size:{} ", pageable.getPageNumber(), pageable.getPageSize());
        Page<T> page = getRepository().findAll(pageable);
        List<TO> pageTo = page.get()
                .map(t -> getMapper().toTo(t))
                .toList();
        return new PageImpl<>(pageTo, pageable, pageTo.size());
    }

    @Override
    public long count() {
        log.info("count");
        return getRepository().count();
    }

    @Override
    public void delete(UUID id) {
        log.info("delete {}", id);
        getRepository().deleteExisted(id);
    }

    @Override
    public TO add(TO to) {
        log.info("add {}", to);
        checkNew(to);
        T forCreate = getMapper().fromTo(to);
        T created = getRepository().save(forCreate);
        return getMapper().toTo(created);
    }

    @Override
    @Transactional
    public void update(TO to) {
        log.info("update by {}", to);
        checkNotNew(to);
        getRepository().getExisted(to.id());
        T forUpdate = getMapper().fromTo(to);
        getRepository().save(forUpdate);
    }
}
