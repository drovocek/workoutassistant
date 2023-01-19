export interface GeneralStore<T> {
    filterText: string;
    selected: T | null;
    data: T[];

    updateFilter(filterText: string): void;

    hasSelected(): boolean;

    createNew(): T;

    copy(original: T): Promise<void>;

    delete(id: string): Promise<void>
}

export interface HasId {
    id?: string;
}