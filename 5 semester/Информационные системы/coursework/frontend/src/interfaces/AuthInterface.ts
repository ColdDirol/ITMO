import {SexEnum} from "./UserInterface.ts";

export interface RegisterInterface {
    id: number,
    email: string,
    password: string,
    phone: string,
    name: string,
    sex: SexEnum,
    dateOfBirth: string,
}

export interface LoginInterface {
    email: string,
    password: string,
}
