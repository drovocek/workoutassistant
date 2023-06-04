package ru.soft.data.load.elements;

import ru.soft.common.data.HasOrder;

public interface LoadParams extends HasOrder {

    int getCount();

    double getVolume();
}
