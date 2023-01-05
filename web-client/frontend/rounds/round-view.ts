import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import type {Grid, GridActiveItemChangedEvent} from '@vaadin/grid';
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
import './round-details';
import './exercise-selector';
import {html} from 'lit';
import {customElement, query, state} from 'lit/decorators.js';
import {View} from '../common/views/view';
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {gridRowDetailsRenderer} from "@vaadin/grid/lit";

@customElement('round-view')
export class RoundView extends View {

    // private selected: ExerciseTo = ExerciseToModel.createEmptyValue();
    // @state()
    // private dialogOpened: boolean = false;

    @query('#grid')
    private grid!: Grid;

    @state()
    private detailsOpenedItem: WorkoutRoundTo[] = [];

    @state()
    private detailsClosed: boolean = true;

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
        this.autorun(() => {
            if (roundStore.selected) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <vaadin-horizontal-layout class="h-full">
                <div class="w-full">
                    <div class="toolbar flex gap-s">
                        <vaadin-text-field
                                placeholder="Filter by name"
                                .value=${roundStore.filterText}
                                @input=${this.updateFilter}
                                clear-button-visible
                        ></vaadin-text-field>
                        <vaadin-button @click=${this.clearForm}>Add round</vaadin-button>
                        <vaadin-button
                                theme="error tertiary icon">
                            <vaadin-icon icon="vaadin:trash"></vaadin-icon>
                        </vaadin-button>
                    </div>
                    <div class="content flex gap-m h-full">
                        <vaadin-grid
                                id="grid"
                                theme="no-border"
                                .items=${roundStore.filtered}
                                .detailsOpenedItems="${this.detailsOpenedItem}"
                                @active-item-changed="${this.updateOpened}"
                                ${this.renderDetails()}>
                            <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        </vaadin-grid>
                    </div>
                </div>
                <exercise-selector ?hidden=${this.detailsClosed}></exercise-selector>
            </vaadin-horizontal-layout>
            <vaadin-notification
                    theme=${uiStore.message.error ? 'error' : 'success'}
                    position="bottom-start"
                    .opened=${uiStore.message.open}
                    .renderer=${(root: HTMLElement) => (root.textContent = uiStore.message.text)}>
            </vaadin-notification>
        `;
    }

    private updateOpened(e: GridActiveItemChangedEvent<WorkoutRoundTo>) {
        this.detailsOpenedItem = [e.detail.value];
        let workoutRound = this.detailsOpenedItem[0];
        this.detailsClosed = workoutRound == null;
        if (workoutRound) {
            roundStore.setDetailsOpenedStations(this.extractDetailsData(workoutRound));
        } else {
            roundStore.setDetailsOpenedStations([]);
        }
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutRoundTo>(
            (round) => {
                return html`
                    <round-details class="h-full"></round-details>
                `
            },
            []
        );
    }

    //
    // private openedChanged(e: ConfirmDialogOpenedChangedEvent) {
    //     this.dialogOpened = e.detail.value;
    // }
    //
    private removeSelected() {

        // this.detailsData = this.detailsData
        //     .filter((station) => {
        //         return station?.exercise.title !== this.selected?.title;
        //     });
    };

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

    private updateFilter(e: { target: HTMLInputElement }) {
        roundStore.updateFilter(e.target.value);
    }

    private extractDetailsData(round: WorkoutRoundTo) {
        if (round.roundSchema && round.roundSchema.roundStations) {
            return round.roundSchema.roundStations;
        } else {
            return [];
        }
    }

    private handleGridSelection(event: CustomEvent) {
        const item: WorkoutRoundTo = event.detail.value as WorkoutRoundTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelected(item);
    }

    private clearForm() {
        roundStore.editNew();
    }
}
