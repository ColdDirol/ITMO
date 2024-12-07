import { Heading } from "rsuite";
import TableComponent from "../components/table/TableComponent";

const MainPage = () => {
  return (
    <>
      <Heading level={3}>Public movies</Heading>
      {/* <SearchComponent /> */}
      <TableComponent page="main" />
    </>
  );
};

export default MainPage;
