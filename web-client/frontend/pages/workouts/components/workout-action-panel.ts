import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import {workoutStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

@customElement('workout-action-panel')
export class WorkoutActionPanel extends AppActionPanel<Workout> {

    public getFilterValue(): string {
        return workoutStore.filterText;
    };

    public getSelected(): Workout | null {
        return workoutStore.selected;
    }

    public getNew(): Workout {
        return workoutStore.createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        workoutStore.updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return workoutStore.hasSelected();
    }

    public onDelete(): void {
        this.deleteBtn.disabled = true;
        workoutStore.delete()
            .catch((err) => {
                this.deleteBtn.disabled = false;
                processErr(err);
            });
    };

    public onCopy(): void {
        this.copyBtn.disabled = true;
        workoutStore.copy()
            .catch(processErr)
            .finally(() => this.copyBtn.disabled = false);
    };
}
