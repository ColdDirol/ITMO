import axios, { AxiosResponse } from "axios";
import {ExportHistoryInterface, ImportHistoryInterface} from "../intefaces/importExportHistoryInterface.ts";

class ImportExportApiService {
    private API_URL: string;

    constructor() {
        this.API_URL = "http://localhost:8080/backend-1.0-SNAPSHOT/api/v1/import-export";
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
                // Обработка ошибки Unauthorized
                if (error.response.status === 401) {
                    console.error("Unauthorized access - redirecting to login");
                    window.location.href = "/logout"; // Вызываем logout, если доступ не авторизован // Здесь вы можете также вызвать login, если у вас есть нужные данные
                }
            }
            // Пробрасываем ошибку дальше, чтобы ее можно было обработать в вызывающем коде
            throw error;
        }
    }

    // admin - all, user - owns
    async countAllImportsByUser(): Promise<number> {
        return this.handleRequest(axios.get(`${this.API_URL}/import/count`));
    }

    // admin - all, user - owns
    async getAllImportsByUser(limit: number, page: number): Promise<ImportHistoryInterface[]> {
        return this.handleRequest(
            axios.get(`${this.API_URL}/import/${limit}/${page}`)
        );
    }

    // admin - all, user - owns
    async countAllExportsByUser(): Promise<number> {
        return this.handleRequest(axios.get(`${this.API_URL}/export/count`));
    }

    // admin - all, user - owns
    async getAllExportsByUser(limit: number, page: number): Promise<ExportHistoryInterface[]> {
        return this.handleRequest(
            axios.get(`${this.API_URL}/export/${limit}/${page}`)
        );
    }

}

export default ImportExportApiService;
