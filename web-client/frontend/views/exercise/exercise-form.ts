import { html } from 'lit';
import { customElement } from 'lit/decorators.js';
import { View } from 'Frontend/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import {Binder, field} from "@hilla/form";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import {Notification} from "@vaadin/notification";
import {EndpointError} from "@hilla/frontend";

@customElement('exercise-form')
export class ExerciseForm extends View {

    private binder = new Binder<ExerciseTo, ExerciseToModel>(this, ExerciseToModel);

    render() {
        return html`
            <div class="editor-layout">
                    <div class="editor">
                        <vaadin-form-layout>
                            <vaadin-text-field
                                    label="Title"
                                    required
                                    id="title"
                                    ${field(this.binder.model.title)}></vaadin-text-field>
                            <vaadin-text-area
                                    label="Description"
                                    id="description"
                                    ${field(this.binder.model.description)}></vaadin-text-area>
                            <vaadin-integer-field
                                    label="Complexity"
                                    id="complexity"
                                    required
                                    min="1"
                                    max="10"
                                    step-buttons-visible
                                    ${field(this.binder.model.complexity)}></vaadin-integer-field>
                        </vaadin-form-layout>
                    </div>
                    <vaadin-horizontal-layout class="button-layout">
                        <vaadin-button theme="primary" @click=${this.save}>Save</vaadin-button>
                        <vaadin-button theme="tertiary" @click=${this.cancel}>Cancel</vaadin-button>
                        <vaadin-button ?disabled=${this.isDisableDeleteBtn} theme="error" @click=${this.delete}>Delete
                        </vaadin-button>
                    </vaadin-horizontal-layout>
                </div>
        `;
    }

    private async save() {
        try {
            const isNew = !this.binder.value.id;
            if (isNew) {
                // We added a new item
                await this.binder.submitTo(ExerciseEndpoint.add);
                this.gridSize++;
            } else {
                await this.binder.submitTo(ExerciseEndpoint.update);
            }
            this.clearForm();
            this.refreshGrid();
            Notification.show(`ExerciseTo details stored.`, {position: 'bottom-start'});
        } catch (error: any) {
            if (error instanceof EndpointError) {
                Notification.show(`Server error. ${error.message}`, {theme: 'error', position: 'bottom-start'});
            } else {
                throw error;
            }
        }
    }

    private async delete() {
        try {
            const deletedId = this.binder.value.id;
            if (deletedId !== undefined) {
                await ExerciseEndpoint.delete(deletedId);
                this.gridSize--;
                this.clearForm();
                this.refreshGrid();
                Notification.show(`Delete ExerciseTo with id = ${deletedId}.`, {position: 'bottom-start'});
            }
        } catch (error: any) {
            if (error instanceof EndpointError) {
                Notification.show(`Server error. ${error.message}`, {theme: 'error', position: 'bottom-start'});
            } else {
                throw error;
            }
        }
    }

    private cancel() {
        this.grid.activeItem = undefined;
    }
}