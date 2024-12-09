import Column from "rsuite/TableColumn";
import {Cell, HeaderCell} from "rsuite-table";
import {FlexboxGrid, Pagination, Table} from "rsuite";
import {useState} from "react";
import {ImportExportHistoryInterface} from "../../intefaces/importExportHistoryInterface.ts";
import userStore from "../../store/UserStore.ts";

const ExportMovieFormComponent = () => {
    const [data, setData] = useState<ImportExportHistoryInterface[]>([])
    const [loading, setLoading] = useState(false);

    const [totalElements, setTotalElements] = useState(0);
    const { pageElementsLimit } = userStore();
    const [activePage, setActivePage] = useState(1);
    const [totalElements, setTotalElements] = useState(0);

    const refreshData = async () => {
        setLoading(true);
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

export default ExportMovieFormComponent