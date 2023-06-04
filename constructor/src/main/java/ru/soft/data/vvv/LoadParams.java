package ru.soft.data.vvv;

import ru.soft.data.load.elements.Rest;
import ru.soft.data.model_v_3.Exercise;

import java.util.List;

public interface LoadParams {

    int getCount();

    double getVolume();

    Rest getRest();
}

interface Station {

    int getOrder();

    Exercise getExercise();

    LoadParams getTargetLoadParams();

    List<LoadParams> loadParams();

    String getCountLoadMeasure();

    String getVolumeLoadMeasure();
}

interface Round {

    int getOrder();

    List<Station> getStations();
}