export interface RegisterInterface {
    id: number,
    email: string,
    password: string,
    role: RoleEnum,
    phone: string,
    name: string,
    sex: SexEnum,
    dateOfBirth: string,
    status: StatusEnum,
}

export interface LoginInterface {
    email: string,
    password: string,
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