package services;

import beans.ResponseBean;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SessionService {
    private static final String BEAN_NAME_IN_SESSION = "results";

    @SuppressWarnings("unchecked")
    public static synchronized List<ResponseBean> getResultBeans(HttpSession session) {
        Object resultsInSession = session.getAttribute(BEAN_NAME_IN_SESSION);

        if (resultsInSession == null) {
            List<ResponseBean> newResultList = new ArrayList<>();
            session.setAttribute(BEAN_NAME_IN_SESSION, newResultList);
            return newResultList;
        } else {
            return (List<ResponseBean>) resultsInSession;
        }
    }

    public static synchronized void addResultBean(HttpSession session, ResponseBean newBean) {
        getResultBeans(session).add(newBean);
    }

    public static synchronized void clearBeans(HttpSession session) {
        getResultBeans(session).clear();
    }
}