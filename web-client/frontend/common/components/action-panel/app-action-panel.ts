import '@vaadin/button';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/notification';
import '@vaadin/polymer-legacy-adapter';
import {html} from 'lit';
import {customElement, property, query} from 'lit/decorators.js';
import {Button} from "@vaadin/button";
import {uiStore} from "Frontend/common/stores/app-store";
import {View} from "Frontend/common/views/view";
import {AppForm} from "Frontend/common/components/app-form";

@customElement('app-action-panel')
export abstract class AppActionPanel<T> extends View {

    @query('#copyBtn')
    protected copyBtn!: Button;

    @query('#deleteBtn')
    protected deleteBtn!: Button;

    @property()
    public targetFormId: string = "#form";

    protected form: AppForm<T> | null = null;

    protected abstract getFilterValue(): string;

    protected abstract getSelected(): T | null;

    protected abstract getNew(): T;

    protected abstract updateFilterValue(e: { target: HTMLInputElement }): void;

    protected abstract hasSelected(): boolean;

    protected abstract onDelete(): void;

    protected abstract onCopy(): void;

    protected firstUpdated(_changedProperties: any) {
        super.firstUpdated(_changedProperties);
        this.form = document.querySelector(this.targetFormId) as unknown as AppForm<T>;
    }

    protected render() {
        return html`
            <div id="actionPanel">
                <vaadin-text-field
                        .value=${this.getFilterValue()}
                        @input=${this.updateFilterValue}
                        clear-button-visible>
                    <vaadin-icon slot="prefix" icon="vaadin:search"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Search field"></vaadin-tooltip>
                </vaadin-text-field>
                <vaadin-button
                        @click=${this.switchAddFormVisible}
                        ?disabled=${this.hasSelected()}
                        title="add"
                        theme="tertiary success icon">
                    <vaadin-icon icon="vaadin:plus-circle-o"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Add button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        @click=${this.switchEditFormVisible}
                        ?disabled=${!this.hasSelected()}
                        title="edit"
                        theme="tertiary icon">
                    <vaadin-icon icon="vaadin:edit"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Edit button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        id="copyBtn"
                        @click=${this.onCopy}
                        ?disabled=${!this.hasSelected()}
                        title="copy"
                        theme="tertiary contrast icon">
                    <vaadin-icon icon="vaadin:copy-o"></vaadin-icon>
                    <vaadin-tooltip slot="tooltip" text="Copy button"></vaadin-tooltip>
                </vaadin-button>
                <vaadin-button
                        id="deleteBtn"
                        @click=${this.onDelete}
                        ?disabled=${!this.hasSelected()}
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

    protected switchEditFormVisible() {
        if (this.form) {
            if (this.form.visible()) {
                this.form.close();
            } else {
                const selected = this.getSelected();
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
                this.form.open(this.getNew());
            }
        }
    }
}
