package biz.r2s.core.crud.dao.ejb.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import biz.r2s.core.crud.dao.GenericDao;
import biz.r2s.core.crud.model.BaseModel;

public abstract class GenericDaoEbjImpl<T extends BaseModel<V>, V extends Serializable> implements GenericDao<T, V>{
	
	@PersistenceContext(unitName = "thePersistenceUnit")
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}	
	protected Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public GenericDaoEbjImpl(){
		if(this.entityClass==null){
			ParameterizedType genericSuperclass = (ParameterizedType) getClass()
		             .getGenericSuperclass();
		        this.entityClass = (Class<T>) genericSuperclass
		             .getActualTypeArguments()[0];	
		}	
	}
	
	@SuppressWarnings("unchecked")
	public GenericDaoEbjImpl(boolean changeEntityClass){
		if(this.entityClass==null&&changeEntityClass){
			ParameterizedType genericSuperclass = (ParameterizedType) getClass()
		             .getGenericSuperclass();
		        this.entityClass = (Class<T>) genericSuperclass
		             .getActualTypeArguments()[0];	
		}		
	}
	
	
	@Override
	public void insert(T t) {
		this.getEntityManager().persist(t);		
	}

	@Override
	public void update(T t) {
		this.getEntityManager().merge(t);
		
	}

	@Override
	public void delete(T t) {
		this.entityManager.remove(getEntityManager().contains(t) ? t : getEntityManager().merge(t));		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll() {
		return (List<T>) this.getEntityManager().createQuery("select o from "+this.entityClass.getName()+" o")
				.getResultList();
	}

	@Override
	public T get(V id) {
		return this.getEntityManager().find(this.entityClass, id);
	}
}
