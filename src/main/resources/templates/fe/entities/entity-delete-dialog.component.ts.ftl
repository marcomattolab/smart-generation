import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ${iNometabella} } from 'app/shared/model/${nometabella}.model';
import { ${Nometabella}Service } from './${nometabella}.service';
@Component({
    selector: 'jhi-${nometabella}-delete-dialog',
    templateUrl: './${nometabella}-delete-dialog.component.html'
})
export class ${Nometabella}DeleteDialogComponent {
    ${nometabella}: ${iNometabella};
    constructor(private ${nometabella}Service: ${Nometabella}Service, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}
    clear() {
        this.activeModal.dismiss('cancel');
    }
    confirmDelete(id: number) {
        this.${nometabella}Service.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: '${nometabella}ListModification',
                content: 'Deleted an ${nometabella}'
            });
            this.activeModal.dismiss(true);
        });
    }
}
@Component({
    selector: 'jhi-${nometabella}-delete-popup',
    template: ''
})
export class ${Nometabella}DeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;
    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}
    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ${nometabella} }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(${Nometabella}DeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.${nometabella} = ${nometabella};
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
