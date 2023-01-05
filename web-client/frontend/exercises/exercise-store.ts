import {uiStore} from 'Frontend/common/stores/app-store';
import {makeAutoObservable, observable, runInAction} from 'mobx';
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {ExerciseEndpoint} from "Frontend/generated/endpoints";

export class ExerciseStore {
    data: ExerciseTo[] = [];
    filterText = '';
    selected: ExerciseTo | null = null;

    constructor() {
        makeAutoObservable(
            this,
            {
                selected: observable.ref,
                initFromServer: false,
                data: observable.shallow,
            },
            {autoBind: true}
        );
        this.initFromServer();
    }

    async initFromServer() {
        const data = await ExerciseEndpoint.getAll();

        runInAction(() => {
            this.data = data;
        });
    }

    get filtered() {
        const filter = new RegExp(this.filterText, 'i');
        return this.data.filter((entity) =>
            filter.test(`${entity.title} ${entity.description}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }

    editNew() {
        let exerciseDefault = ExerciseToModel.createEmptyValue();
        exerciseDefault.complexity = 5;
        this.selected = exerciseDefault;
    }

    cancelEdit() {
        this.selected = null;
    }

    setSelected(selected: ExerciseTo) {
        this.selected = selected;
    }

    public async update(updatable: ExerciseTo): Promise<void> {
        if (!updatable.id) return;
        return await ExerciseEndpoint.update(updatable).then(() => {
            this.saveLocal(updatable);
            uiStore.showSuccess('Exercise updated.');
        });
    }

    public async add(stored: ExerciseTo): Promise<void> {
        return await ExerciseEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Exercise created.');
            });
    }

    private saveLocal(saved: ExerciseTo) {
        const contactExists = this.data.some((c) => c.id === saved.id);
        if (contactExists) {
            this.data = this.data.map((existing) => {
                if (existing.id === saved.id) {
                    return saved;
                } else {
                    return existing;
                }
            });
        } else {
            this.data.push(saved);
        }
    }

    public async delete(removed: ExerciseTo) {
        if (!removed.id) return;
        try {
            await ExerciseEndpoint.delete(removed.id);
            this.deleteLocal(removed);
            uiStore.showSuccess('Contact deleted.');
        } catch (error: any) {
            console.log('Contact delete failed');
            uiStore.showError(`Server error. ${error.message}`);
        }
    }

    private deleteLocal(removed: ExerciseTo) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }
}