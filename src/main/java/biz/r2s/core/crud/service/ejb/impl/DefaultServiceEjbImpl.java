package biz.r2s.core.crud.service.ejb.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import biz.r2s.core.crud.dao.GenericDao;
import biz.r2s.core.crud.dao.ejb.impl.DefaultDaoEjbImpl;
import biz.r2s.core.crud.model.BaseModel;
@Stateless
@LocalBean
public class DefaultServiceEjbImpl<T extends BaseModel<V>, V extends Serializable> extends GenericServiceEjbImpl<T,V>{
	@EJB
	DefaultDaoEjbImpl<T,V> defaultDao;
	Class<T> resource;
	public void setResource(Class<T> resource) {
		this.resource = resource;
	}
	
	@Override
	public GenericDao<T, V> getDao() {
		defaultDao.setEntityClass(resource);
		return defaultDao;
	}
	
}
