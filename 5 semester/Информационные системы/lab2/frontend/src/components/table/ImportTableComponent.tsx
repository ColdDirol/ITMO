import {ImportExportHistoryInterface} from "../../intefaces/importExportHistoryInterface.ts";
import React, {useState} from "react";
import userStore from "../../store/UserStore.ts";
import Column from "rsuite/esm/Table/TableColumn";
import {Cell, HeaderCell} from "rsuite-table";
import {FlexboxGrid, Pagination, Table} from "rsuite";
import MovieApiService from "../../service/movieApiService.ts";

const ImportMovieFormComponent = () => {
    const [data, setData] = useState<ImportExportHistoryInterface[]>([])
    const [loading, setLoading] = useState(false);
    const [totalElements, setTotalElements] = useState(0);
    const { pageElementsLimit } = userStore();
    const [activePage, setActivePage] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [isDataLoaded, setIsDataLoaded] = useState(false);

    const movieApi: MovieApiService = new MovieApiService();

    const refreshData = async () => {
        setLoading(true);
    }

    const refreshData = async () => {
        setLoading(true);
        try {
            let total = await movieApi.countAllImportHistoryByUser();
            const fetchedData = await movieApi.getAllByUser(
                pageElementsLimit,
                activePage
            );
            setData(fetchedData);
            setTotalElements(total);
            setIsDataLoaded(true);
        } catch (error) {
            console.error("Error fetching imports:", error);
        } finally {
            setLoading(false);
        }
    }

    return (
        <>
            <Table
                height={750}
                rowHeight={45}
                bordered={true}
                data={data}
                loading={loading}
            >
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>ID</HeaderCell>
                    <Cell dataKey="id"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Date</HeaderCell>
                    <Cell dataKey="date"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>User</HeaderCell>
                    <Cell dataKey="createdUser"/>
                </Column>
            </Table>
            <div style={{padding: 10}}>
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
            </div>

        </>
    )
}

export default ImportMovieFormComponent