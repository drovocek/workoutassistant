package ru.soft.data.load;

import static ru.soft.data.load.UnitGroup.*;

public enum TrainingType implements HasUnitGroup {

    WEIGHT_LIFT {
        @Override
        public UnitGroup getUnitGroup() {
            return W_L_GROUP;
        }
    },
    RUNNING {
        @Override
        public UnitGroup getUnitGroup() {
            return R_GROUP;
        }
    },
    CIRCUIT_TRAINING {
        @Override
        public UnitGroup getUnitGroup() {
            return C_T_GROUP;
        }
    };
}

