import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {View} from 'Frontend/common/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/form-layout';
import {AppForm} from "Frontend/common/components/app-form";
import {Program} from "Frontend/pages/programs/program-view";
import {programStore, uiStore} from "Frontend/common/stores/app-store";

@customElement('program-form')
export class ProgramForm extends View implements AppForm<Program> {

    render() {
        return html`
            <vaadin-form-layout
                    class="edit-round-form">
                <vaadin-text-field
                        label="Title"
                        id="title"></vaadin-text-field>
                <vaadin-text-area
                        label="Description"
                        id="description"></vaadin-text-area>
                <vaadin-integer-field
                        label="Complexity"
                        id="complexity"
                        step-buttons-visible
                        min="1"
                        max="5"></vaadin-integer-field>
            </vaadin-form-layout>
            <div class="form-button-panel">
                <vaadin-button
                        id="saveBtn"
                        theme="primary"
                        @click=${this.save}>Save
                </vaadin-button>
                <vaadin-button
                        theme="tertiary"
                        @click=${this.close}>Cancel
                </vaadin-button>
            </div>
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
}