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
import './exercise-action-panel'
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../common/views/view';
import './exercise-form';
import {exerciseStore} from "Frontend/common/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {AppForm} from "Frontend/common/components/app-form";

@customElement('exercise-view')
export class ExerciseView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#exercise-form')
    private form!: AppForm<ExerciseTo>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (exerciseStore.formOpened) {
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
                    <exercise-action-panel targetFormId="exercise-form"
                                           class="action-panel"></exercise-action-panel>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${exerciseStore.filtered}
                            @active-item-changed=${this.handleGridSelection}>
                        <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="complexity" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </div>
                <exercise-form id="exercise-form" class="form"></exercise-form>
            </div>
        `;
    }

    private handleGridSelection(event: CustomEvent) {
        this.form.close();

        const item: ExerciseTo = event.detail.value as ExerciseTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        exerciseStore.setSelected(item);
    }
}
