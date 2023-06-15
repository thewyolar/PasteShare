import Api from "./Api";
import {AuthRequestDTO} from "../../dto/AuthRequestDTO";

class AuthService {
  private static BASE_URL = "http://localhost:8080/api/auth";

  public static async login(credentials: AuthRequestDTO) {
    return await Api.post<AuthRequestDTO>(this.BASE_URL + "/login", credentials)
      .then((res) => res.data)
      .catch((error) => {throw error});
  }

  // public static async refreshToken(credentials: InfoLogin) {
  //   return await Api.post<AuthInfo>(this.BASE_URL + "/refreshToken", credentials)
  //     .then((res) => res.data)
  //     .catch((error) => {throw error});
  // }

  public static async getAllRoles() {
    return await Api.get<string[]>(this.BASE_URL + "/roles")
      .then((res) => res.data)
      .catch((error) => {throw error});
  }
}

export default AuthService;