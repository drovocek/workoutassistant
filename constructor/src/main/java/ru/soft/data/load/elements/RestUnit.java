package ru.soft.data.load.elements;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestUnit {

    SEC("sec."),
    MIN("min."),
    HOUR("hour");

    private final String name;
}
