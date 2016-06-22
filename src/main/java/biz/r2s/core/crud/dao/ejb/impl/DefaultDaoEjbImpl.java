package biz.r2s.core.crud.dao.ejb.impl;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import biz.r2s.core.crud.model.BaseModel;
@Stateless
@LocalBean
public class DefaultDaoEjbImpl<T extends BaseModel<V>, V extends Serializable> extends GenericDaoEbjImpl<T, V>{
	public DefaultDaoEjbImpl(){
		super(false);
	}
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}
