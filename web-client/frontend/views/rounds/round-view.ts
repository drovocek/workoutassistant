import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/form-layout';
import '@vaadin/text-field';
import './components/round-form';
import './components/round-details';
import {html} from 'lit';
import {customElement, query, state} from 'lit/decorators.js';
import {View} from '../../view';
import {roundStore, uiStore} from "Frontend/common/stores/app-store";
import {AppForm} from "Frontend/common/components/app-form";
import type {Grid} from '@vaadin/grid';
import Round from "Frontend/generated/ru/soft/common/to/RoundTo";
import {MenuBarItemSelectedEvent} from "@vaadin/menu-bar";
import {gridRowDetailsRenderer} from "@vaadin/grid/lit";
import WorkoutElement from "Frontend/generated/ru/soft/common/data/elements/WorkoutElement";
import {randomString} from "Frontend/common/utils/app-utils";
import {renderTitleWithActionBar} from "Frontend/views/rounds/components/round-component-factory";

@customElement('round-view')
export class RoundView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#round-form')
    private form!: AppForm<Round>;

    private firstSelectionEvent = true;

    @state()
    private detailsOpenedItem: Round[] = [];

    @state()
    private dialogOpened = false;

    private dataBeforeDetailsOpen: WorkoutElement[] = [];

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (roundStore.formVisible) {
                this.classList.add("editing");
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <vaadin-button
                    id="plus-button"
                    class="plus-button"
                    theme="primary error"
                    @click=${this.openAddForm}
                    ?hidden=${roundStore.formVisible || this.detailsOpenedItem.length !== 0}>
                <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
                Create round
            </vaadin-button>
            <vaadin-horizontal-layout class="content">
                <vaadin-vertical-layout class="grid-wrapper">
                    <vaadin-text-field
                            class="filter"
                            .value=${roundStore.filterText}
                            @input=${roundStore.updateFilterByEvent}
                            clear-button-visible>
                        <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                        <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                    </vaadin-text-field>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .items=${roundStore.filtered}
                            @active-item-changed=${this.handleGridSelection}
                            .detailsOpenedItems=${this.detailsOpenedItem}
                            ${this.renderDetails()}>
                        <vaadin-grid-sort-column path="title" header="Title" auto-width
                                                 ${renderTitleWithActionBar(this.processClick())}></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="note" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </vaadin-vertical-layout>
                <round-form id="round-form" class="form" ?hidden=${!roundStore.formVisible}></round-form>
            </vaadin-horizontal-layout>
            <vaadin-notification
                    theme=${uiStore.message.error ? 'error' : 'success'}
                    position="bottom-start"
                    .opened=${uiStore.message.open}
                    .renderer=${(root: HTMLElement) => (root.textContent = uiStore.message.text)}>
        `;
    }

    private openAddForm() {
        roundStore.setSelected(null);
        roundStore.formVisible = true;
        this.grid.selectedItems = [];
    }

    private handleGridSelection(event: CustomEvent) {
        this.form.close();

        const item: Round = event.detail.value as Round;
        this.grid.selectedItems = item ? [item] : [];

        if (this.firstSelectionEvent) {
            this.firstSelectionEvent = false;
            return;
        }

        this.detailsOpenedItem = item ? [item] : [];

        if (item === null && roundStore.selected) {
            roundStore.update(roundStore.selected);
        }

        this.dataBeforeDetailsOpen = item ? [...item.workoutSchema.workoutElements as WorkoutElement[]] : [];

        roundStore.setSelected(item);
    }

    private processClick() {
        return (e: MenuBarItemSelectedEvent, round: Round) => {
            let command = e.detail.value.text;
            let id = round.id;
            if (command === 'Delete' && id) {
                roundStore.delete(id);
            } else if (command === 'Copy') {
                roundStore.copy(round);
            } else if (command === 'Edit') {
                this.form.open(round);
            }
        }
    }

    private renderDetails() {
        return gridRowDetailsRenderer<Round>(
            (round) => {
                let workoutElements = round.workoutSchema.workoutElements as WorkoutElement[];
                workoutElements.map(v => (v as any).id = randomString(10));
                return html`
                    <round-details .items=${workoutElements}></round-details>`
            },
            []
        );
    }
}
