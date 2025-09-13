import { Directive, Input, OnDestroy, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subject, distinctUntilChanged, takeUntil } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { UserRoles } from '../../models/core/auth.model';

/**
 *  To place this for conditional rendering based on ROLES :
 *
 *  <....    *appIfRole="['ROLE_GLOBAL_ADMIN','ROLE_GUEST']"  >
 *
 *  ex. <button *appIfRole="['ROLE_GLOBAL_ADMIN']"> click </button>
 *  ex. <div *appIfRole="['ROLE_GLOBAL_ADMIN', 'ROLE_TENANT_ADMIN']"> my-div </div>
 */
@Directive({
  selector: '[appIfRole]',
})
export class IfRoleDirective implements OnInit, OnDestroy {
  @Input() appIfRole: UserRoles[] = [];
  private destroy$ = new Subject();
  i!: number;
  includeRole = false;

  constructor(
    private template: TemplateRef<unknown>,
    private view: ViewContainerRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // FIXME == COMPLETE THIS
    /*this.authService.isAuthenticated$
      .pipe(distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe(roles => {
        this.includeRole = false;
        if (roles) {
          this.view.clear();
          for (this.i = 0; this.i < roles.length; this.i++) {
            if (this.appIfRole.includes(roles[this.i])) {
              this.includeRole = true;
            }
          }
        }

        if (this.includeRole) {
          this.view.createEmbeddedView(this.template);
        } else {
          this.view.clear();
        }
      });*/
  }

  ngOnDestroy(): void {
    this.destroy$.next(false);
    this.destroy$.complete();
  }
}
