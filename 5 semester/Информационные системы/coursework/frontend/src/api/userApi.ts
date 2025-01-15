import axios from 'axios';
import {UserInfoInterface, UserSearchRequest} from "../interfaces/UserInterface.ts";

class UserApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1/user';
    }

    async searchUser(request: UserSearchRequest): Promise<UserInfoInterface[]> {
        try {
            console.log("user request data: ", request);

            const response = await axios.post<UserInfoInterface[]>(
                `${this.API_URL}/search`,
                request,
                {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            return response.data.content;
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

const userApi = new UserApi();
export default userApi;