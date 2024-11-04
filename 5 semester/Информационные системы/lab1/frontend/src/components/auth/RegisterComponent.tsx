import { useRef, useState } from "react";
import {
  Button,
  Form,
  Message,
  Modal,
  Nav,
  Schema,
  SelectPicker,
  toaster,
} from "rsuite";
import PageEndIcon from "@rsuite/icons/PageEnd";
import { RoleEnum } from "../../intefaces/userInterface";
import userApiService from "../../service/userApiService";
import userStore from "../../store/UserStore";
import MessageComponent from "../message/MessageComponent";
import axios from "axios";

const { StringType } = Schema.Types;
const model = Schema.Model({
  username: StringType().isRequired("This field is required."),
  email: StringType()
    .isEmail("Please enter a valid email address.")
    .isRequired("This field is required."),
  password: StringType()
    .minLength(5, "Minimum 5 characters required")
    .isRequired("This field is required."),
  role: StringType().isRequired("This field is required."),
});

function TextField(props) {
  const { name, label, accepter, ...rest } = props;
  return (
    <Form.Group controlId={`${name}-3`}>
      <Form.ControlLabel>{label} </Form.ControlLabel>
      <Form.Control name={name} accepter={accepter} {...rest} />
    </Form.Group>
  );
}

function ChoosingField(props) {
  const { name, label, accepter, ...rest } = props;
  return (
    <Form.Group controlId="select=3">
      <Form.ControlLabel>{label} </Form.ControlLabel>
      <Form.Control name={name} accepter={accepter} {...rest} />
    </Form.Group>
  );
}

const selectData = Object.entries(RoleEnum).map(([value]) => ({
  label: value,
  value: value,
}));

export default function RegisterComponent() {
  const { login } = userStore();
  const [open, setOpen] = useState(false);
  const [formValue, setFormValue] = useState({
    username: "",
    email: "",
    password: "",
    role: "", // Добавляем поле для роли
  });
  const [formError, setFormError] = useState({});
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

  const formRef = useRef();

  const handleRegister = async () => {
    if (!formRef.current.check()) {
      console.error("Form Error");
      return;
    }
    try {
      const token = await userApiService.register(formValue);
      login(token);
      showMessage("success", "Registration successful");
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
        Sign up{" "}
      </Nav.Item>
      <Modal open={open} onClose={handleClose} size="xs">
        <Modal.Header>
          <Modal.Title>Sign up</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            model={model}
            fluid
            onChange={handleFormChange}
            formValue={formValue}
            ref={formRef}
          >
            <TextField
              placeholder="username"
              name="username"
              label="Username"
              autoComplete="off"
            />
            <TextField
              placeholder="email"
              name="email"
              label="Email"
              type="email"
              autoComplete="off"
            />
            <TextField
              placeholder="password"
              name="password"
              label="Password"
              type="password"
            />
            <ChoosingField
              placeholder="role"
              name="role"
              label="Role"
              data={selectData}
              accepter={SelectPicker}
            />
            {/* <Form.Group controlId="name-9">
              <Form.ControlLabel>Username</Form.ControlLabel>
              <Form.Control
                placeholder="username"
                name="username"
                type="username"
                autoComplete="off"
              />
            </Form.Group>
            <Form.Group controlId="email-9">
              <Form.ControlLabel>Email</Form.ControlLabel>
              <Form.Control placeholder="email" name="email" type="email" />
            </Form.Group>
            <Form.Group controlId="password-9">
              <Form.ControlLabel>Password</Form.ControlLabel>
              <Form.Control
                placeholder="password"
                name="password"
                type="password"
                autoComplete="off"
              />
            </Form.Group>
            <Form.Group controlId="select-10">
              <Form.ControlLabel>Role</Form.ControlLabel>
              <Form.Control
                placeholder="choose role"
                name="role"
                data={selectData}
                accepter={SelectPicker}
              />
            </Form.Group> */}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleRegister} appearance="primary" type="submit">
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
