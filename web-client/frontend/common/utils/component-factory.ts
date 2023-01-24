import {columnBodyRenderer} from "@vaadin/grid/lit";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {html} from 'lit';
import '../icons/my-icons-svg.js'
import WorkoutSchema from "Frontend/generated/ru/soft/common/data/elements/WorkoutSchema";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";
import Rest from "Frontend/generated/ru/soft/common/data/elements/Rest";
import Station from "Frontend/generated/ru/soft/common/data/elements/Station";
import {Binder} from "@hilla/form/Binder";
import StationModel from "Frontend/generated/ru/soft/common/data/elements/StationModel";
import {TemplateResult} from "lit/development";
import {exerciseStore} from "Frontend/common/stores/app-store";
import {field} from "@hilla/form";
import DurationUnit from "Frontend/generated/ru/soft/common/data/elements/DurationUnit";
import RestModel from "Frontend/generated/ru/soft/common/data/elements/RestModel";
import {FormLayoutResponsiveStep} from "@vaadin/form-layout";

export function createIconItem(iconName: string) {
    const item = document.createElement('vaadin-context-menu-item');
    const icon = document.createElement('vaadin-icon');
    icon.setAttribute('icon', `vaadin:${iconName}`);
    item.appendChild(icon);
    return item;
}

export function createItem(iconName: string, text: string) {
    const item = document.createElement('vaadin-context-menu-item');
    const icon = document.createElement('vaadin-icon');

    if (iconName === 'copy') {
        item.setAttribute('aria-label', 'duplicate');
    }

    icon.setAttribute('icon', `vaadin:${iconName}`);
    item.appendChild(icon);
    if (text) {
        item.appendChild(document.createTextNode(text));
    }
    return item;
}

export function renderTitleWithActionBar(onClick: (e: MenuBarItemSelectedEvent, entity: ActionBarEntity) => void) {
    return columnBodyRenderer<ActionBarEntity>(
        (entity) => {
            const items = [
                {
                    component: createIconItem('ellipsis-dots-v'),
                    tooltip: 'Actions',
                    children: [{text: 'Copy'}, {text: 'Delete'}, {text: 'Edit'}]
                }
            ];
            return html`
                <vaadin-horizontal-layout style="align-items: center">
                    <vaadin-menu-bar
                            theme="icon"
                            .items="${items}"
                            @item-selected="${(e: MenuBarItemSelectedEvent) => onClick(e, entity)}">
                    </vaadin-menu-bar>
                    <span>${entity.title}</span>
                </vaadin-horizontal-layout>
            `
        });
}

export function renderWorkoutElement() {
    return columnBodyRenderer<WorkoutElement>(
        (element) => {
            let anyElement = element as any;
            let type = anyElement.type;
            if (type === 'rest') {
                let rest = element as Rest;
                return html`
                    <span theme="badge pill" title="Rest">
                      <vaadin-icon icon="my-icons-svg:clock-solid"></vaadin-icon>
                      <span style="margin-left: 5px">${rest.duration} ${rest.unit.toLowerCase()}</span>
                    </span>
                `
            } else if (type === 'station') {
                let station = element as Station;
                return html`
                    <vaadin-horizontal-layout theme="spacing">
                         <span theme="badge success pill">
                             <span title="Repetitions">
                                 <vaadin-icon icon="my-icons-svg:dumbbell-solid"></vaadin-icon> 
                                 <span title=${station.exercise.note}>${station.exercise.title}</span>
                             </span>
                              <span title="Weight">
                                 <vaadin-icon icon="my-icons-svg:weight-hanging-solid"
                                              style="margin-left: 5px"></vaadin-icon>
                                 <span>${station.weight} kg</span>
                              </span>
                              <span title="Repetitions">
                                 <vaadin-icon icon="my-icons-svg:recycle-solid" style="margin-left: 5px"></vaadin-icon>
                                 <span>${station.repetitions} rep</span>
                              </span>
                              <span title="Duration">
                                 <vaadin-icon icon="my-icons-svg:stopwatch-solid" style="margin-left: 5px"></vaadin-icon>
                                 <span>${station.duration} ${station.unit.toLowerCase()}</span>
                              </span>
                        </span>
                    </vaadin-horizontal-layout>
                `
            }
            return html`
            `
        });
}

export type ActionBarEntity = {
    title?: string;
    workoutSchema?: WorkoutSchema;
}

export function renderStationDialog(binder: Binder<Station, StationModel>): TemplateResult<1> {
    const {model} = binder;
    return html`
        <vaadin-form-layout
                class="station-dialog-form"
                .responsiveSteps=${responsiveSteps}
                style="width: 30em">
            <vaadin-combo-box label="Exercise"
                              item-label-path="title"
                              .items=${exerciseStore.data}
                              colspan="2"
                              ${field(model.exercise)}
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

const responsiveSteps: FormLayoutResponsiveStep[] = [
    // Use one column by default
    {minWidth: 0, columns: 1},
    // Use two columns, if layout's width exceeds 500px
    {minWidth: '30em', columns: 2},
];

export function renderRestDialog(binder: Binder<Rest, RestModel>): TemplateResult<1> {
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


