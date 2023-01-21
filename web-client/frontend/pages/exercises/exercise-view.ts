import '@vaadin/grid';
import type {Grid} from '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/overlay'
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import './components/exercise-form';
import {exerciseStore, uiStore} from "Frontend/common/stores/app-store";
import Exercise from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {AppForm} from "Frontend/common/components/app-form";
import {View} from 'Frontend/common/views/view';
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {renderTitleWithActionBar} from "Frontend/pages/exercises/utils/exercise-component-factory";

@customElement('exercise-view')
export class ExerciseView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#exercise-form')
    private form!: AppForm<Exercise>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (exerciseStore.formVisible) {
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
                    ?hidden="${exerciseStore.formVisible}">
                <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
                Create exercise
            </vaadin-button>
            <vaadin-horizontal-layout class="content">
                <vaadin-vertical-layout class="grid-wrapper">
                    <vaadin-text-field
                            class="filter"
                            .value=${exerciseStore.filterText}
                            @input=${exerciseStore.updateFilterByEvent}
                            clear-button-visible>
                        <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                        <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                    </vaadin-text-field>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${exerciseStore.filtered}
                            @active-item-changed=${this.handleGridSelection}>
                        <vaadin-grid-sort-column path="title" header="Title" auto-width
                                                 ${renderTitleWithActionBar(this.processExerciseClick())}></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="note" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </vaadin-vertical-layout>
                <exercise-form id="exercise-form" class="form" ?hidden="${!exerciseStore.formVisible}"></exercise-form>
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
        exerciseStore.formVisible = true;
    }

    private handleGridSelection(event: CustomEvent) {
        this.form.close();

        const item: Exercise = event.detail.value as Exercise;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        exerciseStore.setSelected(item);
    }

    private processExerciseClick() {
        return (e: MenuBarItemSelectedEvent, exercise: Exercise) => {
            let command = e.detail.value.text;
            let id = exercise.id;
            if (command === 'Delete' && id) {
                exerciseStore.delete(id);
            } else if (command === 'Copy') {
                exerciseStore.copy(exercise);
            }
        }
    }
}
