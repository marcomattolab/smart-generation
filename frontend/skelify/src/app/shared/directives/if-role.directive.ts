import { Directive, Input, TemplateRef, ViewContainerRef, inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { distinctUntilChanged } from 'rxjs';

@Directive({
  selector: '[appIfRole]',
  standalone: true,
})
export class IfRoleDirective {
  @Input() appIfRole: string[] = [];

  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly template = inject(TemplateRef<unknown>);
  private readonly view = inject(ViewContainerRef);

  constructor() {
    this.oidcSecurityService.userData$
      .pipe(
        distinctUntilChanged(),
        takeUntilDestroyed()
      )
      .subscribe(({ userData }) => {
        if (userData && userData.roles && this.appIfRole.some(role => userData.roles.includes(role))) {
          this.view.createEmbeddedView(this.template);
        } else {
          this.view.clear();
        }
      });
  }
}
