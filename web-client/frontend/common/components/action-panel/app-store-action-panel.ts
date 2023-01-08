import '@vaadin/button';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import {customElement} from 'lit/decorators.js';
import {GeneralStore} from "Frontend/common/stores/general-store";
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";

@customElement('app-store-action-panel')
export abstract class AppStoreActionPanel<T> extends AppActionPanel<T> {

    public getFilterValue(): string {
        return this.generalStore().filterText
    };

    public getSelected(): T | null {
        return this.generalStore().selected;
    }

    public getNew(): T {
        return this.generalStore().createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        this.generalStore().updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return this.generalStore().hasSelected();
    }

    public onDelete(): void {
        this.deleteBtn.disabled = true;
        this.generalStore().delete()
            .finally(() => this.copyBtn.disabled = false);
    };

    public onCopy(): void {
        this.copyBtn.disabled = true;
        this.generalStore().copy()
            .finally(() => this.copyBtn.disabled = false);
    };

    protected abstract generalStore(): GeneralStore<T>;
}
