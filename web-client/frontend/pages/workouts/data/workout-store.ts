import {makeAutoObservable, observable, runInAction} from 'mobx';
import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {deepEquals, processErr, randomString} from "Frontend/common/utils/app-utils";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import {WorkoutPlanEndpoint} from "Frontend/generated/endpoints";
import WorkoutModel from "Frontend/generated/ru/soft/common/to/WorkoutToModel";
import RoundSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/RoundSnapshot";

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
                selectedDetailsItem: observable.ref,
                selectedDetailsItemChildData: observable.shallow,
                selectedDetailsItemChild: observable.ref,
            },
            {autoBind: true}
        );
        this.initFromServer();
    }

    async initFromServer() {
        const data = await WorkoutPlanEndpoint.getAll();

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
            filter.test(`${entity.title} ${entity.description}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }

    public async update(updatable: Workout) {
        if (!updatable.id) return;
        await WorkoutPlanEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Exercise updated.');
            })
            .catch(processErr);
    }

    public async add(stored: Workout) {
        await WorkoutPlanEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Round created.');
            })
            .catch(processErr);
    }

    public async copy() {
        const original = this.selected;
        if (!original) return;

        let copy = WorkoutModel.createEmptyValue();
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.description = original.description;

        copy.roundsSchema = JSON.parse(JSON.stringify(original.roundsSchema));

        await WorkoutPlanEndpoint.add(copy)
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
        await WorkoutPlanEndpoint.delete(id)
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

    selectedDetailsItem: Workout | null = null;
    selectedDetailsItemChildData: RoundSnapshot[] = [];
    selectedDetailsItemChild: RoundSnapshot | null = null;
    detailsItemChildFilterText = '';

    public get filteredDetailsItemChild() {
        const filter = new RegExp(this.detailsItemChildFilterText, 'i');
        return this.selectedDetailsItemChildData.filter((entity) =>
            filter.test(`${entity.title}`)
        );
    }

    public updateDetailsItemChildFilter(filterText: string) {
        this.detailsItemChildFilterText = filterText;
    }

    public setSelectedDetailsItem(detailsItem: Workout | null): void {
        if (this.hasSelectedDetailsItem()) {
            this.updateDetailsItem();
        }
        this.selectedDetailsItem = detailsItem;
        this.selectedDetailsItemChildData = this.selectedDetailsItem !== null ? this.extractStations(this.selectedDetailsItem) : [];

        this.selectedDetailsItemChild = null;
    }

    public hasSelectedDetailsItem(): boolean {
        return this.selectedDetailsItem !== null;
    }

    public setSelectedDetailsItemChild(detailsItemChild: RoundSnapshot | null): void {
        this.selectedDetailsItemChild = detailsItemChild;
    }

    public hasSelectedDetailsItemChild(): boolean {
        return this.selectedDetailsItemChild !== null;
    }

    private extractStations(round: Workout): RoundSnapshot[] {
        if (round.roundsSchema && round.roundsSchema.roundSnapshots) {
            return round.roundsSchema.roundSnapshots as RoundSnapshot[];
        } else {
            return [];
        }
    }

    public detailsItemIsOpened(detailsItem: Workout): boolean {
        return deepEquals(this.selectedDetailsItem, detailsItem);
    }

    public getSelectedItemsDetailAsArr(): Workout[] {
        return this.selectedDetailsItem !== null ? [this.selectedDetailsItem] : [];
    }

    public copyLocalSelectedDetailsItemChild() {
        const selected = this.selectedDetailsItemChild;
        if (!selected) return;
        const copy = {...selected};

        const originalExists = this.selectedDetailsItemChildData
            .some((station) => deepEquals(station, selected));
        if (originalExists) {
            const dropIndex = this.selectedDetailsItemChildData.indexOf(selected) + 1;
            this.selectedDetailsItemChildData.splice(dropIndex, 0, copy);
            this.updateOrder();
        }
    }


    private updateOrder() {
        for (let i = 0; i < this.selectedDetailsItemChildData.length; i++) {
            this.selectedDetailsItemChildData[i].order = i;
        }
    }

    public deleteLocalSelectedDetailsItemChild() {
        const selected = this.selectedDetailsItemChild;
        if (!selected) return;
        this.selectedDetailsItemChildData = this.selectedDetailsItemChildData
            .filter((station) => !deepEquals(station, selected));
        this.setSelectedDetailsItemChild(null);
    }

    public async updateDetailsItem() {
        const selected = this.selectedDetailsItem;
        if (!selected?.id) return;

        selected.roundsSchema.roundSnapshots = this.selectedDetailsItemChildData;

        await this.update(selected);
    }

    public updateLocalDetailsItemChild(updated: RoundSnapshot) {
        const selected = this.selectedDetailsItemChild;
        if (!selected) return;
        this.selectedDetailsItemChildData = this.selectedDetailsItemChildData.map((station) => {
            if (deepEquals(station, selected)) {
                return updated;
            } else {
                return station;
            }
        });
    }

    public saveLocalDetailsItemChild(saved: RoundSnapshot[]) {
        const selected = this.selectedDetailsItemChild;
        if (selected) return;
        this.selectedDetailsItemChildData = [...this.selectedDetailsItemChildData, ...saved];
    }
}