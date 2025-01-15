import {
    BankAccountInterface,
    TransferRequestInterface
} from "../interfaces/BankAccountInterface.ts";
import axios from 'axios';

class TransactionApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/account-management-service/api/v1/bank-account';
    }

    async transfer(request: TransferRequestInterface) {
        try {
            console.log("creation data: ", request);

            const response = await axios.post<BankAccountInterface>(
                `${this.API_URL}/transfer`,
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


}

const transactionApi = new TransactionApi();
export default transactionApi;
