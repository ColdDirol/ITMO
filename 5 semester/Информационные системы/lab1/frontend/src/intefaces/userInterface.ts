export interface IUser {
  username: string;
  email: string;
  password: string;
  role: RoleEnum;
}

export enum RoleEnum {
  ADMIN = "ADMIN",
  USER = "USER",
  EGOSHIN = "EGOSHIN",
}
