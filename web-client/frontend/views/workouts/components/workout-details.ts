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
import {createIconItem, renderWorkoutElement} from "Frontend/common/utils/component-factory";
import {dialogFooterRenderer, dialogRenderer} from "@vaadin/dialog/lit";
import {DialogOpenedChangedEvent} from "@vaadin/dialog";
import {Binder} from "@hilla/form";
import Rest from "Frontend/generated/ru/soft/common/data/elements/Rest";
import RestModel from "Frontend/generated/ru/soft/common/data/elements/RestModel";
import Station from "Frontend/generated/ru/soft/common/data/elements/Station";
import StationModel from "Frontend/generated/ru/soft/common/data/elements/StationModel";
import DurationUnit from "Frontend/generated/ru/soft/common/data/elements/DurationUnit";
import {deepEquals, randomString} from "Frontend/common/utils/app-utils";
import {renderRestDialog, renderStationDialog} from "../../../common/utils/component-factory";
import {exerciseStore, roundStore, workoutStore} from "Frontend/common/stores/app-store";
import {MenuBarItemSelectedEvent} from '@vaadin/menu-bar';
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import {gridRowDetailsRenderer} from "@vaadin/grid/lit";
import '@vaadin/grid/vaadin-grid-tree-column.js';

@customElement('workout-details')
export class WorkoutDetails extends View {

    @query('#grid')
    private grid!: Grid;

    private firstSelectionEvent = true;

    @property()
    actionsHidden: boolean = false;

    @state()
    private draggedElement?: WorkoutElement;

    @state()
    private dialogRestOpened = false;

    @state()
    private dialogStationOpened = false;

    @property()
    public items: WorkoutElement[] = [];

