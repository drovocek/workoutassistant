import {makeAutoObservable, observable, runInAction} from 'mobx';
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {WorkoutRoundEndpoint} from "Frontend/generated/endpoints";
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";
import {EndpointError} from "@hilla/frontend";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {deepEquals, randomString} from "Frontend/common/utils/app-utils";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";

export class RoundStore implements GeneralStore<WorkoutRoundTo> {
    data: WorkoutRoundTo[] = [];
    filterText = '';
    selected: WorkoutRoundTo | null = null;

    setSelected(selected: WorkoutRoundTo | null) {
        this.selected = selected;
    }

    hasSelected(): boolean {
        return this.selected !== null;
    }

    constructor() {
        makeAutoObservable(
            this,
            {
                selected: observable.ref,
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

    selectedDetailsItem: WorkoutRoundTo | null = null;
    selectedDetailsItemChildData: WorkoutStationSnapshot[] = [];
    selectedDetailsItemChild: WorkoutStationSnapshot | null = null;
    detailsItemChildFilterText = '';

    public get filteredDetailsItemChild() {
        const filter = new RegExp(this.detailsItemChildFilterText, 'i');
        return this.selectedDetailsItemChildData.filter((entity) =>
            filter.test(`${entity.exercise.title}`)
        );
    }

    public updateDetailsItemChildFilter(filterText: string) {
        this.detailsItemChildFilterText = filterText;
    }

    public updateDetailsItemChild(filterText: string) {
        this.detailsItemChildFilterText = filterText;
    }

    public setSelectedDetailsItem(detailsItem: WorkoutRoundTo | null): void {
        this.selectedDetailsItem = detailsItem;
        this.selectedDetailsItemChildData = this.selectedDetailsItem !== null ? this.extractStations(this.selectedDetailsItem) : [];
    }

    public hasSelectedDetailsItem(): boolean {
        return this.selectedDetailsItem !== null;
    }

    public setSelectedDetailsItemChild(detailsItemChild: WorkoutStationSnapshot | null): void {
        this.selectedDetailsItemChild = detailsItemChild;
    }

    public hasSelectedDetailsItemChild(): boolean {
        return this.selectedDetailsItemChild !== null;
    }

    private extractStations(round: WorkoutRoundTo): WorkoutStationSnapshot[] {
        if (round.roundSchema && round.roundSchema.roundStations) {
            return round.roundSchema.roundStations as WorkoutStationSnapshot[];
        } else {
            return [];
        }
    }

    public detailsItemIsOpened(detailsItem: WorkoutRoundTo): boolean {
        return deepEquals(this.selectedDetailsItem, detailsItem);
    }

    public getSelectedItemsDetailAsArr(): WorkoutRoundTo[] {
        return this.selectedDetailsItem !== null ? [this.selectedDetailsItem] : [];
    }

    public copyLocalSelectedDetailsItemChild() {
        const selected = roundStore.selectedDetailsItemChild;
        if (!selected) return;
        const copy = {...selected};

        const originalExists = roundStore.selectedDetailsItemChildData
            .some((station) => deepEquals(station, selected));
        if (originalExists) {
            const dropIndex = roundStore.selectedDetailsItemChildData.indexOf(selected) + 1;
            roundStore.selectedDetailsItemChildData.splice(dropIndex, 0, copy);
        }
    }

    public deleteLocalSelectedDetailsItemChild() {
        const selected = roundStore.selectedDetailsItemChild;
        if (!selected) return;
        roundStore.selectedDetailsItemChildData = roundStore.selectedDetailsItemChildData
            .filter((station) => !deepEquals(station, selected));
    }

    public async updateDetailsItem() {
        const selected = roundStore.selectedDetailsItem;
        if (!selected?.id) return;
        for (let i = 0; i < this.selectedDetailsItemChildData.length; i++) {
            this.selectedDetailsItemChildData[i].order = i;
        }
        selected.roundSchema.roundStations = this.selectedDetailsItemChildData;
        await this.update(selected);
    }
}