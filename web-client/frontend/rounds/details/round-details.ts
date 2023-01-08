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
import '../../common/components/action-panel/app-action-panel'
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {Grid, GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {query} from "lit/decorators";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from "Frontend/common/views/view";
import {exerciseSelectorStore, roundStore} from "Frontend/common/stores/app-store";

@customElement('round-details')
export class RoundDetails extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#round-form')
    private form!: AppForm<WorkoutRoundTo>;

    @state()
    private draggedStation?: WorkoutStationSnapshot;

    @state()
    public draggedExercise?: ExerciseTo;

    private firstSelectionEvent = true;

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<WorkoutStationSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
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

    render() {
        return html`
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        style="height: 25em"
                        .items=${roundStore.filteredDetailsItemChild}
                        @active-item-changed="${this.handleGridSelection}"
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingStation}"
                        @grid-dragend="${this.clearDraggedStation}"
                        @grid-drop="${this.onGridDrop()}">
                    <vaadin-grid-column header="Title"
                                        ${columnBodyRenderer<WorkoutStationSnapshot>(
                                                (station) => this.renderExerciseTitle(station),
                                                []
                                        )}
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="repetitions" auto-width></vaadin-grid-column>
                    <vaadin-grid-column path="weight" auto-width></vaadin-grid-column>
                    <vaadin-grid-column path="duration" auto-width></vaadin-grid-column>
                    <vaadin-grid-column path="rest" auto-width></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `;
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<WorkoutStationSnapshot>) {
        // this.form.close();

        const item: WorkoutStationSnapshot = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelectedDetailsItemChild(item);
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

    private renderExerciseTitle(station: WorkoutStationSnapshot) {
        return html`
            <span title="${station.exercise.description}">${station.exercise.title}</span>
        `
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutStationSnapshot>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (exerciseSelectorStore.draggedExercise) {
                const draggedItem = this.asDefaultStation(exerciseSelectorStore.draggedExercise)
                const dropIndex = roundStore.selectedDetailsItemChildData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                roundStore.selectedDetailsItemChildData.splice(dropIndex, 0, draggedItem);
                roundStore.selectedDetailsItemChildData = [...roundStore.selectedDetailsItemChildData];
            } else if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                const draggedItemIndex = roundStore.selectedDetailsItemChildData.indexOf(this.draggedStation);
                roundStore.selectedDetailsItemChildData.splice(draggedItemIndex, 1);
                const dropIndex = roundStore.selectedDetailsItemChildData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                roundStore.selectedDetailsItemChildData.splice(dropIndex, 0, this.draggedStation);
                roundStore.selectedDetailsItemChildData = [...roundStore.selectedDetailsItemChildData];
            }
        }
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
