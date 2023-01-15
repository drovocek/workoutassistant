import {css, html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {View} from 'Frontend/common/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/horizontal-layout';
import {AppForm} from "Frontend/common/components/app-form";
import {Program} from "Frontend/pages/programs/program-view";
import {programStore} from "Frontend/common/stores/app-store";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {createItem, createWorkoutChildItems} from "Frontend/pages/programs/utils/program-menu-bar-utils";

@customElement('program-form')
export class ProgramForm extends View implements AppForm<Program> {

    @state()
    private items = [
        {
            component: createItem('plus', 'Add workout'),
            children: createWorkoutChildItems(),
        }
    ];

    render() {
        return html`
            <vaadin-grid
                    id="grid"
                    theme="no-border"
                    .items=${programStore.workouts}
                    .detailsOpenedItems="${workoutStore.getSelectedItemsDetailAsArr()}"
                    ${this.renderDetails()}>
                <vaadin-grid-column ${this.renderDetailsButton()} header="Title"></vaadin-grid-column>
                <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
            </vaadin-grid>
            <vaadin-horizontal-layout
                    class="form-button-panel"
                    theme="spacing padding">
                <vaadin-button
                        id="save-button"
                        class="save-button"
                        theme="primary"
                        @click=${this.save}>Save
                </vaadin-button>
                <vaadin-button
                        id="cancel-button"
                        class="cancel-button"
                        theme="tertiary"
                        @click=${this.close}>Cancel
                </vaadin-button>
                <vaadin-menu-bar
                        theme="primary error"
                        .items="${this.items}"
                        @item-selected="${this.addWorkout}"
                ></vaadin-menu-bar>
            </vaadin-horizontal-layout>
        `;
    }

    visible(): boolean {
        return programStore.formVisible;
    }

    clear(): void {
    }

    close(): void {
        programStore.formVisible = false;
    }

    open(value: Program): void {
        programStore.formVisible = true;
    }

    private save() {
        console.log("save")
        this.clear();
        this.close();
    }

    private addWorkout(e: MenuBarItemSelectedEvent) {
        console.log(e.detail.value);
    }
}