package com.mincom.restful.resources;

import jersey.repackaged.com.google.common.collect.Sets;

import org.glassfish.jersey.server.ResourceConfig;

import com.mincom.rest.auth.DemoRESTRequestFilter;

public class WhatIfConfig  extends ResourceConfig{
	
	public WhatIfConfig(){
		System.out.println("******** Carregando filtros *************");
		 register(com.mincom.rest.auth.DemoRESTRequestFilter.class);
		 register(com.mincom.rest.auth.DemoRESTResponseFilter.class);
	}

}
