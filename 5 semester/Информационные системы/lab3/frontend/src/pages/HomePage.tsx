import { FlexboxGrid, Heading } from "rsuite";
import TableComponent from "../components/table/TableComponent";
import AddMovieFormComponent from "../components/movie/AddMovieFormComponent";
import { useState, useCallback } from "react";

const HomePage = () => {
  const [refreshData, setRefreshData] = useState(() => () => {});

  const handleRefreshData = useCallback(() => {
    if (typeof refreshData === "function") {
      refreshData(); // Call the refreshData function if it's defined
    }
  }, [refreshData]);

  return (
    <>
      <div className="show-grid" style={{ padding: 10 }}>
        <FlexboxGrid>
          <FlexboxGrid.Item colspan={14}>
            <Heading level={3}>Your movies</Heading>
          </FlexboxGrid.Item>

          <FlexboxGrid.Item
            colspan={10}
            style={{ display: "flex", justifyContent: "flex-end" }}
          >
            <AddMovieFormComponent onRefresh={handleRefreshData} />
          </FlexboxGrid.Item>
        </FlexboxGrid>
      </div>

      <TableComponent page="home" />
    </>
  );
};

export default HomePage;
