export interface UserInfoInterface {
    id: number,
    email: string,
    role: RoleEnum,
    phone: string,
    name: string,
    sex: SexEnum,
    dateOfBirth: string,
    status: StatusEnum,
}

export interface UserMainInfoInterface {
    id: number,
    email: string,
    name: string,
}

export interface UserSearchRequest {
    searchTerm: string,
    page: number,
    size: number,
}

export enum RoleEnum {
    ADMIN = "ADMIN",
    USER = "USER",
    SUPER_ADMIN = "SUPER_ADMIN",
}

export enum SexEnum {
    MAN = "MAN",
    WOMAN = "WOMAN",
}

export enum StatusEnum {
    ACTIVE = "ACTIVE",
    FROZEN = "FROZEN",
    DELETED = "DELETED",
    BLOCKED = "BLOCKED",
}


export const roles = Object.values(RoleEnum).map(role => ({ label: role, value: role }));
export const sexes = Object.values(SexEnum).map(sex => ({ label: sex, value: sex }));
export const statuses = Object.values(StatusEnum).map(status => ({ label: status, value: status }));