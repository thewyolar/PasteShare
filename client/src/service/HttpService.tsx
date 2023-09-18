import Api from "./auth/Api";
import {PasteCreateDTO} from "../dto/request/PasteCreateDTO";

class HttpService {
  private static BASE_URL = "http://localhost:8079";
  private static API_PASTE = "/api/pastes";

  public static async addPaste(body: PasteCreateDTO) {
    return await Api.post(this.BASE_URL + this.API_PASTE, body)
      .then((res) => res.data)
      .catch((error) => {
        throw error;
      });
  }
}

export default HttpService;