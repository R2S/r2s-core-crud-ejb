package biz.r2s.core.crud.controller.ejb.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import biz.r2s.core.crud.controller.GenericController;
import biz.r2s.core.crud.model.BaseModel;
import biz.r2s.core.crud.service.GenericService;
import biz.r2s.core.crud.service.ejb.impl.DefaultServiceEjbImpl;
public abstract class GenericControllerEjbImpl<T extends BaseModel<V>, V extends Serializable> implements GenericController<T, V, Response>{

	@EJB
	DefaultServiceEjbImpl<T,V> defaultServiceEjbImpl;
	
	protected abstract GenericService<T, V> getService();
	
	protected abstract void copyObject(T objectDB, T objectRequest);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listObject() {
		try {
			Map map = new HashMap();
			List<T> arrayList = this.getService().list();
			map.put("data", arrayList);
			return Response.ok(map).build();//
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirObject(T t) {
		try {
			T objectSave = this.getService().save(t);
			if (objectSave != null) {
				return Response.ok(objectSave).build();
			} else {
				return Response.serverError().build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response editObject(T t, @PathParam("id")  V id) {
		try {
			T objectDB = this.getService().show(id);
			if(objectDB!=null){
				this.copyObject(objectDB, t);
				T objectSave = this.getService().edit(objectDB);
				if (objectSave != null) {
					return Response.ok(objectSave).build();
				} else {
					return Response.serverError().build();
				}
			}else{
				return Response.status(404).build();
			}	
		} catch (Exception e) {
			System.out.println(e);
			return Response.serverError().build();
		}
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirObject(@PathParam("id") V id) {
		try {
			T object = this.getService().show(id);
			if(object!=null){
				this.getService().delete(object);
				return Response.ok(object).build();
			}else{
				return Response.status(404).build();
			}		
			
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response showObject(@PathParam("id") V id) {
		try {
			T object = this.getService().show(id);
			if(object!=null){
				return Response.ok(object).build();
			}else{
				return Response.status(404).build();
			}		
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

}
