import {makeAutoObservable, observable, runInAction} from 'mobx';
import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";

import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import {WorkoutEndpoint} from "Frontend/generated/endpoints";
import WorkoutModel from "Frontend/generated/ru/soft/common/to/WorkoutToModel";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";

export class WorkoutStore implements GeneralStore<Workout> {

    data: Workout[] = [];
    filterText = '';
    selected: Workout | null = null;
    selectedWorkoutElement: WorkoutElement | null = null;
    formVisible: boolean = false;
    entityName: string = 'Workout';

    constructor() {
        makeAutoObservable(
            this,
            {
                selected: observable.ref,
                selectedWorkoutElement: observable.ref,
                formVisible: observable.ref,
                filterText: observable.ref,
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

    setSelectedWorkoutElement(selected: WorkoutElement | null) {
        this.selectedWorkoutElement = selected;
    }

    hasSelectedWorkoutElement(): boolean {
        return this.selectedWorkoutElement !== null;
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

    updateFilterByEvent(e: { target: HTMLInputElement }) {
        this.filterText = e.target.value;
    }

    createNew(): Workout {
        return WorkoutModel.createEmptyValue();
    }

    public async update(updatable: Workout) {
        if (!updatable.id) return;
        await WorkoutEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess(`${this.entityName} update.`);
            })
            .catch(processErr);
    }

    public async add(stored: Workout) {
        await WorkoutEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess(`${this.entityName} create.`);
            })
            .catch(processErr);
    }

    public async copy(original: Workout) {
        if (!original) return;

        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;
        (copy as any).type = "round";

        await WorkoutEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(original, copy);
                uiStore.showSuccess(`${this.entityName} copy.`);
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

    public saveLocalAfterSelected(original: Workout, saved: Workout) {
        const originalExists = this.data.some((c) => c.id === original.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(original) + 1;
            this.data.splice(dropIndex, 0, saved);
        }
    }

    async delete(id: string) {
        if (!id) return;

        await WorkoutEndpoint.delete(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(id);
                uiStore.showSuccess(`${this.entityName} delete.`);
            });

    }

    private deleteLocal(id: string) {
        this.data = this.data.filter((c) => c.id !== id);
    }
}