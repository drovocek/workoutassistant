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
import {css, html, LitElement} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {columnBodyRenderer, GridColumnBodyLitRenderer} from "@vaadin/grid/lit";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {ConfirmDialogOpenedChangedEvent} from "@vaadin/confirm-dialog";
import {appStore} from "Frontend/common/stores/app-store";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import ExerciseSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshot";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";

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

    // static get styles() {
    //     return css`
    //       .grids-container {
    //         display: flex;
    //         flex-direction: row;
    //         flex-wrap: wrap;
    //       }
    //
    //       vaadin-grid {
    //         width: 300px;
    //         height: 300px;
    //         margin-left: 0.5rem;
    //         margin-top: 0.5rem;
    //         align-self: unset;
    //       }
    //     `;
    // }

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
