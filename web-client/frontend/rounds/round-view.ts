import '@vaadin/grid';
import type {Grid, GridActiveItemChangedEvent} from '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import './round-form';
import './details/round-details';
import './selector/exercise-selector';
import './round-store-action-panel';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../common/views/view';
import {roundStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {AppForm} from "Frontend/common/components/app-form";

@customElement('round-view')
export class RoundView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#round-form')
    private form!: AppForm<WorkoutRoundTo>;

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
        this.classList.add("editing");
    }

    render() {
        return html`
            <vaadin-horizontal-layout class="h-full">
                <div class="w-full">
                    <round-store-action-panel targetFormId="round-form"
                                              class="toolbar flex gap-s"></round-store-action-panel>
                    <div class="content flex gap-m h-full">
                        <vaadin-grid
                                id="grid"
                                theme="no-border"
                                .items=${roundStore.filtered}
                                @active-item-changed=${this.handleGridSelection}
                                .detailsOpenedItems="${roundStore.getSelectedItemsDetailAsArr()}"
                                ${this.renderDetails()}>
                            <vaadin-grid-column ${this.renderDetailsButton()} header="Title"></vaadin-grid-column>
                            <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        </vaadin-grid>
                        <round-form id="round-form" class="flex flex-col gap-s"></round-form>
                    </div>
                </div>
            </vaadin-horizontal-layout>
        `;
    }

    private switchDetailsVisible(round: WorkoutRoundTo) {
        return () => {
            if(roundStore.hasSelectedDetailsItem()){
                roundStore.updateDetailsItem();
            }
            this.form.close();
            this.deselectAll();
            const isOpened = roundStore.detailsItemIsOpened(round);
            roundStore.setSelectedDetailsItem(isOpened ? null : round);
        }
    }

    private deselectAll() {
        this.grid.selectedItems = [];
        roundStore.setSelected(null);
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutRoundTo>(
            () => html`
                <round-details></round-details>`
            ,
            []
        );
    }

    private renderDetailsButton() {
        return columnBodyRenderer<WorkoutRoundTo>(
            (round) => {
                const isOpened = roundStore.detailsItemIsOpened(round);
                return html`
                    <vaadin-button
                            title="Exercises"
                            theme="tertiary contrast icon"
                            @click="${this.switchDetailsVisible(round)}">
                        <vaadin-icon ?hidden=${isOpened} icon="vaadin:chevron-right-small"></vaadin-icon>
                        <vaadin-icon ?hidden=${!isOpened} icon="vaadin:chevron-down-small"></vaadin-icon>
                    </vaadin-button>
                    <span>${round.title}</span>
                `
            });
    }

    private handleGridSelection(event: GridActiveItemChangedEvent<WorkoutRoundTo>) {
        this.closeDetails();
        this.form.close();

        const item: WorkoutRoundTo = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelected(item);
    }

    private closeDetails() {
        roundStore.selectedDetailsItem = null;
    }
}

// private selected: ExerciseTo = ExerciseToModel.createEmptyValue();
// @state()
// private dialogOpened: boolean = false;

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
