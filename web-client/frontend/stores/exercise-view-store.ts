import {modelStore} from 'Frontend/stores/app-store';
import {makeAutoObservable, observable} from 'mobx';
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";

class ExerciseViewStore {
    filterText = '';
    selectedExercise: ExerciseTo | null = null;

    constructor() {
        makeAutoObservable(
            this,
            {selectedExercise: observable.ref},
            {autoBind: true}
        );
    }

    get filteredExercises() {
        const filter = new RegExp(this.filterText, 'i');
        const contacts = modelStore.exercices;
        return contacts.filter((exercise) =>
            filter.test(`${exercise.title} ${exercise.description}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }

    editNew() {
        this.selectedExercise = ExerciseToModel.createEmptyValue();
    }

    cancelEdit() {
        this.selectedExercise = null;
    }

    setSelectedExercise(exercise: ExerciseTo) {
        this.selectedExercise = exercise;
    }

    async save(exercise: ExerciseTo) {
        await modelStore.save(exercise);
        this.cancelEdit();
    }

    async delete() {
        if (this.selectedExercise) {
            await modelStore.delete(this.selectedExercise);
            this.cancelEdit();
        }
    }
}

export const exerciseViewStore = new ExerciseViewStore();