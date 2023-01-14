import {makeAutoObservable, observable, runInAction} from 'mobx';
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import {RoundEndpoint} from "Frontend/generated/endpoints";
import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {deepEquals, processErr, randomString} from "Frontend/common/utils/app-utils";
import Station from "Frontend/generated/ru/soft/common/data/Station";
import RoundModel from "Frontend/generated/ru/soft/common/to/RoundToModel";

export class RoundStore implements GeneralStore<Round> {
    data: Round[] = [];
    filterText = '';
    selected: Round | null = null;
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
            filter.test(`${entity.title} ${entity.description}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }

    public async update(updatable: Round) {
        if (!updatable.id) return;
        await RoundEndpoint.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Exercise updated.');
            })
            .catch(processErr);
    }

    public async add(stored: Round) {
        await RoundEndpoint.add(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Round created.');
            })
            .catch(processErr);
    }

    public async copy() {
        const original = this.selected;
        if (!original) return;

        let copy = RoundModel.createEmptyValue();
        copy.title = original.title + ' Copy ' + randomString(5);
        copy.description = original.description;
        copy.stationsSchema = JSON.parse(JSON.stringify(original.stationsSchema));

        await RoundEndpoint.add(copy)
            .then(copy => {
                this.saveLocalAfterSelected(copy);
                uiStore.showSuccess('Round copy.');
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

    private saveLocalAfterSelected(copy: Round) {
        const selected = this.selected;
        if (!selected) return;

        const originalExists = this.data.some((c) => c.id === selected.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(selected) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    async delete() {
        const removed = this.selected;
        if (!removed) return;
        let id = removed.id;
        if (!id) return;
        await RoundEndpoint.delete(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(removed);
                uiStore.showSuccess('Round deleted.');
            });

    }

    private deleteLocal(removed: Round) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }

    createNew(): Round {
        return RoundModel.createEmptyValue();
    }

    selectedDetailsItem: Round | null = null;
    selectedDetailsItemChildData: Station[] = [];
    selectedDetailsItemChild: Station | null = null;
    detailsItemChildFilterText = '';

    public get filteredDetailsItemChild() {
        const filter = new RegExp(this.detailsItemChildFilterText, 'i');
        return this.selectedDetailsItemChildData.filter((entity) =>
            filter.test(`${entity.exerciseSnapshot.title}`)
        );
    }

    public updateDetailsItemChildFilter(filterText: string) {
        this.detailsItemChildFilterText = filterText;
    }

    public setSelectedDetailsItem(detailsItem: Round | null): void {
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

    public setSelectedDetailsItemChild(detailsItemChild: Station | null): void {
        this.selectedDetailsItemChild = detailsItemChild;
    }

    public hasSelectedDetailsItemChild(): boolean {
        return this.selectedDetailsItemChild !== null;
    }

    private extractStations(round: Round): Station[] {
        if (round.stationsSchema && round.stationsSchema.stations) {
            return round.stationsSchema.stations as Station[];
        } else {
            return [];
        }
    }

    public detailsItemIsOpened(detailsItem: Round): boolean {
        return deepEquals(this.selectedDetailsItem, detailsItem);
    }

    public getSelectedItemsDetailAsArr(): Round[] {
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

        selected.stationsSchema.stations = this.selectedDetailsItemChildData;
        await this.update(selected);
    }

    public updateLocalDetailsItemChild(updated: Station) {
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

    public saveLocalDetailsItemChild(saved: Station[]) {
        const selected = this.selectedDetailsItemChild;
        if (selected) return;
        this.selectedDetailsItemChildData = [...this.selectedDetailsItemChildData, ...saved];
    }
}