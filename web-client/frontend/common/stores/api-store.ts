import {uiStore} from "Frontend/common/stores/app-store";
import {GeneralStore, HasId} from "Frontend/common/stores/general-store";
import {processErr} from "Frontend/common/utils/app-utils";

export class ApiStore<T extends HasId> implements GeneralStore<T> {
    data: T[] = [];
    filterText = '';
    selected: T | null = null;
    formVisible: boolean = false;

    createEmptyValue: () => T;
    addEntity: (stored: T) => Promise<T>;
    copyEntity: (original: T) => T;
    updateEntity: (updatable: T) => Promise<void>;
    deleteEntity: (id: string) => Promise<void>;

    constructor(createEmptyValue: () => T,
                addEntity: (stored: T) => Promise<T>,
                copyEntity: (original: T) => T,
                updateEntity: (updatable: T) => Promise<void>,
                deleteEntity: (id: string) => Promise<void>) {
        this.createEmptyValue = createEmptyValue;
        this.addEntity = addEntity;
        this.copyEntity = copyEntity;
        this.updateEntity = updateEntity;
        this.deleteEntity = deleteEntity;
    }

    createNew(): T {
        return this.createEmptyValue();
    }

    //server operations
    public async add(stored: T): Promise<void> {
        await this.addEntity(stored)
            .then(stored => {
                this.saveLocal(stored);
                uiStore.showSuccess('Created');
            })
            .catch(processErr);
    }

    public async copy(): Promise<void> {
        const original = this.selected;
        if (!original) return;

        let copy = this.copyEntity(original);

        await this.addEntity(copy)
            .then(copy => {
                this.saveLocalAfterSelected(copy);
                uiStore.showSuccess('Round copy.');
            });
    }

    public async update(updatable: T): Promise<void> {
        if (!updatable.id) return;
        await this.update(updatable)
            .then(() => {
                this.saveLocal(updatable);
                uiStore.showSuccess('Updated');
            })
            .catch(processErr);
    }

    async delete(): Promise<void> {
        const removed = this.selected;
        if (!removed) return;

        let id = removed.id;
        if (!id) return;

        await this.deleteEntity(id)
            .then(() => {
                this.setSelected(null);
                this.deleteLocal(removed);
                uiStore.showSuccess('Delete');
            });

    }

    private saveLocal(saved: T) {
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

    private saveLocalAfterSelected(copy: T) {
        const selected = this.selected;
        if (!selected) return;

        const originalExists = this.data.some((c) => c.id === selected.id);
        if (originalExists) {
            const dropIndex = this.data.indexOf(selected) + 1;
            this.data.splice(dropIndex, 0, copy);
        }
    }

    private deleteLocal(removed: T) {
        this.data = this.data.filter((c) => c.id !== removed.id);
    }

    setSelected(selected: T | null) {
        this.selected = selected;
    }

    hasSelected(): boolean {
        return this.selected !== null;
    }

    filtered(pattern: string) {
        const filter = new RegExp(this.filterText, 'i');
        return this.data.filter((entity) =>
            filter.test(pattern)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }
}