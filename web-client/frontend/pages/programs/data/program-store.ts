import {makeAutoObservable, observable} from 'mobx';

export class ProgramStore {

    formVisible: boolean = false;

    constructor() {
        makeAutoObservable(
            this,
            {
                formVisible: observable.ref,
            },
            {autoBind: true}
        );
    }
}