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
import '@vaadin/integer-field';
import '@vaadin/upload';
import type Sort from 'Frontend/generated/dev/hilla/mappedtypes/Sort';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {html} from 'lit';
import {customElement, property, query} from 'lit/decorators.js';
import {View} from '../view';

import {ExerciseEndpoint} from "Frontend/generated/endpoints";
import ExerciseTo from "Frontend/generated/ru/soft/common/to/ExerciseTo";
import ExerciseToModel from 'Frontend/generated/ru/soft/common/to/ExerciseToModel';

@customElement('exercise-view')
export class ExerciseView extends View {
    @query('#grid')
    private grid!: Grid;

    @property({type: Number})
    private gridSize = 0;

    private gridDataProvider = this.getGridData.bind(this);

    private binder = new Binder<ExerciseTo, ExerciseToModel>(this, ExerciseToModel);

    render() {
        return html`
            <vaadin-split-layout>
                <div class="grid-wrapper">
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
                        <vaadin-form-layout
                        >
                            <vaadin-text-field
                                    label="Title"
                                    id="title"
                                    ${field(this.binder.model.title)}></vaadin-text-field>
                            <vaadin-text-field
                                    label="Description"
                                    id="description"
                                    ${field(this.binder.model.description)}></vaadin-text-field>
                            <vaadin-integer-field
                                    label="Complexity"
                                    id="complexity"
                                    ${field(this.binder.model.complexity)}></vaadin-integer-field>
                        </vaadin-form-layout>
                    </div>
                    <vaadin-horizontal-layout class="button-layout">
                        <vaadin-button theme="primary" @click=${this.save}>Save</vaadin-button>
                        <vaadin-button theme="tertiary" @click=${this.cancel}>Cancel</vaadin-button>
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
        const data = await ExerciseEndpoint.list({pageNumber: params.page, pageSize: params.pageSize, sort});
        callback(data);
    }

    async connectedCallback() {
        super.connectedCallback();
        this.gridSize = (await ExerciseEndpoint.count()) ?? 0;
    }

    private async itemSelected(event: CustomEvent) {
        const item: ExerciseTo = event.detail.value as ExerciseTo;
        this.grid.selectedItems = item ? [item] : [];

        if (item) {
            const fromBackend = await ExerciseEndpoint.get(item.id!);
            fromBackend ? this.binder.read(fromBackend) : this.refreshGrid();
        } else {
            this.clearForm();
        }
    }

    private async save() {
        try {
            const isNew = !this.binder.value.id;
            await this.binder.submitTo(ExerciseEndpoint.update);
            if (isNew) {
                // We added a new item
                this.gridSize++;
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
