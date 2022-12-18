package ru.soft.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppApi {

    @UtilityClass
    public static class Exercise {
        public static final String URL = "/api/exercises";
    }

    @UtilityClass
    public static class WorkoutPlan {
        public static final String URL = "/api/workouts/plans";
    }

    @UtilityClass
    public static class WorkoutResult {
        public static final String URL = "/api/workouts/results";
    }

    @UtilityClass
    public static class WorkoutRound {
        public static final String URL = "/api/rounds";
    }

    @UtilityClass
    public static class WorkoutSession {
        public static final String URL = "/api/sessions";
    }
}
