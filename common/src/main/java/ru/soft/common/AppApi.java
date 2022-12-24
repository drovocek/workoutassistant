package ru.soft.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppApi {

    @UtilityClass
    public static class Exercises {
        public static final String URL = "/api/exercises";
    }

    @UtilityClass
    public static class WorkoutPlans {
        public static final String URL = "/api/workouts/plans";
    }

    @UtilityClass
    public static class WorkoutResults {
        public static final String URL = "/api/workouts/results";
    }

    @UtilityClass
    public static class WorkoutRounds {
        public static final String URL = "/api/rounds";
    }

    @UtilityClass
    public static class WorkoutSessions {
        public static final String URL = "/api/sessions";
    }
}
