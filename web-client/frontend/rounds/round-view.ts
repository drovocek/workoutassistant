import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import type {Grid} from '@vaadin/grid';
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
import {html} from 'lit';
import {customElement, query, state} from 'lit/decorators.js';
import {View} from '../common/views/view';
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {columnBodyRenderer, gridRowDetailsRenderer} from "@vaadin/grid/lit";
import {Button} from "@vaadin/button";

@customElement('round-view')
export class RoundView extends View {

    // private selected: ExerciseTo = ExerciseToModel.createEmptyValue();
    // @state()
    // private dialogOpened: boolean = false;

    @query('#grid')
    private grid!: Grid;

    @query('#copyBtn')
    private copyBtn!: Button;

    @query('#deleteBtn')
    private deleteBtn!: Button;

    @query('#actionPanel')
    private actionPanel!: HTMLDivElement;

    @state()
    private detailsOpenedItem: WorkoutRoundTo[] = [];

    @state()
    private detailsVisible: boolean = false

    @state()
    private formVisible: boolean = false

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
                    <div id="actionPanel" class="toolbar flex gap-s" aria-disabled="true">
                        <vaadin-text-field
                                .value=${roundStore.filterText}
                                @input=${this.updateFilter}
                                clear-button-visible>
                            <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                            <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                        </vaadin-text-field>
                        <vaadin-button
                                @click=${this.switchAddFormVisible}
                                ?disabled=${roundStore.selected?.id || this.detailsVisible}
                                title="add"
                                theme="tertiary success icon">
                            <vaadin-icon icon="vaadin:plus-circle-o"></vaadin-icon>
                            <vaadin-tooltip slot="tooltip" text="Add button"></vaadin-tooltip>
                        </vaadin-button>
                        <vaadin-button
                                @click=${this.switchEditFormVisible}
                                ?disabled=${!roundStore.selected?.id || this.detailsVisible}
                                title="edit"
                                theme="tertiary icon">
                            <vaadin-icon icon="vaadin:edit"></vaadin-icon>
                            <vaadin-tooltip slot="tooltip" text="Edit button"></vaadin-tooltip>
                        </vaadin-button>
                        <vaadin-button
                                id="copyBtn"
                                @click=${this.copy}
                                ?disabled=${!roundStore.selected?.id || this.detailsVisible}
                                title="copy"
                                theme="tertiary contrast icon">
                            <vaadin-icon icon="vaadin:copy-o"></vaadin-icon>
                            <vaadin-tooltip slot="tooltip" text="Copy button"></vaadin-tooltip>
                        </vaadin-button>
                        <vaadin-button
                                id="deleteBtn"
                                @click=${this.delete}
                                ?disabled=${!roundStore.selected?.id || this.detailsVisible}
                                title="delete"
                                theme="error tertiary icon">
                            <vaadin-icon icon="vaadin:trash"></vaadin-icon>
                            <vaadin-tooltip slot="tooltip" text="Delete button"></vaadin-tooltip>
                        </vaadin-button>
                        <vaadin-notification
                                theme=${uiStore.message.error ? 'error' : 'success'}
                                position="bottom-start"
                                .opened=${uiStore.message.open}
                                .renderer=${(root: HTMLElement) => (root.textContent = uiStore.message.text)}>
                        </vaadin-notification>
                    </div>
                    <div class="content flex gap-m h-full">
                        <vaadin-grid
                                id="grid"
                                theme="no-border"
                                .items=${roundStore.filtered}
                                @active-item-changed=${this.handleGridSelection}
                                .detailsOpenedItems="${this.detailsOpenedItem}"
                                ${this.renderDetails()}>
                            <vaadin-grid-column ${this.renderDetailsButton()}
                            ></vaadin-grid-column>
                            <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                            <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        </vaadin-grid>
                        <round-form class="flex flex-col gap-s"
                                    ?hidden=${!this.formVisible || this.detailsVisible}></round-form>
                    </div>
                </div>
                <exercise-selector ?hidden=${!this.detailsVisible}></exercise-selector>
            </vaadin-horizontal-layout>
        `;
    }

    private switchDetailsVisible(round: WorkoutRoundTo) {
        return () => {
            this.closeForm();
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

    private updateFilter(e: { target: HTMLInputElement }) {
        roundStore.updateFilter(e.target.value);
    }

    private extractStations(round: WorkoutRoundTo) {
        if (round.roundSchema && round.roundSchema.roundStations) {
            return round.roundSchema.roundStations;
        } else {
            return [];
        }
    }

    private handleGridSelection(event: CustomEvent) {
        this.closeForm();

        const item: WorkoutRoundTo = event.detail.value as WorkoutRoundTo;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        roundStore.setSelected(item);
    }

    private switchEditFormVisible() {
        this.formVisible = !this.formVisible;
    }

    private closeForm() {
        roundStore.cancelEdit();
        this.formVisible = false;
    }

    private switchAddFormVisible() {
        if (roundStore.hasSelected()) {
            this.closeForm();
        } else {
            roundStore.editNew();
            this.formVisible = true;
        }
    }

    copy() {
        this.copyBtn.disabled = true;
        roundStore.copy()
            .finally(() => this.copyBtn.disabled = false);
    }

    delete() {
        this.deleteBtn.disabled = true;
        roundStore.delete()
            .catch(() =>  this.copyBtn.disabled = false);
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
