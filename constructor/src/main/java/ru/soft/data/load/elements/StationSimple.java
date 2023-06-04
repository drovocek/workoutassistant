package ru.soft.data.load.elements;

import lombok.Getter;
import ru.soft.data.load.LoadMeasure;
import ru.soft.data.model_v_3.Exercise;

import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

//    private final int count;             // 1ч      шт       30сек
//    private final int intensity;         // 10км/ч   кг/шт   раз/сек
//    private final int volume;            // 10км     кг      раз

@Getter
public final class StationSimple implements Station {

    private final Exercise exercise;
    private final LoadMeasure countLoadMeasure;
    private final LoadMeasure volumeLoadMeasure;
    private final List<LoadParams> loadSchema;
    private final LoadParams targetLoad;
    private final List<Rest> restSchema;
    private final int order;

    public StationSimple(Exercise exercise,
                         LoadMeasure countLoadMeasure, LoadMeasure volumeLoadMeasure,
                         List<LoadParams> loadSchema, LoadParams targetLoad,
                         List<Rest> restSchema, int order) {
        this.exercise = exercise;
        this.countLoadMeasure = countLoadMeasure;
        this.volumeLoadMeasure = volumeLoadMeasure;
        this.targetLoad = targetLoad;
        this.loadSchema = loadSchema.stream()
                .sorted(comparingInt(LoadParams::getOrder))
                .collect(toList());
        this.restSchema = restSchema.stream()
                .sorted(comparingInt(Rest::getOrder))
                .collect(toList());
        this.order = order;

        checkConsistency();
    }
}






















