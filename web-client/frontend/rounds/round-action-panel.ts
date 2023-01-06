import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import '@vaadin/split-layout';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/upload'
import './round-form';
import './round-details';
import './exercise-selector';
import {customElement} from 'lit/decorators.js';
import {roundStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {AppActionPanel} from "Frontend/common/components/app-action-panel";

@customElement('round-action-panel')
export class RoundActionPanel extends AppActionPanel<WorkoutRoundTo> {

    protected generalStore() {
        return roundStore;
    };
}
