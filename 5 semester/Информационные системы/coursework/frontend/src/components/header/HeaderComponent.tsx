import {Nav, Navbar} from "rsuite";
import UserDropdown from "./UserDropdown.tsx";
import {Link} from "react-router-dom";

const HeaderComponent = () => {
    return(
        <>
            <Navbar style={{ position: 'relative', zIndex: 1 }}>
                <Navbar.Brand as={Link} to="/">GFN system</Navbar.Brand>

                <Nav>
                    {/*<Nav.Item as={Link} to="/">Home</Nav.Item>*/}

                    {/*<Nav.Item as={Link} to="/">Accounts</Nav.Item>*/}
                    <Nav.Item as={Link} to="/deposits">Deposits</Nav.Item>
                    <Nav.Item as={Link} to="/payments">Payments</Nav.Item>
                    <Nav.Item as={Link} to="/history">History</Nav.Item>

                    {/*<Nav.Menu title="About">*/}
                    {/*    <Nav.Item>Company</Nav.Item>*/}
                    {/*    <Nav.Item>Team</Nav.Item>*/}
                    {/*    <Nav.Menu title="Contact">*/}
                    {/*        <Nav.Item>Via email</Nav.Item>*/}
                    {/*        <Nav.Item>Via telephone</Nav.Item>*/}
                    {/*    </Nav.Menu>*/}
                    {/*</Nav.Menu>*/}
                </Nav>

                <UserDropdown />
            </Navbar>
        </>
    )
}

export default HeaderComponent;