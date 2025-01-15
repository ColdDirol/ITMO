import { Input, InputGroup, Nav, Text } from "rsuite";
import { Link, useNavigate } from "react-router-dom";
import SearchIcon from "@rsuite/icons/Search";
import useUserStore from "../../store/UserStore.tsx";
import { useState } from "react";
import { UserSearchRequest, UserInfoInterface } from "../../interfaces/UserInterface.ts";
import userApi from "../../api/userApi.ts";

const UserFindingComponent = () => {
    const { isAuthorized } = useUserStore();
    const [searchRequest, setSearchRequest] = useState<Partial<UserSearchRequest>>({
        searchTerm: "",
        page: 0,
        size: 10,
    });
    const [searchResults, setSearchResults] = useState<UserInfoInterface[]>([]);
    const navigate = useNavigate(); // Используем navigate для перенаправления

    const handleSearch = async (term: string) => {
        const controller = new AbortController();
        const signal = controller.signal;

        if (term.length > 0) {
            const request: UserSearchRequest = { searchTerm: term, page: 0, size: 10 };

            try {
                const results = await userApi.searchUser(request, { signal });
                setSearchResults(results);
            } catch (error) {
                if (error.name !== 'AbortError') {
                    console.error("Error searching for user:", error);
                }
            }
        } else {
            setSearchResults([]);
        }

        return () => controller.abort(); // Отменить запрос при размонтировании.
    };


    const handleSearchChange = (value: string) => {
        setSearchRequest({ ...searchRequest, searchTerm: value });
        handleSearch(value);
    };

    const handleResultClick = (user: UserInfoInterface) => {
        setSearchResults([]); // Очищаем результаты
        setSearchRequest({ ...searchRequest, searchTerm: "" }); // Сбрасываем текст поиска
        navigate("/profile", { state: { userInfo: user } }); // Перенаправляем пользователя
    };

    return (
        <>
            {isAuthorized ? (
                <Nav pullRight style={{ paddingRight: "20px" }}>
                    <div style={{ position: "relative", marginTop: 10 }}>
                        <InputGroup inside style={{ width: 300 }}>
                            <Input
                                placeholder="Find a person"
                                value={searchRequest.searchTerm}
                                onChange={(value) => handleSearchChange(value)}
                            />
                            <InputGroup.Button>
                                <SearchIcon />
                            </InputGroup.Button>
                        </InputGroup>
                        {searchResults.length > 0 && (
                            <div
                                style={{
                                    position: "absolute",
                                    top: "100%",
                                    left: 0,
                                    width: "100%",
                                    marginTop: "5px",
                                    zIndex: 1000,
                                    background: "rgba(15, 23, 28, 0.92)",
                                    borderRadius: "4px",
                                }}
                            >
                                {searchResults.map((user) => (
                                    <div
                                        key={user.id}
                                        onClick={() => handleResultClick(user)}
                                        style={{
                                            padding: "8px 12px",
                                            cursor: "pointer",
                                            borderBottom: "1px solid #000000",
                                        }}
                                    >
                                        <Text style={{ fontSize: "15px", color: "#fff" }}>
                                            {user.name} - {user.email}
                                        </Text>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </Nav>
            ) : null}
        </>
    );
};

export default UserFindingComponent;
