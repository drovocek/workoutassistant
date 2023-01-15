import {workoutStore} from "Frontend/common/stores/app-store";
import Workout from "Frontend/generated/ru/soft/common/to/WorkoutTo";

export const createWorkoutChildItems = function createWorkoutChildItems() {
    return workoutStore.data.map(createWorkoutChildItem)
}

const createWorkoutChildItem = function createWorkoutChildItem(workout: Workout) {
    const item = document.createElement('vaadin-context-menu-item');

    if (workout.title) {
        item.appendChild(document.createTextNode(workout.title));
    }

    return {
        component: item
    };
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