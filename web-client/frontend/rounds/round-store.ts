import {makeAutoObservable, observable, runInAction} from 'mobx';
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {WorkoutRoundEndpoint} from "Frontend/generated/endpoints";
import {uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";

export class RoundStore {
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

    editNew() {
        this.selected = WorkoutRoundToModel.createEmptyValue();
    }

    cancelEdit() {
        this.selected = null;
    }

    setSelected(selected: WorkoutRoundTo) {
        console.log(selected);
        this.selected = selected;
    }

    public async update(updatable: WorkoutRoundTo): Promise<void> {
        if (!updatable.id) return;
        return await WorkoutRoundEndpoint.update(updatable).then(() => {
            this.saveLocal(updatable);
            uiStore.showSuccess('Exercise updated.');
        });
    }

    public async add(stored: WorkoutRoundTo): Promise<void> {
        return await WorkoutRoundEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Exercise created.');
            });
    }

    private saveLocal(saved: WorkoutRoundTo) {
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

    async delete(removed: WorkoutRoundTo) {
        if (!removed.id) return;

        try {
            await WorkoutRoundEndpoint.delete(removed.id);
            this.deleteLocal(removed);
            uiStore.showSuccess('Contact deleted.');
        } catch (error: any) {
            console.log('Contact delete failed');
            uiStore.showError(`Server error. ${error.message}`);
        }
    }

    private deleteLocal(removed: WorkoutRoundTo) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }
}