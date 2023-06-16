import { TokenStorageService } from "./TokenStorageService";
import axios, { AxiosInstance } from "axios";


const tokenStorageService = new TokenStorageService();

const Api: AxiosInstance = axios.create({
  headers: {
    "Content-Type": "application/json",
    Authorization: `Bearer ${tokenStorageService.getToken() ?? ""}`,
  },
});

Api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log(error);
    if (error.response.status === 401) {
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default Api;