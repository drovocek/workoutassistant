package ru.soft.data.load;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
final class LoadProfileSimple implements LoadProfile {

    private final int order;
    private final int count;
    private final CountLoadUnit countLoadUnit;
    private final double volume;
    private final VolumeLoadUnit volumeLoadUnit;
    private final int rest;
    private final RestUnit restUnit;
    private final UnitGroup unitGroup;

    public LoadProfileSimple(int order,
                             int count, CountLoadUnit countLoadUnit,
                             double volume, VolumeLoadUnit volumeLoadUnit) {
        this(order, count, countLoadUnit, volume, volumeLoadUnit, 0, RestUnit.SEC);
    }

    public LoadProfileSimple(int order,
                             int count, CountLoadUnit countLoadUnit,
                             double volume, VolumeLoadUnit volumeLoadUnit,
                             int rest, RestUnit restUnit) {
        this.order = order;
        this.count = count;
        this.countLoadUnit = countLoadUnit;
        this.volume = volume;
        this.volumeLoadUnit = volumeLoadUnit;
        this.rest = rest;
        this.restUnit = restUnit;
        UnitGroup countUnitGroup = this.countLoadUnit.getUnitGroup();
        UnitGroup volumeUnitGroup = this.volumeLoadUnit.getUnitGroup();
        if (!countUnitGroup.equals(volumeUnitGroup)) {
            throw new ConsistencyViolationException(
                    "units of measure must have the same group, now count - %s and volume - %s".formatted(countUnitGroup, volumeUnitGroup)
            );
        }
        this.unitGroup = countUnitGroup;
    }
}
