import { forwardRef, useRef, useState } from "react";
import { Button, Form, Modal, Nav, Schema } from "rsuite";
import PageEndIcon from "@rsuite/icons/PageEnd";
import userApiService from "../../service/userApiService";
import userStore from "../../store/UserStore";
import MessageComponent from "../message/MessageComponent";
import axios from "axios";
import { FormComponent } from "rsuite/esm/Form/Form";

interface TextFieldProps {
  name: string;
  label: string;
  accepter?: React.ElementType; // Опциональный тип для 'accepter'
  [key: string]: any; // Для остальных пропсов, которые могут быть переданы
}

// Используем Omit для исключения ref из пропсов
const TextField = forwardRef<HTMLDivElement, TextFieldProps>((props, ref) => {
  const { name, label, accepter, ...rest } = props;

  return (
    <Form.Group ref={ref}>
      <Form.ControlLabel>{label}</Form.ControlLabel>
      <Form.Control name={name} accepter={accepter} {...rest} />
    </Form.Group>
  );
});

const { StringType } = Schema.Types;
const model = Schema.Model({
  email: StringType()
    .isEmail("Please enter a valid email address.")
    .isRequired("This field is required."),
  password: StringType().isRequired("This field is required."),
});

export default function LoginComponent() {
  const { login } = userStore();
  const [open, setOpen] = useState(false);
  const [formValue, setFormValue] = useState({
    email: "",
    password: "",
  });
  const { showMessage } = MessageComponent();

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleFormChange = (value: any) => {
    setFormValue(value);
  };

  const formRef = useRef<FormComponent>();

  const handleLogin = async () => {
    if (!formRef.current.check()) {
      console.error("Form Error");
      return;
    }
    try {
      const token = await userApiService.login(formValue);
      login(token);
      showMessage("success", "Login successful");
      setOpen(false);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        showMessage("error", error.response.data);
      }
    }
  };

  return (
    <>
      <Nav.Item icon={<PageEndIcon />} onClick={handleOpen}>
        Log in{" "}
      </Nav.Item>
      <Modal open={open} onClose={handleClose} size="xs">
        <Modal.Header>
          <Modal.Title>Log in</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            fluid
            onChange={handleFormChange}
            formValue={formValue}
            model={model}
            ref={formRef}
          >
            <TextField
              placeholder="email"
              name="email"
              label="Email"
              type="email"
            />
            <TextField
              placeholder="password"
              name="password"
              label="Password"
              type="password"
            />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleLogin} appearance="primary">
            Confirm
          </Button>
          <Button onClick={handleClose} appearance="subtle">
            Cancel
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
