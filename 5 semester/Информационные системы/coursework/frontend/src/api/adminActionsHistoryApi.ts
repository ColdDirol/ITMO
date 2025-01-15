import {AdminActionsHistoryRequest} from "../interfaces/AdminActionsHistoryInterface.ts";
import axios from "axios";

class AdminActionsHistoryApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1/admin/actions';
    }

    async getAllHistory(request: AdminActionsHistoryRequest) {
        try {
            console.log("requesting data: ", request);

            const response = await axios.post<{ token: string }>(
                `${this.API_URL}/history/all`,
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

const adminActionsHistoryApi = new AdminActionsHistoryApi();
export default adminActionsHistoryApi;