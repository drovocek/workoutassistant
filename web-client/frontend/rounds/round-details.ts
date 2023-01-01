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
import '@vaadin/confirm-dialog'
import {html} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {View} from '../common/views/view';
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";
import {GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {columnBodyRenderer, GridColumnBodyLitRenderer} from "@vaadin/grid/lit";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import {ConfirmDialogOpenedChangedEvent} from "@vaadin/confirm-dialog";

@customElement('round-details')
export class RoundDetails extends View {

    @state()
    public detailsData: Array<WorkoutStationSnapshot | undefined> = [];

    @state()
    private draggedItem?: WorkoutStationSnapshot;

    @state()
    private dialogOpened: boolean = false;

    private selected: ExerciseTo | undefined;

    render() {
        return html`
            <vaadin-grid
                    .items=${this.detailsData}
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${(event: GridDragStartEvent<WorkoutStationSnapshot>) => {
                        this.draggedItem = event.detail.draggedItems[0];
                    }}"
                    @grid-dragend="${() => {
                        delete this.draggedItem;
                    }}"

                    @grid-drop="${(event: GridDropEvent<WorkoutStationSnapshot>) => {
                        const {dropTargetItem, dropLocation} = event.detail;
                        if (this.draggedItem && dropTargetItem !== this.draggedItem) {
                            const draggedItemIndex = this.detailsData.indexOf(this.draggedItem);
                            this.detailsData.splice(draggedItemIndex, 1);
                            const dropIndex = this.detailsData.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                            this.detailsData.splice(dropIndex, 0, this.draggedItem);
                            this.detailsData = [...this.detailsData];
                            console.log(this.detailsData)
                        }
                    }}"
            >
                <vaadin-grid-sort-column path="exercise.title"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="exercise.description"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="exercise.complexity"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="repetitions"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="weight"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="duration"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-sort-column path="rest"
                                         auto-width></vaadin-grid-sort-column>
                <vaadin-grid-column
                        header="Manage"
                        ${columnBodyRenderer(this.removeBtnRenderer, [])}
                ></vaadin-grid-column>
            </vaadin-grid>
            <vaadin-confirm-dialog
                    header='Delete station for "${this.selected?.title}"?'
                    cancel
                    @cancel="${() => this.dialogOpened = false}"
                    confirm-text="Delete"
                    confirm-theme="error primary"
                    @confirm="${this.removeSelected}"
                    .opened="${this.dialogOpened}"
                    @opened-changed="${this.openedChanged}"
            >
                Are you sure you want to permanently delete this item?
            </vaadin-confirm-dialog>
        `;
    }

    openedChanged(e: ConfirmDialogOpenedChangedEvent) {
        this.dialogOpened = e.detail.value;
    }

    private removeSelected() {
        console.log("REMOVE")
        this.detailsData = this.detailsData
            .filter((station) => {
                return station?.exercise.title !== this.selected?.title;
            });
    };

    private removeBtnRenderer: GridColumnBodyLitRenderer<WorkoutStationSnapshot> = ({exercise}) => html`
        <vaadin-button
                theme="error tertiary icon"
                @click="${() => {
                    this.selected = exercise;
                    this.dialogOpened = true;
                }}"
        >
            <vaadin-icon icon="vaadin:trash"></vaadin-icon>
        </vaadin-button>
    `;

    public actualWorkoutStationSnapshots() {
        return this.detailsData;
    }
}
