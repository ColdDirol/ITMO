import {Button, ButtonToolbar, Col, Form, Modal, Row, SelectPicker, Text} from "rsuite";
import useUserStore from "../../store/UserStore.tsx";
import {useLocation} from "react-router-dom";
import {RoleEnum} from "../../interfaces/UserInterface.ts";
import axios from "axios";
import adminApi from "../../api/adminApi.ts";
import {AdminActionOnUserInterface} from "../../interfaces/AdminInterface.ts";
import MessageComponent from "../../components/message/MessageComponent.tsx";
import {
    BankAccountInterface,
    BankAccountRequestInterface, MakeUserAdminRequest,
    TransferRequestInterface
} from "../../interfaces/BankAccountInterface.ts";
import {useEffect, useRef, useState} from "react";
import bankAccountApi from "../../api/bankAccountApi.ts";
import transactionApi from "../../api/transactionApi.ts";
import superAdminApi from "../../api/superAdminApi.ts";

const UserProfilePage: React.FC = () => {
    const { showMessage } = MessageComponent();

    const location = useLocation();
    const userInfo = location.state?.userInfo;
    const storeUser = useUserStore();
    const user = userInfo || storeUser; // Используем переданный userInfo или fallback на данные из стора

    // modal
    const [openTransactionModal, setOpenTransactionModal] = useState(false);
    const [transactionFormValue, setTransactionFormValue] = useState<Partial<TransferRequestInterface>>({
        idFrom: 0,
        idTo: 0,
        amount: 0,
    })

    // bank accounts
    const [myBankAccounts, setMyBankAccounts] = useState<BankAccountInterface[]>([]);
    const [userBankAccounts, setUserBankAccounts] = useState<BankAccountInterface[]>([]);

    let avatarSrc =
        user.role === "ADMIN" || user.role === "SUPER_ADMIN"
            ? "/avatar/admin_man.svg"
            : user.role === "USER" && user.sex === "MAN"
                ? "/avatar/man.svg"
                : user.role === "USER" && user.sex === "WOMAN"
                    ? "/avatar/woman.svg"
                    : "/avatar/man.svg";

    if (user.role === "ADMIN" && user.sex === "WOMAN") {
        avatarSrc = "/avatar/admin_woman.svg";
    }

    if (user.role === "SUPER_ADMIN" && user.sex === "WOMAN") {
        avatarSrc = "/avatar/admin_woman.svg";
    }

    const formRef = useRef();

    const isMe = storeUser.email === user.email
    const isMeAdmin = storeUser.role === RoleEnum.ADMIN || storeUser.role === RoleEnum.SUPER_ADMIN
    const isMeSuperAdmin = storeUser.role === RoleEnum.SUPER_ADMIN



    let isFrozen = false;
    let isDeleted = false;
    let isBlocked = false;

    if (userInfo) {
        isFrozen = userInfo.status === "FROZEN";
        isDeleted = userInfo.status === "DELETED";
        isBlocked = userInfo.status === "BLOCKED";
    }


    useEffect(() => {
        const fetchMyBankAccounts = async () => {
            try {
                const email = storeUser.email;
                if (!email) {
                    throw new Error("Email пользователя не найден");
                }

                const request: BankAccountRequestInterface = {
                    email,
                    page: 1,
                    size: 10,
                };

                const accounts = await bankAccountApi.getUserBankAccountsByEmail(request);
                setMyBankAccounts(accounts);
            } catch (err: any) {
                console.error(err);
            }
        };

        const fetchUserBankAccounts = async () => {
            try {
                const email = userInfo.email;
                if (!email) {
                    throw new Error("Email пользователя не найден");
                }

                const request: BankAccountRequestInterface = {
                    email,
                    page: 1,
                    size: 10,
                };

                const accounts = await bankAccountApi.getUserBankAccountsByEmail(request);
                setUserBankAccounts(accounts);
            } catch (err: any) {
                console.error(err);
            }
        };

        if (!isMe) {
            fetchMyBankAccounts();
            fetchUserBankAccounts();
        }
    }, [isMe, storeUser.email]); // Указываем зависимости



    const handleSubmitTransactionModal = async () => {
        try {
            console.log("transactionFormValue:", transactionFormValue);
            await transactionApi.transfer(transactionFormValue);
            showMessage("success", "The transaction was successful")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
        handleCancelTransactionModal();
    }

    const handleOpenTransactionModal = () => {
        setOpenTransactionModal(true);
    }

    const handleCancelTransactionModal = () => {
        setOpenTransactionModal(false);
    }

    const handleFroze = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.frozeUser(request)
            showMessage("success", "User has been frozen successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const handleUnfroze = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.unfrozeUser(request)
            showMessage("success", "User has been unfrozen successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const handleDelete = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.deleteUser(request)
            showMessage("success", "User has been deleted successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const handleActivate = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.activateUser(request)
            showMessage("success", "User has been activated successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const handleBlock = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.blockUser(request)
            showMessage("success", "User has been blocked successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const handleUnblock = async () => {
        try {
            const request: AdminActionOnUserInterface = {
                userId: userInfo.id,
                cause: "JUST FORM FUN",
            };
            await adminApi.unblockUser(request)
            showMessage("success", "User has been unblocked successfully")
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const makeUserAdministrator = async () => {
        try {
            const request: MakeUserAdminRequest = { id: userInfo.id };
            superAdminApi.makeUserAdmin(request)
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    const dismissAdministrator = async () => {
        try {
            const request: MakeUserAdminRequest = { id: userInfo.id };
            superAdminApi.dismissAdmin(request)
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    }

    return (
        <div style={{ position: "relative", zIndex: 0 }}>
            {/* Фоновая картинка */}
            <div className="profile-fullscreen-bg" />

            {/* Контент */}
            <div className="content-bg">
                <Row
                    style={{
                        padding: "20px",
                        color: "#fff",
                    }}
                >
                    <Col xs={12} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                        <img
                            src={avatarSrc}
                            alt="User Profile"
                            style={{
                                width: "50%",
                                height: "auto",
                                objectFit: "cover",
                                borderRadius: "50%",
                                overflow: "hidden",
                                boxShadow: "0 0 0 1px rgba(255, 255, 255, 0.3)",
                            }}
                        />
                        <div style={{ paddingTop: "20px" }}>
                            {user.role === "USER" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="green">USER</Text>}
                            {user.role === "ADMIN" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="cyan">ADMIN</Text>}
                            {user.role === "SUPER_ADMIN" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="violet">SUPER ADMIN</Text>}
                        </div>
                        {!isMe && isMeAdmin ? (
                            <div style={{ paddingTop: "20px" }}>
                                {userInfo.status === "ACTIVE" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="green">[ACTIVE]</Text>}
                                {userInfo.status === "FROZEN" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="blue">[FROZEN]</Text>}
                                {userInfo.status === "DELETED" && <Text style={{ textAlign: "center", fontSize: "20px" }}>[DELETED]</Text>}
                                {userInfo.status === "BLOCKED" && <Text style={{ textAlign: "center", fontSize: "20px" }} color="red">[BLOCKED]</Text>}
                            </div>
                        ) : (null)}
                    </Col>
                    <Col xs={12}>
                        <h2>User Information</h2>

                        <div style={{ paddingTop: "30px" }}>
                            <p style={{ fontSize: "19px" }}>Name: {user.name ?? "fullname"}</p>
                            <p style={{ fontSize: "19px" }}>Email: {user.email ?? "email"}</p>
                            <p style={{ fontSize: "19px" }}>Phone: {user.phone ?? "phone"}</p>
                        </div>

                        {isMe ? (
                            <ButtonToolbar style={{ paddingTop: "20px" }}>
                                <Button color="violet" appearance="primary">
                                    Edit
                                </Button>
                                <Button color="violet" appearance="ghost">
                                    Remove
                                </Button>
                            </ButtonToolbar>
                        ) : (
                            <>
                                <Modal open={openTransactionModal} onClose={handleCancelTransactionModal} size="xs">
                                    <Modal.Header>
                                        <Modal.Title>Authorization</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <Form fluid onChange={setTransactionFormValue} formValue={transactionFormValue} ref={formRef}>
                                            <Form.Group controlId="idFrom">
                                                <Form.ControlLabel>From</Form.ControlLabel>
                                                <SelectPicker
                                                    name="idFrom"
                                                    data={myBankAccounts.map((account) => ({
                                                        label: `${account.name} (${account.currencyShortName})`,
                                                        value: account.id,
                                                    }))}
                                                    searchable={false}
                                                    block
                                                    onChange={(value) => setTransactionFormValue((prev) => ({ ...prev, idFrom: value }))}
                                                />
                                                <Form.HelpText>Required</Form.HelpText>
                                            </Form.Group>
                                            <Form.Group controlId="idTo">
                                                <Form.ControlLabel>To</Form.ControlLabel>
                                                <SelectPicker
                                                    name="idTo"
                                                    data={userBankAccounts.map((account) => ({
                                                        label: `${account.name} (${account.currencyShortName})`,
                                                        value: account.id,
                                                    }))}
                                                    searchable={false}
                                                    block
                                                    onChange={(value) => setTransactionFormValue((prev) => ({ ...prev, idTo: value }))}
                                                />
                                                <Form.HelpText>Required</Form.HelpText>
                                            </Form.Group>
                                            <Form.Group controlId="amount">
                                                <Form.ControlLabel>Amount</Form.ControlLabel>
                                                <Form.Control
                                                    name="amount"
                                                    type="number"
                                                    onChange={(value) => setTransactionFormValue((prev) => ({ ...prev, amount: value }))}
                                                />
                                                <Form.HelpText>Required</Form.HelpText>
                                            </Form.Group>
                                        </Form>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button onClick={handleSubmitTransactionModal} appearance="primary">
                                            Confirm
                                        </Button>
                                        <Button onClick={handleCancelTransactionModal} appearance="subtle">
                                            Cancel
                                        </Button>
                                    </Modal.Footer>
                                </Modal><ButtonToolbar style={{paddingTop: "20px"}}>
                                    <Button color="violet" appearance="primary" onClick={handleOpenTransactionModal}>
                                        Make transaction
                                    </Button>
                                </ButtonToolbar>
                            </>
                        )}

                        {!isMe && isMeAdmin ? (
                            <ButtonToolbar style={{ paddingTop: "20px" }}>
                                {isFrozen ? (
                                    <Button color="red" appearance="ghost" onClick={handleUnfroze}>
                                        UNFROZE
                                    </Button>
                                ) : (
                                    <Button color="red" appearance="ghost" onClick={handleFroze}>
                                        FROZE
                                    </Button>
                                )}
                                {isDeleted ? (
                                    <Button color="red" appearance="ghost" onClick={handleActivate}>
                                        ACTIVATE
                                    </Button>
                                ) : (
                                    <Button color="red" appearance="ghost" onClick={handleDelete}>
                                        DELETE
                                    </Button>
                                )}
                                {isBlocked ? (
                                    <Button color="red" appearance="ghost" onClick={handleUnblock}>
                                        UNBLOCK
                                    </Button>
                                ) : (
                                    <Button color="red" appearance="ghost" onClick={handleBlock}>
                                        BLOCK
                                    </Button>
                                )}
                            </ButtonToolbar>
                        ) : null}

                        {!isMe && isMeSuperAdmin ? (
                            <ButtonToolbar style={{ paddingTop: "20px" }}>
                                {userInfo.role === "ADMIN" || userInfo.role === "SUPER_ADMIN" ? (
                                    <>
                                        <Button color="orange" appearance="ghost" onClick={dismissAdministrator}>
                                            Dismiss administrator to user
                                        </Button>
                                    </>
                                ) : (
                                    <>
                                        <Button color="orange" appearance="ghost" onClick={makeUserAdministrator}>
                                            Promote user to administrator
                                        </Button>
                                    </>
                                )}

                            </ButtonToolbar>
                        ) : null}
                    </Col>
                </Row>
            </div>
        </div>
    );
};

export default UserProfilePage;
