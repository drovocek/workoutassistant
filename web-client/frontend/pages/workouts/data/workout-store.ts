import {makeAutoObservable, observable, runInAction} from 'mobx';
import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import WorkoutModel from "Frontend/generated/ru/soft/common/to/WorkoutToModel";
import {WorkoutEndpoint} from 'Frontend/generated/endpoints';

export class WorkoutStore implements GeneralStore<Workout> {
    data: Workout[] = [];
    filterText = '';
    selected: Workout | null = null;
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
        const data = await WorkoutEndpoint.getAll();

        runInAction(() => {
            this.data = data;
        });
    }

    setSelected(selected: Workout | null) {
        this.selected = selected;
    }

    hasSelected(): boolean {
        return this.selected !== null;
    }

    get filtered() {
        const filter = new RegExp(this.filterText, 'i');
        return this.data.filter((entity) =>
            filter.test(`${entity.title} ${entity.note}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }

    public async update(updatable: Workout) {
        if (!updatable.id) return;
        await WorkoutEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Exercise updated.');
            })
            .catch(processErr);
    }

    public async add(stored: Workout) {
        await WorkoutEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Exercise created.');
            })
            .catch(processErr);
    }

    public async copy() {
        const original = this.selected;
        if (!original) return;

        let copy = WorkoutModel.createEmptyValue();
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.note = original.note;

        copy.workoutSchema = JSON.parse(JSON.stringify(original.workoutSchema));

        await WorkoutEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(copy);
                uiStore.showSuccess('Round copy.');
            });
    }

    private saveLocal(saved: Workout) {
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

    private saveLocalAfterSelected(copy: Workout) {
        const selected = this.selected
        if (!selected) return;

        const originalExists = this.data.some((c) => c.id === selected.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(selected) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    async delete(): Promise<void> {
        const removed = this.selected;
        if (!removed) return;
        let id = removed.id;
        if (!id) return;
        await WorkoutEndpoint.delete(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(removed);
                uiStore.showSuccess('Plan deleted.');
            });

    }

    private deleteLocal(removed: Workout) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }

    createNew(): Workout {
        return WorkoutModel.createEmptyValue();
    }
}