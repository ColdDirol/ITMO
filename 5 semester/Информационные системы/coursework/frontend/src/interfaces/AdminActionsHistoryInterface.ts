import {UserInfoInterface, UserMainInfoInterface} from "./UserInterface.ts";

export interface AdminActionsHistoryInterface {
    id: number;
    date: string;
    administrator: UserMainInfoInterface;
    action: ActionType;
    user: UserMainInfoInterface;
}

export interface AdminActionsHistoryRequest {
    page: number;
    size: number;
}

export enum ActionType {
    BLOCKED = 'BLOCKED',
    UNBLOCKED = 'UNBLOCKED',
    FROZEN = 'FROZEN',
    UNFROZEN = 'UNFROZEN',

    DELETE = 'DELETE',
    ACTIVE = 'ACTIVATED',
}