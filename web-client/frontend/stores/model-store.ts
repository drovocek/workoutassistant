import {makeAutoObservable, observable, runInAction} from 'mobx';

import {ExerciseEndpoint} from 'Frontend/generated/endpoints';
import {uiStore} from "Frontend/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";

export class ModelStore {
    exercises: ExerciseTo[] = [];

    constructor() {
        makeAutoObservable(
            this,
            {
                initFromServer: false,
                exercises: observable.shallow,
            },
            {autoBind: true}
        );

        this.initFromServer();
    }

    async initFromServer() {
        const data = await ExerciseEndpoint.getAll();

        runInAction(() => {
            this.exercises = data;
        });
    }

    async save(exercise: ExerciseTo) {
        if (exercise.id) {
            await this.update(exercise).then(() => {
                this.saveLocal(exercise);
                uiStore.showSuccess('Exercise updated.');
            });
        } else {
            await this.add(exercise).then(newExercise => {
                this.saveLocal(newExercise);
                uiStore.showSuccess('Exercise created.');
            });
        }
    }

    private async update(exercise: ExerciseTo): Promise<void> {
        if (!exercise.id) return;
        return await ExerciseEndpoint.update(exercise);
    }

    private async add(exercise: ExerciseTo): Promise<ExerciseTo> {
        return await ExerciseEndpoint.add(exercise);
    }

    private saveLocal(saved: ExerciseTo) {
        const contactExists = this.exercises.some((c) => c.id === saved.id);
        if (contactExists) {
            this.exercises = this.exercises.map((existing) => {
                if (existing.id === saved.id) {
                    return saved;
                } else {
                    return existing;
                }
            });
        } else {
            this.exercises.push(saved);
        }
    }

    async delete(exercise: ExerciseTo) {
        if (!exercise.id) return;

        try {
            await ExerciseEndpoint.delete(exercise.id);
            this.deleteLocal(exercise);
            uiStore.showSuccess('Contact deleted.');
        } catch (error: any) {
            console.log('Contact delete failed');
            uiStore.showError(`Server error. ${error.message}`);
        }
    }

    private deleteLocal(exercise: ExerciseTo) {
        this.exercises = this.exercises.filter((c) => c.id !== exercise.id);
    }
}

