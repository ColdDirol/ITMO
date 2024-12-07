import { useNavigate } from "react-router-dom";
import userStore from "../store/UserStore";
import { useEffect } from "react";

const LogoutPage = () => {
  const { logout } = userStore();
  const navigate = useNavigate();

  useEffect(() => {
    logout();
    navigate("/");
  }, [logout, navigate]);

  return <div>Logging out...</div>;
};

export default LogoutPage;
