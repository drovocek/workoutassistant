export interface AppForm<T> {

    visible(): boolean;

    clear(): void;

    close(): void;

    open(value: T): void;
}