import {LoginInterface, RegisterInterface} from "../interfaces/AuthInterface.ts";
import axios from 'axios';

class AuthApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1/auth';
    }

    async register(
        user: RegisterInterface
    ): Promise<string> {
        try {
            console.log("rigistradion data: ", user);

            const response = await axios.post<{ token: string }>(
                `${this.API_URL}/register`,
                user,
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            console.log(response.data.token);

            return response.data.token;
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.log('Error Response:', error.response?.data);
                console.log('Error Status:', error.response?.status);
                console.log('Error Headers:', error.response?.headers);
            }
            throw error;
        }
    }

    async login(
        user: LoginInterface
    ) {
        try {
            const response = await axios.post<{ token: string }>(
                `${this.API_URL}/login`,
                user,
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            return response.data.token;
        } catch (error) {
            throw error;
        }
    }

}

const authApi = new AuthApi();
export default authApi;
