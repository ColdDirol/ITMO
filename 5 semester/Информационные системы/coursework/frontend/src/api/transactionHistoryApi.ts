import {BankAccountInterface} from "../interfaces/BankAccountInterface.ts";
import axios from "axios";
import {TransactionHistoryLogRequest} from "../interfaces/TransactionHistoryLogInterface.ts";

class TransactionHistoryApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/account-management-service/api/v1/transaction-history-log';
    }

    async getHistoryForAllBankAccounts(request: TransactionHistoryLogRequest) {
        try {
            console.log("creation data: ", request);

            const response = await axios.post<BankAccountInterface>(
                `${this.API_URL}`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async exportHistoryForAllBankAccounts(request: TransactionHistoryLogRequest) {
        try {
            console.log("creation data: ", request);

            const response = await axios.post(
                `${this.API_URL}/export`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                    responseType: 'blob', // важно: указать тип ответа как 'blob'
                }
            );

            // Создание ссылки для скачивания
            const downloadUrl = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.setAttribute('download', 'transaction_logs.csv');
            document.body.appendChild(link);
            link.click();

            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }


}

const transactionHistoryApi = new TransactionHistoryApi();
export default transactionHistoryApi;