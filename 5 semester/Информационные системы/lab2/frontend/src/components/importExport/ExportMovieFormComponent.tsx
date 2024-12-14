import MovieApiService from "../../service/movieApiService.ts";
import {IconButton} from "rsuite";
import FileDownloadIcon from '@rsuite/icons/FileDownload';

const ExportMovieFormComponent = () => {
    const movieApiService = new MovieApiService();

    const handleExport = async () => {
        try {
            await movieApiService.export();
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <>
            <IconButton
                onClick={handleExport}
                appearance="primary"
                color="blue"
                icon={<FileDownloadIcon />}
            >
                Download
            </IconButton>
        </>
    );
};

export default ExportMovieFormComponent;
