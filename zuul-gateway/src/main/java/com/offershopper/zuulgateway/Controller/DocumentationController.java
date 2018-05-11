package com.offershopper.zuulgateway.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/*
 * File name:DocumentationController
 * Author: Girish Mehta
 * Date: 28-March-2018
 * Final Version: 12-April-2018
 * Description: Swagger Implementation of all services.
*/

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

	@Value("${servicesList}")
	String servicesList;

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		String[] services = servicesList.split(",");
		for (String service : services) {
			resources.add(swaggerResource(service, "/api/" + service + "/v2/api-docs", "2.0"));
		}

		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}

}
