package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DurationUnit {

    SEC("SEC"), MIN("MIN");

    @Getter
    private final String label;
}
