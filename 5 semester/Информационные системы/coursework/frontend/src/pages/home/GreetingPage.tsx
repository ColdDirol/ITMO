import useUserStore from "../../store/UserStore.tsx";
import UserBankAccountsPage from "./UserBankAccountsPage.tsx";
import LandingPage from "./LandingPage.tsx";

const GreetingPage = () => {
    const { isAuthorized } = useUserStore();

    return(
        <>
            {isAuthorized ? <UserBankAccountsPage /> : <LandingPage /> }
            {/*<UserBankAccountsPage />*/}
            {/*<LandingPage />*/}
        </>
    )
}

export default GreetingPage;