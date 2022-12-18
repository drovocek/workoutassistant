package ru.soft.web.controller;

import ru.soft.data.model.Exercise;
import ru.soft.utils.MatcherFactory;
import ru.soft.common.AppApi;
import ru.soft.common.to.ExerciseTo;

import java.util.List;
import java.util.UUID;

class ExerciseApiControllerTest extends AbstractApiControllerTest<Exercise, ExerciseTo> {

    private static final MatcherFactory.Matcher<ExerciseTo> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(ExerciseTo.class);
    private static final UUID EXITED_ID = UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squat title";

    @Override
    protected String getApiUrl() {
        return AppApi.Exercise.URL;
    }

    @Override
    protected MatcherFactory.Matcher<ExerciseTo> matcher() {
        return MATCHER;
    }

    @Override
    protected UUID expectedId() {
        return EXITED_ID;
    }

    @Override
    protected ExerciseTo requestEntity(boolean isNew) {
        return new ExerciseTo(
                isNew ? null : EXITED_ID,
                "request title",
                "request description",
                10
        );
    }

    @Override
    protected List<ExerciseTo> invalids(boolean isNew) {
        return List.of(
                new ExerciseTo(
                        isNew ? null : EXITED_ID,
                        "",
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : EXITED_ID,
                        "request title",
                        "request description",
                        100
                ),
                new ExerciseTo(
                        isNew ? EXITED_ID : null,
                        "request title",
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : EXITED_ID,
                        null,
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : EXITED_ID,
                        "request title",
                        "request description",
                        -1
                )
        );
    }

    @Override
    protected List<ExerciseTo> duplicates(boolean isNew) {
        return List.of(
                new ExerciseTo(
                        isNew ? null : EXITED_ID,
                        DUPLICATE_TITLE,
                        "request description",
                        10
                )
        );
    }

    @Override
    protected List<ExerciseTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}