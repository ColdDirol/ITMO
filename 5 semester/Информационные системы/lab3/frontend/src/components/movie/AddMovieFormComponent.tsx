import { FC, forwardRef, useRef, useState } from "react";
import {
  Button,
  DatePicker,
  Form,
  IconButton,
  Modal,
  Schema,
  SelectPicker,
  Toggle,
} from "rsuite";
import {
  MpaaRatingEnum,
  MovieGenreEnum,
  mapFormDataToIMovie,
  ColorEnum,
} from "../../intefaces/movieInterface";
import MovieApiService from "../../service/movieApiService";
import PlusIcon from "@rsuite/icons/Plus";
import axios from "axios";
import MessageComponent from "../message/MessageComponent";

const { StringType, DateType } = Schema.Types;
const model = Schema.Model({
  name: StringType().isRequired("This field is required."),
  creationDate: DateType().isRequired("This field is required."),
  oscarsCount: StringType().isRequired("This field is required."),
  budget: StringType().isRequired("This field is required."),
  totalBoxOffice: StringType().isRequired("This field is required."),
  mpaaRating: StringType().isRequired("This field is required."),
  length: StringType().isRequired("This field is required."),
  goldenPalmCount: StringType().isRequired("This field is required."),
  usaBoxOffice: StringType().isRequired("This field is required."),
  tagline: StringType().isRequired("This field is required."),
});

