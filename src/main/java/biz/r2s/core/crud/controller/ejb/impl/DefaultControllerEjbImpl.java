package biz.r2s.core.crud.controller.ejb.impl;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import biz.r2s.core.crud.model.BaseModel;
import biz.r2s.core.crud.service.GenericService;
import biz.r2s.core.crud.service.ejb.impl.DefaultServiceEjbImpl;
import biz.r2s.core.util.ObjectUtil;
@Path("/{className}")
public class DefaultControllerEjbImpl extends GenericControllerEjbImpl<BaseModel<Long>, Long>{
	@PathParam("className") 
	String className;
	@EJB
	DefaultServiceEjbImpl<BaseModel<Long>, Long> defaultService; 
	
	public Class<BaseModel<Long>> getResource(){
		if(className!=null){
			try {
				return (Class<BaseModel<Long>>) Class.forName(className);				 				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	protected GenericService<BaseModel<Long>, Long> getService() {
		defaultService.setResource(this.getResource());				
		return defaultService;
	}

	@Override
	protected void copyObject(BaseModel<Long> objectDB, BaseModel<Long> objectRequest) {
		ObjectUtil.copyFieldByField(objectRequest, objectDB);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirObject(String jsonObject) {
		Gson gson = new Gson();
		BaseModel<Long> t = gson.fromJson(jsonObject, getResource());
		return this.inserirObject(t);
	}

	@Path("{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response editObject(String jsonObject, @PathParam("id")  Long id) {
		Gson gson = new Gson();
		BaseModel<Long> t = gson.fromJson(jsonObject, getResource());
		return this.editObject(t, id);
	}

	
	
}
