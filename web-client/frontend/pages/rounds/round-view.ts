import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import './components/round-form';
import './components/round-details';
import './components/round-action-panel';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../../common/views/view';
import {roundStore} from "Frontend/common/stores/app-store";
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {AppForm} from "Frontend/common/components/app-form";
import type {Grid, GridActiveItemChangedEvent} from '@vaadin/grid';

@customElement('round-view')
export class RoundView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#round-form')
    private form!: AppForm<Round>;

    private firstSelectionEvent = true;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (roundStore.formOpened) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <div class="content">
                <div class="filter-grid">
                    <round-action-panel targetFormId="round-form"
                                              class="action-panel"></round-action-panel>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${roundStore.filtered}
                            .detailsOpenedItems="${roundStore.getSelectedItemsDetailAsArr()}"
                            @active-item-changed=${this.handleGridSelection}
                            ${this.renderDetails()}>
                        <vaadin-grid-column ${this.renderDetailsButton()} header="Title"></vaadin-grid-column>
                        <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </div>
                <round-form id="round-form" class="form"></round-form>
            </div>
        `;
    }

    private switchDetailsVisible(round: Round) {
        return () => {
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
        return gridRowDetailsRenderer<Round>(
            () => html`
                <round-details></round-details>`
            ,
            []
        );
    }

    private renderDetailsButton() {
        return columnBodyRenderer<Round>(
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

    private handleGridSelection(event: GridActiveItemChangedEvent<Round>) {
        this.closeDetails();
        this.form.close();

        const item: Round = event.detail.value;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelected(item);
    }

    private closeDetails() {
        roundStore.setSelectedDetailsItem(null);
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
