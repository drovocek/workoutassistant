import {css, html, LitElement} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import '@vaadin/grid';
import type {GridColumnBodyLitRenderer} from '@vaadin/grid/lit.js';
import {columnBodyRenderer} from '@vaadin/grid/lit.js';
import type {GridDragStartEvent} from '@vaadin/grid';
import {applyTheme} from 'Frontend/generated/theme';

@customElement('grid-drag-rows-between-grids')
export class Example extends LitElement {
    protected createRenderRoot() {
        const root = super.createRenderRoot();
        // Apply custom theme (only supported if your app uses one)
        applyTheme(root);
        return root;
    }

    static get styles() {
        return css`
          .grids-container {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
          }

          vaadin-grid {
            width: 300px;
            height: 300px;
            margin-left: 0.5rem;
            margin-top: 0.5rem;
            align-self: unset;
          }
        `;
    }

    // tag::snippet[]
    @state()
    private draggedItem?: Person;

    @state()
    private grid1Items: Person[] = [];

    @state()
    private grid2Items: Person[] = [];

    private startDraggingItem = (event: GridDragStartEvent<Person>) => {
        this.draggedItem = event.detail.draggedItems[0];
    };

    private clearDraggedItem = () => {
        delete this.draggedItem;
    };

    render() {
        return html`
            <div class="grids-container">
                <vaadin-grid
                        .items="${this.grid1Items}"
                        rows-draggable
                        drop-mode="on-grid"
                        @grid-dragstart="${this.startDraggingItem}"
                        @grid-dragend="${this.clearDraggedItem}"
                        @grid-drop="${() => {
                            const draggedPerson = this.draggedItem as Person;
                            const draggedItemIndex = this.grid2Items.indexOf(draggedPerson);
                            if (draggedItemIndex >= 0) {
                                // Remove the item from its previous position
                                this.grid2Items.splice(draggedItemIndex, 1);
                                // Re-assign the array to refresh the grid
                                this.grid2Items = [...this.grid2Items];
                                // Re-assign the array to refresh the grid
                                this.grid1Items = [...this.grid1Items, draggedPerson];
                            }
                        }}"
                >
                    <vaadin-grid-column
                            header="Full name"
                            ${columnBodyRenderer(this.fullNameRenderer, [])}
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="profession"></vaadin-grid-column>
                </vaadin-grid>

                <vaadin-grid
                        .items="${this.grid2Items}"
                        rows-draggable
                        drop-mode="on-grid"
                        @grid-dragstart="${this.startDraggingItem}"
                        @grid-dragend="${this.clearDraggedItem}"
                        @grid-drop="${() => {
                            const draggedPerson = this.draggedItem as Person;
                            const draggedItemIndex = this.grid1Items.indexOf(draggedPerson);
                            if (draggedItemIndex >= 0) {
                                // Remove the item from its previous position
                                this.grid1Items.splice(draggedItemIndex, 1);
                                // Re-assign the array to refresh the grid
                                this.grid1Items = [...this.grid1Items];
                                // Re-assign the array to refresh the grid
                                this.grid2Items = [...this.grid2Items, draggedPerson];
                            }
                        }}"
                >
                    <vaadin-grid-column
                            header="Full name"
                            ${columnBodyRenderer(this.fullNameRenderer, [])}
                    ></vaadin-grid-column>
                    <vaadin-grid-column path="profession"></vaadin-grid-column>
                </vaadin-grid>
            </div>
        `;
    }

    // end::snippet[]

    private fullNameRenderer: GridColumnBodyLitRenderer<Person> = (person) => {
        return html`${person.firstName} ${person.lastName}`;
    };
}
