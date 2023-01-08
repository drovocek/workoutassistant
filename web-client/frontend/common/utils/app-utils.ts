import {EndpointError} from "@hilla/frontend";
import {uiStore} from "Frontend/common/stores/app-store";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import WorkoutStationSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshotModel";
import ExerciseSnapshotModel from "Frontend/generated/ru/soft/common/data/snapshot/ExerciseSnapshotModel";
import {html} from "lit";

export const randomString = function (length: number) {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()';
    const charLength = chars.length;
    let result = '';
    for (let i = 0; i < length; i++) {
        result += chars.charAt(Math.floor(Math.random() * charLength));
    }
    return result;
}

export const deepEquals = function (object1: any, object2: any) {
    if (object1 === undefined || object1 === null || object2 === undefined || object2 === null) {
        return false;
    }
    const keys1 = Object.keys(object1);
    const keys2 = Object.keys(object2);
    if (keys1.length !== keys2.length) {
        return false;
    }
    for (const key of keys1) {
        const val1 = object1[key];
        const val2 = object2[key];
        const areObjects = isObject(val1) && isObject(val2);
        if (
            areObjects && !deepEquals(val1, val2) ||
            !areObjects && val1 !== val2
        ) {
            return false;
        }
    }
    return true;
}

function isObject(object: any) {
    return object != null && typeof object === 'object';
}

export const processErr = function (err: any) {
    console.log('Operation failed');
    if (err instanceof EndpointError) {
        uiStore.showError(`Server error. ${err.message}`);
    } else {
        throw err;
    }
}

// private asDefaultStation(exercise: ExerciseTo): WorkoutStationSnapshot {
//     const station = WorkoutStationSnapshotModel.createEmptyValue();
//     const exerciseSnapshot = ExerciseSnapshotModel.createEmptyValue();
//     exerciseSnapshot.title = exercise.title;
//     exerciseSnapshot.description = exercise.description;
//     exerciseSnapshot.complexity = exercise.complexity;
//     station.exercise = exerciseSnapshot;
//     return station;
// }
//
// private renderStationBadge(station: WorkoutStationSnapshot) {
//     let badgeThemes = "badge";
//     const complexity = station.exercise.complexity;
//     if (complexity < 3) {
//         badgeThemes = badgeThemes.concat(" success");
//     } else if (complexity > 4) {
//         badgeThemes = badgeThemes.concat(" error");
//     }
//
//     return html`
//             <vaadin-vertical-layout style="line-height: var(--lumo-line-height-m);">
//                 <span theme="badge contrast" title="${station.exercise.description}">${station.exercise.title}</span>
//                 <span theme=${badgeThemes} title="${station.exercise.description}">
//                      <vaadin-icon title="Repetitions" icon="vaadin:rotate-right"
//                                   style="padding: var(--lumo-space-xs)"></vaadin-icon>
//                      <span title="Repetitions">${station.repetitions}</span>
//                      <vaadin-icon title="Weight" icon="vaadin:compile"
//                                   style="padding: var(--lumo-space-xs)"></vaadin-icon>
//                      <span title="Weight">${station.weight}</span>
//                      <vaadin-icon title="Duration" icon="vaadin:stopwatch"
//                                   style="padding: var(--lumo-space-xs)"></vaadin-icon>
//                      <span title="Duration">${station.duration}</span>
//                      <vaadin-icon title="Rest" icon="vaadin:coffee" style="padding: var(--lumo-space-xs)"></vaadin-icon>
//                      <span title="Rest">${station.rest}</span>
//                 </span>
//             </vaadin-vertical-layout>
//         `
// }