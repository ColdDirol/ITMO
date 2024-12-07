import axios, { AxiosResponse } from "axios";
import {
  formatDate,
  IAdminElement,
  IAdminRequest,
} from "../intefaces/adminInterface";

class AdminApiService {
  private API_URL: string;

  constructor() {
    this.API_URL = "http://localhost:8080/backend-1.0-SNAPSHOT/api/admin";
    axios.interceptors.request.use(
      (config) => {
        config.headers["Authorization"] = `Bearer ${localStorage.getItem(
          "token"
        )}`;
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );
  }

  private async handleRequest<T>(
    request: Promise<AxiosResponse<T>>
  ): Promise<T> {
    try {
      const response = await request;
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        if (error.response.status === 401) {
          console.error("Unauthorized access - redirecting to login");
          window.location.href = "/logout";
        }
      }
      throw error;
    }
  }

  private convertToAdminElement(request: IAdminRequest): IAdminElement {
    return {
      ...request,
      requestDate: formatDate(request.requestDate),
    };
  }

  public async getPendingRequests(): Promise<IAdminElement[]> {
    const requests = await this.handleRequest(
      axios.get<IAdminRequest[]>(`${this.API_URL}/requests`)
    );
    return requests.map((request) => this.convertToAdminElement(request));
  }

  public async countPendingRequests(): Promise<number> {
    return this.handleRequest(axios.get(`${this.API_URL}/count/requests`));
  }

  public async approveRequest(requestId: number): Promise<void> {
    return this.handleRequest(
      axios.post<void>(`${this.API_URL}/vote/approve/${requestId}`)
    );
  }

  public async rejectRequest(requestId: number): Promise<void> {
    return this.handleRequest(
      axios.post<void>(`${this.API_URL}/vote/reject/${requestId}`)
    );
  }

  public async getRequestStatus(): Promise<any> {
    return this.handleRequest(
      axios.get<any>(`${this.API_URL}/requests/status`)
    );
  }

  public async voteApprove(requestId: number): Promise<void> {
    return this.handleRequest(
      axios.post<void>(`${this.API_URL}/vote/approve/${requestId}`)
    );
  }

  public async voteReject(requestId: number): Promise<void> {
    return this.handleRequest(
      axios.post<void>(`${this.API_URL}/vote/reject/${requestId}`)
    );
  }
}

export default AdminApiService;
