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
import {View} from '../common/views/view';
import './exercise-form';
import {exerciseStore, uiStore} from "Frontend/common/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";

@customElement('exercise-view')
export class ExerciseView extends View {

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
        this.classList.add("editing");
        // this.autorun(() => {
        //     if (exerciseStore.selected) {
        //         this.classList.add("editing");
        //     } else {
        //         this.classList.remove("editing");
        //     }
        // });
    }

    render() {
        return html`
            <div class="toolbar flex gap-s">
                <vaadin-text-field
                        placeholder="Filter by name"
                        .value=${exerciseStore.filterText}
                        @input=${this.updateFilter}
                        clear-button-visible
                ></vaadin-text-field>
                <vaadin-button @click=${this.clearForm}>Add exercise</vaadin-button>
            </div>
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        theme="no-border"
                        .size=${this.gridSize}
                        .items=${exerciseStore.filtered}
                        @active-item-changed=${this.handleGridSelection}>
                    <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="complexity" auto-width></vaadin-grid-sort-column>
                </vaadin-grid>
                <exercise-form class="flex flex-col gap-s"
                               ?hidden=${!exerciseStore.selected}></exercise-form>
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
        exerciseStore.updateFilter(e.target.value);
    }

    private handleGridSelection(event: CustomEvent) {
        const item: ExerciseTo = event.detail.value as ExerciseTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        exerciseStore.setSelected(item);
    }

    private clearForm() {
        exerciseStore.editNew();
    }
}
