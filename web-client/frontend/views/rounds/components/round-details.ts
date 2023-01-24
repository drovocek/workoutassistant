import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/dialog';
import '@vaadin/integer-field';
import {html} from 'lit';
import {customElement} from 'lit/decorators.js';
import {Grid, GridDragStartEvent, GridDropEvent} from "@vaadin/grid";
import {property, query, state} from "lit/decorators";
import {View} from "Frontend/view";
import {PropertyValues} from "@lit/reactive-element/development/reactive-element";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";
import {renderWorkoutElement} from "Frontend/common/utils/component-factory";
import {dialogFooterRenderer, dialogRenderer} from "@vaadin/dialog/lit";
import {DialogOpenedChangedEvent} from "@vaadin/dialog";
import {Binder} from "@hilla/form";
import Rest from "Frontend/generated/ru/soft/common/data/elements/Rest";
import RestModel from "Frontend/generated/ru/soft/common/data/elements/RestModel";
import Station from "Frontend/generated/ru/soft/common/data/elements/Station";
import StationModel from "Frontend/generated/ru/soft/common/data/elements/StationModel";
import DurationUnit from "Frontend/generated/ru/soft/common/data/elements/DurationUnit";
import {Button} from "@vaadin/button";
import {deepEquals, randomString} from "Frontend/common/utils/app-utils";
import './round-component-factory';
import {renderRestDialog, renderStationDialog} from "Frontend/views/rounds/components/round-component-factory";
import {exerciseStore, roundStore} from "Frontend/common/stores/app-store";

