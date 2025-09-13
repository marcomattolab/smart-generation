import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-page.html',
  styleUrls: ['./profile-page.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProfilePage implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);

  userData = signal<any>(null);

  ngOnInit() {
    this.oidcSecurityService.userData$.subscribe(({ userData }) => {
      this.userData.set(userData);
    });
  }
}
