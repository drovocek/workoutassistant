import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import './components/workout-form';
import './components/workout-details';
import './components/workout-action-panel';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../../common/views/view';
import {workoutStore} from "Frontend/common/stores/app-store";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {AppForm} from "Frontend/common/components/app-form";
import type {Grid, GridActiveItemChangedEvent} from '@vaadin/grid';
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

@customElement('workout-view')
export class WorkoutView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#plan-form')
    private form!: AppForm<Workout>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (workoutStore.formOpened) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <div class="content">
                <div class="filter-grid">
                    <plan-action-panel targetFormId="plan-form"
                                       class="action-panel"></plan-action-panel>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${workoutStore.filtered}
                            @active-item-changed=${this.handleGridSelection}
                            ${this.renderDetails()}>
                        <vaadin-grid-column ${this.renderDetailsButton()} header="Title"></vaadin-grid-column>
                        <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </div>
                <plan-form id="plan-form" class="form"></plan-form>
            </div>
        `;
    }

    private switchDetailsVisible(plan: Workout) {
        return () => {
            this.form.close();
            this.deselectAll();
            // const isOpened = workoutStore.detailsItemIsOpened(plan);
            // workoutStore.setSelectedDetailsItem(isOpened ? null : plan);
        }
    }

    private deselectAll() {
        this.grid.selectedItems = [];
        workoutStore.setSelected(null);
    }

    private renderDetails() {
        return gridRowDetailsRenderer<Workout>(
            () => html`
                <plan-details></plan-details>`
            ,
            []
        );
    }

    private renderDetailsButton() {
        return columnBodyRenderer<Workout>(
            (plan) => {
                // const isOpened = workoutStore.detailsItemIsOpened(plan);
                return html`
                    <vaadin-button
                            title="Rounds"
                            theme="tertiary contrast icon"
                            @click="${this.switchDetailsVisible(plan)}">
                    </vaadin-button>
                    <span>${plan.title}</span>
                `
            });
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<Workout>) {
        this.closeDetails();
        this.form.close();

        const item: Workout = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        workoutStore.setSelected(item);
    }

    private closeDetails() {
        // workoutStore.setSelectedDetailsItem(null);
    }
}
