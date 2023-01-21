import '@vaadin/grid';
import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {Grid} from "@vaadin/grid";
import {query} from "lit/decorators";
import {View} from "Frontend/view";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";

@customElement('workout-details')
export class WorkoutDetails extends View {

    @query('#grid')
    private grid!: Grid;

    items: WorkoutElement[] = []

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
                    rows-draggable
                    drop-mode="between">
                <vaadin-grid-column path="title" auto-width></vaadin-grid-column>
                <vaadin-grid-column path="note" auto-width></vaadin-grid-column>
            </vaadin-grid>
        `;
    }
}

