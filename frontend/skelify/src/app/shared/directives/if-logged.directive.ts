import { Directive, OnDestroy, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subject, distinctUntilChanged, takeUntil } from 'rxjs';
import { AuthService } from '../../services/auth.service';

/**
 *  To place this for conditional rendering if user is Logged In --> *appIfLogged
 *  ex. <button *appIfLogged> click </button>
 */
@Directive({
  selector: '[appIfLogged]',
})
export class IfLoggedDirective implements OnInit, OnDestroy {
  private destroy$ = new Subject();

  constructor(
    private template: TemplateRef<unknown>,
    private view: ViewContainerRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$
      .pipe(distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe(isLogged => {
        if (isLogged) {
          this.view.createEmbeddedView(this.template);
        } else {
          this.view.clear();
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next(false);
    this.destroy$.complete();
  }
}
