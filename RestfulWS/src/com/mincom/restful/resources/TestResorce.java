package com.mincom.restful.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mincom.rest.models.Banda;

@Path("/tests")
public class TestResorce {
	
	
	@GET
	@Produces("application/json") 
	public List getTests(){
		System.out.println("Pegando Listas");
		return new ArrayList();
	}
	
	@GET
	@Path("{id}/{name}")
	@Produces("application/json")
	public Object getTest(@PathParam("id") int id, @PathParam("name") String name){
		System.out.println("Unico elemento com " + id + " name " + name);
		return null;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String create(Object o){
		System.out.println("Criando " + o);
		return "";
	}
	
	@Path("{id}")
	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String atualizaBanda(Object o, @PathParam("id") int id) {
		System.out.println("Update " + id + " o " + o);
		//Banda atual = bandasMap.get(id);
//		atual.setNome(banda.getNome());
//		atual.setAnoDeFormacao(banda.getAnoDeFormacao());
//		return banda.getNome() + " atualizada.";
		
		return "Updated";
	}

	@Path("{id}")
	@DELETE
	@Produces("text/plain")
	public String removeBanda(@PathParam("id") int id) {
		System.out.println("Delete " + id);
//		bandasMap.remove(id);
		return "Banda removida.";
	}
	
	
	

}
