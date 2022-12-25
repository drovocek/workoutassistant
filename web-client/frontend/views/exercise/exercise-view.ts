import {Binder, field} from '@hilla/form';
import {EndpointError} from '@hilla/frontend';
import '@vaadin/button';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import type {Grid, GridDataProviderCallback, GridDataProviderParams} from '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import {Notification} from '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import '@vaadin/split-layout';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/integer-field';
import '@vaadin/upload';
import type Sort from 'Frontend/generated/dev/hilla/mappedtypes/Sort';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {html} from 'lit';
import {customElement, property, query, state} from 'lit/decorators.js';
import {View} from '../view';

import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from 'Frontend/generated/ru/soft/common/to/ExerciseToModel';

@customElement('exercise-view')
export class ExerciseView extends View {

    @query('#grid')
    private grid!: Grid;

    @state()
    private isDisableDeleteBtn = true;

    @property({type: Number})
    private gridSize = 0;

    private gridDataProvider = this.getGridData.bind(this);

    private binder = new Binder<ExerciseTo, ExerciseToModel>(this, ExerciseToModel);

    render() {
        return html`
            <vaadin-split-layout>
                <div class="grid-wrapper">
                    <div class="toolbar flex gap-s">
                        <vaadin-text-field placeholder="Filter by title" clear-button-visible></vaadin-text-field>
                    </div>
                    <vaadin-grid
                            id="grid"
                            theme="no-border"
                            .size=${this.gridSize}
                            .dataProvider=${this.gridDataProvider}
                            @active-item-changed=${this.itemSelected}>
                        <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="description" auto-width></vaadin-grid-sort-column>
                        <vaadin-grid-sort-column path="complexity" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                </div>
                <div class="editor-layout">
                    <div class="editor">
                        <vaadin-form-layout>
                            <vaadin-text-field
                                    label="Title"
                                    required
                                    id="title"
                                    ${field(this.binder.model.title)}></vaadin-text-field>
                            <vaadin-text-area
                                    label="Description"
                                    id="description"
                                    ${field(this.binder.model.description)}></vaadin-text-area>
                            <vaadin-integer-field
                                    label="Complexity"
                                    id="complexity"
                                    required
                                    min="1"
                                    max="10"
                                    step-buttons-visible
                                    ${field(this.binder.model.complexity)}></vaadin-integer-field>
                        </vaadin-form-layout>
                    </div>
                    <vaadin-horizontal-layout class="button-layout">
                        <vaadin-button theme="primary" @click=${this.save}>Save</vaadin-button>
                        <vaadin-button theme="tertiary" @click=${this.cancel}>Cancel</vaadin-button>
                        <vaadin-button ?disabled=${this.isDisableDeleteBtn} theme="error" @click=${this.delete}>Delete
                        </vaadin-button>
                    </vaadin-horizontal-layout>
                </div>
            </vaadin-split-layout>
        `;
    }

    private async getGridData(
        params: GridDataProviderParams<ExerciseTo>,
        callback: GridDataProviderCallback<ExerciseTo | undefined>
    ) {
        const sort: Sort = {
            orders: params.sortOrders.map((order) => ({
                property: order.path,
                direction: order.direction == 'asc' ? Direction.ASC : Direction.DESC,
                ignoreCase: false,
            })),
        };
        const data = await ExerciseEndpoint.getPage({pageNumber: params.page, pageSize: params.pageSize, sort});
        callback(data);
    }

    async connectedCallback() {
        super.connectedCallback();
        this.gridSize = (await ExerciseEndpoint.count()) ?? 0;
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

    private async itemSelected(event: CustomEvent) {
        const item: ExerciseTo = event.detail.value as ExerciseTo;
        this.grid.selectedItems = item ? [item] : [];

        if (item) {
            const fromBackend = await ExerciseEndpoint.get(item.id!);
            fromBackend ? this.binder.read(fromBackend) : this.refreshGrid();
            this.isDisableDeleteBtn = false;
        } else {
            this.clearForm();
            this.isDisableDeleteBtn = true;
        }
    }

    private async save() {
        try {
            const isNew = !this.binder.value.id;
            if (isNew) {
                // We added a new item
                await this.binder.submitTo(ExerciseEndpoint.add);
                this.gridSize++;
            } else {
                await this.binder.submitTo(ExerciseEndpoint.update);
            }
            this.clearForm();
            this.refreshGrid();
            Notification.show(`ExerciseTo details stored.`, {position: 'bottom-start'});
        } catch (error: any) {
            if (error instanceof EndpointError) {
                Notification.show(`Server error. ${error.message}`, {theme: 'error', position: 'bottom-start'});
            } else {
                throw error;
            }
        }
    }

    private async delete() {
        try {
            const deletedId = this.binder.value.id;
            if (deletedId !== undefined) {
                await ExerciseEndpoint.delete(deletedId);
                this.gridSize--;
                this.clearForm();
                this.refreshGrid();
                Notification.show(`Delete ExerciseTo with id = ${deletedId}.`, {position: 'bottom-start'});
            }
        } catch (error: any) {
            if (error instanceof EndpointError) {
                Notification.show(`Server error. ${error.message}`, {theme: 'error', position: 'bottom-start'});
            } else {
                throw error;
            }
        }
    }

    private cancel() {
        this.grid.activeItem = undefined;
    }

    private clearForm() {
        this.binder.clear();
    }

    private refreshGrid() {
        this.grid.selectedItems = [];
        this.grid.clearCache();
    }
}
