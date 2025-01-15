import {BankAccountInterface, MakeUserAdminRequest} from "../interfaces/BankAccountInterface.ts";
import axios from "axios";

class SuperAdminApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1/admin/super-admin';
    }

    async makeUserAdmin(request: MakeUserAdminRequest) {
        try {
            await axios.post<BankAccountInterface>(
                `${this.API_URL}/make-user-admin`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async dismissAdmin(request: MakeUserAdminRequest) {
        try {
            await axios.post<BankAccountInterface>(
                `${this.API_URL}/dismiss-user`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );
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
const superAdminApi = new SuperAdminApi();
export default superAdminApi;