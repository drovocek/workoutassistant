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
import {appStore, exerciseStore} from "Frontend/common/stores/app-store";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";

@customElement('round-details')
export class RoundDetails extends LitElement {

    @state()
    public detailsData: Array<WorkoutStationSnapshot | undefined> = [];

    @state()
    private dialogOpened: boolean = false;

    private selected: ExerciseTo = ExerciseToModel.createEmptyValue();

    @state()
    private draggedStation?: WorkoutStationSnapshot;

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<WorkoutStationSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
    };

    @state()
    private draggedExercise?: ExerciseTo;

    private clearDraggedExercise = () => {
        this.draggedExercise = undefined;
    };

    private storeDraggingExercise = (event: GridDragStartEvent<ExerciseTo>) => {
        this.draggedExercise = event.detail.draggedItems[0];
    };

    async connectedCallback() {
        super.connectedCallback();
        this.classList.add(
            'box-border',
            'flex',
            'flex-col',
            'p-m',
            'gap-s',
            'w-full',
            'h-full'
        );
    }

    static get styles() {
        return css`
          .grids-container {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
          }

          vaadin-grid {
            width: 300px;
            height: 300px;
            margin-left: 0.5rem;
            margin-top: 0.5rem;
            align-self: unset;
          }
        `;
    }

    render() {
        return html`
            <div class="grids-container">
                <vaadin-grid
                        .items=${this.detailsData}
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingStation}"
                        @grid-dragend="${this.clearDraggedStation}"
                        @grid-drop="${(event: GridDropEvent<WorkoutStationSnapshot>) => {
                            const {dropTargetItem, dropLocation} = event.detail;
                            if (this.draggedExercise !== undefined && this.isAnExercise(this.draggedExercise)) {
                                const draggedItem = this.asDefaultStation(this.draggedExercise)
                                const dropIndex = this.detailsData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                                this.detailsData.splice(dropIndex, 0, draggedItem);
                                this.detailsData = [...this.detailsData];
                            } else if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                                const draggedItemIndex = this.detailsData.indexOf(this.draggedStation);
                                this.detailsData.splice(draggedItemIndex, 1);
                                const dropIndex = this.detailsData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                                this.detailsData.splice(dropIndex, 0, this.draggedStation);
                                this.detailsData = [...this.detailsData];
                            }
                        }}"
                >
                    <vaadin-grid-sort-column path="exercise.title"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="exercise.description"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="exercise.complexity"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="repetitions"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="weight"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="duration"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="rest"
                                             auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-column
                            header="Manage"
                            ${columnBodyRenderer(this.removeBtnRenderer, [])}
                    ></vaadin-grid-column>
                </vaadin-grid>
                <vaadin-grid
                        .items="${appStore.exerciseStore.filtered}"
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingExercise}"
                        @grid-dragend="${this.clearDraggedExercise}">
                    <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                </vaadin-grid>
            </div>
            <vaadin-confirm-dialog
                    header='Delete station for "${this.selected?.title}"?'
                    cancel
                    @cancel="${() => this.dialogOpened = false}"
                    confirm-text="Delete"
                    confirm-theme="error primary"
                    @confirm="${this.removeSelected}"
                    .opened="${this.dialogOpened}"
                    @opened-changed="${this.openedChanged}"
            >
                Are you sure you want to permanently delete this item?
            </vaadin-confirm-dialog>
        `;
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

    private openedChanged(e: ConfirmDialogOpenedChangedEvent) {
        this.dialogOpened = e.detail.value;
    }

    private removeSelected() {
        this.detailsData = this.detailsData
            .filter((station) => {
                return station?.exercise.title !== this.selected?.title;
            });
    };

    private removeBtnRenderer: GridColumnBodyLitRenderer<WorkoutStationSnapshot> = ({exercise}) => html`
        <vaadin-button
                theme="error tertiary icon"
                @click="${() => {
                    this.selected = exercise;
                    this.dialogOpened = true;
                }}"
        >
            <vaadin-icon icon="vaadin:trash"></vaadin-icon>
        </vaadin-button>
    `;

    public actualWorkoutStationSnapshots() {
        return this.detailsData;
    }
}
