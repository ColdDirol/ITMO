import axios from "axios";

class UserApiService {
  private API_URL: string;

  constructor() {
    this.API_URL = "http://localhost:8080/backend-1.0-SNAPSHOT/api/auth/";
  }

  async register(userData: {
    username: string;
    email: string;
    password: string;
    role: string;
  }) {
    try {
      console.log("userData:", userData);
      const response = await axios.post(
        `${this.API_URL}registration`,
        userData,
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

  async login(credentials: { email: string; password: string }) {
    try {
      const response = await axios.post(`${this.API_URL}login`, credentials, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      return response.data.token;
    } catch (error) {
      throw error;
    }
  }
}

const userApiService = new UserApiService();
export default userApiService;
