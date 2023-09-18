import axios, { AxiosInstance } from 'axios';
import { TokenStorageService } from './TokenStorageService';

const tokenStorageService = new TokenStorageService();

const Api: AxiosInstance = axios.create({
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${tokenStorageService.getAccessToken() ?? ''}`,
  },
});

const refreshToken = async () => {
  try {
    const refreshedTokenResponse = await Api.post('http://localhost:8079/api/auth/refresh_token', {
      refreshToken: tokenStorageService.getRefreshToken()
    });

    const { accessToken } = refreshedTokenResponse.data;
    tokenStorageService.saveAccessToken(accessToken);
    return refreshedTokenResponse.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

Api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response.status === 401) {
      try {
        const refreshedTokenResponse = await refreshToken();
        const refreshedToken = refreshedTokenResponse.accessToken;
        Api.defaults.headers.common.Authorization = `Bearer ${refreshedToken}`;
        return Api.request(error.config);
      } catch (refreshError) {
        console.error('Ошибка при обновлении Access токена:', refreshError);
        tokenStorageService.clear();
        window.location.href = '/login';
      }
    }

    return Promise.reject(error);
  }
);

export default Api;