import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {View} from 'Frontend/common/views/view';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/form-layout';
import {Binder, field, NotBlank} from "@hilla/form";
import {roundStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";
import {query} from "lit/decorators";
import {Button} from "@vaadin/button";

@customElement('round-form')
export class RoundForm extends View {

    @query('#saveBtn')
    private saveBtn!: Button;

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
                message: 'Enter a title'
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
                <vaadin-button
                        id="saveBtn"
                        theme="primary"
                        @click=${this.save}>
                    ${this.binder.value.id ? 'Save' : 'Create'}
                </vaadin-button>
                <vaadin-button theme="tertiary"
                               @click=${roundStore.cancelEdit}>
                    Cancel
                </vaadin-button>
            </div>
        `;
    }

    save() {
        this.saveBtn.disabled = true;
        if (this.binder.value.id) {
            this.binder.submitTo(roundStore.update)
                .then(()=>{
                    this.binder.clear();
                    this.hidden = true;
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.binder.submitTo(roundStore.add)
                .then(()=>{
                    this.binder.clear();
                    this.hidden = true;
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }
}