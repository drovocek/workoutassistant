import '@vaadin/grid';
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {Grid, GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import {query} from "lit/decorators";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from "Frontend/common/views/view";
import {workoutStore} from "Frontend/common/stores/app-store";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import RoundSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/RoundSnapshot";

@customElement('plan-details')
export class WorkoutDetails extends View {

    @query('#grid')
    private grid!: Grid;

    @state()
    private draggedStation?: RoundSnapshot;

    private firstSelectionEvent = true;

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<RoundSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
    };

    private form!: AppForm<Workout>;

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
        workoutStore.setSelectedDetailsItemChild(null);
    }

    render() {
        return html`
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        style="height: 25em"
                        .items=${workoutStore.filteredDetailsItemChild}
                        @active-item-changed="${this.handleGridSelection}"
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingStation}"
                        @grid-dragend="${this.clearDraggedStation}"
                        @grid-drop="${this.onGridDrop()}">
                    <vaadin-grid-column header="Title"
                                        ${columnBodyRenderer<RoundSnapshot>(
                                                (station) => this.renderExerciseTitle(station),
                                                []
                                        )}
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="description" auto-width></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `;
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<RoundSnapshot>) {
        if (!this.form) {
            this.form = document.querySelector('#plan-form') as unknown as AppForm<Workout>;
        }
        this.form.close();

        const item: RoundSnapshot = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        workoutStore.setSelectedDetailsItemChild(item);
    }

    private renderExerciseTitle(station: RoundSnapshot) {
        console.log(station)
        return html`
            <span title="${station.description}">${station.title}</span>
        `
    }

    private onGridDrop() {
        return (event: GridDropEvent<RoundSnapshot>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                const draggedItemIndex = workoutStore.selectedDetailsItemChildData.indexOf(this.draggedStation);
                workoutStore.selectedDetailsItemChildData.splice(draggedItemIndex, 1);
                const dropIndex = workoutStore.selectedDetailsItemChildData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                workoutStore.selectedDetailsItemChildData.splice(dropIndex, 0, this.draggedStation);
                workoutStore.selectedDetailsItemChildData = [...workoutStore.selectedDetailsItemChildData];
            }
        }
    }
}
