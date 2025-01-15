import {BankAccountInterface, BankAccountRequestInterface} from "../interfaces/BankAccountInterface.ts";
import axios from 'axios';

class BankAccountApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/account-management-service/api/v1/bank-account';
    }

    async create(request: BankAccountInterface): Promise<BankAccountInterface> {
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


    async getUserBankAccountsByEmail(request: BankAccountRequestInterface): Promise<BankAccountInterface[]> {
        try {
            console.log("bank-account request data: ", request);

            const response = await axios.post<BankAccountInterface[]>(
                `${this.API_URL}/user`,
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

const bankAccountApi = new BankAccountApi();
export default bankAccountApi;