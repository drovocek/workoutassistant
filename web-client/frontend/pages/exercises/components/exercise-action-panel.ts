import {customElement} from 'lit/decorators.js';
import {AppActionPanel} from "Frontend/common/components/action-panel/app-action-panel";
import {exerciseStore} from "Frontend/common/stores/app-store";
import {processErr} from "Frontend/common/utils/app-utils";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";

@customElement('exercise-action-panel')
export class ExerciseActionPanel extends AppActionPanel<ExerciseTo> {

    public getFilterValue(): string {
        return exerciseStore.filterText;
    };

    public getSelected(): ExerciseTo | null {
        return exerciseStore.selected;
    }

    public getNew(): ExerciseTo {
        return exerciseStore.createNew();
    }

    public updateFilterValue(e: { target: HTMLInputElement }): void {
        exerciseStore.updateFilter(e.target.value);
    }

    public hasSelected(): boolean {
        return exerciseStore.hasSelected();
    }

    public onDelete(): void {
        this.deleteBtn.disabled = true;
        exerciseStore.delete()
            .catch((err) => {
                this.deleteBtn.disabled = false;
                processErr(err);
            });
    };

    public onCopy(): void {
        this.copyBtn.disabled = true;
        exerciseStore.copy()
            .catch(processErr)
            .finally(() => this.copyBtn.disabled = false);
    };
}
