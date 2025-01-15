import useUserStore from "../../store/UserStore.tsx";
import MessageComponent from "../message/MessageComponent.tsx";
import {useRef, useState} from "react";
import {LoginInterface} from "../../interfaces/AuthInterface.ts";
import authApi from "../../api/authApi.ts";
import axios from "axios";
import {Button, Form, Modal, Nav} from "rsuite";
import PageEndIcon from "@rsuite/icons/PageEnd";

const LoginComponent = () => {
    const { login } = useUserStore();
    const { showMessage } = MessageComponent();
    const [open, setOpen] = useState(false);
    const [formValue, setFormValue] = useState<Partial<LoginInterface>>({
        email: '',
        password: '',
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

        try {
            const token = authApi.login(formValue);
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
                    <Modal.Title>Authorization</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form fluid onChange={setFormValue} formValue={formValue} ref={formRef}>
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
                Login
            </Nav.Item>
        </>
    );
}

export default LoginComponent;