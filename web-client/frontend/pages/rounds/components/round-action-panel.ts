import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import Station from "Frontend/generated/ru/soft/common/data/Station";
import {roundStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";

@customElement('round-action-panel')
export class RoundActionPanel extends AppActionPanel<Round | Station> {

    public getFilterValue(): string {
        return roundStore.hasSelectedDetailsItem() ?
            roundStore.detailsItemChildFilterText : roundStore.filterText;
    };

    public getSelected(): Round | Station | null {
        return roundStore.hasSelectedDetailsItem() ?
            roundStore.selectedDetailsItemChild : roundStore.selected;
    }

    public getNew(): Round | Station {
        return roundStore.createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        roundStore.hasSelectedDetailsItem() ?
            roundStore.updateDetailsItemChildFilter(e.target.value) : roundStore.updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return roundStore.hasSelectedDetailsItem() ?
            roundStore.hasSelectedDetailsItemChild() : roundStore.hasSelected();
    }

    public onDelete(): void {
        if (roundStore.hasSelectedDetailsItem()) {
            roundStore.deleteLocalSelectedDetailsItemChild();
        } else {
            this.deleteBtn.disabled = true;
            roundStore.delete()
                .catch((err) => {
                    this.deleteBtn.disabled = false;
                    processErr(err);
                });
        }
    };

    public onCopy(): void {
        if (roundStore.hasSelectedDetailsItem()) {
            roundStore.copyLocalSelectedDetailsItemChild();
        } else {
            this.copyBtn.disabled = true;
            roundStore.copy()
                .catch(processErr)
                .finally(() => this.copyBtn.disabled = false);
        }
    };
}
