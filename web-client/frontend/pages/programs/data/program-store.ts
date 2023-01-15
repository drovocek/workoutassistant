import {makeAutoObservable, observable} from 'mobx';
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

export class ProgramStore {

    formVisible: boolean = false;
    ///workoutSnapshots: WorkoutSnapshot[] = [];  !!!!
    workouts: Workout[] = [];

    constructor() {
        makeAutoObservable(
            this,
            {
                formVisible: observable.ref,
                workouts: observable.shallow,
            },
            {autoBind: true}
        );
    }
}