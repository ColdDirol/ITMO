import axios, { AxiosResponse } from "axios";
import { IMovie, IMovieCreate } from "../intefaces/movieInterface";

class MovieApiService {
  private API_URL: string;

  constructor() {
    this.API_URL = "http://localhost:8080/backend-1.0-SNAPSHOT/api/v1/movie";
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

  // Создание нового фильма
  async create(movie: IMovieCreate): Promise<IMovie> {
    return this.handleRequest(axios.post(this.API_URL, movie));
  }

  async countAllByUser(): Promise<number> {
    return this.handleRequest(axios.get(`${this.API_URL}/count/all-by-user`));
  }

  async countAllPublic(): Promise<number> {
    return this.handleRequest(axios.get(`${this.API_URL}/count/all-public`));
  }

  // Получение фильма по ID
  async getById(id: number): Promise<IMovie> {
    return this.handleRequest(axios.get(`${this.API_URL}/${id}`));
  }

  async getAll(limit: number, page: number): Promise<IMovie[]> {
    return this.handleRequest(
      axios.get(`${this.API_URL}/all/${limit}/${page}`)
    );
  }

  async getAllByUser(limit: number, page: number): Promise<IMovie[]> {
    return this.handleRequest(
      axios.get(`${this.API_URL}/all-by-user/${limit}/${page}`)
    );
  }

  async getAllPublic(limit: number, page: number): Promise<IMovie[]> {
    return this.handleRequest(
      axios.get(`${this.API_URL}/all-public/${limit}/${page}`)
    );
  }

  async getAllByUserFiltered(
    limit: number,
    page: number,
    filter: string,
    columnWithRequest: Record<string, string>
  ): Promise<IMovie[]> {
    // Преобразование columnWithRequest в нужный формат
    const columnWithRequestString = Object.entries(columnWithRequest)
      .map(([key, value]) => `${key}:${value}`) // Преобразуем в "key:value"
      .join(","); // Объединяем через запятую

    const queryParams = new URLSearchParams({
      filter,
      columnWithRequest: columnWithRequestString, // Используем преобразованную строку
    }).toString();

    console.log(
      "URL:",
      `${this.API_URL}/all-by-user-filtered/${limit}/${page}?${queryParams}`
    );

    return this.handleRequest(
      axios.get(
        `${this.API_URL}/all-by-user-filtered/${limit}/${page}?${queryParams}`
      )
    );
  }

  async getAllPublicFiltered(
    limit: number,
    page: number,
    filter: string,
    columnWithRequest: Record<string, string>
  ): Promise<IMovie[]> {
    // Преобразование columnWithRequest в нужный формат
    const columnWithRequestString = Object.entries(columnWithRequest)
      .map(([key, value]) => `${key}:${value}`) // Преобразуем в "key:value"
      .join(","); // Объединяем через запятую

    const queryParams = new URLSearchParams({
      filter,
      columnWithRequest: columnWithRequestString, // Используем преобразованную строку
    }).toString();

    console.log(
      "URL:",
      `${this.API_URL}/all-public-filtered/${limit}/${page}?${queryParams}`
    );

    return this.handleRequest(
      axios.get(
        `${this.API_URL}/all-public-filtered/${limit}/${page}?${queryParams}`
      )
    );
  }

  // Обновление фильма по ID
  async update(movie: Partial<IMovie>): Promise<IMovie> {
    return this.handleRequest(axios.put(`${this.API_URL}`, movie));
  }

  // Удаление фильма по ID
  async delete(id: number): Promise<void> {
    return this.handleRequest(axios.delete(`${this.API_URL}/${id}`));
  }
}

export default MovieApiService;
