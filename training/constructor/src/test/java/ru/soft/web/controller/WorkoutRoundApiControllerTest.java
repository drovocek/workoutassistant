package ru.soft.web.controller;

import ru.soft.data.model.WorkoutRound;
import ru.soft.utils.JsonTestUtils;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.to.WorkoutRoundTo;

import java.util.List;
import java.util.UUID;

class WorkoutRoundApiControllerTest extends BaseApiControllerTest<WorkoutRound, WorkoutRoundTo> {

    private static final MatcherFactory.Matcher<WorkoutRoundTo> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(WorkoutRoundTo.class);
    private static final UUID EXITED_ID = UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Strength";

    @Override
    protected String getApiUrl() {
        return WorkoutRoundApiController.REST_URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutRoundTo> matcher() {
        return MATCHER;
    }

    @Override
    protected UUID expectedId() {
        return EXITED_ID;
    }

    @Override
    protected WorkoutRoundTo requestEntity(boolean isNew) {
        return new WorkoutRoundTo(
                isNew ? null : EXITED_ID,
                JsonTestUtils.createWorkoutRoundSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    protected List<WorkoutRoundTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutRoundTo(
                        isNew ? null : EXITED_ID,
                        JsonTestUtils.createWorkoutRoundSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutRoundTo(
                        isNew ? EXITED_ID : null,
                        JsonTestUtils.createWorkoutRoundSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutRoundTo(
                        isNew ? null : EXITED_ID,
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutRoundTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutRoundTo(
                        isNew ? null : EXITED_ID,
                        JsonTestUtils.createWorkoutRoundSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutRoundTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}