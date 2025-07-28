package com.car.rental.gateway.configuration;

//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CorsFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpServletRequest req = (HttpServletRequest) request;
//		String orginHeader = req.getHeader("origin");
//		res.setHeader("Access-Control-Allow-Origin", orginHeader);
//		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//		res.setHeader("Access-Control-Max-Age", "3600");
//		res.setHeader("Access-Control-Allow-Headers", "*");
//		if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
//			
//			res.setStatus(HttpServletResponse.SC_OK);
//		}else {
//			chain.doFilter(req, res);
//		}
//	}
//	
//	@Override
//	public void init(FilterConfig filterConfig) {}
//}


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;



import java.util.List;

@Configuration
public class CorsFilter{

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:5000"));
        config.setAllowedOrigins(List.of("http://16.170.223.44:4200", "http://16.171.57.106:4200"));
        // You can specify domains instead of "*"
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // Set to false if you use "*" for origins
        config.setMaxAge((long) 3600);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}