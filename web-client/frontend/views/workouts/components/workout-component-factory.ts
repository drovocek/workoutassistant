import {html} from "lit";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {columnBodyRenderer} from "@vaadin/grid/lit";
import {createIconItem} from "Frontend/common/utils/component-factory";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

export function renderTitleWithActionBar(onClick: (e: MenuBarItemSelectedEvent, entity: Workout) => void) {
    return columnBodyRenderer<Workout>(
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

export function renderDateTime() {
    return columnBodyRenderer<Workout>(
        (entity) => {
            let date = new Date(entity.dateTime);
            return html`
                <span theme="badge">${date.toLocaleDateString()} ${date.toLocaleTimeString()}</span>
            `
        });
}