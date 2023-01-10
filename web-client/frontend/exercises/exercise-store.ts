import {exerciseStore, uiStore} from 'Frontend/common/stores/app-store';
import {makeAutoObservable, observable, runInAction} from 'mobx';
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";

export class ExerciseStore implements GeneralStore<ExerciseTo> {
    data: ExerciseTo[] = [];
    filterText = '';
    selected: ExerciseTo | null = null;
    formOpened: boolean = false;

    constructor() {
        makeAutoObservable(
            this,
            {
                selected: observable.ref,
                formOpened: observable.ref,
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

    setSelected(selected: ExerciseTo | null) {
        this.selected = selected;
    }

    hasSelected(): boolean {
        return this.selected !== null;
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

    createNew(): ExerciseTo {
        let exerciseDefault = ExerciseToModel.createEmptyValue();
        exerciseDefault.complexity = 5;
        return exerciseDefault;
    }

    public async update(updatable: ExerciseTo) {
        if (!updatable.id) return;
        await ExerciseEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Exercise updated.');
            })
            .catch(processErr);
    }

    public async add(stored: ExerciseTo) {
        await ExerciseEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Round created.');
            })
            .catch(processErr);
    }

    public async copy() {
        const original = exerciseStore.selected;
        if (!original) return;

        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;

        await ExerciseEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(copy);
                uiStore.showSuccess('Round copy.');
            });
    }

    private saveLocal(saved: ExerciseTo) {
        const exist = this.data.some((c) => c.id === saved.id);
        if (exist) {
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

    private saveLocalAfterSelected(copy: ExerciseTo) {
        const selected = exerciseStore.selected;
        if (!selected) return;

        const originalExists = this.data.some((c) => c.id === selected.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(selected) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    async delete() {
        const removed = exerciseStore.selected;
        if (!removed) return;
        let id = removed.id;
        if (!id) return;
        await ExerciseEndpoint.delete(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(removed);
                uiStore.showSuccess('Round deleted.');
            });

    }

    private deleteLocal(removed: ExerciseTo) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }
}