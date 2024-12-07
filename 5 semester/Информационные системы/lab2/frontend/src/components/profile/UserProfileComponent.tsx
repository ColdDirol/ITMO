import { Nav } from "rsuite";
import IdMappingIcon from "@rsuite/icons/IdMapping";
import userStore from "../../store/UserStore";

export default function UserProfileComponent() {
  const { username, email, role, logout } = userStore();

  const handleLogout = () => {
    logout();
  };

  return (
    <>
      <Nav.Menu icon={<IdMappingIcon />} placement="bottomEnd">
        <Nav.Item panel style={{ padding: 10, width: 200 }}>
          <p>Signed in as</p>
          <strong>{username}</strong>
        </Nav.Item>
        <Nav.Item>Email: {email}</Nav.Item>
        <Nav.Item>Role: {role}</Nav.Item>
        <Nav.Item onClick={handleLogout}>Log out</Nav.Item>
      </Nav.Menu>
    </>
  );
}
