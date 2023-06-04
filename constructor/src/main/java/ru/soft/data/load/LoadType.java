package ru.soft.data.load;

import java.util.Set;

import static java.util.Set.of;
import static ru.soft.data.load.LoadMeasure.*;

public enum LoadType {

    WEIGHT_LIFT {
        @Override
        public Set<LoadMeasure> getCountLoadMeasures() {
            return of(REP);
        }

        @Override
        public Set<LoadMeasure> getVolumeLoadMeasures() {
            return of(KG, GR);
        }
    },
    CYCLIC {
        @Override
        public Set<LoadMeasure> getCountLoadMeasures() {
            return of(SEC, MIN, HOUR);
        }

        @Override
        public Set<LoadMeasure> getVolumeLoadMeasures() {
            return of(M, KM);
        }
    },
    CIRCUIT_TRAINING {
        @Override
        public Set<LoadMeasure> getCountLoadMeasures() {
            return of(SEC, MIN);
        }

        @Override
        public Set<LoadMeasure> getVolumeLoadMeasures() {
            return of(REP);
        }
    };

    public abstract Set<LoadMeasure> getCountLoadMeasures();

    public abstract Set<LoadMeasure> getVolumeLoadMeasures();

    public boolean containsCountLoadMeasures(LoadMeasure measure) {
        return getCountLoadMeasures().stream().anyMatch(m -> m.equals(measure));
    }

    public boolean containsVolumeLoadMeasures(LoadMeasure measure) {
        return getVolumeLoadMeasures().stream().anyMatch(m -> m.equals(measure));
    }

    public void throwIfNotContainsCountLoadMeasures(LoadMeasure measure) {
        if (!containsCountLoadMeasures(measure)) {
            throw new ConsistencyViolationException("%s does not contains 'count' load measure %s".formatted(this, measure));
        }
    }

    public void throwIfNotContainsVolumeLoadMeasures(LoadMeasure measure) {
        if (!containsVolumeLoadMeasures(measure)) {
            throw new ConsistencyViolationException("%s does not contains 'volume' load measure %s".formatted(this, measure));
        }
    }
}

