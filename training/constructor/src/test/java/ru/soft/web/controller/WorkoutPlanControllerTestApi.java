package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.utils.MatcherFactory;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.WorkoutPlanTestDataStore.workoutSchemaSnapshot;

class WorkoutPlanControllerTestApi extends AbstractApiControllerTest<WorkoutPlan, WorkoutPlanTo> {

    private static final MatcherFactory.Matcher<WorkoutPlanTo> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(WorkoutPlanTo.class);
    private static final UUID EXITED_ID = UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squats training";

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutPlan.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutPlanTo> matcher() {
        return MATCHER;
    }

    @Override
    protected UUID expectedId() {
        return EXITED_ID;
    }

    @Override
    protected WorkoutPlanTo requestEntity(boolean isNew) {
        return new WorkoutPlanTo(
                isNew ? null : EXITED_ID,
                workoutSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    protected List<WorkoutPlanTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutPlanTo(
                        isNew ? null : EXITED_ID,
                        workoutSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutPlanTo(
                        isNew ? EXITED_ID : null,
                        workoutSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutPlanTo(
                        isNew ? null : EXITED_ID,
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutPlanTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutPlanTo(
                        isNew ? null : EXITED_ID,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    protected List<WorkoutPlanTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}