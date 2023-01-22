package ru.soft.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppApi {

    @UtilityClass
    public static class Workouts {
        public static final String URL = "/api/workouts";
    }

    @UtilityClass
    public static class Rounds {
        public static final String URL = "/api/rounds";
    }

    @UtilityClass
    public static class Exercises {
        public static final String URL = "/api/exercises";
    }
}
