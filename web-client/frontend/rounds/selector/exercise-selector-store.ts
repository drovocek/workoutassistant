import {appStore} from 'Frontend/common/stores/app-store';
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {makeAutoObservable} from "mobx";

export class ExerciseSelectorStore {

    filterText = '';
    public draggedExercise?: ExerciseTo;

    constructor() {
        makeAutoObservable(
            this,
            {
            },
            {autoBind: true}
        );
    }

    setDraggedExercise(draggedExercise?: ExerciseTo) {
        this.draggedExercise = draggedExercise;
    }

    get filtered() {
        console.log("!!!!")
        const filter = new RegExp(this.filterText, 'i');
        return appStore.exerciseStore.data.filter((entity) =>
            filter.test(`${entity.title}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }
}