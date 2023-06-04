package ru.soft.data.load;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

public enum UnitGroup {

    W_L_GROUP {
        @Getter
        @RequiredArgsConstructor
        enum W_L_CU implements CountLoadUnit {
            REP("rep.");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return W_L_GROUP;
            }
        }

        @Getter
        @RequiredArgsConstructor
        enum W_L_VU implements VolumeLoadUnit {
            KG("kg."),
            GR("gr.");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return W_L_GROUP;
            }
        }

        @Override
        public Set<CountLoadUnit> getCountUnits() {
            return Set.of(W_L_CU.REP);
        }

        @Override
        public Set<VolumeLoadUnit> getVolumeUnit() {
            return Set.of(W_L_VU.KG, W_L_VU.GR);
        }
    },
    R_GROUP {
        @Getter
        @RequiredArgsConstructor
        enum R_CU implements CountLoadUnit {
            MIN("min."),
            HOUR("hour");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return R_GROUP;
            }
        }

        @Getter
        @RequiredArgsConstructor
        enum R_VU implements VolumeLoadUnit {
            KM("km."),
            M("m.");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return R_GROUP;
            }
        }

        @Override
        public Set<CountLoadUnit> getCountUnits() {
            return Set.of(R_CU.MIN, R_CU.HOUR);
        }

        @Override
        public Set<VolumeLoadUnit> getVolumeUnit() {
            return Set.of(R_VU.KM, R_VU.M);
        }
    },
    C_T_GROUP {
        @Getter
        @RequiredArgsConstructor
        enum C_T_CU implements CountLoadUnit {
            SEC("sec"),
            MIN("min.");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return C_T_GROUP;
            }
        }

        @Getter
        @RequiredArgsConstructor
        enum C_T_VU implements VolumeLoadUnit {
            REP("rep.");

            private final String name;

            @Override
            public UnitGroup getUnitGroup() {
                return C_T_GROUP;
            }
        }

        @Override
        public Set<CountLoadUnit> getCountUnits() {
            return Set.of(C_T_CU.SEC, C_T_CU.MIN);
        }

        @Override
        public Set<VolumeLoadUnit> getVolumeUnit() {
            return Set.of(C_T_VU.REP);
        }
    };

    public abstract Set<CountLoadUnit> getCountUnits();

    public abstract Set<VolumeLoadUnit> getVolumeUnit();
}