const TOKEN_KEY = "AuthToken";
const USERNAME_KEY = "AuthUsername";
const AUTHORITIES_KEY = "AuthAuthorities";
const ROLE_KEY = 'Role'

export class TokenStorageService {

  constructor() {}

  public signOut() {
    window.localStorage.clear();
  }

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY)!;
  }

  public saveLogin(login: string) {
    window.localStorage.removeItem(USERNAME_KEY);
    window.localStorage.setItem(USERNAME_KEY, login);
  }

  public getLogin(): string {
    return localStorage.getItem(USERNAME_KEY)!;
  }

  public saveRoles(roles: string) {
    window.localStorage.removeItem(ROLE_KEY);
    window.localStorage.setItem(ROLE_KEY, roles);
  }

  public getRoles(): string {
    return localStorage.getItem(ROLE_KEY)!;
  }

  public saveAuthorities(authorities: string) {
    window.localStorage.removeItem(AUTHORITIES_KEY);
    window.localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(arguments));
  }
}