    @property()
    public rootItem: Round | null = null;

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
        if (this.rootItem === null) {
            if (workoutStore.selected) {
                workoutStore.selected.workoutSchema.workoutElements = this.items;
            }
        } else {
            this.rootItem.workoutSchema.workoutElements = this.items;
        }
    }

    private deselectAll() {
        this.grid.selectedItems = [];
    }

    private menuBarItems = [
        {
            component: createIconItem('my-icons-svg:draw-polygon-solid'),
            tooltip: 'Rounds',
            children: roundStore.data.map(round => {
                (round as any).text = round.title;
                return round;
            })
        }
    ];

    @state()
    private detailsOpenedItem: WorkoutElement[] = [];

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
                    @grid-dragstart=${this.storeDraggingElement}
                    @grid-dragend=${this.clearDraggedElement}
                    @grid-drop=${this.onGridDrop()}
                    .detailsOpenedItems=${this.detailsOpenedItem}
                    ${this.renderDetails()}>
                <vaadin-grid-sort-column path="title"
                                         header="Workout element"
                                         auto-width
                                         ${renderWorkoutElement()}></vaadin-grid-sort-column>
            </vaadin-grid>
            <vaadin-horizontal-layout theme="spacing" ?hidden="${this.actionsHidden}">
                <vaadin-menu-bar
                        theme="icon"
                        .items=${this.menuBarItems}
                        @item-selected=${this.addRound}
                        ?disabled=${workoutStore.selectedWorkoutElement}>
                    <vaadin-tooltip slot="tooltip" text="Add round"></vaadin-tooltip>
                </vaadin-menu-bar>
                <vaadin-button
                        id="station-button"
                        class="station-button overlay-details-button"
                        theme="secondary success icon"
                        @click=${this.openStationDialog}
                        ?disabled=${workoutStore.selectedWorkoutElement}>
                    <vaadin-tooltip slot="tooltip" text="Add station"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:dumbbell-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="rest-button"
                        class="rest-button overlay-details-button"
                        theme="secondary icon"
                        @click=${this.openRestDialog}
                        ?disabled=${workoutStore.selectedWorkoutElement}>
                    <vaadin-tooltip slot="tooltip" text="Add rest"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:clock-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="edit-button"
                        class="edit-button overlay-details-button"
                        theme="secondary icon"
                        ?disabled=${!workoutStore.hasSelectedWorkoutElement()}
                        @click=${this.edit}>
                    <vaadin-tooltip slot="tooltip" text="Edit"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:edit-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="copy-button"
                        class="copy-button overlay-details-button"
                        theme="tertiary icon"
                        ?disabled=${!workoutStore.hasSelectedWorkoutElement()}
                        @click=${this.copy}>
                    <vaadin-tooltip slot="tooltip" text="Copy"></vaadin-tooltip>
                    <vaadin-icon icon="my-icons-svg:copy-solid" slot="prefix"></vaadin-icon>
                </vaadin-button>
                <vaadin-button
                        id="delete-button"
                        class="delete-button overlay-details-button"
                        theme="error icon"
                        ?disabled=${!workoutStore.hasSelectedWorkoutElement()}
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

    private getData() {
        return this.rootItem ? this.rootItem.workoutSchema.workoutElements : this.items;
    }

    private addRound(e: MenuBarItemSelectedEvent): void {
        let round = e.detail.value as Round;
        (round as any).type = 'round';
        (round as any).clientId = randomString(10);
        this.items.push(round);
        this.items = [...this.items];
    }

    private onGridDrop() {
        return (event: GridDropEvent<WorkoutElement>) => {
            let items = this.items;
            const {dropTargetItem, dropLocation} = event.detail;
            if (this.draggedElement && dropTargetItem !== this.draggedElement) {
                const draggedItemIndex = items.indexOf(this.draggedElement);
                items.splice(draggedItemIndex, 1);
                const dropIndex = items.indexOf(dropTargetItem) + (dropLocation === 'below' ? 1 : 0);
                items.splice(dropIndex, 0, this.draggedElement);
                this.items = [...items];
            }
        }
    }

    private handleGridSelection(event: CustomEvent) {
        const item: WorkoutElement = event.detail.value as WorkoutElement;
        this.grid.selectedItems = item ? [item] : [];

        workoutStore.setSelectedWorkoutElement(item);

        if (item !== null && (item as any).type === 'round') {
            this.detailsOpenedItem = item ? [item] : [];
        } else {
            this.detailsOpenedItem = [];
        }

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }
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
        (rest as any).clientId = randomString(10);
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
        (station as any).clientId = randomString(10);
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
        let selected = workoutStore.selectedWorkoutElement as any;
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
        let selected = workoutStore.selectedWorkoutElement;
        if (selected) {
            let copy = JSON.parse(JSON.stringify(selected));
            copy.clientId = randomString(10);
            let items = this.items;
            const dropIndex = items.indexOf(selected) + 1;
            items.splice(dropIndex, 0, copy);
            this.items = [...items];
        }
    }

    private delete(): void {
        let selected = workoutStore.selectedWorkoutElement;
        if (selected) {
            let items = this.items;
            items = items.filter((element) => !deepEquals(selected, element));
            this.items = [...items];
            workoutStore.setSelectedWorkoutElement(null);
        }
    }

    private pushOrUpdate(element: WorkoutElement) {
        let items = this.items;
        const exist = items.some((c) => (c as any).clientId === (element as any).clientId);
        if (exist) {
            items = items.map((existing) => {
                if ((existing as any).clientId === (element as any).clientId) {
                    return element;
                } else {
                    return existing;
                }
            });
        } else {
            items.push(element);
        }
        this.items = [...items];
        workoutStore.setSelectedWorkoutElement(null);
    }

    private renderDetails() {
        return gridRowDetailsRenderer<WorkoutElement>(
            (workoutElement) => {
                if ((workoutElement as any).type === 'round') {
                    let round = workoutElement as Round;
                    let workoutElements = (workoutElement as Round).workoutSchema.workoutElements as WorkoutElement[];
                    workoutElements.map(v => (v as any).clientId = randomString(10));
                    return html`
                        <workout-details .rootItem=${round}
                                         .items=${workoutElements}
                                         .actionsHidden=${true}></workout-details>`
                } else {
                    return html`
                    `
                }
            },
            []
        );
    }
}

