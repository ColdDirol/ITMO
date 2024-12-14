import PlusIcon from "@rsuite/icons/Plus";
import {Button, IconButton, Modal, Uploader} from "rsuite";
import {useState} from "react";

const ImportMovieFormComponent = () => {
    const [open, setOpen] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleClose = () => {
        setOpen(false);
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleSubmit = async () => {
        setLoading(false);
        handleClose();
    };

    return (
        <>
            <IconButton
                onClick={handleOpen}
                appearance="primary"
                color="blue"
                icon={<PlusIcon />}
            >
                Import movies from CSV
            </IconButton>
            <Modal open={open} onClose={handleClose} size="lg">
                <Modal.Header>
                    <Modal.Title>Import new movies from CSV file</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Uploader
                        action="http://localhost:8080/backend-1.0-SNAPSHOT/api/v1/movie/import"
                        headers={{
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }}
                        accept=".csv"
                        draggable
                        onSuccess={handleClose}
                    >
                        <div style={{height: 200, display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                            <span>Click or Drag files to this area to upload</span>
                        </div>
                    </Uploader>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={handleSubmit} appearance="primary" loading={loading}>
                        Confirm
                    </Button>
                    <Button onClick={handleClose} appearance="subtle">
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default ImportMovieFormComponent;