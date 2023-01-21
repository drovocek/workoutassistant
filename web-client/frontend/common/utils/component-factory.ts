import {columnBodyRenderer} from "@vaadin/grid/lit";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {html} from 'lit';
import WorkoutSchema from "Frontend/generated/ru/soft/common/data/elements/WorkoutSchema";

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
                    children: [{text: 'Copy'}, {text: 'Delete'}]
                }
            ];
            return html`
                <vaadin-horizontal-layout style="align-items: center">
                    <vaadin-menu-bar
                            theme="icon"
                            .items="${items}"
                            @item-selected="${(e: MenuBarItemSelectedEvent) => onClick(e, entity)}">
                        <vaadin-tooltip slot="tooltip"></vaadin-tooltip>
                    </vaadin-menu-bar>
                    <span>${entity.title}</span>
                </vaadin-horizontal-layout>
            `
        });
}

export type ActionBarEntity = {
    title?: string;
    workoutSchema: WorkoutSchema;
}