package beans;

import beans.ResultBean;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ResultBeanManager {
    private static final String BEAN_NAME_IN_SESSION = "results";

    @SuppressWarnings("unchecked")
    public static synchronized List<ResultBean> getResultBeans(HttpSession session) {
        Object resultsInSession = session.getAttribute(BEAN_NAME_IN_SESSION);

        if (resultsInSession == null) {
            List<ResultBean> newResultList = new ArrayList<>();
            session.setAttribute(BEAN_NAME_IN_SESSION, newResultList);
            return newResultList;
        } else {
            return (List<ResultBean>) resultsInSession;
        }
    }

    public static synchronized void addResultBean(HttpSession session, ResultBean newBean) {
        getResultBeans(session).add(newBean);
    }

    public static synchronized void clearBeans(HttpSession session) {
        getResultBeans(session).clear();
    }

    public static ResultBean parseToResultBean(Double x, Double y, Double R, Boolean result, String compiledIn) {
        ResultBean resultBean = new ResultBean();
        resultBean.setX(x);
        resultBean.setY(y);
        resultBean.setR(R);
        resultBean.setResult(result);
        resultBean.setCompiledIn(compiledIn);

        return resultBean;
    }
}