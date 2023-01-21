import '@vaadin/grid';
import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {Grid, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {property, query, state} from "lit/decorators";
import {View} from "Frontend/view";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import {workoutStore} from "Frontend/common/stores/app-store";

@customElement('workout-details')
export class WorkoutDetails extends View {

    @query('#grid')
    private grid!: Grid;

    private firstSelectionEvent = true;

    @property()
    items: WorkoutElement[] = []

    @state()
    private draggedElement?: WorkoutElement;

    private clearDraggedElement = () => {
        delete this.draggedElement;
    };

    private storeDraggingElement = (event: GridDragStartEvent<WorkoutElement>) => {
        this.draggedElement = event.detail.draggedItems[0];
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

    updated(_changedProperties: PropertyValues) {
        super.updated(_changedProperties);
        this.deselectAll();
    }

    private deselectAll() {
        this.grid.selectedItems = [];
    }

    render() {
        return html`
            <vaadin-grid
                    id="grid"
                    .items=${this.items}
                    @active-item-changed=${this.handleGridSelection}
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${this.storeDraggingElement}"
                    @grid-dragend="${this.clearDraggedElement}"
                    @grid-drop="${this.onGridDrop()}">
                <vaadin-grid-column path="title" auto-width></vaadin-grid-column>
                <vaadin-grid-column path="note" auto-width></vaadin-grid-column>
            </vaadin-grid>
        `;
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutElement>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedElement && dropTargetItem !== this.draggedElement) {
                const draggedItemIndex = this.items.indexOf(this.draggedElement);
                this.items.splice(draggedItemIndex, 1);
                const dropIndex = this.items.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.items.splice(dropIndex, 0, this.draggedElement);
                this.items = [...this.items];
            }
        }
    }

    private handleGridSelection(event: CustomEvent) {
        const item: WorkoutElement = event.detail.value as WorkoutElement;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }
    }
}

