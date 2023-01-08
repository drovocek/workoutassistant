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
import '@vaadin/confirm-dialog'
import {html, LitElement} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {GridDragStartEvent} from "@vaadin/grid";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {exerciseSelectorStore} from "Frontend/common/stores/app-store";
import {View} from "Frontend/common/views/view";

@customElement('exercise-selector')
export class ExerciseSelector extends View {

    private clearDraggedExercise = () => {
        exerciseSelectorStore.setDraggedExercise(undefined);
    };

    private storeDraggingExercise = (event: GridDragStartEvent<ExerciseTo>) => {
        exerciseSelectorStore.setDraggedExercise(event.detail.draggedItems[0]);
    };

    render() {
        return html`
            <vaadin-text-field
                    style="width: 100%"
                    placeholder="Filter by name"
                    .value=${exerciseSelectorStore.filterText}
                    @input=${this.updateFilter}
                    clear-button-visible
            ></vaadin-text-field>
            <vaadin-grid
                    theme="hide-filter-header-row"
                    style="height: 100%"
                    .items="${exerciseSelectorStore.filtered}"
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${this.storeDraggingExercise}"
                    @grid-dragend="${this.clearDraggedExercise}">
                <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
            </vaadin-grid>
        `;
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        exerciseSelectorStore.updateFilter(e.target.value);
    }
}
