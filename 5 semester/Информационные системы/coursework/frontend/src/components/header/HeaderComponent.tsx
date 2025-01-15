import { useState, useEffect } from "react";
import { Nav, Navbar } from "rsuite";
import UserDropdown from "./UserDropdown.tsx";
import { Link } from "react-router-dom";
import UserFindingComponent from "./UserFindingComponent.tsx";
import useUserStore from "../../store/UserStore.tsx";
import {RoleEnum} from "../../interfaces/UserInterface.ts";

const HeaderComponent = () => {
    const { role } = useUserStore()
    const [isFixed, setIsFixed] = useState(false);

    const isAdmin = role === RoleEnum.ADMIN || role === RoleEnum.SUPER_ADMIN;

    useEffect(() => {
        const handleScroll = () => {
            // Устанавливаем фиксированный режим при скролле вниз
            setIsFixed(window.scrollY > 0);
        };

        window.addEventListener("scroll", handleScroll);

        // Очистка события при размонтировании компонента
        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    return (
        <>
            <Navbar
                style={{
                    position: isFixed ? "fixed" : "relative",
                    top: 0,
                    left: 0,
                    width: "100%",
                    zIndex: 1000,
                    boxShadow: isFixed ? "0 2px 4px rgba(0, 0, 0, 0.1)" : "none",
                    transition: "box-shadow 0.3s ease",
                }}
            >
                <Navbar.Brand as={Link} to="/">GFN system</Navbar.Brand>

                <Nav>
                    <Nav.Item as={Link} to="/deposits">Deposits</Nav.Item>
                    <Nav.Item as={Link} to="/payments">Payments</Nav.Item>
                    <Nav.Item as={Link} to="/history">History</Nav.Item>
                    {isAdmin ? (
                        <Nav.Item as={Link} to="/admin/actions/history">Admin actions</Nav.Item>
                    ) : null}
                </Nav>

                <UserDropdown />
                <UserFindingComponent />

            </Navbar>
        </>
    );
};

export default HeaderComponent;
