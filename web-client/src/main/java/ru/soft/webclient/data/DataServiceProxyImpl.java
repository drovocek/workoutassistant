package ru.soft.webclient.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.soft.common.to.ExerciseTo;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DataServiceProxyImpl implements DataServiceProxy{

//    private final UserFeignClient userFeignClient;
//
//    @Value("${trainingConstructor.url}")
//    private String url;
    @Override
    public Page<ExerciseTo> getPage(Pageable page) {
        return null;
    }

    @Override
    public Optional<ExerciseTo> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public ExerciseTo add(ExerciseTo entity) {
        return null;
    }

    @Override
    public void update(ExerciseTo entity) {

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public int count() {
        return 0;
    }
}
