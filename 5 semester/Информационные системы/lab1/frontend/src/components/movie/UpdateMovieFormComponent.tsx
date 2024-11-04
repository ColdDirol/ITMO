import { useEffect, useRef, useState } from "react";
import {
  Button,
  ButtonToolbar,
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
  IMovie,
  mapFormDataToIMovieUpdate,
} from "../../intefaces/movieInterface";
import MovieApiService from "../../service/movieApiService";
import EditIcon from "@rsuite/icons/Edit";
import TrashIcon from "@rsuite/icons/Trash";
import MessageComponent from "../message/MessageComponent";
import axios from "axios";

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

const UpdateMovieFormComponent = ({ movie, onClose, open, onRefresh }) => {
  const [formValue, setFormValue] = useState({
    id: 0,
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
    createdUser: "",
    isPublic: false,
  });

  const { showMessage } = MessageComponent();

  useEffect(() => {
    if (movie) {
      console.log(movie.director?.location?.x);
      const initedData = {
        id: movie.id,
        name: movie.name || "",
        coordinates: movie.coordinates || { x: 0, y: 0 },
        creationDate: movie.creationDate || "",
        oscarsCount: movie.oscarsCount ?? 0,
        budget: movie.budget ?? 0,
        totalBoxOffice: movie.totalBoxOffice ?? 0,
        mpaaRating: movie.mpaaRating || "",
        director: {
          name: movie.director?.name || "",
          eyeColor: movie.director?.eyeColor ?? ColorEnum.GREEN,
          hairColor: movie.director?.hairColor ?? ColorEnum.GREEN,
          location: {
            x: movie.director?.location?.x ?? 0,
            y: movie.director?.location?.y ?? 0,
            z: movie.director?.location?.z ?? 0,
            name: movie.director?.location?.name || "",
          },
          birthday: movie.director?.birthday || "",
        },
        screenwriter: {
          name: movie.screenwriter.name || "",
          eyeColor: movie.screenwriter.eyeColor ?? ColorEnum.GREEN,
          hairColor: movie.screenwriter.hairColor ?? ColorEnum.GREEN,
          location: {
            x: movie.screenwriter.location.x ?? 0,
            y: movie.screenwriter.location.y ?? 0,
            z: movie.screenwriter.location.z ?? 0,
            name: movie.screenwriter.location.name || "",
          },
          birthday: movie.screenwriter.birthday || "",
        },
        operator: {
          name: movie.operator?.name || "",
          eyeColor: movie.operator?.eyeColor ?? ColorEnum.GREEN,
          hairColor: movie.operator?.hairColor ?? ColorEnum.GREEN,
          location: {
            x: movie.operator?.location?.x ?? 0,
            y: movie.operator?.location?.y ?? 0,
            z: movie.operator?.location?.z ?? 0,
            name: movie.operator?.location?.name || "",
          },
          birthday: movie.operator?.birthday || "",
        },
        length: movie.length ?? 0,
        goldenPalmCount: movie.goldenPalmCount ?? 0,
        usaBoxOffice: movie.usaBoxOffice ?? 0,
        tagline: movie.tagline || "",
        genre: movie.genre || "",
        createdUser: movie.createdUser,
        isPublic: movie.isPublic ?? false,
      };
      console.log("initedData:", initedData);
      setFormValue(initedData);
    }
  }, [movie]);

  useEffect(() => {
    console.log("formValue after init:", formValue);
  }, [formValue]);

  useEffect(() => {
    if (!open) {
      setFormValue({
        id: 0,
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
        createdUser: "",
        isPublic: false,
      });
    }
  }, [open]);

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

  const movieApi = new MovieApiService();

  const handleClose = () => {
    onClose();
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
      const data = mapFormDataToIMovieUpdate(formValue);
      console.log("data:", data);
      const createdMovie = await movieApi.update(data);
      console.log("Updated Movie:", createdMovie);
      handleStopEditing();
      onRefresh();
      handleClose();
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        showMessage("error", error.response.data);
      }
      console.error("Failed to create movie:", error);
    }
  };

  const handleRemove = async () => {
    try {
      console.log("remove");
      await movieApi.delete(formValue.id);
      onRefresh();
      handleClose();
    } catch (error) {
      console.error("Error:", error);
      if (axios.isAxiosError(error) && error.response) {
        showMessage("error", error.response.data);
      }
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return null;
    const date = new Date(dateString);
    return isNaN(date) ? null : date;
  };

  const [status, setStatus] = useState("plaintext");
  const [isEditing, setIsEditing] = useState(false);

  const normal = status === "normal";
  const plaintext = status === "plaintext";

  const handleEdit = () => {
    console.log("edit");
    setStatus("normal");
    setIsEditing(true);
  };

  const handleStopEditing = () => {
    setIsEditing(false);
    setStatus("plaintext");
  };

  const canvasRef = useRef(null);

  useEffect(() => {
    if (open) {
      drawCanvas();
    }
  }, [formValue.coordinates, open]);

  const drawCanvas = () => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    const width = canvas.width;
    const height = canvas.height;

    // Очищаем канвас
    ctx.clearRect(0, 0, width, height);
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, width, height);

    // Рисуем координатные оси
    ctx.beginPath();
    ctx.moveTo(width / 2, 0);
    ctx.lineTo(width / 2, height);
    ctx.moveTo(0, height / 2);
    ctx.lineTo(width, height / 2);
    ctx.strokeStyle = "black";
    ctx.stroke();

    let coefficient = 20; // коэффициент для масштабирования
    // const maxValue = Math.max(
    //   Math.abs(formValue.coordinates.x),
    //   Math.abs(formValue.coordinates.y)
    // );
    // if (maxValue === 0) {
    //   coefficient = 1; // Устанавливаем коэффициент по умолчанию
    // } else {
    //   coefficient = width / maxValue;
    // }
    // console.log(coefficient);
    coefficient = 1;
    for (let i = -100; i <= 100; i++) {
      ctx.fillText(i, width / 2 + i * coefficient, height / 2 + 10); // Ось X
      ctx.fillText(i, width / 2 - 20, height / 2 - i * coefficient + 5); // Ось Y
    }

    // Рисуем точки X и Y
    const x = formValue.coordinates.x;
    const y = formValue.coordinates.y;

    // Рисуем точку X
    ctx.beginPath();
    ctx.arc(
      width / 2 + x * coefficient,
      height / 2 - y * coefficient,
      5,
      0,
      2 * Math.PI
    );
    ctx.fillStyle = "red"; // Цвет точки
    ctx.fill();
  };

  const formRef = useRef();

  return (
    <>
      <Modal open={open} onClose={handleClose} size="lg">
        <ButtonToolbar>
          {isEditing ? (
            <IconButton onClick={handleStopEditing} icon={<EditIcon />}>
              Stop editing
            </IconButton>
          ) : (
            <IconButton onClick={handleEdit} icon={<EditIcon />}>
              Edit
            </IconButton>
          )}

          <IconButton
            onClick={handleRemove}
            appearance="primary"
            color="red"
            icon={<TrashIcon />}
          >
            Remove
          </IconButton>
        </ButtonToolbar>
        <Modal.Body>
          <canvas
            ref={canvasRef}
            width={300}
            height={300}
            style={{ border: "1px solid black" }}
          />
          <Form
            fluid
            onChange={handleSetValue}
            formValue={formValue}
            plaintext={plaintext}
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
              <Form.Control
                name="coordinates.x"
                type="number"
                value={formValue.coordinates.x}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Coordinates Y</Form.ControlLabel>
              <Form.Control
                name="coordinates.y"
                type="number"
                value={formValue.coordinates.y}
              />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Creation Date</Form.ControlLabel>
              <Form.Control
                name="creationDate"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
                value={formatDate(formValue.creationDate)}
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
              <Form.Control
                name="director.name"
                value={formValue.director.name}
              />
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
                value={formValue.director.eyeColor}
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
                value={formValue.director.hairColor}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location X</Form.ControlLabel>
              <Form.Control
                name="director.location.x"
                type="number"
                value={formValue.director.location.x}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Y</Form.ControlLabel>
              <Form.Control
                name="director.location.y"
                type="number"
                value={formValue.director.location.y}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Z</Form.ControlLabel>
              <Form.Control
                name="director.location.z"
                type="number"
                value={formValue.director.location.z}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Director Location Name</Form.ControlLabel>
              <Form.Control
                name="director.location.name"
                value={formValue.director.location.name}
              />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Director Birthday</Form.ControlLabel>
              <Form.Control
                name="director.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
                value={formatDate(formValue.director.birthday)}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Name</Form.ControlLabel>
              <Form.Control
                name="screenwriter.name"
                value={formValue.screenwriter.name}
              />
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
                value={formValue.screenwriter.eyeColor}
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
                value={formValue.screenwriter.hairColor}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location X</Form.ControlLabel>
              <Form.Control
                name="screenwriter.location.x"
                type="number"
                value={formValue.screenwriter.location.x}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Y</Form.ControlLabel>
              <Form.Control
                name="screenwriter.location.y"
                type="number"
                value={formValue.screenwriter.location.y}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Z</Form.ControlLabel>
              <Form.Control
                name="screenwriter.location.z"
                type="number"
                value={formValue.screenwriter.location.z}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Screenwriter Location Name</Form.ControlLabel>
              <Form.Control
                name="screenwriter.location.name"
                value={formValue.screenwriter.location.name}
              />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Screenwriter Birthday</Form.ControlLabel>
              <Form.Control
                name="screenwriter.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
                value={formatDate(formValue.screenwriter.birthday)}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Name</Form.ControlLabel>
              <Form.Control
                name="operator.name"
                value={formValue.operator.name}
              />
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
                value={formValue.operator.eyeColor}
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
                value={formValue.operator.hairColor}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location X</Form.ControlLabel>
              <Form.Control
                name="operator.location.x"
                type="number"
                value={formValue.operator.location.x}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Y</Form.ControlLabel>
              <Form.Control
                name="operator.location.y"
                type="number"
                value={formValue.operator.location.y}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Z</Form.ControlLabel>
              <Form.Control
                name="operator.location.z"
                type="number"
                value={formValue.operator.location.z}
              />
            </Form.Group>
            <Form.Group>
              <Form.ControlLabel>Operator Location Name</Form.ControlLabel>
              <Form.Control
                name="operator.location.name"
                value={formValue.operator.location.name}
              />
            </Form.Group>
            <Form.Group controlId="datePicker">
              <Form.ControlLabel>Operator Birthday</Form.ControlLabel>
              <Form.Control
                name="operator.birthday"
                type="date"
                format="dd.MM.yyyy"
                accepter={DatePicker}
                value={formatDate(formValue.operator.birthday)}
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
          {isEditing ? (
            <Button onClick={handleSubmit} appearance="primary">
              Confirm{" "}
            </Button>
          ) : null}

          <Button onClick={handleClose} appearance="subtle">
            Cancel
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default UpdateMovieFormComponent;
