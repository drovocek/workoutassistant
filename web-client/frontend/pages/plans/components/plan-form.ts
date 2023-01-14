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
import {columnBodyRenderer} from "@vaadin/grid/lit";
import {Checkbox} from "@vaadin/checkbox";
import {TemplateResult} from "lit-html/development/lit-html";
import WorkoutPlanTo from "Frontend/generated/ru/soft/common/to/WorkoutPlanTo";
import WorkoutPlanToModel from "Frontend/generated/ru/soft/common/to/WorkoutPlanToModel";
import {planStore, roundStore} from "Frontend/common/stores/app-store";
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";

@customElement('plan-form')
export class PlanForm extends View implements AppForm<WorkoutPlanTo> {

    @query('#grid')
    private grid!: Grid;

    @query('#saveBtn')
    private saveBtn!: Button;

    private checked: Map<Round, Checkbox> = new Map<Round, Checkbox>();

    private planBinder = new Binder<WorkoutPlanTo, WorkoutPlanToModel>(this, WorkoutPlanToModel);

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
                <h3>${this.planBinder.value.id || planStore.hasSelectedDetailsItemChild() ? 'Update' : 'Add'}</h3>
                ${this.renderRoundSelector(!planStore.hasSelectedDetailsItem() || planStore.hasSelectedDetailsItemChild())}
                ${this.renderEditPlanForm(planStore.hasSelectedDetailsItem())}
                <div class="button-layout">
                    <vaadin-button
                            id="saveBtn"
                            theme="primary"
                            @click=${this.save}>
                        ${this.planBinder.value.id || planStore.hasSelectedDetailsItemChild() ? 'Save' : 'Add'}
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

    private renderRoundSelector(hidden: boolean): TemplateResult<1> {
        return html`
            <div class="exercise-selector"
                 ?hidden="${hidden}">
                <vaadin-text-field
                        placeholder="Filter by name"
                        .value=${roundStore.filterText}
                        @input=${this.updateFilter}
                        clear-button-visible>
                    <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                </vaadin-text-field>
                <vaadin-grid
                        id="grid"
                        theme="no-border hide-filter-header-row"
                        .items=${roundStore.filtered}>
                    <vaadin-grid-column header="Title" ${(this.renderRoundBadge())}></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `
    }

    private renderRoundBadge() {
        return columnBodyRenderer<Round>(
            (round) => {
                let badgeThemes = "badge";
                // const complexity = round.complexity;
                // if (complexity < 3) {
                //     badgeThemes = badgeThemes.concat(" success");
                // } else if (complexity > 4) {
                //     badgeThemes = badgeThemes.concat(" error");
                // }
                return html`
                    <span>
                        <vaadin-checkbox @change="${(e: Event) => {
                            let checked = (e.target as HTMLInputElement).checked;
                            if (checked) {
                                this.checked.set(round, e.currentTarget as Checkbox);
                            } else {
                                this.checked.delete(round);
                            }
                        }}"></vaadin-checkbox>
                        <span theme=${badgeThemes} title="${round.description}">${round.title}</span>
                     </span>
                `
            },
            []);
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
                        ${field(this.planBinder.model.description)}></vaadin-text-area>
            </vaadin-form-layout>
        `
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        roundStore.updateFilter(e.target.value);
        this.clearChecked();
    }

    public open(entity: WorkoutPlanTo): void {
        this.planBinder.read(entity);
        this.hidden = false;
        planStore.formOpened = true;
    }

    public close(): void {
        this.hidden = true;
        planStore.formOpened = false;
        this.clearChecked();
        roundStore.updateFilter('');
    }

    public clear(): void {
        this.planBinder.clear();
        this.clearChecked();
        roundStore.updateFilter('');
    }

    private clearChecked() {
        this.checked.forEach((value, key) => {
            value.checked = false;
        });
        this.checked.clear();
    }

    public visible(): boolean {
        return !this.hidden;
    }

    save(): void {
        if (planStore.hasSelectedDetailsItem()) {
            this.saveStationLocal();
        } else {
            this.saveRound();
        }
    }

    private saveRound(): void {
        this.saveBtn.disabled = true;
        if (this.planBinder.value.id) {
            this.planBinder.submitTo(planStore.update)
                .then(() => {
                    this.planBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.planBinder.submitTo(planStore.add)
                .then(() => {
                    this.planBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }

    private saveStationLocal(): void {
        // if (planStore.hasSelectedDetailsItemChild()) {
        //     if (!this.roundBinder.invalid) {
        //         planStore.updateLocalDetailsItemChild(this.roundBinder.value);
        //     }
        // } else {
        //     if (this.checked.size > 0) {
        //         let exercises = Array.from(this.checked, ([name]) => name);
        //         let stations = exercises.map(exercise => this.asDefaultStation(exercise));
        //         planStore.saveLocalDetailsItemChild(stations);
        //         this.clearChecked();
        //     }
        // }
        // this.close();
    }

    // private asDefaultStation(exercise: WorkoutRoundTo): WorkoutStationSnapshot {
    //     const station = WorkoutStationSnapshotModel.createEmptyValue();
    //     const exerciseSnapshot = ExerciseSnapshotModel.createEmptyValue();
    //     exerciseSnapshot.title = exercise.title;
    //     exerciseSnapshot.description = exercise.description;
    //     exerciseSnapshot.complexity = exercise.complexity;
    //     station.exercise = exerciseSnapshot;
    //     return station;
    // }
}