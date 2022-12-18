package ru.soft.web.controller;

import ru.soft.data.model.WorkoutResult;
import ru.soft.utils.JsonTestUtils;
import ru.soft.utils.MatcherFactory;
import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutResultTo;

import java.util.List;
import java.util.UUID;

class WorkoutResultControllerTestApi extends AbstractApiControllerTest<WorkoutResult, WorkoutResultTo> {

    private static final MatcherFactory.Matcher<WorkoutResultTo> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(WorkoutResultTo.class);
    private static final UUID EXITED_ID = UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002");
    private static final UUID EXITED_SESSION_ID = UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squats training";

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutResult.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutResultTo> matcher() {
        return MATCHER;
    }

    @Override
    protected UUID expectedId() {
        return EXITED_ID;
    }

    @Override
    protected int rowsCount() {
        return 2;
    }

    @Override
    protected WorkoutResultTo requestEntity(boolean isNew) {
        return new WorkoutResultTo(
                isNew ? null : EXITED_ID,
                EXITED_SESSION_ID,
                JsonTestUtils.createWorkoutSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    protected List<WorkoutResultTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : EXITED_ID,
                        EXITED_SESSION_ID,
                        JsonTestUtils.createWorkoutSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutResultTo(
                        isNew ? EXITED_ID : null,
                        EXITED_SESSION_ID,
                        JsonTestUtils.createWorkoutSchemaSnapshot(),
                        null,
                        "request description"
                ),
                new WorkoutResultTo(
                        isNew ? EXITED_ID : null,
                        null,
                        JsonTestUtils.createWorkoutSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutResultTo(
                        isNew ? null : EXITED_ID,
                        EXITED_SESSION_ID,
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutResultTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : EXITED_ID,
                        EXITED_SESSION_ID,
                        JsonTestUtils.createWorkoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutResultTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}