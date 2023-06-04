package ru.soft.data.load.elements;

import ru.soft.common.data.HasOrder;

public interface Rest extends HasOrder {

    int getCount();

    RestUnit getRestUnit();
}
