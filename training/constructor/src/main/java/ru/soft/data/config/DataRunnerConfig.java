package ru.soft.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Exercise;
import ru.soft.data.model.Round;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.data.repository.RoundRepository;
import ru.soft.data.repository.TrainingSessionRepository;
import ru.soft.data.repository.WorkoutPlanRepository;

import java.util.List;

@Profile("!production")
@Configuration
public class DataRunnerConfig {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    TestDataStore<Exercise> exerciseTestDataStore;

    @Autowired
    RoundRepository workoutRoundRepository;
    @Autowired
    TestDataStore<Round> workoutRoundTestDataStore;

    @Autowired
    WorkoutPlanRepository workoutPlanRepository;
    @Autowired
    TestDataStore<WorkoutPlan> workoutPlanTestDataStore;

    @Autowired
    TrainingSessionRepository workoutSessionRepository;
    @Autowired
    TestDataStore<TrainingSession> workoutSessionTestDataStore;

    @Bean
    public CommandLineRunner dataRunner() {
        return args -> {
            if (this.exerciseRepository.count() == 0) {
                List<Exercise> exercises = exerciseTestDataStore.entities(true);
                this.exerciseRepository.saveAll(exercises);

                List<Round> rounds = workoutRoundTestDataStore.entities(true);
                this.workoutRoundRepository.saveAll(rounds);

                List<WorkoutPlan> workoutPlans = workoutPlanTestDataStore.entities(true);
                this.workoutPlanRepository.saveAll(workoutPlans);

                List<TrainingSession> trainingSessions = workoutSessionTestDataStore.entities(true);
                this.workoutSessionRepository.saveAll(trainingSessions);
            }
        };
    }
}
