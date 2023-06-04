package ru.soft.data.load;

import ru.soft.data.model_v_3.Exercise;

import java.util.List;

public interface Station {

    int getOrder();

    Exercise getExercise();

    LoadProfile getTargetLoadProfile();

    List<LoadProfile> getWorkingLoadProfiles();
}
