package ru.soft.data.repository.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.soft.data.repository.*;

@Configuration
public class DataConfig {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
    @Autowired
    private WorkoutResultRepository workoutResultRepository;
    @Autowired
    private WorkoutRoundRepository workoutRoundRepository;
    @Autowired
    private WorkoutSessionRepository workoutSessionRepository;
//
//    @Bean
//    ApplicationRunner initData(){
//        return args -> {
//            this.exerciseRepository.saveAll();
//            this.workoutPlanRepository.saveAll();
//            this.workoutResultRepository.saveAll();
//            this.workoutRoundRepository.saveAll();
//            this.workoutSessionRepository.saveAll();
//        };
//    }
}
