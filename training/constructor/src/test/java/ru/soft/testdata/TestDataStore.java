package ru.soft.testdata;

import java.util.List;

public interface TestDataStore<T,TO> {

    List<T> entities();

    List<TO> tos();
}
