<nav id="sidebar" class="navbar-dark bg-dark" [ngClass]="{'hidden': sideNavService.hideSideNav }">
  <ul class="navbar-nav">
    <li class="nav-item active">
      <a class="nav-link" routerLink="/" href="#">Home</a>
    </li>

    <!-- ENTITIES -->
    <li class="nav-item" *jhiHasAnyAuthority="[${gAuthorities}]">
      <a class="nav-link">
        Tables
        <i class="fa fa-caret-down"></i>
      </a>
      <div class="dropdown-container">
<#list tables as table>
        <a *jhiHasAnyAuthority="[${table.authorities}]" class="nav-link" href="#" routerLink="${table.fieldName}">${table.entityName}</a>
</#list>
      </div>
    </li>
  </ul>
</nav>
