package ru.soft.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.*;
import ru.soft.data.repository.BaseRepository;
import ru.soft.data.test.WorkoutResultTestDataStore;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Profile("!production")
@Configuration
public class TestDataConfig {

    @Autowired
    BaseRepository<Exercise> exerciseRepository;
    @Autowired
    TestDataStore<Exercise> exerciseTestDataStore;

    @Autowired
    BaseRepository<WorkoutRound> workoutRoundRepository;
    @Autowired
    TestDataStore<WorkoutRound> workoutRoundTestDataStore;

    @Autowired
    BaseRepository<WorkoutPlan> workoutPlanRepository;
    @Autowired
    TestDataStore<WorkoutPlan> workoutPlanTestDataStore;

    @Autowired
    BaseRepository<WorkoutSession> workoutSessionRepository;
    @Autowired
    TestDataStore<WorkoutSession> workoutSessionTestDataStore;

    @Autowired
    BaseRepository<WorkoutResult> workoutResultRepository;
    @Autowired
    WorkoutResultTestDataStore workoutResultTestDataStore;

    @Bean
    public CommandLineRunner testDataRunner() {
        return args -> {
            List<Exercise> exercises = exerciseTestDataStore.entities(true);
            this.exerciseRepository.saveAll(exercises);

            List<WorkoutRound> workoutRounds = workoutRoundTestDataStore.entities(true);
            this.workoutRoundRepository.saveAll(workoutRounds);

            List<WorkoutPlan> workoutPlans = workoutPlanTestDataStore.entities(true);
            this.workoutPlanRepository.saveAll(workoutPlans);

            List<WorkoutSession> workoutSessions = workoutSessionTestDataStore.entities(true);
            Iterable<WorkoutSession> workoutSessionsWithIds =
                    this.workoutSessionRepository.saveAll(workoutSessions);
            UUID[] sessionIds = StreamSupport.stream(workoutSessionsWithIds.spliterator(), false)
                    .map(WorkoutSession::id)
                    .toArray(UUID[]::new);

            List<WorkoutResult> workoutResults = workoutResultTestDataStore.entities(true, sessionIds);
            this.workoutResultRepository.saveAll(workoutResults);
        };
    }
}
