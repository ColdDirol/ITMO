import axios from 'axios';
import {AdminActionOnUserInterface} from "../interfaces/AdminInterface.ts";

class AdminApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1/admin/actions';
    }

    async blockUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/block`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User blocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async unblockUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/unblock`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User unblocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async frozeUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/froze`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User  blocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async unfrozeUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/unfroze`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User  blocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async deleteUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/delete`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User  blocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async activateUser(request: AdminActionOnUserInterface) {
        try {
            console.log("action data: ", request);

            const response = await axios.post(
                `${this.API_URL}/activate`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (response.status === 200) {
                console.log('User  blocked successfully:', response.data);
            } else {
                console.log('Unexpected response status:', response.status);
            }
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

const adminApi = new AdminApi();
export default adminApi;