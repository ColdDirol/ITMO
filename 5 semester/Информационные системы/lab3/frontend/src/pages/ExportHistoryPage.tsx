import {FlexboxGrid, Heading} from "rsuite";
import ExportTableComponent from "../components/table/ExportTableComponent.tsx";
import {useCallback, useState} from "react";
import ExportMovieFormComponent from "../components/importExport/ExportMovieFormComponent.tsx";

const ExportHistoryPage = () => {
    const [ refreshData ] = useState(() => () => {});

    const handleRefreshData = useCallback(() => {
        if (typeof refreshData === "function") {
            refreshData();
        }
    }, [refreshData]);
    return (
        <>
            <div className="show-grid" style={{padding: 10}}>
                <FlexboxGrid>
                    <FlexboxGrid.Item colspan={14}>
                        <Heading level={3}>Export history</Heading>
                    </FlexboxGrid.Item>

                    <FlexboxGrid.Item
                        colspan={10}
                        style={{display: "flex", justifyContent: "flex-end"}}
                    >
                        <ExportMovieFormComponent onRefresh={handleRefreshData}/>
                    </FlexboxGrid.Item>
                </FlexboxGrid>
            </div>

            <ExportTableComponent />
        </>
    );
}

export default ExportHistoryPage;