@customElement('round-details')
export class RoundDetails extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#edit-button')
    private editButton!: Button;

    @query('#copy-button')
    private copyButton!: Button;

    @query('#delete-button')
    private deleteButton!: Button;

    private firstSelectionEvent = true;

    @property()
    items: WorkoutElement[] = []

    @state()
    private draggedElement?: WorkoutElement;

    @state()
    private dialogRestOpened = false;

    @state()
    private dialogStationOpened = false;

    private clearDraggedElement = () => {
        delete this.draggedElement;
    };

    private storeDraggingElement = (event: GridDragStartEvent<WorkoutElement>) => {
        this.draggedElement = event.detail.draggedItems[0];
    };

    private restBinder = new Binder<Rest, RestModel>(this, RestModel);

    private stationBinder = new Binder<Station, StationModel>(this, StationModel);

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
    }

    updated(_changedProperties: PropertyValues) {
        super.updated(_changedProperties);
        this.deselectAll();
        this.switchButtonActive(null);
        if (roundStore.selected) {
            roundStore.selected.workoutSchema.workoutElements = this.items;
        }
    }

    private deselectAll() {
        this.grid.selectedItems = [];
    }

    render() {
        // @ts-ignore
        return html`
            <vaadin-grid
                    id="grid"
                    theme="hide-filter-header-row"
                    .items=${this.items}
                    @active-item-changed=${this.handleGridSelection}
                    rows-draggable
                    drop-mode="between"
                    @grid-dragstart="${this.storeDraggingElement}"
                    @grid-dragend="${this.clearDraggedElement}"
                    @grid-drop="${this.onGridDrop()}">
                <vaadin-grid-sort-column path="title"
                                         header="Workout element"
                                         auto-width
                                         ${renderWorkoutElement()}></vaadin-grid-sort-column>
            </vaadin-grid>
            <vaadin-horizontal-layout theme="spacing">
                <vaadin-button
                        id="station-button"
                        class="station-button overlay-details-button"
                        theme="secondary success icon"
                        @click="${this.openStationDialog}">
                    <vaadin-tooltip slot="tooltip" text="Add station"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:dumbbell-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="rest-button"
                        class="rest-button overlay-details-button"
                        theme="secondary icon"
                        @click="${this.openRestDialog}">
                    <vaadin-tooltip slot="tooltip" text="Add rest"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:clock-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="edit-button"
                        class="edit-button overlay-details-button"
                        theme="secondary icon"
                        ?disabled=${true}
                        @click=${this.edit}>
                    <vaadin-tooltip slot="tooltip" text="Edit"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:edit-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="copy-button"
                        class="copy-button overlay-details-button"
                        theme="tertiary icon"
                        ?disabled=${true}
                        @click=${this.copy}>
                    <vaadin-tooltip slot="tooltip" text="Copy"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:copy-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="delete-button"
                        class="delete-button overlay-details-button"
                        theme="error icon"
                        ?disabled=${true}
                        @click=${this.delete}>
                    <vaadin-tooltip slot="tooltip" text="Delete"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:trash-alt-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
            </vaadin-horizontal-layout>
            <vaadin-dialog
                    id="station-dialog"
                    header-title="Station"
                    .opened="${this.dialogStationOpened}"
                    @opened-changed="${(e: DialogOpenedChangedEvent) => (this.dialogStationOpened = e.detail.value)}"
                    ${dialogRenderer(this.renderStationDialog, [])}
                    ${dialogFooterRenderer(this.renderStationFooter, [])}
            ></vaadin-dialog>
            <vaadin-dialog
                    id="rest-dialog"
                    header-title="Rest"
                    .opened="${this.dialogRestOpened}"
                    @opened-changed="${(e: DialogOpenedChangedEvent) => (this.dialogRestOpened = e.detail.value)}"
                    ${dialogRenderer(this.renderRestDialog, [])}
                    ${dialogFooterRenderer(this.renderRestFooter, [])}
            ></vaadin-dialog>
        `;
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutElement>) => {
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedElement && dropTargetItem !== this.draggedElement) {
                const draggedItemIndex = this.items.indexOf(this.draggedElement);
                this.items.splice(draggedItemIndex, 1);
                const dropIndex = this.items.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                this.items.splice(dropIndex, 0, this.draggedElement);
                this.items = [...this.items];
            }
        }
    }

    private handleGridSelection(event: CustomEvent) {
        const item: WorkoutElement = event.detail.value as WorkoutElement;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        this.switchButtonActive(item);
    }

    private switchButtonActive(selected: WorkoutElement | null) {
        this.editButton.disabled = selected === null;
        this.copyButton.disabled = selected === null;
        this.deleteButton.disabled = selected === null;
    }

    private renderRestDialog() {
        return renderRestDialog(this.restBinder);
    }

    private renderRestFooter = () => html`
        <vaadin-button @click="${this.closeRestDialog}">Cancel</vaadin-button>
        <vaadin-button theme="primary" @click="${this.addRest}">Save</vaadin-button>
    `;

    private openRestDialog() {
        this.dialogRestOpened = true;
        this.restBinder.read(this.defaultFormRest())
    }

    private defaultFormRest(): Rest {
        let rest = RestModel.createEmptyValue();
        rest.unit = DurationUnit.MIN;
        (rest as any).id = randomString(10);
        return rest;
    }

    private closeRestDialog() {
        this.dialogRestOpened = false;
    }

    private addRest() {
        let rest = this.restBinder.value as any;
        rest.type = 'rest';
        this.pushOrUpdate(rest);
        this.closeRestDialog();
    }

    private renderStationDialog() {
        return renderStationDialog(this.stationBinder);
    }

    private renderStationFooter = () => html`
        <vaadin-button @click="${this.closeStationDialog}">Cancel</vaadin-button>
        <vaadin-button theme="primary" @click="${this.saveStation}">Save</vaadin-button>
    `;

    private openStationDialog() {
        this.dialogStationOpened = true;
        this.stationBinder.read(this.defaultFormStation());
    }

    private defaultFormStation(): Station {
        let station = StationModel.createEmptyValue();
        station.unit = DurationUnit.MIN;
        station.exercise = exerciseStore.data[0];
        (station as any).id = randomString(10);
        return station;
    }

    private closeStationDialog() {
        this.dialogStationOpened = false;
    }

    private saveStation() {
        let station = this.stationBinder.value as any;
        station.type = 'station';
        this.pushOrUpdate(station);
        this.closeStationDialog();
    }

    private edit(): void {
        let selected = this.grid.selectedItems[0] as any;
        if (selected) {
            let type = selected.type;
            if (type === 'rest') {
                this.restBinder.read(selected);
                this.dialogRestOpened = true;
            } else if (type === 'station') {
                this.stationBinder.read(selected);
                this.dialogStationOpened = true;
            }
        }
    }

    private copy(): void {
        let selected = this.grid.selectedItems[0];
        if (selected) {
            let copy = JSON.parse(JSON.stringify(selected));
            copy.id = randomString(10);
            const dropIndex = this.items.indexOf(selected) + 1;
            this.items.splice(dropIndex, 0, copy);
            this.items = [...this.items];
        }
    }

    private delete(): void {
        let selected = this.grid.selectedItems[0];
        if (selected) {
            this.items = this.items.filter((element) => !deepEquals(selected, element));
        }
    }

    private pushOrUpdate(element: WorkoutElement) {
        const exist = this.items.some((c) => (c as any).id === (element as any).id);
        if (exist) {
            this.items = this.items.map((existing) => {
                if ((existing as any).id === (element as any).id) {
                    return element;
                } else {
                    return existing;
                }
            });
        } else {
            this.items.push(element);
        }
    }
}

