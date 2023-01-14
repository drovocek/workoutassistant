import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import './components/plan-form';
import './components/plan-details';
import './components/plan-action-panel';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../../common/views/view';
import {planStore, roundStore} from "Frontend/common/stores/app-store";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {AppForm} from "Frontend/common/components/app-form";
import type {Grid, GridActiveItemChangedEvent} from '@vaadin/grid';
import WorkoutPlanTo from "Frontend/generated/ru/soft/common/to/WorkoutPlanTo";

@customElement('plan-view')
export class PlanView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#plan-form')
    private form!: AppForm<WorkoutPlanTo>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (roundStore.formOpened) {
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
                            .items=${roundStore.filtered}
                            .detailsOpenedItems="${roundStore.getSelectedItemsDetailAsArr()}"
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

    private switchDetailsVisible(plan: WorkoutPlanTo) {
        return () => {
            this.form.close();
            this.deselectAll();
            const isOpened = planStore.detailsItemIsOpened(plan);
            planStore.setSelectedDetailsItem(isOpened ? null : plan);
        }
    }

    private deselectAll() {
        this.grid.selectedItems = [];
        planStore.setSelected(null);
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutPlanTo>(
            () => html`
                <plan-details></plan-details>`
            ,
            []
        );
    }

    private renderDetailsButton() {
        return columnBodyRenderer<WorkoutPlanTo>(
            (plan) => {
                const isOpened = planStore.detailsItemIsOpened(plan);
                return html`
                    <vaadin-button
                            title="Rounds"
                            theme="tertiary contrast icon"
                            @click="${this.switchDetailsVisible(plan)}">
                        <vaadin-icon ?hidden=${isOpened} icon="vaadin:chevron-right-small"></vaadin-icon>
                        <vaadin-icon ?hidden=${!isOpened} icon="vaadin:chevron-down-small"></vaadin-icon>
                    </vaadin-button>
                    <span>${plan.title}</span>
                `
            });
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<WorkoutPlanTo>) {
        this.closeDetails();
        this.form.close();

        const item: WorkoutPlanTo = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        planStore.setSelected(item);
    }

    private closeDetails() {
        planStore.setSelectedDetailsItem(null);
    }
}