const AddMovieFormComponent = () => {
  const [open, setOpen] = useState(false);
  const [formValue, setFormValue] = useState({
    name: "",
    coordinates: { x: 0, y: 0 },
    creationDate: "",
    oscarsCount: 0,
    budget: 0,
    totalBoxOffice: 0,
    mpaaRating: "",
    director: {
      name: "",
      eyeColor: "",
      hairColor: "",
      location: { x: 0, y: 0, z: 0, name: "" },
      birthday: "",
    },
    screenwriter: {
      name: "",
      eyeColor: "",
      hairColor: "",
      location: { x: 0, y: 0, z: 0, name: "" },
      birthday: "",
    },
    operator: {
      name: "",
      eyeColor: "",
      hairColor: "",
      location: { x: 0, y: 0, z: 0, name: "" },
      birthday: "",
    },
    length: 0,
    goldenPalmCount: 0,
    usaBoxOffice: 0,
    tagline: "",
    genre: "",
    isPublic: false,
  });

  const { showMessage } = MessageComponent();

  const movieApi = new MovieApiService();

  const handleSetValue = (value) => {
    console.log(value);
    setFormValue((prevFormValue) => {
      const newFormValue = { ...prevFormValue };

      // Обрабатываем вложенные значения
      for (const key in value) {
        const keys = key.split(".");
        if (keys.length > 1) {
          let temp = newFormValue;
          for (let i = 0; i < keys.length - 1; i++) {
            if (!temp[keys[i]]) {
              temp[keys[i]] = {};
            }
            temp = temp[keys[i]];
          }
          temp[keys[keys.length - 1]] = value[key];
        } else {
          newFormValue[key] = value[key];
        }
      }

      return newFormValue;
    });
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleSubmit = async () => {
    if (!formValue.coordinates.x || !formValue.coordinates.y) {
      showMessage("error", "Fill in coordinates fields");
      return;
    } else if (
      !formValue.director.name ||
      !formValue.director.eyeColor ||
      !formValue.director.hairColor ||
      !formValue.director.location.name
    ) {
      showMessage("error", "Fill in director fieleds");
      return;
    }
    // отображение навязчивых подсказов
    if (!formRef.current.check()) {
      return;
    }
    try {
      console.log("formValue:", formValue);
      const data = mapFormDataToIMovie(formValue);
      console.log("data:", data);
      const createdMovie = await movieApi.create(data);
      console.log("Created Movie:", createdMovie);
      setOpen(false);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        showMessage("error", error.response.data);
      }
    }
  };

  const formRef = useRef();

  return (
    <>
      <IconButton
        onClick={handleOpen}
        appearance="primary"
        color="green"
        icon={<PlusIcon />}
      >
        New movie
      </IconButton>
      <Modal open={open} onClose={handleClose} size="lg">
        <Modal.Header>
          <Modal.Title>Add New Movie</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            fluid
            onChange={handleSetValue}
            formValue={formValue}
            model={model}
            ref={formRef}
          >
            <Form.Group>
              <Form.ControlLabel>Movie Name</Form.ControlLabel>
              <Form.Control name="name" required />
              <Form.HelpText tooltip>Required</Form.HelpText>
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Coordinates X</Form.ControlLabel>
              <Form.Control name="coordinates.x" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Coordinates Y</Form.ControlLabel>
              <Form.Control name="coordinates.y" type="number" />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Creation Date</Form.ControlLabel>
              <Form.Control
                name="creationDate"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Oscars Count</Form.ControlLabel>
              <Form.Control name="oscarsCount" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Budget</Form.ControlLabel>
              <Form.Control name="budget" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Total Box Office</Form.ControlLabel>
              <Form.Control name="totalBoxOffice" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>MPAA Rating</Form.ControlLabel>
              <Form.Control
                name="mpaaRating"
                data={Object.entries(MpaaRatingEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Name</Form.ControlLabel>
              <Form.Control name="director.name" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Eye Color</Form.ControlLabel>
              <Form.Control
                name="director.eyeColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Hair Color</Form.ControlLabel>
              <Form.Control
                name="director.hairColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location X</Form.ControlLabel>
              <Form.Control name="director.location.x" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Y</Form.ControlLabel>
              <Form.Control name="director.location.y" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Z</Form.ControlLabel>
              <Form.Control name="director.location.z" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Name</Form.ControlLabel>
              <Form.Control name="director.location.name" />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Director Birthday</Form.ControlLabel>
              <Form.Control
                name="director.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Name</Form.ControlLabel>
              <Form.Control name="screenwriter.name" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Eye Color</Form.ControlLabel>
              <Form.Control
                name="screenwriter.eyeColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Hair Color</Form.ControlLabel>
              <Form.Control
                name="screenwriter.hairColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location X</Form.ControlLabel>
              <Form.Control name="screenwriter.location.x" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Y</Form.ControlLabel>
              <Form.Control name="screenwriter.location.y" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Z</Form.ControlLabel>
              <Form.Control name="screenwriter.location.z" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Name</Form.ControlLabel>
              <Form.Control name="screenwriter.location.name" />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Screenwriter Birthday</Form.ControlLabel>
              <Form.Control
                name="screenwriter.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Name</Form.ControlLabel>
              <Form.Control name="operator.name" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Eye Color</Form.ControlLabel>
              <Form.Control
                name="operator.eyeColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Hair Color</Form.ControlLabel>
              <Form.Control
                name="operator.hairColor"
                data={Object.entries(ColorEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location X</Form.ControlLabel>
              <Form.Control name="operator.location.x" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Y</Form.ControlLabel>
              <Form.Control name="operator.location.y" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Z</Form.ControlLabel>
              <Form.Control name="operator.location.z" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Name</Form.ControlLabel>
              <Form.Control name="operator.location.name" />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Operator Birthday</Form.ControlLabel>
              <Form.Control
                name="operator.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Length (minutes)</Form.ControlLabel>
              <Form.Control name="length" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Golden Palm Count</Form.ControlLabel>
              <Form.Control name="goldenPalmCount" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>USA Box Office</Form.ControlLabel>
              <Form.Control name="usaBoxOffice" type="number" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Tagline</Form.ControlLabel>
              <Form.Control name="tagline" />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Genre</Form.ControlLabel>
              <Form.Control
                name="genre"
                data={Object.entries(MovieGenreEnum).map(([value]) => ({
                  label: value,
                  value: value,
                }))}
                accepter={SelectPicker}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Is Public</Form.ControlLabel>
              <Form.Control
                name="isPublic"
                accepter={Toggle}
                unCheckedChildren="Private"
                checkedChildren="Public"
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={handleSubmit} appearance="primary">
            Confirm{" "}
          </Button>
          <Button onClick={handleClose} appearance="subtle">
            Cancel
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default AddMovieFormComponent;
