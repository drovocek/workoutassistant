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
import {exerciseStore, roundStore} from "Frontend/common/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from "Frontend/generated/ru/soft/common/to/ExerciseToModel";
import {AppForm} from "Frontend/common/components/app-form";
import {query} from "lit/decorators";
import {Button} from "@vaadin/button";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";

@customElement('exercise-form')
export class ExerciseForm extends View implements AppForm<ExerciseTo> {

    @query('#saveBtn')
    private saveBtn!: Button;

    private binder = new Binder<ExerciseTo, ExerciseToModel>(this, ExerciseToModel);

    constructor() {
        super();
        this.autorun(() => {
            if (exerciseStore.selected) {
                this.binder.read(exerciseStore.selected);
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
                <h3>${this.binder.value.id || roundStore.hasSelectedDetailsItemChild() ? 'Update' : 'Add'}</h3>
                <vaadin-form-layout
                        class="edit-round-form">
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
                <div class="button-layout">
                    <vaadin-button
                            id="saveBtn"
                            theme="primary"
                            @click=${this.save}>
                        ${this.binder.value.id ? 'Save' : 'Add'}
                    </vaadin-button>
                    <vaadin-button
                            theme="tertiary"
                            @click=${this.close}>
                        Cancel
                    </vaadin-button>
                </div>
            </div>
        `;
    }

    public open(entity: ExerciseTo): void {
        this.binder.read(entity);
        this.hidden = false;
        exerciseStore.formOpened = true;
    }

    public close(): void {
        this.hidden = true;
        exerciseStore.formOpened = false;
    }

    public clear(): void {
        this.binder.clear();
    }

    public visible(): boolean {
        return !this.hidden;
    }

    private save(): void {
        this.saveBtn.disabled = true;
        if (this.binder.value.id) {
            this.binder.submitTo(exerciseStore.update)
                .then(() => {
                    this.binder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.binder.submitTo(exerciseStore.add)
                .then(() => {
                    this.binder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }
}