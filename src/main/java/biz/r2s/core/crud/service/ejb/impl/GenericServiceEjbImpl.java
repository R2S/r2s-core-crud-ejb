package biz.r2s.core.crud.service.ejb.impl;

import java.io.Serializable;
import java.util.List;

import biz.r2s.core.crud.dao.GenericDao;
import biz.r2s.core.crud.model.BaseModel;
import biz.r2s.core.crud.service.GenericService;

public abstract class GenericServiceEjbImpl<T extends BaseModel<V>, V extends Serializable> implements GenericService<T, V>{

	public abstract GenericDao<T, V> getDao();
	
	public T save(T t) {
		this.getDao().insert(t);
		if (t.getId() != null) {
			return this.getDao().get(t.getId());
		}
		return null;
	}

	public T edit(T t) {
		if (getDao().get(t.getId()) != null) {
			getDao().update(t);
			return t;
		}
		return null;
	}

	public void delete(T t) {
		if (this.getDao().get(t.getId()) != null) {
			this.getDao().delete(t);
		}		
	}

	public List<T> list() {
		return this.getDao().listAll();
	}

	public T show(V id) {
		return this.getDao().get(id);
	}

}
