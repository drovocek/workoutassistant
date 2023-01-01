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
import {html} from 'lit';
import {customElement, property, query, state} from 'lit/decorators.js';
import {View} from '../common/views/view';
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import WorkoutRoundTo from "Frontend/generated/ru/soft/common/to/WorkoutRoundTo";
import {gridRowDetailsRenderer} from "@vaadin/grid/lit";
import WorkoutStationSnapshot from "Frontend/generated/ru/soft/common/data/snapshot/WorkoutStationSnapshot";

@customElement('round-view')
export class RoundView extends View {

    @query('#grid')
    private grid!: Grid;

    @property({type: Number})
    private gridSize = 0;

    @state()
    private detailsOpenedItem: WorkoutRoundTo[] = [];

    @state()
    private detailsData: Array<WorkoutStationSnapshot | undefined> = [];

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
            <div class="toolbar flex gap-s">
                <vaadin-text-field
                        placeholder="Filter by name"
                        .value=${roundStore.filterText}
                        @input=${this.updateFilter}
                        clear-button-visible
                ></vaadin-text-field>
                <vaadin-button @click=${this.clearForm}>Add round</vaadin-button>
            </div>
            <div class="content flex gap-m h-full">
                <vaadin-grid
                        id="grid"
                        theme="no-border"
                        .size=${this.gridSize}
                        .items=${roundStore.filtered}
                        .detailsOpenedItems="${this.detailsOpenedItem}"
                        @active-item-changed="${(e: GridActiveItemChangedEvent<WorkoutRoundTo>) => (this.detailsOpenedItem = [e.detail.value])}"
                        ${gridRowDetailsRenderer<WorkoutRoundTo>(
                                (round) => {
                                    this.detailsData = [];

                                    console.log(round.roundSchema)
                                    if (round.roundSchema && round.roundSchema.roundStations) {
                                        this.detailsData = round.roundSchema.roundStations;
                                    }

                                    return html`
                                        <vaadin-grid .items=${this.detailsData}>
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
                                        </vaadin-grid>
                                    `
                                },
                                []
                        )}>
                    <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                    <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                </vaadin-grid>
            </div>
            <vaadin-notification
                    theme=${uiStore.message.error ? 'error' : 'success'}
                    position="bottom-start"
                    .opened=${uiStore.message.open}
                    .renderer=${(root: HTMLElement) =>
                            (root.textContent = uiStore.message.text)}>
            </vaadin-notification>
        `;
    }

    private updateFilter(e: { target: HTMLInputElement }) {
        roundStore.updateFilter(e.target.value);
    }

    private showDetails() {

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
