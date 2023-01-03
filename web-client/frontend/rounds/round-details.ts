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
import {customElement, property, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";
import {gridRowDetailsRenderer} from "@vaadin/grid/lit";

@customElement('round-details')
export class RoundDetails extends LitElement {

    @property()
    private detailsData: Array<WorkoutStationSnapshot | undefined> = [];

    @state()
    private draggedStation?: WorkoutStationSnapshot;

    @state()
    private detailsOpenedItem: WorkoutStationSnapshot[] = [];

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<WorkoutStationSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
    };

    @state()
    public draggedExercise?: ExerciseTo;

    render() {
        return html`
            <vaadin-grid
                    .items=${this.detailsData}
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${this.storeDraggingStation}"
                    @grid-dragend="${this.clearDraggedStation}"
                    @grid-drop="${this.onGridDrop()}"
                    .detailsOpenedItems="${this.detailsOpenedItem}"
                    @active-item-changed="${this.updateOpened()}"
                    ${this.renderDetails()}>
                <vaadin-grid-sort-column path="exercise.title"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="repetitions"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="weight"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="duration"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="rest"
                                         auto-width></vaadin-grid-sort-column>
            </vaadin-grid>
        `;
    }

    private updateOpened() {
        return (e: GridActiveItemChangedEvent<WorkoutStationSnapshot>) => {
            this.detailsOpenedItem = [e.detail.value];
        };
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutStationSnapshot>(
            (station) => {
                return html`
                    <vaadin-form-layout .responsiveSteps="${[{minWidth: '0', columns: 10}]}">
                        <vaadin-text-field
                                label="Complexity"
                                .value="${station.exercise.complexity}"
                                colspan="1"
                                readonly
                        ></vaadin-text-field>
                        <vaadin-text-area
                                label="Description"
                                .value="${station.exercise.description}"
                                colspan="9"
                                readonly
                        ></vaadin-text-area>
                    </vaadin-form-layout>
                `
            },
            []
        );
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutStationSnapshot>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            console.log(event)
            if (this.draggedExercise !== undefined && this.isAnExercise(this.draggedExercise)) {
                console.log("1")
                const draggedItem = this.asDefaultStation(this.draggedExercise)
                const dropIndex = this.detailsData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.detailsData.splice(dropIndex, 0, draggedItem);
                this.detailsData = [...this.detailsData];
            } else if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                console.log("2")
                const draggedItemIndex = this.detailsData.indexOf(this.draggedStation);
                this.detailsData.splice(draggedItemIndex, 1);
                const dropIndex = this.detailsData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.detailsData.splice(dropIndex, 0, this.draggedStation);
                this.detailsData = [...this.detailsData];
            }
        }
    }

    private isAnExercise(obj: any) {
        return obj !== undefined && 'id' in obj && 'title' in obj && 'description' in obj && 'complexity' in obj;
    }

    private asDefaultStation(exercise: ExerciseTo): WorkoutStationSnapshot {
        const station = WorkoutStationSnapshotModel.createEmptyValue();
        const exerciseSnapshot = ExerciseSnapshotModel.createEmptyValue();
        exerciseSnapshot.title = exercise.title;
        exerciseSnapshot.description = exercise.description;
        exerciseSnapshot.complexity = exercise.complexity;
        station.exercise = exerciseSnapshot;
        return station;
    }
}
