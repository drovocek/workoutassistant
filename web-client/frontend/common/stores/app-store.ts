import {RouterLocation} from '@vaadin/router';
import {makeAutoObservable} from 'mobx';
import {UiStore} from "Frontend/common/stores/ui-store";
import {ExerciseStore} from 'Frontend/views/exercises/data/exercise-store';
import {RoundStore} from "Frontend/views/rounds/data/round-store";
import {WorkoutStore} from "Frontend/views/workouts/data/workout-store";

export class AppStore {
    uiStore = new UiStore();

    exerciseStore = new ExerciseStore();
    roundStore = new RoundStore();
    workoutStore = new WorkoutStore();

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



