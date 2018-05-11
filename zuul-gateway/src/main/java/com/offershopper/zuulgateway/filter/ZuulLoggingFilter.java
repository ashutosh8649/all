package com.offershopper.zuulgateway.filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulHeaders;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/*
 * File name:Zuul Filter
 * Author: Girish Mehta
 * Date: 28-March-2018
 * Final Version: 17-April-2018
 * Description: Filters all incoming requests before sending to services
*/

@Component
public class ZuulLoggingFilter extends ZuulFilter {

	@Value("${Authorities}")
	String Authorities;

	@Value("${loginUrl}")
	String loginUrl;

	@Value("${publicUser}")
	String publicUser;

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// extract service request from url
		String[] urlParts = request.getServletPath().split("/");
		String serviceUrl = urlParts[2];
		// check if request is to public service or private service
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		// parse JSON from config server
		try {
			json = (JSONObject) parser.parse(Authorities);
		} catch (ParseException e3) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		String services = (String) json.get(publicUser);
		String[] serviceList = services.split(",");
		for (String service : serviceList) {
			// if request to public service
			if (serviceUrl.equals(service)) {
				// then let go
				return HttpServletResponse.SC_OK;
			}
		}

		////		// TODO Remove this code before deployment
		if (urlParts[3].equals("v2")) {
			// then let go
			return HttpServletResponse.SC_OK;
		} else if (urlParts[1].equals("api")) {
			// then let go
			return HttpServletResponse.SC_OK;
		}

		
			// if request is to private service then check
			try {
				// if header contains a token
				String token = request.getHeader("Authorization");
				StringBuffer response = new StringBuffer();
				URL obj = new URL("http://10.151.60.189:7000/verifytoken/" + token);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", request.getHeader("user-agent"));
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
				} else {
					return HttpServletResponse.SC_FORBIDDEN;
				}
				// response from the uaa server
				String verify = response.toString();
				// if response is true let it go
				String[] data = verify.split(",");
				if (data[0].equals("true")) {
					String role = data[1];
					String roles = (String) json.get("roles");
					String[] roless = roles.split(",");
					for (String tempRole : roless) {
						if (role.equals(tempRole)) {
							String service = (String) json.get(tempRole);
							String serviesArray[] = service.split(",");
							for (String tempService : serviesArray) {
								if (tempService.equalsIgnoreCase(serviceUrl)) {
									// append email id in request header and let go
									ctx.addZuulRequestHeader("email", data[2]);
									return HttpServletResponse.SC_OK;
								}
							}
						}
					}
				}
				// redict to login page
				ctx.setSendZuulResponse(false);
				ctx.put("forward:", loginUrl);
				ctx.setResponseStatusCode(HttpServletResponse.SC_TEMPORARY_REDIRECT);
				ctx.getResponse().sendRedirect("");
				return HttpServletResponse.SC_TEMPORARY_REDIRECT;
			} catch (Exception e) {
				// else exception will occur while reading the token
				// redirect to login page
				try {
					ctx.setSendZuulResponse(false);
					ctx.put("forward:", loginUrl);
					ctx.setResponseStatusCode(HttpServletResponse.SC_TEMPORARY_REDIRECT);
					ctx.getResponse().sendRedirect(loginUrl);
					return HttpServletResponse.SC_TEMPORARY_REDIRECT;
				} catch (Exception e1) {
					return HttpServletResponse.SC_FORBIDDEN;
				}
			}
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
