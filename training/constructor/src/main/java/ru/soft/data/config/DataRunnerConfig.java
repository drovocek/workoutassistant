package ru.soft.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Exercise;
import ru.soft.data.model.Round;
import ru.soft.data.model.Workout;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.data.repository.RoundRepository;
import ru.soft.data.repository.WorkoutRepository;

import java.util.List;

@Profile("!production")
@Configuration
public class DataRunnerConfig {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    TestDataStore<Exercise> exerciseTestDataStore;

    @Autowired
    RoundRepository roundRepository;
    @Autowired
    TestDataStore<Round> workoutPlanTestDataStore;

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    TestDataStore<Workout> workoutSessionTestDataStore;

    @Bean
    public CommandLineRunner dataRunner() {
        return args -> {
            if (this.exerciseRepository.count() == 0) {
                List<Exercise> exercises = exerciseTestDataStore.entities(true);
                this.exerciseRepository.saveAll(exercises);

                List<Round> rounds = workoutPlanTestDataStore.entities(true);
                this.roundRepository.saveAll(rounds);

                List<Workout> workouts = workoutSessionTestDataStore.entities(true);
                this.workoutRepository.saveAll(workouts);
            }
        };
    }
}
