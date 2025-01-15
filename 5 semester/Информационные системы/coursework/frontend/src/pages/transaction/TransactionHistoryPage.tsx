import {
    TransactionHistoryLogInterface,
    TransactionHistoryLogRequest
} from "../../interfaces/TransactionHistoryLogInterface.ts";
import {useEffect, useState} from "react";
import userStore from "../../store/UserStore.tsx";
import transactionHistoryApi from "../../api/transactionHistoryApi.ts";
import SortDownIcon from '@rsuite/icons/SortDown';
import useUserStore from "../../store/UserStore.tsx";
import {Cell, Column, HeaderCell} from "rsuite-table";
import {format} from "rsuite/cjs/internals/utils/date";
import {FlexboxGrid, IconButton, Pagination, Table} from "rsuite";

const TransactionHistoryPage = () => {
    const { email } = useUserStore();
    const [data, setData] = useState<TransactionHistoryLogInterface[]>([])
    const [loading, setLoading] = useState(false);
    const [totalElements, setTotalElements] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const {pageElementsLimit} = userStore();
    const [activePage, setActivePage] = useState(1);
    const [request, setRequest] = useState<Partial<TransactionHistoryLogRequest>>({
        userEmail: '',
        page: 0,
        size: 10,
    });

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const updatedRequest = { ...request, userEmail: email };
                const response = await transactionHistoryApi.getHistoryForAllBankAccounts(updatedRequest);
                setData(response.content);  // Обновляем данные транзакций
                setTotalPages(response.totalPages);
                setTotalElements(response.totalElements);
            } catch (error) {
                console.error("Error fetching data: ", error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();

        const intervalId = setInterval(() => {
            fetchData();
        }, 60000); // каждую минуту

        // Очистка интервала при размонтировании компонента
        return () => clearInterval(intervalId);
    }, [email, request.page, request.size]);


    const DateCell = ({rowData, dataKey, ...props}: any) => (
        <Cell {...props}>
            {rowData[dataKey]
                ? format(new Date(rowData[dataKey]), "yyyy-MM-dd HH:mm:ss")
                : "N/A"}
        </Cell>
    );

    return (
        <>
            <div style={{ padding: "20px" }} color="green">
                <IconButton appearance="primary" color="green" icon={<SortDownIcon />}>
                    Export history
                </IconButton>
            </div>
            <Table
                height={550}
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
                    <HeaderCell>Account from</HeaderCell>
                    <Cell dataKey="accountFrom"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Account to</HeaderCell>
                    <Cell dataKey="accountTo"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Sender currency</HeaderCell>
                    <Cell dataKey="senderCurrency"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Receiver currency</HeaderCell>
                    <Cell dataKey="receiverCurrency"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Amount in sender currency</HeaderCell>
                    <Cell dataKey="amountInSenderCurrency"/>
                </Column>
                <Column flexGrow={1} sortable resizable>
                    <HeaderCell>Amount in receiver currency</HeaderCell>
                    <Cell dataKey="amountInReceiverCurrency"/>
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
    )

}

export default TransactionHistoryPage;