import '@vaadin/grid';
import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {Grid} from "@vaadin/grid";
import {query} from "lit/decorators";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from "Frontend/common/views/view";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

@customElement('plan-details')
export class WorkoutDetails extends View {

    @query('#grid')
    private grid!: Grid;


    private firstSelectionEvent = true;


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
    }

    render() {
        return html`
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        style="height: 25em"
                        rows-draggable
                        drop-mode="between">
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="description" auto-width></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `;
    }

}
