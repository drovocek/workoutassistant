import '@vaadin/button';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import {html} from 'lit';
import {customElement, query} from 'lit/decorators.js';
import {Button} from "@vaadin/button";
import {GeneralStore} from "Frontend/common/stores/general-store";
import {uiStore} from "Frontend/common/stores/app-store";
import {View} from "Frontend/common/views/view";
import {AppForm} from "Frontend/common/components/app-form";

@customElement('app-action-panel')
export abstract class AppActionPanel<T> extends View {

    @query('#copyBtn')
    protected copyBtn!: Button;

    @query('#deleteBtn')
    protected deleteBtn!: Button;

    protected form: AppForm<T> | null = null;

    protected abstract generalStore(): GeneralStore<T>;

    protected firstUpdated(_changedProperties: any) {
        super.firstUpdated(_changedProperties);
        this.form = document.querySelector("#form") as unknown as AppForm<T>;
    }

    protected render() {
        return html`
            <div id="actionPanel">
                <vaadin-text-field
                        .value=${this.generalStore().filterText}
                        @input=${this.updateFilter}
                        clear-button-visible>
                    <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                </vaadin-text-field>
                <vaadin-button
                        @click=${this.switchAddFormVisible}
                        ?disabled=${this.generalStore().hasSelected()}
                        title="add"
                        theme="tertiary success icon">
                    <vaadin-icon icon="vaadin:plus-circle-o"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Add button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        @click=${this.switchEditFormVisible}
                        ?disabled=${!this.generalStore().hasSelected()}
                        title="edit"
                        theme="tertiary icon">
                    <vaadin-icon icon="vaadin:edit"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Edit button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        id="copyBtn"
                        @click=${this.copy}
                        ?disabled=${!this.generalStore().hasSelected()}
                        title="copy"
                        theme="tertiary contrast icon">
                    <vaadin-icon icon="vaadin:copy-o"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Copy button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        id="deleteBtn"
                        @click=${this.delete}
                        ?disabled=${!this.generalStore().hasSelected()}
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
        `;
    }

    protected updateFilter(e: { target: HTMLInputElement }) {
        this.generalStore().updateFilter(e.target.value);
    }

    protected switchEditFormVisible() {
        if (this.form) {
            if (this.form.visible()) {
                this.form.close();
            } else {
                const selected = this.generalStore().selected;
                if (selected) {
                    this.form.open(selected);
                }
            }
        }
    }

    protected switchAddFormVisible() {
        if (this.form) {
            if (this.form.visible()) {
                this.form.close();
            } else {
                this.form.open(this.generalStore().createNew());
            }
        }
    }

    protected copy() {
        this.copyBtn.disabled = true;
        this.generalStore().copy()
            .finally(() => this.copyBtn.disabled = false);
    }

    protected delete() {
        this.deleteBtn.disabled = true;
        this.generalStore().delete()
            .finally(() => this.copyBtn.disabled = false);
    }
}
