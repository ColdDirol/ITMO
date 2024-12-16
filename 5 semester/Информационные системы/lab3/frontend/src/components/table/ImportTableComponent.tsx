import {useEffect, useState} from "react";
import userStore from "../../store/UserStore.ts";
import Column from "rsuite/esm/Table/TableColumn";
import {Cell, HeaderCell} from "rsuite-table";
import {FlexboxGrid, Pagination, Table} from "rsuite";
import ImportExportApiService from "../../service/importExportApiService.ts";
import {format} from "rsuite/cjs/internals/utils/date";
import {ImportHistoryInterface} from "../../intefaces/importExportHistoryInterface.ts";
import MovieApiService from "../../service/movieApiService.ts";

const ImportMovieFormComponent = () => {
    const [data, setData] = useState<ImportHistoryInterface[]>([])
    const [loading, setLoading] = useState(false);

    const [totalElements, setTotalElements] = useState(0);
    const { pageElementsLimit } = userStore();
    const [activePage, setActivePage] = useState(1);

    const importExportApi: ImportExportApiService = new ImportExportApiService();
    const moviesApi: MovieApiService = new MovieApiService();

    const refreshData = async () => {
        setLoading(true);
        try {
            setTotalElements(await importExportApi.countAllImportsByUser())
            const fetchedData = await importExportApi.getAllImportsByUser(
                pageElementsLimit,
                activePage
            );
            setData(fetchedData);
        } catch (error) {
            console.error("Error fetching exports:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                setTotalElements(await importExportApi.countAllImportsByUser())
                setData(await importExportApi.getAllImportsByUser(pageElementsLimit, activePage));
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
    }, [activePage]);


    const handleClick = (rowData: ImportHistoryInterface) => {
        console.log(rowData);
        moviesApi.importMoviesFromHistory(rowData.fileIndex);
    };


    const DateCell = ({rowData, dataKey, ...props}: any) => (
        <Cell {...props}>
            {rowData[dataKey]
                ? format(new Date(rowData[dataKey]), "yyyy-MM-dd HH:mm:ss")
                : "N/A"}
        </Cell>
    );

    return (
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
                    <HeaderCell>ID</HeaderCell>
                    <Cell dataKey="id"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Date</HeaderCell>
                    <DateCell dataKey="date"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>User</HeaderCell>
                    <Cell dataKey="createdUser"/>
                </Column>
            </Table>
            <div style={{padding: 10}}>
                <FlexboxGrid justify="end">
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
            </div>

        </>
    )
}

export default ImportMovieFormComponent
