import {uiStore} from 'Frontend/common/stores/app-store';
import {makeAutoObservable, observable, runInAction} from 'mobx';
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";
import '@vaadin/notification';

export class ExerciseStore implements GeneralStore<ExerciseTo> {

    data: ExerciseTo[] = [];
    filterText = '';
    selected: ExerciseTo | null = null;
    formVisible: boolean = false;

    private createCopy(original: ExerciseTo): ExerciseTo {
        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;
        return copy;
    }

    constructor() {
        makeAutoObservable(
            this,
            {
                selected: observable.ref,
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
            filter.test(`${entity.title} ${entity.note}`)
        );
    }

    updateFilter(filterText: string): void {
        this.filterText = filterText;
    }

    updateFilterByEvent(e: { target: HTMLInputElement }) {
        this.filterText = e.target.value;
    }

    createNew(): ExerciseTo {
        return ExerciseToModel.createEmptyValue();
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
                uiStore.showSuccess('Exercise created.');
            })
            .catch(processErr);
    }

    public async copy(original: ExerciseTo) {
        if (!original) return;

        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;

        await ExerciseEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(original, copy);
                uiStore.showSuccess('Exercise copy.');
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

    private saveLocalAfterSelected(original: ExerciseTo, copy: ExerciseTo) {
        const originalExists = this.data.some((c) => c.id === original.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(original) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    async delete(id: string) {
        if (!id) return;

        await ExerciseEndpoint.delete(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(id);
                uiStore.showSuccess('Exercise deleted.');
            });

    }

    private deleteLocal(id: string) {
        this.data = this.data.filter((c) => c.id !== id);
    }
}