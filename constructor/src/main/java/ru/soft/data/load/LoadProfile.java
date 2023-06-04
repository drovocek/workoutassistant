package ru.soft.data.load;

public interface LoadProfile {

    int getOrder();

    int getCount();

    CountLoadUnit getCountLoadUnit();

    double getVolume();

    VolumeLoadUnit getVolumeLoadUnit();

    int getRest();

    RestUnit getRestUnit();

    UnitGroup getUnitGroup();

    default double getIntensity() {
        return getVolume() / getCount();
    }
}
