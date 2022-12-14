package ru.soft.web.controller;

import ru.soft.data.model.WorkoutSession;
import ru.soft.utils.JsonTestUtils;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.to.WorkoutSessionTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class WorkoutSessionApiControllerTest extends BaseApiControllerTest<WorkoutSession, WorkoutSessionTo> {

    private static final MatcherFactory.Matcher<WorkoutSessionTo> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(WorkoutSessionTo.class);
    private static final UUID EXITED_ID = UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002");
    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    @Override
    protected String getApiUrl() {
        return WorkoutSessionApiController.REST_URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutSessionTo> matcher() {
        return MATCHER;
    }

    @Override
    protected UUID expectedId() {
        return EXITED_ID;
    }

    @Override
    protected WorkoutSessionTo requestEntity(boolean isNew) {
        return new WorkoutSessionTo(
                isNew ? null : EXITED_ID,
                JsonTestUtils.createWorkoutPlanSnapshot(),
                SESSION_DATE_TIME,
                "request note"
        );
    }

    @Override
    protected List<WorkoutSessionTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutSessionTo(
                        isNew ? null : EXITED_ID,
                        JsonTestUtils.createWorkoutPlanSnapshot(),
                        null,
                        "request note"
                ),
                new WorkoutSessionTo(
                        isNew ? null : EXITED_ID,
                        null,
                        SESSION_DATE_TIME,
                        "request note"
                )
        );
    }

    @Override
    protected List<WorkoutSessionTo> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    protected List<WorkoutSessionTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}