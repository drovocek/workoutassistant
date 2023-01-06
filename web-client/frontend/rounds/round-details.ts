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
import '@vaadin/vertical-layout';
import {html, LitElement} from 'lit';
import {customElement, property, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {exerciseSelectorStore} from "Frontend/common/stores/app-store";
import {applyTheme} from "Frontend/generated/theme";

@customElement('round-details')
export class RoundDetails extends LitElement {

    @property()
    public stations: Array<WorkoutStationSnapshot> = [];

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

    protected createRenderRoot() {
        const root = super.createRenderRoot();
        applyTheme(root);
        return root;
    }

    render() {
        return html`
            <vaadin-grid
                    .items=${this.stations}
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${this.storeDraggingStation}"
                    @grid-dragend="${this.clearDraggedStation}"
                    @grid-drop="${this.onGridDrop()}"
                    .detailsOpenedItems="${this.detailsOpenedItem}"
                    @active-item-changed="${this.updateOpened()}"
                    ${this.renderDetails()}>
                <vaadin-grid-column
                        ${columnBodyRenderer<WorkoutStationSnapshot>(
                                (station) => this.renderStationBadge(station),
                                []
                        )}
                ></vaadin-grid-column>
            </vaadin-grid>
        `;
    }

    private renderStationBadge(station: WorkoutStationSnapshot) {
        let badgeThemes = "badge";
        const complexity = station.exercise.complexity;
        if (complexity < 3) {
            badgeThemes = badgeThemes.concat(" success");
        } else if (complexity > 4) {
            badgeThemes = badgeThemes.concat(" error");
        }

        return html`
            <vaadin-vertical-layout style="line-height: var(--lumo-line-height-m);">
                <span theme="badge contrast" title="${station.exercise.description}">${station.exercise.title}</span>
                <span theme=${badgeThemes} title="${station.exercise.description}">
                     <vaadin-icon title="Repetitions" icon="vaadin:rotate-right"
                                  style="padding: var(--lumo-space-xs)"></vaadin-icon>
                     <span title="Repetitions">${station.repetitions}</span>
                     <vaadin-icon title="Weight" icon="vaadin:compile"
                                  style="padding: var(--lumo-space-xs)"></vaadin-icon>
                     <span title="Weight">${station.weight}</span>
                     <vaadin-icon title="Duration" icon="vaadin:stopwatch"
                                  style="padding: var(--lumo-space-xs)"></vaadin-icon>
                     <span title="Duration">${station.duration}</span>
                     <vaadin-icon title="Rest" icon="vaadin:coffee" style="padding: var(--lumo-space-xs)"></vaadin-icon>
                     <span title="Rest">${station.rest}</span>
                </span>
            </vaadin-vertical-layout>
        `
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
            if (exerciseSelectorStore.draggedExercise) {
                const draggedItem = this.asDefaultStation(exerciseSelectorStore.draggedExercise)
                const dropIndex = this.stations.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.stations.splice(dropIndex, 0, draggedItem);
                this.stations = [...this.stations];
            } else if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                const draggedItemIndex = this.stations.indexOf(this.draggedStation);
                this.stations.splice(draggedItemIndex, 1);
                const dropIndex = this.stations.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.stations.splice(dropIndex, 0, this.draggedStation);
                this.stations = [...this.stations];
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
