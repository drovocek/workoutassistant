export interface GeneralStore<T> {
    filterText?: string;
    selected: T | null;
    data: T[];

    updateFilter(filterText: string): void;

    hasSelected(): boolean;

    createNew(): T;

    copy(): Promise<void>;

    delete(): Promise<void>;
}