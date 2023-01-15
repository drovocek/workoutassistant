import {RouterLocation} from '@vaadin/router';
import {makeAutoObservable} from 'mobx';
import {UiStore} from "Frontend/common/stores/ui-store";
import {ExerciseStore} from 'Frontend/pages/exercises/data/exercise-store';
import {RoundStore} from "Frontend/pages/rounds/data/round-store";
import {WorkoutStore} from "Frontend/pages/workouts/data/workout-store";
import {ProgramStore} from "Frontend/pages/programs/data/program-store";

export class AppStore {
    uiStore = new UiStore();

    exerciseStore = new ExerciseStore();
    roundStore = new RoundStore();
    workoutStore = new WorkoutStore();
    programStore = new ProgramStore();

    applicationName = 'Workout assistant';

    // The location, relative to the base path, e.g. "hello" when viewing "/hello"
    location = '';

    currentViewTitle = '';

    constructor() {
        makeAutoObservable(this);
    }

    setLocation(location: RouterLocation) {
        const serverSideRoute = location.route?.path == '(.*)';
        if (location.route && !serverSideRoute) {
            this.location = location.route.path;
        } else if (location.pathname.startsWith(location.baseUrl)) {
            this.location = location.pathname.substr(location.baseUrl.length);
        } else {
            this.location = location.pathname;
        }
        if (serverSideRoute) {
            this.currentViewTitle = document.title; // Title set by server
        } else {
            this.currentViewTitle = (location?.route as any)?.title || '';
        }
    }
}

export const appStore = new AppStore();
export const uiStore = appStore.uiStore;
export const exerciseStore = appStore.exerciseStore;
export const roundStore = appStore.roundStore;
export const workoutStore = appStore.workoutStore;
export const programStore = appStore.programStore;



