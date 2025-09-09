import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { ${iNometabella} } from 'app/shared/model/${nometabella}.model';

@Component({
    selector: 'jhi-${nometabella}-detail',
    templateUrl: './${nometabella}-detail.component.html'
})

export class  ${Nometabella}DetailComponent implements OnInit {
    ${nometabella}: ${iNometabella};

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ${nometabella} }) => {
            this.${nometabella} = ${nometabella};
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
    }

}
