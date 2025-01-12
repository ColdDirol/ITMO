import {LoginInterface, RegisterInterface} from "../interfaces/AuthInterface.ts";
import useUserStore from "../store/UserStore.tsx";

class UserApi {
    private API_URL: string;

    constructor() {
        this.API_URL = 'http://172.20.0.5:8080/user-management-service/api/v1';
    }

    async register(
        user: RegisterInterface
    ) {
        try {
            console.log("rigistradion data: ", user);

            const response = await axios.post<{ token: string }>(
                `${this.API_URL}/registration`,
                user,
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );

            return response.data.token;
        } catch (error) {
            console.log(error.response.data.message);
            throw error;
        }
    }

    async login(user: LoginInterface) {
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

const userApi = new UserApi();
export default userApi;
