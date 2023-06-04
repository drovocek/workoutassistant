package ru.soft.data.load.elements;

import ru.soft.common.data.HasOrder;
import ru.soft.data.load.ConsistencyViolationException;
import ru.soft.data.load.LoadMeasure;
import ru.soft.data.load.LoadType;
import ru.soft.data.model_v_3.Exercise;

import java.util.List;

public interface Station extends HasOrder {

    Exercise getExercise();

    LoadMeasure getCountLoadMeasure();

    LoadMeasure getVolumeLoadMeasure();

    List<LoadParams> getLoadSchema();

    LoadParams getTargetLoad();

    List<Rest> getRestSchema();

    default void checkConsistency() {
        LoadType loadType = getExercise().getLoadType();
        loadType.throwIfNotContainsCountLoadMeasures(getCountLoadMeasure());
        loadType.throwIfNotContainsVolumeLoadMeasures(getVolumeLoadMeasure());
        if (getLoadSchema() == null) {
            throw new ConsistencyViolationException("working load schema can't be null");
        }
        if (getLoadSchema().isEmpty()) {
            throw new ConsistencyViolationException("working load schema can't be empty");
        }
    }
}
