export const ADMIN = 'ROLE_ADMIN';
export const USER = 'ROLE_USER';
export const GUEST = 'ROLE_GUEST';

export type UserRoles =
  | typeof ADMIN
  | typeof USER
  | typeof GUEST;


export interface IUserInfo {
  sub: string;
  email_verified: boolean;
  name: string;
  preferred_username: string;
  given_name: string;
  family_name: string;
  email: string;
}
