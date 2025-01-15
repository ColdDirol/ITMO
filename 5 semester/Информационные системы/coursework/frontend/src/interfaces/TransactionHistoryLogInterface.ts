export interface TransactionHistoryLogRequest {
    userEmail: string;
    page: number;
    size: number;
}

export interface TransactionHistoryLogInterface {
    id: number;
    date: string;
    accountFrom: number,
    accountTo: number,
    senderCurrency: string,
    receiverCurrency: string,
    amountInSenderCurrency: number,
    amountInReceiverCurrency: number,
}