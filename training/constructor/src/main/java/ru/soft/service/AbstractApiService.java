package ru.soft.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.soft.common.data.HasId;
import ru.soft.data.BaseEntity;
import ru.soft.data.repository.BaseRepository;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkNew;
import static ru.soft.web.utils.ValidationUtil.checkNotNew;

@Slf4j
abstract class AbstractApiService<T extends BaseEntity, TO extends HasId> implements BaseApiService<TO> {

    @Autowired
    protected BaseRepository<T> repository;
    @Autowired
    protected TOMapper<T, TO> mapper;

    @Override
    public TO get(UUID id) {
        log.info("get by id={}", id);
        T existed = this.repository.getExisted(id);
        return this.mapper.toTo(existed);
    }

    @Override
    public List<TO> getAll() {
        log.info("get all");
        List<T> all = this.repository.findAll();
        return all.stream()
                .map(t -> this.mapper.toTo(t))
                .toList();
    }

    @Override
    public Page<TO> getPage(Pageable pageable) {
        log.info("get page number:{}, size:{} ", pageable.getPageNumber(), pageable.getPageSize());
        Page<T> page = this.repository.findAll(pageable);
        List<TO> pageTo = page.get()
                .map(t -> this.mapper.toTo(t))
                .toList();
        return new PageImpl<>(pageTo, pageable, pageTo.size());
    }

    @Override
    public long count() {
        log.info("count");
        return this.repository.count();
    }

    @Override
    public void delete(UUID id) {
        log.info("delete {}", id);
        this.repository.deleteExisted(id);
    }

    @Override
    public TO add(TO to) {
        log.info("add {}", to);
        checkNew(to);
        T forCreate = this.mapper.fromTo(to);
        T created = this.repository.save(forCreate);
        return this.mapper.toTo(created);
    }

    @Override
    public void update(TO to) {
        log.info("update by {}", to);
        checkNotNew(to);
        this.repository.getExisted(to.id());
        T forUpdate = this.mapper.fromTo(to);
        this.repository.save(forUpdate);
    }
}
