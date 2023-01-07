import {makeAutoObservable, observable, runInAction} from 'mobx';
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {WorkoutRoundEndpoint} from "Frontend/generated/endpoints";
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";
import {EndpointError} from "@hilla/frontend";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {randomString} from "Frontend/common/app-utils";

export class RoundStore implements GeneralStore<WorkoutRoundTo> {
    data: WorkoutRoundTo[] = [];
    filterText = '';
    selected: WorkoutRoundTo | null = null;

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
        const data = await WorkoutRoundEndpoint.getAll();

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

    hasSelected(): boolean {
        return this.selected !== null;
    }

    setSelected(selected: WorkoutRoundTo | null) {
        this.selected = selected;
    }

    public async update(updatable: WorkoutRoundTo) {
        if (!updatable.id) return;
        await WorkoutRoundEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Exercise updated.');
            })
            .catch(this.processErr);
    }

    public async add(stored: WorkoutRoundTo) {
        await WorkoutRoundEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Round created.');
            })
            .catch(this.processErr);
    }

    public async copy() {
        const original = roundStore.selected;
        if (!original) return;

        let copy = WorkoutRoundToModel.createEmptyValue();
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.description = original.description;
        copy.roundSchema = JSON.parse(JSON.stringify(original.roundSchema));

        await WorkoutRoundEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(copy);
                uiStore.showSuccess('Round copy.');
            })
            .catch(this.processErr);
    }

    private saveLocal(saved: WorkoutRoundTo) {
        const roundExists = this.data.some((c) => c.id === saved.id);
        if (roundExists) {
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

    private saveLocalAfterSelected(copy: WorkoutRoundTo) {
        const selected = roundStore.selected;
        if (!selected) return;

        const originalExists = this.data.some((c) => c.id === selected.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(selected) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    async delete() {
        const removed = roundStore.selected;
        if (!removed) return;
        let id = removed.id;
        if (!id) return;
        await WorkoutRoundEndpoint.delete(id)
            .then(() => {
                this.deleteLocal(removed);
                uiStore.showSuccess('Round deleted.');
                this.setSelected(null);
            })
            .catch(this.processErr);

    }

    private deleteLocal(removed: WorkoutRoundTo) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }

    protected processErr(err: any) {
        console.log('Operation failed');
        if (err instanceof EndpointError) {
            uiStore.showError(`Server error. ${err.message}`);
        } else {
            throw err;
        }
    }

    createNew(): WorkoutRoundTo {
        return WorkoutRoundToModel.createEmptyValue();
    }
}