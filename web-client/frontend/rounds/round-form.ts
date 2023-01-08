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
import {AppForm} from "Frontend/common/components/app-form";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";

@customElement('round-form')
export class RoundForm extends View implements AppForm<WorkoutRoundTo> {

    @query('#saveBtn')
    private saveBtn!: Button;

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
            <div class="editor" ?hidden="${!roundStore.hasSelectedDetailsItem()}">
                <vaadin-form-layout .responsiveSteps="${this.responsiveSteps}">
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
            </div>
            <div class="editor" ?hidden="${roundStore.hasSelectedDetailsItem()}">
                <vaadin-form-layout>
                    <vaadin-text-field
                            label="Title"
                            id="title"
                            ${field(this.roundBinder.model.title)}></vaadin-text-field>
                    <vaadin-text-area
                            label="Description"
                            id="description"
                            ${field(this.roundBinder.model.description)}></vaadin-text-area>
                </vaadin-form-layout>
            </div>
            <div class="flex gap-s button-layout">
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
        `;
    }

    public close(): void {
        this.hidden = true;
    }

    public open(entity: WorkoutRoundTo | WorkoutStationSnapshot): void {
        if (roundStore.hasSelectedDetailsItem()) {
            this.stationBinder.read(entity as WorkoutStationSnapshot);
        } else {
            this.roundBinder.read(entity as WorkoutRoundTo);
        }
        this.hidden = false;
    }

    public clear(): void {
        this.roundBinder.clear();
        this.stationBinder.clear();
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
                    this.hidden = true;
                })
                .finally(() => this.saveBtn.disabled = false);
        } else {
            this.roundBinder.submitTo(roundStore.add)
                .then(() => {
                    this.roundBinder.clear();
                    this.hidden = true;
                })
                .finally(() => this.saveBtn.disabled = false);
        }
    }

    private saveStationLocal(): void {
        if (roundStore.hasSelectedDetailsItemChild()) {
            // console.log(this.stationBinder.validating)
            if(!this.stationBinder.invalid){
                // const station = this.stationBinder.(WorkoutStationSnapshotModel.createEmptyValue());
                console.log(this.stationBinder.value)
                roundStore.updateDetailsItemChild(this.stationBinder.value);
            }
        } else {

        }
    }
}