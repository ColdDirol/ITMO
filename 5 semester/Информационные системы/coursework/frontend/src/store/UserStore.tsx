import { jwtDecode } from "jwt-decode";
import {useEffect, useState} from "react";


const useUserStore = () => {
    const [token, setToken] = useState<string | null>(null);
    const [name, setName] = useState<string | null>(null);
    const [email, setEmail] = useState<string | null>(null);
    const [phone, setPhone] = useState<string | null>(null);
    const [role, setRole] = useState<string | null>(null);
    const [sex, setSex] = useState<string | null>(null);
    const [pageElementsLimit, setPageElementsLimit] = useState<number>(15);
    const [isAuthorized, setIsAuthorized] = useState<boolean>(false);
    const [hasAvatar, setHasAvatar] = useState<boolean>(false);
    const [isDarkTheme, setIsDarkTheme] = useState<boolean>(false);

    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        const storedName = localStorage.getItem("name");
        const storedEmail = localStorage.getItem("email");
        const storedPhone = localStorage.getItem("phone");
        const storedRole = localStorage.getItem("role");
        const storedSex = localStorage.getItem("sex");
        const storedPageElementsLimit = localStorage.getItem("pageElementsLimit");
        const storedIsAuthorized = localStorage.getItem("isAuthorized") === "true";
        const storedHasAvatar = localStorage.getItem("hasAvatar") === "true";
        const storedIsDarkTheme = localStorage.getItem("isDarkTheme") === "true";

        if (storedToken) setToken(storedToken);
        if (storedName) setName(storedName);
        if (storedEmail) setEmail(storedEmail);
        if (storedPhone) setPhone(storedPhone);
        if (storedRole) setRole(storedRole);
        if (storedSex) setSex(storedSex)
        if (storedPageElementsLimit)
            setPageElementsLimit(Number(storedPageElementsLimit));
        setIsAuthorized(storedIsAuthorized);
        setHasAvatar(storedHasAvatar);
        setIsDarkTheme(storedIsDarkTheme);
    }, []);

    const login = (newToken: string, hasAvatar: boolean) => {
        setToken(newToken);

        const decodedToken: {
            name: string;
            email: string;
            phone: string;
            role: string;
            sex: string;
            pageElementsLimit: number;
            iat: number;
            exp: number;
        } = jwtDecode(newToken);
        const newName = decodedToken.name;
        const newEmail = decodedToken.email;
        const newPhone = decodedToken.phone;
        const newRole = decodedToken.role;
        const newSex = decodedToken.sex;
        const newPageElementsLimit = decodedToken.pageElementsLimit;
        setName(newName);
        setEmail(newEmail);
        setPhone(newPhone);
        setRole(newRole);
        setSex(newSex);
        setPageElementsLimit(newPageElementsLimit);
        setIsAuthorized(true);
        setHasAvatar(hasAvatar);

        localStorage.setItem("token", newToken);
        localStorage.setItem("name", newName);
        localStorage.setItem("email", newEmail);
        localStorage.setItem("phone", newPhone);
        localStorage.setItem("role", newRole);
        localStorage.setItem("sex", newSex);
        localStorage.setItem("pageElementsLimit", String(newPageElementsLimit));
        localStorage.setItem("isAuthorized", "true");
        localStorage.setItem("hasAvatar", `${hasAvatar}`);

        goToStartPage();
    };

    const logout = () => {
        setToken(null);
        setName(null);
        setEmail(null);
        setPhone(null);
        setRole(null);
        setSex(null);
        setPageElementsLimit(15);
        setIsAuthorized(false);
        setIsDarkTheme(false);

        console.log("logouting");

        localStorage.removeItem("token");
        localStorage.removeItem("name");
        localStorage.removeItem("email");
        localStorage.removeItem("phone");
        localStorage.removeItem("role");
        localStorage.removeItem("sex");
        localStorage.removeItem("pageElementsLimit");
        localStorage.removeItem("isAuthorized");
        localStorage.removeItem("isDarkTheme");
        localStorage.removeItem("hasAvatar");

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
        name,
        email,
        phone,
        role,
        sex,
        pageElementsLimit,
        isAuthorized,
        isDarkTheme,
        hasAvatar,

        login,
        logout,
        toggleTheme,
    };
};

export default useUserStore;