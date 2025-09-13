import { Directive, TemplateRef, ViewContainerRef, inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { distinctUntilChanged } from 'rxjs';

@Directive({
  selector: '[appIfLogged]',
  standalone: true,
})
export class IfLoggedDirective {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly template = inject(TemplateRef<unknown>);
  private readonly view = inject(ViewContainerRef);

  constructor() {
    this.oidcSecurityService.isAuthenticated$
      .pipe(
        distinctUntilChanged(),
        takeUntilDestroyed()
      )
      .subscribe(({ isAuthenticated }) => {
        if (isAuthenticated) {
          this.view.createEmbeddedView(this.template);
        } else {
          this.view.clear();
        }
      });
  }
}
