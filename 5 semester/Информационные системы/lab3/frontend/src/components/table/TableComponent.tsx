import { FC, useEffect, useState } from "react";
import {
  Button,
  ButtonToolbar,
  FlexboxGrid,
  Form,
  IconButton,
  Message,
  Modal,
  Pagination,
  Table,
  toaster,
} from "rsuite";
import { Cell, HeaderCell } from "rsuite-table";
import Column from "rsuite/esm/Table/TableColumn";
import { IMovie } from "../../intefaces/movieInterface";
import MovieApiService from "../../service/movieApiService";
import userStore from "../../store/UserStore";
import UpdateMovieFormComponent from "../movie/UpdateMovieFormComponent";
import MenuIcon from "@rsuite/icons/Menu";
import ReloadIcon from "@rsuite/icons/Reload";
import CloseIcon from "@rsuite/icons/Close";

interface TableComponentProps {
  page?: string;
}
const TableComponent: FC<TableComponentProps> = ({ page }) => {
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [isUpdateFormOpen, setIsUpdateFormOpen] = useState(false);
  const { pageElementsLimit } = userStore();
  const movieApi: MovieApiService = new MovieApiService();

  const [data, setData] = useState<IMovie[]>([]);
  const [loading, setLoading] = useState(false);
  const [isDataLoaded, setIsDataLoaded] = useState(false);
  const [activePage, setActivePage] = useState(1);
  const [totalElements, setTotalElements] = useState(0);

  const [isFiltered, setIsFiltered] = useState(false);

  const refreshData = async () => {
    setLoading(true);
    try {
      let total: number;
      if (isFiltered) {
        const columnWithRequest = {
          id: formValue.id,
          name: formValue.name,
          budget: formValue.budget,
          genre: formValue.genre,
        };

        if (page === "home") {
          const fetchedData = await movieApi.getAllByUserFiltered(
            pageElementsLimit,
            activePage,
            "",
            columnWithRequest
          );
          setData(fetchedData);
          total = fetchedData.length; // Используем свойство length
        } else {
          const fetchedData = await movieApi.getAllPublicFiltered(
            pageElementsLimit,
            activePage,
            "",
            columnWithRequest
          );
          setData(fetchedData);
          total = fetchedData.length; // Используем свойство length
        }
      } else {
        if (page === "home") {
          total = await movieApi.countAllByUser();
          const fetchedData = await movieApi.getAllByUser(
            pageElementsLimit,
            activePage
          );
          setData(fetchedData);
        } else {
          total = await movieApi.countAllPublic();
          const fetchedData = await movieApi.getAllPublic(
            pageElementsLimit,
            activePage
          );
          setData(fetchedData);
        }
      }
      setTotalElements(total);
      setIsDataLoaded(true);
    } catch (error) {
      console.error("Error fetching movies:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        let total: number;
        if (page === "home") {
          total = await movieApi.countAllByUser();
          setData(await movieApi.getAllByUser(pageElementsLimit, activePage));
        } else {
          total = await movieApi.countAllPublic();
          setData(await movieApi.getAllPublic(pageElementsLimit, activePage));
        }
        setTotalElements(total);
        setIsDataLoaded(true);
      } catch (error) {
        console.error("Error fetching movies:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();

    const intervalId = setInterval(() => {
      refreshData();
    }, 60000); // каждую минуту

    return () => clearInterval(intervalId);
  }, [page, activePage]);

  const handleClick = (rowData: IMovie) => {
    console.log(rowData);
    setSelectedMovie(rowData);
    setIsUpdateFormOpen(true);
  };

  const handleCloseForm = () => {
    setIsUpdateFormOpen(false);
    setSelectedMovie(null);
    refreshData(); // Обновляем данные после закрытия формы
  };

  const [open, setOpen] = useState(false);
  const [formValue, setFormValue] = useState({
    id: "",
    name: "",
    budget: "",
    genre: "",
  });

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  useEffect(() => {
    refreshData();
  }, [isFiltered]);

  const handleStopFiltering = () => {
    setIsFiltered(false);
  };

  const handleFormChange = (value: any) => {
    setFormValue(value);
    console.log("formValue:", formValue);
  };

  const handleFilter = async () => {
    const allFieldsEmpty = Object.values(formValue).every(
      (value) => value === ""
    );

    if (allFieldsEmpty) {
      toaster.push(
        <Message showIcon type="error">
          Select at least one filter
        </Message>
      );
      return;
    }
    console.log("validate params:", formValue);
    setIsFiltered(true); // Устанавливаем флаг фильтрации
    setOpen(false); // Закрываем модальное окно
  };

  return (
    <div>
      {isDataLoaded ? (
        <>
          <Table
            height={750}
            rowHeight={45}
            bordered={true}
            data={data}
            loading={loading}
            onRowClick={handleClick}
          >
            <Column flexGrow={1} sortable resizable>
              <HeaderCell>Id</HeaderCell>
              <Cell dataKey="id" />
            </Column>
            <Column flexGrow={7} align="center" fixed sortable resizable>
              <HeaderCell>Name</HeaderCell>
              <Cell dataKey="name" />
            </Column>
            <Column flexGrow={2} align="center" sortable resizable>
              <HeaderCell>Budget</HeaderCell>
              <Cell dataKey="budget" />
            </Column>
            <Column flexGrow={2} align="center" sortable resizable>
              <HeaderCell>Genre</HeaderCell>
              <Cell dataKey="genre" />
            </Column>
            {page === "home" ? (
              <Column flexGrow={2} align="center" sortable resizable>
                <HeaderCell>Visibility</HeaderCell>
                <Cell dataKey="isPublic">
                  {(rowData) => (rowData.isPublic ? "Public" : "Private")}
                </Cell>
              </Column>
            ) : null}
          </Table>
          {selectedMovie && (
            <UpdateMovieFormComponent
              open={isUpdateFormOpen}
              onClose={handleCloseForm}
              movie={selectedMovie}
              onRefresh={refreshData}
            />
          )}
          <div style={{ padding: 10 }}>
            <FlexboxGrid>
              <FlexboxGrid.Item colspan={14}>
                <ButtonToolbar>
                  <IconButton
                    icon={isFiltered ? <CloseIcon /> : <MenuIcon />}
                    onClick={isFiltered ? handleStopFiltering : handleOpen}
                    appearance="primary"
                    color={isFiltered ? "red" : "cyan"}
                  >
                    Filter
                  </IconButton>
                  <IconButton icon={<ReloadIcon />} onClick={refreshData}>
                    Reload
                  </IconButton>
                </ButtonToolbar>
              </FlexboxGrid.Item>

              <FlexboxGrid.Item
                colspan={10}
                style={{ display: "flex", justifyContent: "flex-end" }}
              >
                <Pagination
                  prev
                  last
                  next
                  first
                  size="md"
                  layout={["total", "|", "pager"]}
                  total={totalElements}
                  limit={pageElementsLimit}
                  activePage={activePage}
                  onChangePage={setActivePage}
                />
              </FlexboxGrid.Item>
            </FlexboxGrid>
            <Modal open={open} onClose={handleClose} size="xs">
              <Modal.Header>
                <Modal.Title>Filter</Modal.Title>
              </Modal.Header>
              <Modal.Body>
                <Form fluid onChange={handleFormChange} formValue={formValue}>
                  <Form.Group controlId="id">
                    <Form.ControlLabel>ID</Form.ControlLabel>
                    <Form.Control placeholder="id" name="id" type="id" />
                  </Form.Group>
                  <Form.Group controlId="name">
                    <Form.ControlLabel>Movie name</Form.ControlLabel>
                    <Form.Control placeholder="name" name="name" type="name" />
                  </Form.Group>
                  <Form.Group controlId="budger">
                    <Form.ControlLabel>Budget</Form.ControlLabel>
                    <Form.Control
                      placeholder="budger"
                      name="budger"
                      type="budger"
                    />
                  </Form.Group>
                  <Form.Group controlId="genre">
                    <Form.ControlLabel>Genre</Form.ControlLabel>
                    <Form.Control
                      placeholder="genre"
                      name="genre"
                      type="genre"
                    />
                  </Form.Group>
                </Form>
              </Modal.Body>
              <Modal.Footer>
                <Button onClick={handleFilter} appearance="primary">
                  Confirm
                </Button>
                <Button onClick={handleClose} appearance="subtle">
                  Cancel
                </Button>
              </Modal.Footer>
            </Modal>
          </div>
        </>
      ) : null}
    </div>
  );
};

export default TableComponent;
