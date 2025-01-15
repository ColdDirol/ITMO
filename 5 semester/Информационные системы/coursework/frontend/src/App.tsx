import {Container, Content, CustomProvider  } from 'rsuite';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import 'rsuite/dist/rsuite.min.css';
import HeaderComponent from "./components/header/HeaderComponent.tsx";
import GreetingPage from "./pages/home/GreetingPage.tsx";
import UserProfilePage from "./pages/user/UserProfilePage.tsx";
import UserBankAccountsPage from "./pages/user/UserBankAccountsPage.tsx";
import useUserStore from "./store/UserStore.tsx";
import TransactionHistoryPage from "./pages/transaction/TransactionHistoryPage.tsx";
import AdminActionsHistoryPage from "./pages/admin/AdminActionsHistoryPage.tsx";

function App() {
    const { isDarkTheme } = useUserStore();

    return (
        <CustomProvider theme={isDarkTheme ? "light" : "dark"}>
            <BrowserRouter>
                <Container>
                    <HeaderComponent
                        style={{
                            position: "relative",
                            zIndex: 1
                        }} />
                    <Content>
                        <Routes>
                            <Route path="/" element={<GreetingPage />} />
                            <Route path="/profile" element={<UserProfilePage />} />
                            <Route path="/bank/accounts" element={<UserBankAccountsPage />} />
                            <Route path="/history" element={<TransactionHistoryPage />} />
                            <Route path="/admin/actions/history" element={<AdminActionsHistoryPage />} />
                        </Routes>
                    </Content>
                </Container>
            </BrowserRouter>
        </CustomProvider>
    )
}

export default App;