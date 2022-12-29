import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {View} from 'Frontend/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/form-layout';
import {Binder, field, Max, Min, NotBlank} from "@hilla/form";
import {exerciseViewStore} from "Frontend/stores/exercise-view-store";
import {uiStore} from "Frontend/stores/app-store";
import {EndpointError} from "@hilla/frontend";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";

@customElement('exercise-form')
export class ExerciseForm extends View {

    private binder = new Binder<ExerciseTo, ExerciseToModel>(this, ExerciseToModel);

    constructor() {
        super();
        this.autorun(() => {
            if (exerciseViewStore.selectedExercise) {
                this.binder.read(exerciseViewStore.selectedExercise);
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

        this.binder.for(model.complexity).addValidator(
            new Min({
                message: 'Not less then 1',
                value: 1
            }));

        this.binder.for(model.complexity).addValidator(
            new Max({
                message: 'Not more then 10',
                value: 10
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
                    <vaadin-integer-field
                            label="Complexity"
                            id="complexity"
                            step-buttons-visible
                            min="1"
                            max="10"
                            ${field(model.complexity)}></vaadin-integer-field>
                </vaadin-form-layout>
            </div>
            <div class="flex gap-s button-layout">
                <vaadin-button theme="primary" 
                               @click=${this.save}>
                    ${this.binder.value.id ? 'Save' : 'Create'}
                </vaadin-button>
                <vaadin-button theme="tertiary" 
                               @click=${exerciseViewStore.cancelEdit}>
                    Cancel
                </vaadin-button>
                <vaadin-button class="deleteBtn"
                               theme="error" 
                               @click=${exerciseViewStore.delete} 
                               ?disabled=${!this.binder.value.id}>
                    Delete
                </vaadin-button>
            </div>
        `;
    }

    async save() {
        try {
            await this.binder.submitTo(exerciseViewStore.save);
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