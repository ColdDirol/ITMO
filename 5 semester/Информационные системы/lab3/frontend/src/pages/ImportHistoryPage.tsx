import {FlexboxGrid, Heading} from "rsuite";
import {useCallback, useState} from "react";
import ImportMovieFormComponent from "../components/importExport/ImportMovieFormComponent.tsx";
import ImportTableComponent from "../components/table/ImportTableComponent.tsx";


const ImportHistoryPage = () => {
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
                        <Heading level={3}>Import history</Heading>
                    </FlexboxGrid.Item>

                    <FlexboxGrid.Item
                        colspan={10}
                        style={{display: "flex", justifyContent: "flex-end"}}
                    >
                        <ImportMovieFormComponent onRefresh={handleRefreshData}/>
                    </FlexboxGrid.Item>
                </FlexboxGrid>
            </div>

            <ImportTableComponent />
        </>
    );
};

export default ImportHistoryPage;
