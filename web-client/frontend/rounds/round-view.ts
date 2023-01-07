import '@vaadin/grid';
import type {Grid} from '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import './round-form';
import './round-details';
import './exercise-selector';
import './round-action-panel';
import {html} from 'lit';
import {customElement, query, state} from 'lit/decorators.js';
import {View} from '../common/views/view';
import {roundStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {AppForm} from "Frontend/common/components/app-form";

@customElement('round-view')
export class RoundView extends View {

    // private selected: ExerciseTo = ExerciseToModel.createEmptyValue();
    // @state()
    // private dialogOpened: boolean = false;

    @query('#grid')
    private grid!: Grid;

    @query('#round-form')
    private form!: AppForm<WorkoutRoundTo>;

    @state()
    private detailsOpenedItem: WorkoutRoundTo[] = [];

    @state()
    private detailsVisible: boolean = false

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.classList.add(
            'box-border',
            'flex',
            'flex-col',
            'p-m',
            'gap-s',
            'w-full',
            'h-full'
        );
        // this.autorun(() => {
        //     if (roundStore.selected) {
        //         this.classList.add("editing");
        //     } else {
        //         this.classList.remove("editing");
        //     }
        // });
        this.classList.add("editing");
    }

    render() {
        return html`
            <vaadin-horizontal-layout class="h-full">
                <div class="w-full">
                    <round-action-panel ?hidden="${this.detailsVisible}" targetFormId="round-form" class="toolbar flex gap-s"></round-action-panel>
                    <div class="content flex gap-m h-full">
                        <vaadin-grid
                                id="grid"
                                theme="no-border"
                                .items=${roundStore.filtered}
                                @active-item-changed=${this.handleGridSelection}
                                .detailsOpenedItems="${this.detailsOpenedItem}"
                                ${this.renderDetails()}>
                            <vaadin-grid-column ${this.renderDetailsButton()} header="Details"
                            ></vaadin-grid-column>
                            <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        </vaadin-grid>
                        <round-form id="round-form" class="flex flex-col gap-s"></round-form>
                    </div>
                </div>
                <exercise-selector ?hidden=${!this.detailsVisible}></exercise-selector>
            </vaadin-horizontal-layout>
        `;
    }

    private switchDetailsVisible(round: WorkoutRoundTo) {
        return () => {
            this.form.close();
            const isOpened = this.detailsOpenedItem.includes(round);
            this.detailsOpenedItem = isOpened
                ? this.detailsOpenedItem.filter((r) => r !== round)
                : [...this.detailsOpenedItem, round]
            let workoutRound = this.detailsOpenedItem[0];
            this.detailsVisible = workoutRound != null;
        }
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutRoundTo>(
            (round) => {
                const stations = this.extractStations(round);
                return html`
                    <round-details .stations="${stations}" class="h-full"></round-details>
                `
            },
            []
        );
    }

    private renderDetailsButton() {
        return columnBodyRenderer<WorkoutRoundTo>(
            (round) => {
                return html`
                    <vaadin-button
                            title="Exercises"
                            theme="tertiary icon"
                            @click="${this.switchDetailsVisible(round)}">
                        <vaadin-icon icon="vaadin:chevron-down"></vaadin-icon>
                    </vaadin-button>
                `
            });
    }

    private extractStations(round: WorkoutRoundTo) {
        if (round.roundSchema && round.roundSchema.roundStations) {
            return round.roundSchema.roundStations;
        } else {
            return [];
        }
    }

    private handleGridSelection(event: CustomEvent) {
        this.form.close();

        const item: WorkoutRoundTo = event.detail.value as WorkoutRoundTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelected(item);
    }
}


//
// private openedChanged(e: ConfirmDialogOpenedChangedEvent) {
//     this.dialogOpened = e.detail.value;
// }
//
//  private removeSelected() {

// this.detailsData = this.detailsData
//     .filter((station) => {
//         return station?.exercise.title !== this.selected?.title;
//     });
//  };

// <vaadin-confirm-dialog
//     header='Delete station for "${this.selected?.title}"?'
//     cancel
// @cancel="${() => this.dialogOpened = false}"
//     confirm-text="Delete"
//     confirm-theme="error primary"
// @confirm="${this.removeSelected}"
// .opened="${this.dialogOpened}"
// @opened-changed="${this.openedChanged}"
//         >
//         Are you sure you want to permanently delete this item?
//     </vaadin-confirm-dialog>

// private removeBtnRenderer: GridColumnBodyLitRenderer<WorkoutStationSnapshot> = ({exercise}) => html`
//     <vaadin-button
//             theme="error tertiary icon"
//             @click="${() => {
//     this.selected = exercise;
//     this.dialogOpened = true;
// }}"
//     >
//         <vaadin-icon icon="vaadin:trash"></vaadin-icon>
//     </vaadin-button>
// `;
