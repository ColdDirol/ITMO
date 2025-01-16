import {Button, Form, Modal, Nav, SelectPicker} from "rsuite";
import {useRef, useState} from "react";
import {RegisterInterface} from "../../interfaces/AuthInterface.ts";
import {sexes} from "../../interfaces/UserInterface.ts";
import authApi from "../../api/authApi.ts";
import useUserStore from "../../store/UserStore.tsx";
import MessageComponent from "../message/MessageComponent.tsx";
import axios from "axios";
import PageEndIcon from "@rsuite/icons/PageEnd";

const RegisterComponent = () => {
    const { login } = useUserStore();
    const { showMessage } = MessageComponent();
    const [open, setOpen] = useState(false);
    const [formValue, setFormValue] = useState<Partial<RegisterInterface>>({
        name: '',
        email: '',
        password: '',
        phone: '',
        sex: undefined,
        dateOfBirth: '',
    });

    const handleClose = () => {
        setOpen(false);
    };
    const handleOpen = () => {
        setOpen(true);
    };

    const formRef = useRef();

    const handleSubmit = async () => {
        console.log('Form submitted:', formValue);
        if (!formRef.current.check()) {
            console.error("Form Error");
            return;
        }

        formValue.dateOfBirth = `${formValue.dateOfBirth}T00:00:00`; // Убедитесь, что поле обновлено

        try {
            const token = authApi.register(formValue);
            console.log("token", token);
            login(await token, false);
            showMessage("success", "Registration successful");
            handleClose();
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                showMessage("error", error.response.data);
            }
        }
    };

    return (
        <>
            <Modal open={open} onClose={handleClose} size="xs">
                <Modal.Header>
                    <Modal.Title>Registration</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form fluid onChange={setFormValue} formValue={formValue} ref={formRef}>
                        <Form.Group controlId="name">
                            <Form.ControlLabel>Full Name</Form.ControlLabel>
                            <Form.Control name="name" />
                            <Form.HelpText>Required</Form.HelpText>
                        </Form.Group>
                        <Form.Group controlId="email">
                            <Form.ControlLabel>Email</Form.ControlLabel>
                            <Form.Control name="email" type="email" />
                            <Form.HelpText>Required</Form.HelpText>
                        </Form.Group>
                        <Form.Group controlId="password">
                            <Form.ControlLabel>Password</Form.ControlLabel>
                            <Form.Control name="password" type="password" autoComplete="off" />
                            <Form.HelpText>Required</Form.HelpText>
                        </Form.Group>
                        <Form.Group controlId="phone">
                            <Form.ControlLabel>Phone</Form.ControlLabel>
                            <Form.Control name="phone" type="tel" />
                        </Form.Group>
                        <Form.Group controlId="sex">
                            <Form.ControlLabel>Sex</Form.ControlLabel>
                            <Form.Control name="sex" accepter={SelectPicker} data={sexes} />
                        </Form.Group>
                        <Form.Group controlId="dateOfBirth">
                            <Form.ControlLabel>Date of Birth</Form.ControlLabel>
                            <Form.Control name="dateOfBirth" type="date" />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                
                <Modal.Footer>
                    <Button onClick={handleSubmit} appearance="primary">
                        Confirm
                    </Button>
                    <Button onClick={handleClose} appearance="subtle">
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>
            <Nav.Item icon={<PageEndIcon />} onClick={handleOpen}>
                Register
            </Nav.Item>
        </>
    );
}

export default RegisterComponent;