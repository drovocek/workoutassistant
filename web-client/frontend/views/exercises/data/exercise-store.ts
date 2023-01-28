import {uiStore} from 'Frontend/common/stores/app-store';
import {makeAutoObservable, observable, runInAction} from 'mobx';
import Exercise from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";

export class ExerciseStore implements GeneralStore<Exercise> {

    data: Exercise[] = [];
    filterText = '';
    selected: Exercise | null = null;
    formVisible: boolean = false;
    entityName: string = 'Exercise';

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

    setSelected(selected: Exercise | null) {
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

    createNew(): Exercise {
        return ExerciseToModel.createEmptyValue();
    }

    public async update(updatable: Exercise) {
        if (!updatable.id) return;
        await ExerciseEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess(`${this.entityName} update.`);
            })
            .catch(processErr);
    }

    public async add(stored: Exercise) {
        await ExerciseEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess(`${this.entityName} create.`);
            })
            .catch(processErr);
    }

    public async copy(original: Exercise) {
        if (!original) return;

        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;

        await ExerciseEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(original, copy);
                uiStore.showSuccess(`${this.entityName} copy.`);
            });
    }

    private saveLocal(saved: Exercise) {
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

    private saveLocalAfterSelected(original: Exercise, copy: Exercise) {
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
                uiStore.showSuccess(`${this.entityName} delete.`);
            });

    }

    private deleteLocal(id: string) {
        this.data = this.data.filter((c) => c.id !== id);
    }
}