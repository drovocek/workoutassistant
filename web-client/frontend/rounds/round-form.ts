import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {View} from 'Frontend/common/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/form-layout';
import {Binder, field, Max, Min, NotBlank} from "@hilla/form";
import {exerciseStore, roundStore, uiStore} from "Frontend/common/stores/app-store";
import {EndpointError} from "@hilla/frontend";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";

@customElement('round-form')
export class RoundForm extends View {

    private binder = new Binder<WorkoutRoundTo, WorkoutRoundToModel>(this, WorkoutRoundToModel);

    constructor() {
        super();
        this.autorun(() => {
            if (roundStore.selected) {
                this.binder.read(roundStore.selected);
            } else {
                this.binder.clear();
            }
        });
    }

    protected firstUpdated(_changedProperties: any) {
        super.firstUpdated(_changedProperties);

        const model = this.binder.model;

        this.binder.for(model.title).addValidator(
            new NotBlank({
                message: 'Please enter a title'
            }));
    }

    render() {
        const {model} = this.binder;
        return html`
            <div class="editor">
                <vaadin-form-layout>
                    <vaadin-text-field
                            label="Title"
                            id="title"
                            ${field(model.title)}></vaadin-text-field>
                    <vaadin-text-area
                            label="Description"
                            id="description"
                            ${field(model.description)}></vaadin-text-area>
                </vaadin-form-layout>
            </div>
            <div class="flex gap-s button-layout">
                <vaadin-button theme="primary"
                               @click=${this.save}>
                    ${this.binder.value.id ? 'Save' : 'Create'}
                </vaadin-button>
                <vaadin-button theme="tertiary"
                               @click=${roundStore.cancelEdit}>
                    Cancel
                </vaadin-button>
                <vaadin-button class="deleteBtn"
                               theme="error"
                               @click=${this.delete}
                               ?disabled=${!this.binder.value.id}>
                    Delete
                </vaadin-button>
            </div>
        `;
    }

    async save() {
        try {
            if (this.binder.value.id) {
                await this.binder.submitTo(roundStore.update);
            } else {
                await this.binder.submitTo(roundStore.add);
            }
            this.binder.clear();
        } catch (error: any) {
            if (error instanceof EndpointError) {
                uiStore.showError(`Server error. ${error.message}`);
            } else {
                throw error;
            }
        }
    }

    async delete() {
        try {
            await this.binder.submitTo(roundStore.delete);
            this.binder.clear();
        } catch (error: any) {
            if (error instanceof EndpointError) {
                uiStore.showError(`Server error. ${error.message}`);
            } else {
                throw error;
            }
        }
    }
}