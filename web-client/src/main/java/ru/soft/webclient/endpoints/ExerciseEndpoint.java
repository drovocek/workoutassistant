package ru.soft.webclient.endpoints;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.soft.common.to.ExerciseTo;
import ru.soft.webclient.resources.ExerciseClient;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Endpoint
@AnonymousAllowed
public class ExerciseEndpoint {

    private final ExerciseClient client;

    @Nonnull
    public Page<@Nonnull ExerciseTo> list(Pageable pageable) {
        log.info("get page number:{}, size:{} ", pageable.getPageNumber(), pageable.getPageSize());
        return this.client.getPage(pageable);
    }

    public Optional<ExerciseTo> get(@Nonnull UUID id) {
        log.info("get by id={}", id);
        ExerciseTo to = this.client.get(id);
        return Optional.of(to);
    }

    @Nonnull
    public ExerciseTo update(@Nonnull ExerciseTo to) {
        log.info("update by {}", to);
        if (to.isNew()) {
            return this.client.add(to);
        } else {
            this.client.update(to);
            return to;
        }
    }

    public void delete(@Nonnull UUID id) {
        log.info("delete {}", id);
        this.client.delete(id);
    }

    public long count() {
        log.info("count");
        return this.client.count();
    }

//    List<ExerciseTo> exerciseTos = new ArrayList<>();
//
//    {
//        exerciseTos.add(new ExerciseTo(UUID.randomUUID(), "1 title", "1 description", 1));
//        exerciseTos.add(new ExerciseTo(UUID.randomUUID(), "2 title", "2 description", 2));
//        exerciseTos.add(new ExerciseTo(UUID.randomUUID(), "3 title", "3 description", 3));
//    }
//
//    @Nonnull
//    public Page<@Nonnull ExerciseTo> list(Pageable page) {
//
//        return new PageImpl<>(exerciseTos, page, 3);
//
//    }
//
//    public Optional<ExerciseTo> get(@Nonnull UUID id) {
//        return exerciseTos.stream().filter(d -> d.id().equals(id)).findFirst();
//    }
//
//    @Nonnull
//    public ExerciseTo update(@Nonnull ExerciseTo entity) {
//        if(entity.id()==null){
//            ExerciseTo exerciseTo = new ExerciseTo(UUID.randomUUID(), entity.title(), entity.description(), entity.complexity());
//            exerciseTos.add(exerciseTo);
//            return exerciseTo;
//        }
//        exerciseTos.replaceAll(exerciseTo -> {
//            if (exerciseTo.id().equals(entity.id())) {
//                return entity;
//            }
//            return exerciseTo;
//        });
//        return entity;
//    }
//
//    public void delete(@Nonnull UUID id) {
//        exerciseTos.removeIf(e -> e.id().equals(id));
//    }
//
//    public int count() {
//        return exerciseTos.size();
//    }
}
