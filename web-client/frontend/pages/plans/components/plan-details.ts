import '@vaadin/grid';
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {Grid, GridActiveItemChangedEvent, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import {query} from "lit/decorators";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from "Frontend/common/views/view";
import {planStore} from "Frontend/common/stores/app-store";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import WorkoutPlanTo from "Frontend/generated/ru/soft/common/to/WorkoutPlanTo";
import WorkoutRoundSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutRoundSnapshot";

@customElement('plan-details')
export class PlanDetails extends View {

    @query('#grid')
    private grid!: Grid;

    @state()
    private draggedStation?: WorkoutRoundSnapshot;

    private firstSelectionEvent = true;

    private clearDraggedStation = () => {
        delete this.draggedStation;
    };

    private storeDraggingStation = (event: GridDragStartEvent<WorkoutRoundSnapshot>) => {
        this.draggedStation = event.detail.draggedItems[0];
    };

    private form!: AppForm<WorkoutPlanTo>;

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
        planStore.setSelectedDetailsItemChild(null);
    }

    render() {
        return html`
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        style="height: 25em"
                        .items=${planStore.filteredDetailsItemChild}
                        @active-item-changed="${this.handleGridSelection}"
                        rows-draggable
                        drop-mode="between"
                        @grid-dragstart="${this.storeDraggingStation}"
                        @grid-dragend="${this.clearDraggedStation}"
                        @grid-drop="${this.onGridDrop()}">
                    <vaadin-grid-column header="Title"
                                        ${columnBodyRenderer<WorkoutRoundSnapshot>(
                                                (station) => this.renderExerciseTitle(station),
                                                []
                                        )}
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="description" auto-width></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `;
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<WorkoutRoundSnapshot>) {
        if (!this.form) {
            this.form = document.querySelector('#plan-form') as unknown as AppForm<WorkoutPlanTo>;
        }
        this.form.close();

        const item: WorkoutRoundSnapshot = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        planStore.setSelectedDetailsItemChild(item);
    }

    private renderExerciseTitle(station: WorkoutRoundSnapshot) {
        console.log(station)
        return html`
            <span title="${station.description}">${station.title}</span>
        `
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutRoundSnapshot>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedStation && dropTargetItem !== this.draggedStation) {
                const draggedItemIndex = planStore.selectedDetailsItemChildData.indexOf(this.draggedStation);
                planStore.selectedDetailsItemChildData.splice(draggedItemIndex, 1);
                const dropIndex = planStore.selectedDetailsItemChildData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                planStore.selectedDetailsItemChildData.splice(dropIndex, 0, this.draggedStation);
                planStore.selectedDetailsItemChildData = [...planStore.selectedDetailsItemChildData];
            }
        }
    }
}
