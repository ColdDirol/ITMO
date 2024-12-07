import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import LogoutPage from "../pages/LogoutPage";

const userStore = () => {
  const [token, setToken] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);
  const [email, setEmail] = useState<string | null>(null);
  const [role, setRole] = useState<string | null>(null);
  const [pageElementsLimit, setPageElementsLimit] = useState<number>(15);
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);
  const [isDarkTheme, setIsDarkTheme] = useState<boolean>(false);

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedUsername = localStorage.getItem("username");
    const storedEmail = localStorage.getItem("email");
    const storedRole = localStorage.getItem("role");
    const storedPageElementsLimit = localStorage.getItem("pageElementsLimit");
    const storedIsAuthorized = localStorage.getItem("isAuthorized") === "true";
    const storedIsDarkTheme = localStorage.getItem("isDarkTheme") === "true";

    if (storedToken) setToken(storedToken);
    if (storedUsername) setUsername(storedUsername);
    if (storedEmail) setEmail(storedEmail);
    if (storedRole) setRole(storedRole);
    if (storedPageElementsLimit)
      setPageElementsLimit(Number(storedPageElementsLimit));
    setIsAuthorized(storedIsAuthorized);
    setIsDarkTheme(storedIsDarkTheme);
  }, []);

  const login = (newToken: string) => {
    setToken(newToken);

    const decodedToken: {
      username: string;
      email: string;
      role: string;
      pageElementsLimit: number;
      iat: number;
      exp: number;
    } = jwtDecode(newToken);
    const newUsername = decodedToken.username;
    const newEmail = decodedToken.email;
    const newRole = decodedToken.role;
    const newPageElementsLimit = decodedToken.pageElementsLimit;
    setUsername(newUsername);
    setEmail(newEmail);
    setRole(newRole);
    setPageElementsLimit(newPageElementsLimit);
    setIsAuthorized(true);

    localStorage.setItem("token", newToken);
    localStorage.setItem("username", newUsername);
    localStorage.setItem("email", newEmail);
    localStorage.setItem("role", newRole);
    localStorage.setItem("pageElementsLimit", String(newPageElementsLimit));
    localStorage.setItem("isAuthorized", "true");

    goToStartPage();
  };

  const logout = () => {
    setToken(null);
    setUsername(null);
    setEmail(null);
    setRole(null);
    setPageElementsLimit(15);
    setIsAuthorized(false);
    setIsDarkTheme(false);

    console.log("logouting");

    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    localStorage.removeItem("pageElementsLimit");
    localStorage.removeItem("isAuthorized");
    localStorage.removeItem("isDarkTheme");

    goToStartPage();
  };

  const toggleTheme = (value: boolean) => {
    setIsDarkTheme(value);
    localStorage.setItem("isDarkTheme", value.toString());
    reloadPage();
  };

  const reloadPage = () => {
    window.location.reload();
  };

  const goToStartPage = () => {
    window.location.href = "/";
  };

  return {
    token,
    username,
    email,
    role,
    pageElementsLimit,
    isAuthorized,
    isDarkTheme,

    login,
    logout,
    toggleTheme,
  };
};

export default userStore;
