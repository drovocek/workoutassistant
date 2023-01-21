import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import './components/workout-form';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../../view';
import {uiStore, workoutStore} from "Frontend/common/stores/app-store";
import {AppForm} from "Frontend/common/components/app-form";
import type {Grid} from '@vaadin/grid';
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import {renderTitleWithActionBar} from "Frontend/common/utils/component-factory";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";

@customElement('workout-view')
export class WorkoutView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#workout-form')
    private form!: AppForm<Workout>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (workoutStore.formVisible) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <vaadin-button
                    id="plus-button"
                    class="plus-button"
                    theme="primary error"
                    @click=${this.openAddForm}
                    ?hidden="${workoutStore.formVisible}">
                <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
                Create exercise
            </vaadin-button>
            <vaadin-horizontal-layout class="content">
                <vaadin-vertical-layout class="grid-wrapper">
                    <vaadin-text-field
                            class="filter"
                            .value=${workoutStore.filterText}
                            @input=${workoutStore.updateFilterByEvent}
                            clear-button-visible>
                        <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                        <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                    </vaadin-text-field>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${workoutStore.filtered}
                            @active-item-changed=${this.handleGridSelection}>
                        <vaadin-grid-sort-column path="title" header="Title" auto-width
                                                 ${renderTitleWithActionBar(this.processClick())}></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="note" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </vaadin-vertical-layout>
                <workout-form id="workout-form" class="form" ?hidden="${!workoutStore.formVisible}"></workout-form>
            </vaadin-horizontal-layout>
            <vaadin-notification
                    theme=${uiStore.message.error ? 'error' : 'success'}
                    position="bottom-start"
                    .opened=${uiStore.message.open}
                    .renderer=${(root: HTMLElement) =>
                            (root.textContent = uiStore.message.text)}>
        `;
    }

    private openAddForm() {
        workoutStore.setSelected(null);
        workoutStore.formVisible = true;
        this.grid.selectedItems = [];
    }

    private handleGridSelection(event: CustomEvent) {
        this.form.close();

        const item: Workout = event.detail.value as Workout;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        workoutStore.setSelected(item)
        workoutStore.formVisible = item !== null;
    }

    private processClick() {
        return (e: MenuBarItemSelectedEvent, workout: Workout) => {
            let command = e.detail.value.text;
            let id = workout.id;
            if (command === 'Delete' && id) {
                workoutStore.delete(id);
            } else if (command === 'Copy') {
                workoutStore.copy(workout);
            }
        }
    }
}
