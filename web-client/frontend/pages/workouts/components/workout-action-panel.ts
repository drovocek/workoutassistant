import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import {workoutStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import RoundSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/RoundSnapshot";

@customElement('plan-action-panel')
export class WorkoutActionPanel extends AppActionPanel<Workout | RoundSnapshot> {

    public getFilterValue(): string {
        return workoutStore.hasSelectedDetailsItem() ?
            workoutStore.detailsItemChildFilterText : workoutStore.filterText;
    };

    public getSelected(): Workout | RoundSnapshot | null {
        return workoutStore.hasSelectedDetailsItem() ?
            workoutStore.selectedDetailsItemChild : workoutStore.selected;
    }

    public getNew(): Workout | RoundSnapshot {
        return workoutStore.createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        workoutStore.hasSelectedDetailsItem() ?
            workoutStore.updateDetailsItemChildFilter(e.target.value) : workoutStore.updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return workoutStore.hasSelectedDetailsItem() ?
            workoutStore.hasSelectedDetailsItemChild() : workoutStore.hasSelected();
    }

    public onDelete(): void {
        if (workoutStore.hasSelectedDetailsItem()) {
            workoutStore.deleteLocalSelectedDetailsItemChild();
        } else {
            this.deleteBtn.disabled = true;
            workoutStore.delete()
                .catch((err) => {
                    this.deleteBtn.disabled = false;
                    processErr(err);
                });
        }
    };

    public onCopy(): void {
        if (workoutStore.hasSelectedDetailsItem()) {
            workoutStore.copyLocalSelectedDetailsItemChild();
        } else {
            this.copyBtn.disabled = true;
            workoutStore.copy()
                .catch(processErr)
                .finally(() => this.copyBtn.disabled = false);
        }
    };
}
