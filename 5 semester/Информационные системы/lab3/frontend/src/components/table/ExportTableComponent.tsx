import Column from "rsuite/TableColumn";
import {Cell, HeaderCell} from "rsuite-table";
import {FlexboxGrid, Pagination, Table} from "rsuite";
import {useEffect, useState} from "react";
import userStore from "../../store/UserStore.ts";
import ImportExportApiService from "../../service/importExportApiService.ts";
import {format} from "rsuite/cjs/internals/utils/date";
import {ExportHistoryInterface} from "../../intefaces/importExportHistoryInterface.ts";

const ExportTableComponent = () => {
    const [data, setData] = useState<ExportHistoryInterface[]>([]);
    const [loading, setLoading] = useState(false);
    const [totalElements, setTotalElements] = useState(0);
    const {pageElementsLimit} = userStore();
    const [activePage, setActivePage] = useState(1);

    const importExportApi: ImportExportApiService = new ImportExportApiService();

    const refreshData = async () => {
        setLoading(true);
        try {
            setTotalElements(await importExportApi.countAllExportsByUser());
            const fetchedData = await importExportApi.getAllExportsByUser(
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
                setTotalElements(await importExportApi.countAllExportsByUser());
                setData(await importExportApi.getAllExportsByUser(pageElementsLimit, activePage));
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
                        style={{display: "flex", justifyContent: "flex-end"}}
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
    );
}

export default ExportTableComponent
