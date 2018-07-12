package com.ahao.core.spring.interceptor;


import com.ahao.core.context.PageContext;
import com.ahao.core.spring.interceptor.annotation.Interceptor;
import com.ahao.core.util.lang.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor
public class PageInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 静态资源不处理
        if (StringHelper.endsWithAnyIgnoreCase(request.getRequestURI(),
                ".css", ".js", ".png", ".jpg", ".woff2", ".map", ".ico")) {
            return true;
        }

        // 2. 初始化分页大小, 排序方式, 排序字段
        initPageSize(request);
        initOrder(request);
        initSort(request);
        return true;
    }

    /**
     * 获取并设置分页大小, 默认25
     *
     * @param request Request请求
     */
    private void initPageSize(ServletRequest request) {
        String pageSize = request.getParameter(PageContext.PAGE_SIZE);
        logger.debug("分页大小:" + pageSize);
        if (StringHelper.isNumeric(pageSize)) {
            PageContext.setPageSize(Integer.parseInt(pageSize));
        } else {
            PageContext.setPageSize(PageContext.DEFAULT_PAGE_SIZE);
        }
    }

    /**
     * 获取并设置分页倒序还是正序, 默认正序
     *
     * @param request Request请求
     */
    private void initOrder(ServletRequest request) {
        String order = request.getParameter(PageContext.ORDER);
        logger.debug("排序方式:" + order);
        if (StringHelper.isNotEmpty(order)) {
            PageContext.setOrder(order);
        } else {
            PageContext.setOrder(PageContext.DEFAULT_ORDER);
        }
    }

    /**
     * 获取并设置分页排序字段, 默认为id
     *
     * @param request Request请求
     */
    private void initSort(ServletRequest request) {
        String sort = request.getParameter(PageContext.SORT);
        logger.debug("排序字段:" + sort);
        if (StringHelper.isNotEmpty(sort)) {
            PageContext.setSort(sort);
        } else {
            PageContext.setSort(PageContext.DEFAULT_SORT);
        }
    }
}