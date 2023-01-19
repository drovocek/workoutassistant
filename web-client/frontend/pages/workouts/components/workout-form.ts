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
import {query} from "lit/decorators";
import {Button} from "@vaadin/button";
import {AppForm} from "Frontend/common/components/app-form";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";
import {Grid} from "@vaadin/grid";
import {TemplateResult} from "lit-html/development/lit-html";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";
import WorkoutModel from "Frontend/generated/ru/soft/common/to/WorkoutToModel";
import {workoutStore} from "Frontend/common/stores/app-store";

@customElement('plan-form')
export class WorkoutForm extends View implements AppForm<Workout> {

    @query('#grid')
    private grid!: Grid;

    @query('#saveBtn')
    private saveBtn!: Button;


    private planBinder = new Binder<Workout, WorkoutModel>(this, WorkoutModel);

    protected firstUpdated(_changedProperties: any) {
        super.firstUpdated(_changedProperties);

        const model = this.planBinder.model;

        this.planBinder.for(model.title).addValidator(
            new NotBlank({
                message: 'Enter a title'
            }));
    }

    private responsiveSteps: FormLayoutResponsiveStep[] = [
        {minWidth: 0, columns: 1},
        {minWidth: '320px', columns: 2},
        {minWidth: '500px', columns: 3},
    ];

    render() {
        return html`
            <div class="editor">

            </div>
        `;
    }

    private renderRoundSelector(hidden: boolean): TemplateResult<1> {
        return html`
            <div class="exercise-selector"
                 ?hidden="${hidden}">
                <vaadin-text-field
                        placeholder="Filter by name"
                    
                        @input=${this.updateFilter}
                        clear-button-visible>
                    <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                </vaadin-text-field>
            </div>
        `
    }

    private renderEditPlanForm(hidden: boolean): TemplateResult<1> {
        return html`
            <vaadin-form-layout
                    .responsiveSteps="${this.responsiveSteps}"
                    class="edit-round-form"
                    ?hidden="${hidden}">
                <vaadin-text-field
                        label="Title"
                        id="title"
                        ${field(this.planBinder.model.title)}></vaadin-text-field>
                <vaadin-text-area
                        label="Description"
                        id="description"
                        ${field(this.planBinder.model.note)}></vaadin-text-area>
            </vaadin-form-layout>
        `
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        // roundStore.updateFilter(e.target.value);
    }

    public open(entity: Workout): void {
        this.planBinder.read(entity);
        this.hidden = false;
        workoutStore.formOpened = true;
    }

    public close(): void {
        this.hidden = true;
        workoutStore.formOpened = false;
        // roundStore.updateFilter('');
    }

    public clear(): void {
        this.planBinder.clear();
        // roundStore.updateFilter('');
    }

    public visible(): boolean {
        return !this.hidden;
    }

    save(): void {

    }

    private saveRound(): void {
        this.saveBtn.disabled = true;
        if (this.planBinder.value.id) {
            this.planBinder.submitTo(workoutStore.update)
                .then(() => {
                    this.planBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.planBinder.submitTo(workoutStore.add)
                .then(() => {
                    this.planBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }
}