package ru.soft.data.load;

import lombok.Getter;
import ru.soft.data.model_v_3.Exercise;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//    private final int count;             // 1ч      шт       30сек
//    private final int intensity;         // 10км/ч   кг/шт   раз/сек
//    private final int volume;            // 10км     кг      раз

@Getter
final class StationSimple implements Station {

    private final int order;
    private final Exercise exercise;
    private final List<LoadProfile> workingLoadProfiles;
    private final LoadProfile targetLoadProfile;

    public StationSimple(int order, Exercise exercise, List<LoadProfile> workingLoadProfiles, LoadProfile targetLoadProfile) {
        this.order = order;
        this.exercise = exercise;
        this.targetLoadProfile = targetLoadProfile;
        if (workingLoadProfiles.isEmpty()) {
            throw new ConsistencyViolationException("working load profiles can't be empty");
        }
        this.workingLoadProfiles = workingLoadProfiles.stream()
                .sorted(Comparator.comparingInt(LoadProfile::getOrder))
                .collect(Collectors.toList());

        Set<UnitGroup> unitGroups = new LinkedHashSet<>();
        UnitGroup exerciseUnitGroup = exercise.getTrainingType().getUnitGroup();
        unitGroups.add(exerciseUnitGroup);
        workingLoadProfiles.forEach(profile -> unitGroups.add(profile.getUnitGroup()));
        if (targetLoadProfile != null) {
            UnitGroup profileUnitGroup = targetLoadProfile.getUnitGroup();
            unitGroups.add(profileUnitGroup);
        }
        if (unitGroups.size() > 1) {
            throw new ConsistencyViolationException(
                    "units of measure must have the same group, but now is %s (first val - exercise)".formatted(unitGroups)
            );
        }
    }
}






















