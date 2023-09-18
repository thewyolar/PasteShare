const ACCESSTOKEN_KEY = "AccessToken";
const REFRESHTOKEN_KEY = "RefreshToken";

export class TokenStorageService {

  constructor() {}

  public clear() {
    if (typeof window !== 'undefined') {
      window.localStorage.clear();
    }
  }

  public saveAccessToken(token: string) {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(ACCESSTOKEN_KEY);
      window.localStorage.setItem(ACCESSTOKEN_KEY, token);
    }
  }

  public saveRefreshToken(token: string) {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(REFRESHTOKEN_KEY);
      window.localStorage.setItem(REFRESHTOKEN_KEY, token);
    }
  }

  public getAccessToken() {
    if (typeof window !== 'undefined') {
      return window.localStorage.getItem(ACCESSTOKEN_KEY);
    }
    return null;
  }

  public getRefreshToken() {
    if (typeof window !== 'undefined') {
      return window.localStorage.getItem(REFRESHTOKEN_KEY);
    }
    return null;
  }
}