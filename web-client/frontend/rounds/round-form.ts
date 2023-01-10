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
import {exerciseStore, roundStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import WorkoutRoundToModel from "Frontend/generated/ru/soft/common/to/WorkoutRoundToModel";
import {query} from "lit/decorators";
import {Button} from "@vaadin/button";
import {AppForm} from "Frontend/common/components/app-form";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";
import {Grid} from "@vaadin/grid";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";
import {Checkbox} from "@vaadin/checkbox";
import {TemplateResult} from "lit-html/development/lit-html";

@customElement('round-form')
export class RoundForm extends View implements AppForm<WorkoutRoundTo> {

    @query('#grid')
    private grid!: Grid;

    @query('#saveBtn')
    private saveBtn!: Button;

    private checked: Map<ExerciseTo, Checkbox> = new Map<ExerciseTo, Checkbox>();

    private roundBinder = new Binder<WorkoutRoundTo, WorkoutRoundToModel>(this, WorkoutRoundToModel);
    private stationBinder = new Binder<WorkoutStationSnapshot, WorkoutStationSnapshotModel>(this, WorkoutStationSnapshotModel);

    protected firstUpdated(_changedProperties: any) {
        super.firstUpdated(_changedProperties);

        const model = this.roundBinder.model;

        this.roundBinder.for(model.title).addValidator(
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
                <h3>${this.roundBinder.value.id || roundStore.hasSelectedDetailsItemChild() ? 'Update' : 'Add'}</h3>
                ${this.renderExerciseSelector(!roundStore.hasSelectedDetailsItem() || roundStore.hasSelectedDetailsItemChild())}
                ${this.renderStationEditForm(!roundStore.hasSelectedDetailsItemChild())}
                ${this.renderEditRoundForm(roundStore.hasSelectedDetailsItem())}
                <div class="button-layout">
                    <vaadin-button
                            id="saveBtn"
                            theme="primary"
                            @click=${this.save}>
                        ${this.roundBinder.value.id || roundStore.hasSelectedDetailsItemChild() ? 'Save' : 'Add'}
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

    private renderExerciseSelector(hidden: boolean): TemplateResult<1> {
        return html`
            <div class="exercise-selector"
                 ?hidden="${hidden}">
                <vaadin-text-field
                        placeholder="Filter by name"
                        .value=${exerciseStore.filterText}
                        @input=${this.updateFilter}
                        clear-button-visible>
                    <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                </vaadin-text-field>
                <vaadin-grid
                        id="grid"
                        theme="no-border hide-filter-header-row"
                        .items=${exerciseStore.filtered}>
                    <vaadin-grid-column header="Title" ${(this.renderExerciseBadge())}></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `
    }

    private renderExerciseBadge() {
        return columnBodyRenderer<ExerciseTo>(
            (exercise) => {
                let badgeThemes = "badge";
                const complexity = exercise.complexity;
                if (complexity < 3) {
                    badgeThemes = badgeThemes.concat(" success");
                } else if (complexity > 4) {
                    badgeThemes = badgeThemes.concat(" error");
                }
                return html`
                    <span>
                        <vaadin-checkbox @change="${(e: Event) => {
                            let checked = (e.target as HTMLInputElement).checked;
                            console.log(e.currentTarget)
                            if (checked) {
                                this.checked.set(exercise, e.currentTarget as Checkbox);
                            } else {
                                this.checked.delete(exercise);
                            }
                            console.log(this.checked)
                        }}"></vaadin-checkbox>
                        <span theme=${badgeThemes} title="${exercise.description}">${exercise.title}</span>
                     </span>
                `
            },
            []);
    }

    private renderStationEditForm(hidden: boolean): TemplateResult<1> {
        return html`
            <vaadin-form-layout
                    class="station-edit-form"
                    ?hidden="${hidden}"
                    .responsiveSteps="${this.responsiveSteps}">
                <vaadin-integer-field
                        label="Weight"
                        id="weight"
                        step="10"
                        step-buttons-visible
                        min="0"
                        max="500"
                        ${field(this.stationBinder.model.weight)}></vaadin-integer-field>
                <vaadin-integer-field
                        label="Duration"
                        id="duration"
                        step="30"
                        step-buttons-visible
                        min="0"
                        max="500"
                        ${field(this.stationBinder.model.duration)}></vaadin-integer-field>
                <vaadin-integer-field
                        label="Repetitions"
                        id="repetitions"
                        step="1"
                        step-buttons-visible
                        min="1"
                        max="1000"
                        ${field(this.stationBinder.model.repetitions)}></vaadin-integer-field>
                <vaadin-integer-field
                        label="Rest"
                        id="rest"
                        step="30"
                        step-buttons-visible
                        min="0"
                        max="500"
                        ${field(this.stationBinder.model.rest)}>
                </vaadin-integer-field>
            </vaadin-form-layout>
        `
    }

    private renderEditRoundForm(hidden: boolean): TemplateResult<1> {
        return html`
            <vaadin-form-layout
                    .responsiveSteps="${this.responsiveSteps}"
                    class="edit-round-form"
                    ?hidden="${hidden}">
                <vaadin-text-field
                        label="Title"
                        id="title"
                        ${field(this.roundBinder.model.title)}></vaadin-text-field>
                <vaadin-text-area
                        label="Description"
                        id="description"
                        ${field(this.roundBinder.model.description)}></vaadin-text-area>
            </vaadin-form-layout>
        `
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        exerciseStore.updateFilter(e.target.value);
        this.clearChecked();
    }

    public open(entity: WorkoutRoundTo | WorkoutStationSnapshot): void {
        if (roundStore.hasSelectedDetailsItem()) {
            this.stationBinder.read(entity as WorkoutStationSnapshot);
        } else {
            this.roundBinder.read(entity as WorkoutRoundTo);
        }
        this.hidden = false;
        roundStore.formOpened = true;
    }

    public close(): void {
        this.hidden = true;
        roundStore.formOpened = false;
        this.clearChecked();
        exerciseStore.updateFilter('');
    }

    public clear(): void {
        this.roundBinder.clear();
        this.stationBinder.clear();
        this.clearChecked();
        exerciseStore.updateFilter('');
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
        if (roundStore.hasSelectedDetailsItem()) {
            this.saveStationLocal();
        } else {
            this.saveRound();
        }
    }

    private saveRound(): void {
        this.saveBtn.disabled = true;
        if (this.roundBinder.value.id) {
            this.roundBinder.submitTo(roundStore.update)
                .then(() => {
                    this.roundBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.roundBinder.submitTo(roundStore.add)
                .then(() => {
                    this.roundBinder.clear();
                    this.close();
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }

    private saveStationLocal(): void {
        if (roundStore.hasSelectedDetailsItemChild()) {
            if (!this.stationBinder.invalid) {
                roundStore.updateLocalDetailsItemChild(this.stationBinder.value);
            }
        } else {
            if (this.checked.size > 0) {
                let exercises = Array.from(this.checked, ([name]) => name);
                let stations = exercises.map(exercise => this.asDefaultStation(exercise));
                roundStore.saveLocalDetailsItemChild(stations);
                this.clearChecked();
            }
        }
        this.close();
    }

    private asDefaultStation(exercise: ExerciseTo): WorkoutStationSnapshot {
        const station = WorkoutStationSnapshotModel.createEmptyValue();
        const exerciseSnapshot = ExerciseSnapshotModel.createEmptyValue();
        exerciseSnapshot.title = exercise.title;
        exerciseSnapshot.description = exercise.description;
        exerciseSnapshot.complexity = exercise.complexity;
        station.exercise = exerciseSnapshot;
        return station;
    }
}