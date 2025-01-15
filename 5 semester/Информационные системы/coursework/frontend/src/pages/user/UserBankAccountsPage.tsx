import {useEffect, useRef, useState} from "react";
import {Button, Col, FlexboxGrid, Form, Modal, Row, SelectPicker} from "rsuite";
import {
    BankAccountInterface,
    BankAccountRequestInterface,
    currencyNames
} from "../../interfaces/BankAccountInterface.ts";
import bankAccountApi from "../../api/bankAccountApi.ts";
import axios from "axios";
import MessageComponent from "../../components/message/MessageComponent.tsx";
import useUserStore from "../../store/UserStore.tsx";

const UserBankAccountsPage = () => {
    const { showMessage } = MessageComponent();

    const { email } = useUserStore()

    const [bankAccounts, setBankAccounts] = useState<BankAccountInterface[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [openNewCardForm, setOpenNewCardForm] = useState(false);
    const [newCardFormValues, setNewCardFormValues] = useState<Partial<BankAccountInterface>>({
        email: email!,
        name: '',
        balance: 0,
        currencyShortName: undefined,
    })
    const cardPicture = "/credit-card/card3.svg";

    const formRef = useRef();

    useEffect(() => {
        const fetchBankAccounts = async () => {
            setLoading(true);
            try {
                const email = localStorage.getItem("email"); // Предполагается, что email хранится в localStorage
                if (!email) {
                    throw new Error("Email пользователя не найден");
                }

                const request: BankAccountRequestInterface = {
                    email,
                    page: 1,
                    size: 10, // Задайте нужный размер страницы
                };

                console.log("BankAccountRequest:", request);

                const accounts = await bankAccountApi.getUserBankAccountsByEmail(request);
                setBankAccounts(accounts);
            } catch (err: any) {
                console.error(err);
                setError(err.message || "Ошибка при загрузке счетов");
            } finally {
                setLoading(false);
            }
        };

        fetchBankAccounts();
    }, []);

    const handleAddAccountClick = () => {
        console.log("Добавить новый счет");
        handleOpenNewCard();
    };

    const handleColClick = (id: number, name: string, balance: string, currencyShortName: string) => {
        console.log(`Choosed: ${id}, ${name}, ${balance}, ${currencyShortName}`);
    };

    const handleOpenNewCard = () => {
        setOpenNewCardForm(true);
    }

    const handleCloseNewCard = () => {
        setOpenNewCardForm(false);
    }

    const handleSubmit = async () => {
        try {
            newCardFormValues.email = email!;
            await bankAccountApi.create(newCardFormValues);
            showMessage("success", "Bank account created successful");
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
        handleCloseNewCard()
    }

    const handleCancel = () => {
        setOpenNewCardForm(false);
    }

    return (
        <>
            <div style={{position: "relative", zIndex: 0}}>
                <div className="profile-fullscreen-bg"/>

                <div className="content-bg">
                    <Row
                        style={{
                            padding: "20px",
                            color: "#fff", // Для читаемости текста на фоне
                        }}
                    >
                        <Col xs={16}>
                            {loading ? (
                                <p>Загрузка...</p>
                            ) : error ? (
                                <p style={{color: "red"}}>Ошибка: {error}</p>
                            ) : (
                                <>
                                    <FlexboxGrid
                                        justify="center"
                                        style={{
                                            fontSize: "20px",
                                            marginBottom: "10px"
                                        }}>
                                        <FlexboxGrid.Item
                                            colspan={10}
                                            style={{
                                                display: "flex",
                                                justifyContent: "center",
                                                alignItems: "center",
                                            }}
                                        >
                                            <strong>Карточка</strong>
                                        </FlexboxGrid.Item>
                                        <FlexboxGrid.Item
                                            colspan={9}
                                            style={{
                                                cursor: "pointer",
                                                display: "flex",
                                                justifyContent: "center",
                                                alignItems: "center",
                                            }}
                                        >
                                            <strong>Название</strong>
                                        </FlexboxGrid.Item>
                                        <FlexboxGrid.Item
                                            colspan={4}
                                            style={{
                                                cursor: "pointer",
                                                display: "flex",
                                                justifyContent: "center",
                                                alignItems: "center",
                                            }}
                                        >
                                            <strong>Баланс</strong>
                                        </FlexboxGrid.Item>
                                        <FlexboxGrid.Item
                                            colspan={1}
                                            style={{
                                                cursor: "pointer",
                                                display: "flex",
                                                justifyContent: "center",
                                                alignItems: "center",
                                            }}
                                        >
                                            <strong>Валюта</strong>
                                        </FlexboxGrid.Item>
                                    </FlexboxGrid>
                                    <div style={{paddingTop: "30px"}}/>
                                    {bankAccounts.map((account) => (
                                        <FlexboxGrid
                                            justify="center"
                                            style={{fontSize: "30px", marginBottom: "10px"}}
                                            key={account.id}>
                                            <FlexboxGrid.Item
                                                colspan={10}
                                                onClick={() => handleColClick(account.id, account.name, account.balance, account.currencyShortName)}
                                                style={{
                                                    cursor: "pointer",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }}
                                            >
                                                <img
                                                    src={cardPicture}
                                                    alt="Card"
                                                    style={{
                                                        width: "50px",
                                                        height: "auto",
                                                        objectFit: "cover",
                                                    }}
                                                />
                                            </FlexboxGrid.Item>
                                            <FlexboxGrid.Item
                                                colspan={9}
                                                onClick={() => handleColClick(account.id, account.name, account.balance, account.currencyShortName)}
                                                style={{
                                                    cursor: "pointer",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center",
                                                }}
                                            >
                                                {account.name}
                                            </FlexboxGrid.Item>
                                            <FlexboxGrid.Item
                                                colspan={4}
                                                onClick={() => handleColClick(account.id, account.name, account.balance, account.currencyShortName)}
                                                style={{
                                                    cursor: "pointer",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center",
                                                }}
                                            >
                                                {account.balance}
                                            </FlexboxGrid.Item>
                                            <FlexboxGrid.Item
                                                colspan={1}
                                                onClick={() => handleColClick(account.id, account.name, account.balance, account.currencyShortName)}
                                                style={{
                                                    cursor: "pointer",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center",
                                                }}
                                            >
                                                {account.currencyShortName}
                                            </FlexboxGrid.Item>

                                        </FlexboxGrid>
                                    ))}
                                </>
                            )}
                        </Col>
                    </Row>
                </div>

                <div
                    className="content-bg"
                    onClick={handleAddAccountClick}
                    style={{
                        cursor: "pointer",
                        textAlign: "center",
                        fontSize: "50px",
                        padding: "20px",
                        background: "#022c59",
                        color: "#fff"
                    }}
                >
                    +
                </div>

                <Modal open={openNewCardForm} onClose={handleCancel} size="xs">
                    <Modal.Header>
                        <Modal.Title>Authorization</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form fluid onChange={setNewCardFormValues} formValue={newCardFormValues} ref={formRef}>
                            <Form.Group controlId="name">
                                <Form.ControlLabel>Name</Form.ControlLabel>
                                <Form.Control name="name" type="name" />
                                <Form.HelpText>Required</Form.HelpText>
                            </Form.Group>
                            <Form.Group controlId="currencyShortName">
                                <Form.ControlLabel>Currency</Form.ControlLabel>
                                <Form.Control name="currencyShortName" accepter={SelectPicker} data={currencyNames} />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={handleSubmit} appearance="primary">
                            Confirm
                        </Button>
                        <Button onClick={handleCancel} appearance="subtle">
                            Cancel
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </>
    );
};

export default UserBankAccountsPage;
