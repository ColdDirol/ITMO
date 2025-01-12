import { Button, ButtonToolbar, Col, Row, Text } from "rsuite";
import useUserStore from "../../store/UserStore.tsx";

const UserProfilePage = () => {
    const { fullname, role, email, phone, sex } = useUserStore();

    const avatarSrc =
        role === "ADMIN" || role === "SUPER_ADMIN"
            ? "/avatar/admin.svg"
            : role === "USER" && sex === "MAN"
                ? "/avatar/man.svg"
                : role === "USER" && sex === "WOMAN"
                    ? "/avatar/woman.svg"
                    : "/avatar/man.svg";

    return (
        <div style={{ position: "relative", zIndex: 0 }}>
            {/* Фоновая картинка */}
            <div className="profile-fullscreen-bg" />

            {/* Контент */}
            <div className="content-bg">
                <Row
                    style={{
                        padding: "20px",
                        color: "#fff", // Для читаемости текста на фоне
                    }}
                >
                    <Col xs={12} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                        <img
                            src={avatarSrc}
                            alt="User Profile"
                            style={{ width: "50%", height: "auto", objectFit: "cover" }}
                        />
                        <div style={{ paddingTop: "20px" }}>
                            {role === null && <Text style={{ textAlign: "center", fontSize: "20px" }} color="green">USER</Text>}
                            {role === "ADMIN" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="cyan">ADMIN</Text>}
                            {role === "SUPER_ADMIN" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="violet">SUPER ADMIN</Text>}
                        </div>
                    </Col>
                    <Col xs={12}>
                        <h2>User Information</h2>

                        <div style={{ paddingTop: "30px" }}>
                            <p style={{fontSize: "19px"}}>Name: {fullname ?? "fullname"}</p>
                            <p style={{fontSize: "19px"}}>Email: {email ?? "email"}</p>
                            <p style={{fontSize: "19px"}}>Phone: {phone ?? "phone"}</p>
                        </div>

                        <ButtonToolbar style={{paddingTop: "20px"}}>
                            <Button color="violet" appearance="primary">
                                Edit
                            </Button>

                            <Button color="violet" appearance="ghost">
                                Remove
                            </Button>
                        </ButtonToolbar>
                    </Col>
                </Row>
            </div>
        </div>
    );
};

export default UserProfilePage;
