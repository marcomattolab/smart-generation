export const ADMIN = 'ROLE_ADMIN';
export const USER = 'ROLE_USER';
export const GUEST = 'ROLE_GUEST';

export type UserRoles =
  | typeof ADMIN
  | typeof USER
  | typeof GUEST;
