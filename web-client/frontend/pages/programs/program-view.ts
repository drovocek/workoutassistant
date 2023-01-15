import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column';
import '@vaadin/vertical-layout';
import '@vaadin/horizontal-layout';
import '@vaadin/icon';
import '@vaadin/icons';
import './components/program-form';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {View} from '../../common/views/view';
import type {Grid} from '@vaadin/grid';
import {AppForm} from "Frontend/common/components/app-form";
import {_getPropertyModel, StringModel} from "@hilla/form";
import {programStore, workoutStore} from "Frontend/common/stores/app-store";

@customElement('program-view')
export class ProgramView extends View {

    @query('#grid')
    private grid!: Grid;

    @query('#program-form')
    private form!: AppForm<Program>;

    async connectedCallback() {
        super.connectedCallback();
        this.autorun(() => {
            if (programStore.formVisible) {
                this.classList.add("editing");
                console.log("editing")
            } else {
                this.classList.remove("editing");
            }
        });
    }

    render() {
        return html`
            <vaadin-horizontal-layout class="content">
                <vaadin-vertical-layout class="grid-wrapper">
                    <vaadin-grid
                            id="grid"
                            theme="no-border">
                        <vaadin-grid-sort-column path="title" auto-width></vaadin-grid-sort-column>
                    </vaadin-grid>
                    <vaadin-horizontal-layout
                            class="grid-button-panel"
                            theme="spacing padding">
                        <vaadin-button
                                id="plus-button"
                                class="plus-button"
                                theme="primary error"
                                @click=${this.openAddForm}>
                            <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
                            Create program
                        </vaadin-button>
                    </vaadin-horizontal-layout>
                </vaadin-vertical-layout>
                <program-form id="program-form" class="form" ?hidden="${!programStore.formVisible}"></program-form>
            </vaadin-horizontal-layout>
        `;
    }

    private openAddForm() {
        programStore.formVisible = true;
    }
}

export interface Program {
    id?: string;
}

export default class ProgramModel<T extends Program = Program> {
    declare static createEmptyValue: () => Program;

    get id(): StringModel {
        // @ts-ignore
        return this[_getPropertyModel]('id', StringModel, [true]);
    }
}