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
import {appStore} from "Frontend/common/stores/app-store";

@customElement('round-exercise-selector')
export class RoundExerciseSelector extends LitElement {

    @state()
    private draggedExercise?: ExerciseTo;

    private clearDraggedExercise = () => {
        this.draggedExercise = undefined;
    };

    private storeDraggingExercise = (event: GridDragStartEvent<ExerciseTo>) => {
        this.draggedExercise = event.detail.draggedItems[0];
    };

    render() {
        return html`
            <div>
                <vaadin-grid
                        .items="${appStore.exerciseStore.filtered}"
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingExercise}"
                        @grid-dragend="${this.clearDraggedExercise}">
                    <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                </vaadin-grid>
            </div>
        `;
    }
}
