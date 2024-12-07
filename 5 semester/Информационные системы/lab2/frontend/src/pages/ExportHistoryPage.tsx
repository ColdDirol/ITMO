import {FlexboxGrid, Heading} from "rsuite";

const ExportHistoryPage = () => {

    return (
        <>
            <div className="show-grid" style={{padding: 10}}>
                <FlexboxGrid>
                    <FlexboxGrid.Item colspan={14}>
                        <Heading level={3}>Export history</Heading>
                    </FlexboxGrid.Item>
                </FlexboxGrid>
            </div>
        </>
    );
}

export default ExportHistoryPage;