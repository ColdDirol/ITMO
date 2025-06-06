import { Content, Container, CustomProvider } from "rsuite";
import "rsuite/dist/rsuite.min.css";
import HeaderComponent from "./components/header/HeaderComponent";
import userStore from "./store/UserStore";
import MainPage from "./pages/MainPage";
import HomePage from "./pages/HomePage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LogoutPage from "./pages/LogoutPage";

export default function App() {
  const { isDarkTheme } = userStore();

  return (
    <CustomProvider theme={isDarkTheme ? "dark" : "light"}>
      <BrowserRouter>
        <div className="show-container">
          <Container>
            <HeaderComponent />
            <Content>
              <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/movies" element={<HomePage />} />
                <Route path="/logout" element={<LogoutPage />} />
              </Routes>
            </Content>
          </Container>
        </div>
      </BrowserRouter>
    </CustomProvider>
  );
}
