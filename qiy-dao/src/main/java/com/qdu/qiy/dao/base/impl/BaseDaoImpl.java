package com.qdu.qiy.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.qdu.qiy.dao.base.IBaseDao;
import com.qdu.qiy.utils.PageBean;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> entityClass;
	//注入sessionFactory
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	//在构造方法中动态获得entityClass
	public BaseDaoImpl() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得父类上声明的泛型数组
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
		
	}
	
	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = " FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}
	//执行更新
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for(Object object : objects){
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
	}
	//通用分页查询方法
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//查total 总数据量
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		
		//查rows
		detachedCriteria.setProjection(null);
		int firstResult = (currentPage -1 ) * pageSize;
		int maxResults = pageSize;
		//List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
		
	}
	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

}
