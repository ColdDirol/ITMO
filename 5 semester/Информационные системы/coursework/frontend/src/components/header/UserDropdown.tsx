import { Avatar, Dropdown, Nav } from "rsuite";
import GearIcon from "@rsuite/icons/Gear";
import { Link } from "react-router-dom";
import useUserStore from "../../store/UserStore.tsx";
import AuthComponent from "../auth/AuthComponent.tsx";
import ExitIcon from '@rsuite/icons/Exit';
import HelpIcon from '@rsuite/icons/HelpOutline'
import {RoleEnum, SexEnum} from "../../interfaces/UserInterface.ts";

const UserProfile = () => {
    const { isAuthorized, name, role, sex, logout } = useUserStore();
    // const { fullname, role, sex } = useUserStore();
    // const [isAuthorized] = useState(true);


    let avatarSrc =
        role === RoleEnum.SUPER_ADMIN || role === RoleEnum.ADMIN
            ? "/avatar/admin_man.svg"
            : role === RoleEnum.USER && sex === SexEnum.MAN
                ? "/avatar/man.svg"
                : role === RoleEnum.USER && sex === SexEnum.WOMAN
                    ? "/avatar/woman.svg"
                    : "/avatar/man.svg";

    if (role === "ADMIN" && sex === "WOMAN") {
        avatarSrc = "/avatar/admin_woman.svg";
    }

    if (role === "SUPER_ADMIN" && sex === "WOMAN") {
        avatarSrc = "/avatar/admin_woman.svg";
    }

    const renderToggle = props => (
        <Avatar
            circle
            {...props}
            src={avatarSrc}
            style={{ marginRight: "15px", marginTop: "8px" }}
        />
    );

    return (
        <>
            <Nav pullRight style={{ textAlign: "center" }}>
                {/*<Nav.Item icon={<GearIcon />}>Settings</Nav.Item>*/}

                { isAuthorized ?
                    <Dropdown
                        renderToggle={renderToggle}
                        placement="bottomEnd"
                    >
                        <Dropdown.Item panel style={{ padding: 10, width: 160 }}>
                            <p>Signed in as</p>
                            <strong>{name ?? "name"}</strong>
                        </Dropdown.Item>
                        <Dropdown.Separator />
                        <Dropdown.Item as={Link} to="/profile">Profile</Dropdown.Item>
                        <Dropdown.Item as={Link} to="/bank/accounts">Accounts</Dropdown.Item>
                        <Dropdown.Item>History</Dropdown.Item>
                        <Dropdown.Item>Deposits</Dropdown.Item>
                        <Dropdown.Separator />
                        <Dropdown.Item icon={<HelpIcon />}>Help</Dropdown.Item>
                        <Dropdown.Item icon={<GearIcon />}>Settings</Dropdown.Item>
                        <Dropdown.Item icon={<ExitIcon />} onClick={logout}>Sign out</Dropdown.Item>
                    </Dropdown>

                    : <AuthComponent />
                }
            </Nav>
        </>
    );
};

export default UserProfile;