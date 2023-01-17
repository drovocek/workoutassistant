package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DurationUnit {

    SEC, MIN, HOUR, DAY
}
