import { IUser } from "./userInterface";

export interface IAdminRequest {
  id: number;
  user: IUser;
  requestDate: number;
  isApproved: boolean | null;
}

export function formatDate(timestamp: number): string {
  const date = new Date(timestamp);
  return date.toLocaleString();
}

export interface IAdminElement {
  id: number;
  user: IUser;
  requestDate: string;
  isApproved: boolean | null;
}
