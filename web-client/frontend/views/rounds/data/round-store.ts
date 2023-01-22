import {makeAutoObservable, observable, runInAction} from 'mobx';
import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {processErr, randomString} from "Frontend/common/utils/app-utils";
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import RoundModel from "Frontend/generated/ru/soft/common/to/RoundToModel";
import {RoundEndpoint} from 'Frontend/generated/endpoints';

export class RoundStore implements GeneralStore<Round> {

    data: Round[] = [];
    filterText = '';
    selected: Round | null = null;
    formVisible: boolean = false;
    entityName: string = 'Workout';

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
        const data = await RoundEndpoint.getAll();

        runInAction(() => {
            this.data = data;
        });
    }

    setSelected(selected: Round | null) {
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

    updateFilterByEvent(e: { target: HTMLInputElement }) {
        this.filterText = e.target.value;
    }

    createNew(): Round {
        return RoundModel.createEmptyValue();
    }

    public async update(updatable: Round) {
        if (!updatable.id) return;
        (updatable as any).type = "round";
        await RoundEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess(`${this.entityName} update.`);
            })
            .catch(processErr);
    }

    public async add(stored: Round) {
        (stored as any).type = "round";
        await RoundEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess(`${this.entityName} create.`);
            })
            .catch(processErr);
    }

    public async copy(original: Round) {
        if (!original) return;

        let copy = JSON.parse(JSON.stringify(original));
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.id = null;
        (copy as any).type = "round";

        await RoundEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(original, copy);
                uiStore.showSuccess(`${this.entityName} copy.`);
            });
    }

    private saveLocal(saved: Round) {
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

    public saveLocalAfterSelected(original: Round, saved: Round) {
        const originalExists = this.data.some((c) => c.id === original.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(original) + 1;
            this.data.splice(dropIndex, 0, saved);
        }
    }

    async delete(id: string) {
        if (!id) return;

        await RoundEndpoint.delete(id)
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