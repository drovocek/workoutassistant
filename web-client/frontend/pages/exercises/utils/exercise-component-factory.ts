import {columnBodyRenderer} from "@vaadin/grid/lit";
import Exercise from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {html} from 'lit';

export const createIconItem = function createIconItem(iconName: string) {
    const item = document.createElement('vaadin-context-menu-item');
    const icon = document.createElement('vaadin-icon');
    icon.setAttribute('icon', `vaadin:${iconName}`);
    item.appendChild(icon);
    return item;
}

export const createItem = function createItem(iconName: string, text: string) {
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

export const renderTitleWithActionBar = function renderTitleWithActionBar(onClick: (e: MenuBarItemSelectedEvent, exercise: Exercise) => void) {
    return columnBodyRenderer<Exercise>(
        (exercise) => {
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
                            @item-selected="${(e: MenuBarItemSelectedEvent) => onClick(e, exercise)}">
                        <vaadin-tooltip slot="tooltip"></vaadin-tooltip>
                    </vaadin-menu-bar>
                    <span>${exercise.title}</span>
                </vaadin-horizontal-layout>
            `
        });
}