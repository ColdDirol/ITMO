export interface BankAccountInterface {
    id: number,
    email: string,
    name: string,
    balance: number,
    currencyShortName: CurrencyShortNameEnum
}

export interface BankAccountRequestInterface {
    email: string,
    page: number,
    size: number,
}

export interface TransferRequestInterface {
    idFrom: number,
    idTo: number,
    amount: number,
}

export enum CurrencyShortNameEnum {
    USD = 'USD',
    RUB = 'RUB',
    MUR = 'MUR',
}

export interface MakeUserAdminRequest {
    id: number,
}

export const currencyNames = Object.values(CurrencyShortNameEnum).map(currency => ({ label: currency, value: currency }));