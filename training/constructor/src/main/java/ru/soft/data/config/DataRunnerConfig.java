package ru.soft.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Exercise;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.model.Workout;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.data.repository.TrainingSessionRepository;
import ru.soft.data.repository.WorkoutRepository;

import java.util.List;

//@Profile("!production")
//@Configuration
public class DataRunnerConfig {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    TestDataStore<Exercise> exerciseTestDataStore;

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    TestDataStore<Workout> workoutPlanTestDataStore;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;
    @Autowired
    TestDataStore<TrainingSession> workoutSessionTestDataStore;

    @Bean
    public CommandLineRunner dataRunner() {
        return args -> {
            if (this.exerciseRepository.count() == 0) {
                List<Exercise> exercises = exerciseTestDataStore.entities(true);
                this.exerciseRepository.saveAll(exercises);

                List<Workout> workouts = workoutPlanTestDataStore.entities(true);
                this.workoutRepository.saveAll(workouts);

                List<TrainingSession> trainingSessions = workoutSessionTestDataStore.entities(true);
                this.trainingSessionRepository.saveAll(trainingSessions);
            }
        };
    }
}
