import { FC, useState } from "react";
import { Header, Nav, Navbar } from "rsuite";
import LoginComponent from "../auth/LoginComponent";
import RegisterComponent from "../auth/RegisterComponent";
import UserProfileComponent from "../profile/UserProfileComponent";
import AdminComponent from "../admin/AdminComponent";
import ThemeComponent from "../theme/ThemeComponent";
import { RoleEnum } from "../../intefaces/userInterface";
import userStore from "../../store/UserStore";
import { Link } from "react-router-dom";

interface CustomNavbarProps {
  onSelect: (eventKey: string | number) => void; // Указываем тип для функции onSelect
  activeKey: string | number | null; // Указываем тип для activeKey
  [key: string]: any; // Позволяем передавать другие пропсы
}

const CustomNavbar: FC<CustomNavbarProps> = ({
  onSelect,
  activeKey,
  ...props
}) => {
  const { isAuthorized, role } = userStore();

  return (
    <Navbar {...props}>
      <Navbar.Brand as={Link} to="/">
        MOVIE CRUD
      </Navbar.Brand>
      {isAuthorized ? (
        <Nav defaultActiveKey="Home" onSelect={onSelect} activeKey={activeKey}>
          <Nav.Item as={Link} to="/movies" eventKey="HOME">
            Movies
          </Nav.Item>
          <Nav.Item as={Link} to="/import-history" eventKey="HOME">
            Imports
          </Nav.Item>
          <Nav.Item as={Link} to="/export-history" eventKey="HOME">
            Exports
          </Nav.Item>
        </Nav>
      ) : null}
      <Nav pullRight>
        {!isAuthorized ? (
          <>
            <RegisterComponent />
            <LoginComponent />
          </>
        ) : (
          <>
            {(isAuthorized && role === RoleEnum.ADMIN) ||
            role === RoleEnum.EGOSHIN ? (
              <>
                <AdminComponent />
              </>
            ) : null}
            <ThemeComponent />
            <UserProfileComponent />
          </>
        )}
      </Nav>
    </Navbar>
  );
};

export default function HeaderComponent() {
  const [activeKey, setActiveKey] = useState<string | number | null>(null);

  const handleSelect = (eventKey: string | number) => {
    console.log("active:", eventKey);
    setActiveKey(eventKey);
  };

  return (
    <>
      <Header>
        <CustomNavbar activeKey={activeKey} onSelect={handleSelect} />
      </Header>
    </>
  );
}
