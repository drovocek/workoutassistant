package ru.soft.data.load;

import ru.soft.common.data.HasOrder;
import ru.soft.data.model_v_3.Exercise;

import java.util.List;

public interface Station extends HasOrder {

    Exercise getExercise();

    LoadProfile getTargetLoadProfile();

    List<LoadProfile> getWorkingLoadProfiles();
}
