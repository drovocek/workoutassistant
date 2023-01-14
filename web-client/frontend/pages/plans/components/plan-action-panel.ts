import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import {planStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";
import WorkoutPlan from "Frontend/generated/ru/soft/common/to/WorkoutPlanTo";
import RoundSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/RoundSnapshot";

@customElement('plan-action-panel')
export class PlanActionPanel extends AppActionPanel<WorkoutPlan | RoundSnapshot> {

    public getFilterValue(): string {
        return planStore.hasSelectedDetailsItem() ?
            planStore.detailsItemChildFilterText : planStore.filterText;
    };

    public getSelected(): WorkoutPlan | RoundSnapshot | null {
        return planStore.hasSelectedDetailsItem() ?
            planStore.selectedDetailsItemChild : planStore.selected;
    }

    public getNew(): WorkoutPlan | RoundSnapshot {
        return planStore.createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        planStore.hasSelectedDetailsItem() ?
            planStore.updateDetailsItemChildFilter(e.target.value) : planStore.updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return planStore.hasSelectedDetailsItem() ?
            planStore.hasSelectedDetailsItemChild() : planStore.hasSelected();
    }

    public onDelete(): void {
        if (planStore.hasSelectedDetailsItem()) {
            planStore.deleteLocalSelectedDetailsItemChild();
        } else {
            this.deleteBtn.disabled = true;
            planStore.delete()
                .catch((err) => {
                    this.deleteBtn.disabled = false;
                    processErr(err);
                });
        }
    };

    public onCopy(): void {
        if (planStore.hasSelectedDetailsItem()) {
            planStore.copyLocalSelectedDetailsItemChild();
        } else {
            this.copyBtn.disabled = true;
            planStore.copy()
                .catch(processErr)
                .finally(() => this.copyBtn.disabled = false);
        }
    };
}
