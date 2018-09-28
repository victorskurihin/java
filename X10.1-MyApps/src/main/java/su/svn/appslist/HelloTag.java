package su.svn.appslist;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<h1>Hello world from custom tag!</h1>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in HelloTag ", ex);
        }
    }
}
