package ru.soft.data.load;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadMeasure {

    REP("rep."), //C //V

    SEC("sec."), //C
    MIN("min."), //C
    HOUR("hour"), //C

    M("m."), //V
    KM("km."), //V

    KG("kg."), //V
    GR("gr."); //V

    private final String name;
}
