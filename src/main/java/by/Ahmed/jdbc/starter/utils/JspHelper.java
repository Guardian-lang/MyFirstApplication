package by.Ahmed.jdbc.starter.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    public static final String JSP_FORMAT = "src/main/webapp/WEB-INF/JSP/%s.jsp";

    public static String getPath(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}