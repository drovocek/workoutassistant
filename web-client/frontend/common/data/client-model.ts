import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import WorkoutRoundSchemaSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutRoundSchemaSnapshot";
import ExerciseSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshot";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";


export class WorkoutRound {

    id?: string;
    roundSchema: WorkoutRoundSchemaSnapshot;
    title?: string;
    description?: string;
    // avgComplexity: number;
    // fullDuration: number;
    // fullWeight: number;

    constructor(to: WorkoutRoundTo) {
        this.id = to.id;
        this.roundSchema = to.roundSchema;
        this.title = to.title;
        this.description = to.description;
    }

    private calculateAvgComplexity(stations: WorkoutStationSnapshot[]) {
        return stations.map(station => {
            let complexity = station.exercise.complexity;
            let repetitions = station.repetitions;
            let weight = station.weight;
            let rest = station.rest;
        })
    }
}