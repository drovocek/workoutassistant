import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import '@vaadin/split-layout';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/upload'
import './round-form';
import './details/round-details';
import './selector/exercise-selector';
import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {roundStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";

@customElement('round-store-action-panel')
export class RoundStoreActionPanel extends AppActionPanel<WorkoutRoundTo | WorkoutStationSnapshot> {

    public getFilterValue(): string {
        return roundStore.hasSelectedDetailsItem() ?
            roundStore.detailsItemChildFilterText : roundStore.filterText;
    };

    public getSelected(): WorkoutRoundTo | WorkoutStationSnapshot | null {
        return roundStore.hasSelectedDetailsItem() ?
            roundStore.selectedDetailsItemChild : roundStore.selected;
    }

    public getNew(): WorkoutRoundTo | WorkoutStationSnapshot {
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
