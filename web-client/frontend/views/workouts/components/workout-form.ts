import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {View} from 'Frontend/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/form-layout';
import {Binder, field, NotBlank} from "@hilla/form";
import {query} from "lit/decorators";
import {Button} from "@vaadin/button";
import {AppForm} from "Frontend/common/components/app-form";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import WorkoutModel from "Frontend/generated/ru/soft/common/to/WorkoutToModel";
import {workoutStore} from "Frontend/common/stores/app-store";

@customElement('workout-form')
export class WorkoutForm extends View implements AppForm<Workout> {

    @query('#saveBtn')
    private saveBtn!: Button;

    private binder = new Binder<Workout, WorkoutModel>(this, WorkoutModel);

    constructor() {
        super();
        this.autorun(() => {
            if (workoutStore.selected) {
                this.binder.read(workoutStore.selected);
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
                <h3>${this.binder.value.id || workoutStore.hasSelected() ? 'Update' : 'Add'}</h3>
                <vaadin-form-layout
                        class="edit-round-form">
                    <vaadin-text-field
                            label="Title"
                            id="title"
                            ${field(model.title)}></vaadin-text-field>
                    <vaadin-text-area
                            label="Description"
                            id="description"
                            ${field(model.note)}></vaadin-text-area>
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

    public open(entity: Workout): void {
        this.binder.read(entity);
        this.hidden = false;
        workoutStore.formVisible = true;
    }

    public close(): void {
        this.hidden = true;
        workoutStore.formVisible = false;
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
            this.binder.submitTo(workoutStore.update)
                .then(() => {
                    this.binder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.binder.submitTo(workoutStore.add)
                .then(() => {
                    this.binder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }
}