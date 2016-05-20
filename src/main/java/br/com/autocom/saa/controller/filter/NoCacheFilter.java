package br.com.autocom.saa.controller.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe de implementação de 'Filter' que desabilita o cache da página no navegador, adicionando 
 * headers indicando que a página atual está expirada.
 * 
 * Obs: O filtro foi adotado para implementar o 'no-cache', para solucionar o problema de cache associado as 
 * páginas *.html existentes na aplicação.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
@WebFilter(urlPatterns="/*", dispatcherTypes={ DispatcherType.FORWARD, DispatcherType.REQUEST })
public class NoCacheFilter implements Filter {
	
	private final static Pattern EXTENSOES_NAO_CACHEADAS = Pattern.compile(".*(\\.(css|bmp|gif|jpe?g" 
																		 + "|png|tiff?|mid|mp2|mp3|mp4"
																		 + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
																		 + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String path = request.getRequestURI();
		
		if(!EXTENSOES_NAO_CACHEADAS.matcher(path).matches()) {
			/* set the expires to past */
			response.setHeader("Expires", "Wed, 31 Dec 1969 21:00:00 GMT");
	
			/* no-cache headers for HTTP/1.1 */
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			/* no-cache headers for HTTP/1.1 (IE) */
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	
			/* no-cache headers for HTTP/1.0 */
			response.setHeader("Pragma", "no-cache");
		}
		
		chain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {}

}
