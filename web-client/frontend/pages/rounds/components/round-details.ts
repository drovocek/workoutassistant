import '@vaadin/grid';
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {Grid, GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import {query} from "lit/decorators";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from "Frontend/common/views/view";
import {roundStore} from "Frontend/common/stores/app-store";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";

@customElement('round-details')
export class RoundDetails extends View {

    @query('#grid')
    private grid!: Grid;

    @state()
    private draggedStation?: WorkoutStationSnapshot;

    private firstSelectionEvent = true;

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<WorkoutStationSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
    };

    private form!: AppForm<WorkoutRoundTo>;

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

    updated(_changedProperties: PropertyValues) {
        super.updated(_changedProperties);
        this.deselectAll();
    }

    private deselectAll() {
        this.grid.selectedItems = [];
        roundStore.setSelectedDetailsItemChild(null);
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
        if (!this.form) {
            this.form = document.querySelector('#round-form') as unknown as AppForm<WorkoutRoundTo>;
        }
        this.form.close();

        const item: WorkoutStationSnapshot = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelectedDetailsItemChild(item);
    }

    private renderExerciseTitle(station: WorkoutStationSnapshot) {
        return html`
            <span title="${station.exercise.description}">${station.exercise.title}</span>
        `
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutStationSnapshot>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                const draggedItemIndex = roundStore.selectedDetailsItemChildData.indexOf(this.draggedStation);
                roundStore.selectedDetailsItemChildData.splice(draggedItemIndex, 1);
                const dropIndex = roundStore.selectedDetailsItemChildData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                roundStore.selectedDetailsItemChildData.splice(dropIndex, 0, this.draggedStation);
                roundStore.selectedDetailsItemChildData = [...roundStore.selectedDetailsItemChildData];
            }
        }
    }
}
