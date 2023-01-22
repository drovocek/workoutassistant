import {html, TemplateResult} from "lit";
import {exerciseStore} from "Frontend/common/stores/app-store";
import DurationUnit from "Frontend/generated/ru/soft/common/data/elements/DurationUnit";
import {Binder} from "@hilla/form/Binder";
import Station from "Frontend/generated/ru/soft/common/data/elements/Station";
import StationModel from "Frontend/generated/ru/soft/common/data/elements/StationModel";
import {DialogLitRenderer} from "@vaadin/dialog/src/lit/renderer-directives";
import {field} from "@hilla/form";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";
import Rest from "Frontend/generated/ru/soft/common/data/elements/Rest";
import RestModel from "Frontend/generated/ru/soft/common/data/elements/RestModel";

const responsiveSteps: FormLayoutResponsiveStep[] = [
    // Use one column by default
    { minWidth: 0, columns: 1 },
    // Use two columns, if layout's width exceeds 500px
    { minWidth: '30em', columns: 2 },
];

export function renderStationDialog(binder : Binder<Station, StationModel>): TemplateResult<1> {
    const {model} = binder;
    return html`
        <vaadin-form-layout 
                class="station-dialog-form" 
                .responsiveSteps=${responsiveSteps}
                style="width: 30em">
            <vaadin-combo-box label="Exercise"
                              item-label-path="title"
                              item-value-path="id"
                              .items=${exerciseStore.data}
                              colspan="2"
                              ${field(model.exerciseId)}
            ></vaadin-combo-box>
            <vaadin-integer-field
                    label="Weight"
                    min="0"
                    max="500"
                    value="0"
                    step="10"
                    step-buttons-visible
                    ${field(model.weight)}
            ></vaadin-integer-field>
            <vaadin-integer-field
                    label="Repetitions"
                    min="0"
                    max="1000"
                    value="0"
                    step="5"
                    step-buttons-visible
                    ${field(model.repetitions)}
            ></vaadin-integer-field>
            <vaadin-integer-field
                    label="Duration"
                    min="0"
                    max="60"
                    step-buttons-visible
                    value="0"
                    ${field(model.duration)}
            ></vaadin-integer-field>
            <vaadin-combo-box label="Unit"
                              value=${DurationUnit.MIN}
                              .items=${[DurationUnit.SEC, DurationUnit.MIN]}
                              ${field(model.unit)}
            ></vaadin-combo-box>
        </vaadin-form-layout>
    `;
}

export function renderRestDialog(binder : Binder<Rest, RestModel>): TemplateResult<1> {
    const {model} = binder;
    return html`
         <vaadin-form-layout 
                class="rest-dialog-form" 
                .responsiveSteps=${responsiveSteps}
                style="width: 30em">
            <vaadin-integer-field
                    label="Duration"
                    min="0"
                    max="60"
                    step-buttons-visible
                    value="0"
                    ${field(model.duration)}
            ></vaadin-integer-field>
            <vaadin-combo-box label="Unit"
                              .items=${Object.values(DurationUnit)}
                              ${field(model.unit)}
            ></vaadin-combo-box>
        </vaadin-form-layout>
    `;
}