import {appStore} from 'Frontend/common/stores/app-store';
import {makeAutoObservable} from "mobx";

export class ExerciseSelectorStore {

    filterText = '';

    constructor() {
        makeAutoObservable(
            this,
            {
            },
            {autoBind: true}
        );
    }

    get filtered() {
        console.log("!!!!")
        const filter = new RegExp(this.filterText, 'i');
        return appStore.exerciseStore.data.filter((entity) =>
            filter.test(`${entity.title}`)
        );
    }

    updateFilter(filterText: string) {
        this.filterText = filterText;
    }
}