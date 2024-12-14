import AdminIcon from "@rsuite/icons/Admin";
import { useEffect, useState } from "react";
import { Badge, ButtonToolbar, IconButton, Modal, Nav, Table } from "rsuite";
import { Cell, HeaderCell } from "rsuite-table";
import Column from "rsuite/esm/Table/TableColumn";
import CheckIcon from "@rsuite/icons/Check";
import CloseIcon from "@rsuite/icons/Close";
import { IAdminElement } from "../../intefaces/adminInterface";
import AdminApiService from "../../service/adminApiService";

export default function AdminComponent() {
  const [open, setOpen] = useState(false);
  const [data, setData] = useState<IAdminElement[]>([]);
  const [newContent, setNewContent] = useState(false);

  const adminApiService = new AdminApiService();

  const fetchData = async () => {
    try {
      const requests = await adminApiService.getPendingRequests();
      setData(requests);
    } catch (error) {
      console.error("Error fetching requests:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    const fetchPendingRequestsCount = async () => {
      try {
        const count = await adminApiService.countPendingRequests();
        setNewContent(count > 0);
      } catch (error) {
        console.error("Error fetching pending requests count:", error);
      }
    };

    fetchPendingRequestsCount();
    const intervalId = setInterval(fetchPendingRequestsCount, 60000);
    return () => clearInterval(intervalId);
  }, [adminApiService]);

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    fetchData();
    setOpen(true);
    fetchData();
  };

  const handleApprove = async (rowData: any) => {
    try {
      await adminApiService.approveRequest(rowData.id);
      fetchData();
    } catch (error) {
      console.error("Error approving request:", error);
    }
  };

  const handleReject = async (rowData: any) => {
    try {
      await adminApiService.rejectRequest(rowData.id);
      fetchData();
    } catch (error) {
      console.error("Error rejecting request:", error);
    }
  };

  return (
    <>
      <Nav.Item>
        <Badge content={newContent}>
          <IconButton icon={<AdminIcon />} onClick={handleOpen} />
        </Badge>
      </Nav.Item>
      <Modal open={open} onClose={handleClose} size="lg">
        <Modal.Header>
          <Modal.Title>Requests for getting ADMIN role</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Table height={500} data={data}>
            <Column flexGrow={2} align="center">
              <HeaderCell>Id</HeaderCell>
              <Cell dataKey="user.id" />
            </Column>
            <Column flexGrow={2} align="center">
              <HeaderCell>Username</HeaderCell>
              <Cell dataKey="user.username" />
            </Column>
            <Column flexGrow={2} align="center">
              <HeaderCell>Email</HeaderCell>
              <Cell dataKey="user.email" />
            </Column>
            <Column flexGrow={2} align="center">
              <HeaderCell>Actions</HeaderCell>
              <Cell style={{ padding: "6px" }}>
                {(rowData) => (
                  <ButtonToolbar>
                    <IconButton
                      color="green"
                      appearance="primary"
                      icon={<CheckIcon />}
                      size="xs"
                      onClick={() => handleApprove(rowData)}
                    />
                    <IconButton
                      color="red"
                      appearance="primary"
                      icon={<CloseIcon />}
                      size="xs"
                      onClick={() => handleReject(rowData)}
                    />
                  </ButtonToolbar>
                )}
              </Cell>
            </Column>
          </Table>
        </Modal.Body>
      </Modal>
    </>
  );
}
