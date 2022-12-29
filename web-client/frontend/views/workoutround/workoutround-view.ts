import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import type {Grid} from '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import '@vaadin/split-layout';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/upload'
import {html} from 'lit';
import {customElement, property, query} from 'lit/decorators.js';
import {View} from '../view';
import {exerciseViewStore} from "Frontend/stores/exercise-view-store";
import {uiStore} from "Frontend/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";

@customElement('workoutround-view')
export class WorkoutRoundView extends View {

    @query('#grid')
    private grid!: Grid;

    @property({type: Number})
    private gridSize = 0;

    private firstSelectionEvent = true;

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
        this.autorun(() => {
            if (exerciseViewStore.selectedExercise) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <div class="toolbar flex gap-s">
                <vaadin-text-field
                        placeholder="Filter by name"
                        .value=${exerciseViewStore.filterText}
                        @input=${this.updateFilter}
                        clear-button-visible
                ></vaadin-text-field>
                <vaadin-button @click=${this.clearForm}>Add round</vaadin-button>
            </div>
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        theme="no-border"
                        .size=${this.gridSize}
                        .items=${exerciseViewStore.filteredExercises}
                        @active-item-changed=${this.handleGridSelection}>
                    <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="complexity" auto-width></vaadin-grid-sort-column>
                </vaadin-grid>
            </div>
            <vaadin-notification
                    theme=${uiStore.message.error ? 'error' : 'success'}
                    position="bottom-start"
                    .opened=${uiStore.message.open}
                    .renderer=${(root: HTMLElement) =>
                            (root.textContent = uiStore.message.text)}>
            </vaadin-notification>
        `;
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        exerciseViewStore.updateFilter(e.target.value);
    }

    private handleGridSelection(event: CustomEvent) {
        const item: ExerciseTo = event.detail.value as ExerciseTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        exerciseViewStore.setSelectedExercise(item);
    }

    private clearForm() {
        exerciseViewStore.editNew();
    }
}
