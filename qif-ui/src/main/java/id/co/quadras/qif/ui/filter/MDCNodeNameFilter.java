package id.co.quadras.qif.ui.filter;

import com.google.inject.Singleton;
import com.irwin13.winwork.basic.utilities.WinWorkUtil;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author irwin Timestamp : 15/10/12 16:54
 */
@Singleton
public class MDCNodeNameFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        MDC.put("nodeName", WinWorkUtil.getNodeName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
