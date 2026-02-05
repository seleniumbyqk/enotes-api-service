package com.enotes.config.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enotes.handler.GenericResponse;
import com.enotes.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtFilter extends OncePerRequestFilter{

	/*
	//field injection
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	*/
	
	//Constructor injection
	 private final JwtService jwtService;
	 private final UserDetailsService userDetailsService;
	 
	 public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
	        this.jwtService = jwtService;
	        this.userDetailsService = userDetailsService;
	    }
	    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*
		//Without using try-catch
		String authHeader = request.getHeader("Authorization");
		
		//Authorization - Bearer dkfnlknmv.jsfhasdhfon.cdoboeujcjdcn
		
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			token = authHeader.substring(7);
			
			username = jwtService.extractUsername(token);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				Boolean validateToken = jwtService.validateToken(token, userDetails);
				
				if(validateToken)
				{
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					
				}
			}
			
		}
		
		filterChain.doFilter(request, response);
		
		*/
		
		//Using try-catch
		try
		{
        String authHeader = request.getHeader("Authorization");
		
		//Authorization - Bearer dkfnlknmv.jsfhasdhfon.cdoboeujcjdcn
		
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer "))
		{
			token = authHeader.substring(7);
			
			username = jwtService.extractUsername(token);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				boolean validateToken = jwtService.validateToken(token, userDetails);
				
				if(validateToken)
				{
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					
				}
			}
			
		}
	}
	catch(Exception e)
	{
		
		generateResponseError(response, e);
		
		return;
		
		/*
			//e.printStackTrace();
			response.setContentType("application/json");
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			Object error = GenericResponse.builder()
			.status("failed")
			.message(e.getMessage())
			.responseStatus(HttpStatus.UNAUTHORIZED)
			.build()
			.create()
			.getBody();
			
			//response.getWriter().write(e.getMessage());
			//response.getWriter().write(error.toString());
			response.getWriter().write(new ObjectMapper().writeValueAsString(error));
			
			return;
			*/
	}
		
		filterChain.doFilter(request, response);
		
	}

	private void generateResponseError(HttpServletResponse response, Exception e) throws IOException {
		// TODO Auto-generated method stub
		
		//e.printStackTrace();
		response.setContentType("application/json");
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		Object error = GenericResponse.builder()
		.status("failed")
		.message(e.getMessage())
		.responseStatus(HttpStatus.UNAUTHORIZED)
		.build()
		.create()
		.getBody();
		
		//response.getWriter().write(e.getMessage());
		//response.getWriter().write(error.toString());
		response.getWriter().write(new ObjectMapper().writeValueAsString(error));
		
	}

}
