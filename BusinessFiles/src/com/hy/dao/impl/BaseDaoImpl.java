package com.hy.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;
import com.hy.dao.BaseDao;
import com.hy.util.PageUtil;

public class BaseDaoImpl<T> implements BaseDao<T> {
	protected Class<T> claz;

	@SessionTarget
	private Session session;

	// @Resource
	// private SessionFactory sessionFactory;
	/*
	 * Configuration cfg=new Configuration();
	 * sessionFactory=cfg.configure().buildSessionFactory();
	 */

	/*
	 * Configuration cfg=new AnnotationConfiguration(); SessionFactory
	 * sessionFactory s=cfg.configure().buildSessionFactory();
	 */

	@TransactionTarget
	private Transaction transaction;

	@Override
	public Transaction getTransaction() {
		return transaction;
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	/*
	 * 
	 * 单表查询,返回单个对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> search(String hql) {
		// System.out.println(session);
		// System.out.println(hql);
		List<T> t = getSession().createQuery(hql).list();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchX(String hql, Map<String, Object> map) {
		// System.out.println(session);
		// System.out.println(hql);
		Query query = getSession().createQuery(hql);
		query.setProperties(map);
		List<T> t = query.list();
		return t;
	}

	// 查询所有,多表查询,返回复杂对象 Object可以是T,Object,?
	public List<?> searchMoreTable(String hql) {
		Query query = getSession().createQuery(hql);
		List<?> resultList = query.list();
		return resultList;
	}

	public List<?> searchMoreTableExport(String hql) {
		Query query = getSession().createQuery(hql);
		List<?> resultList = query.list();
		return resultList;
	}

	// 分页查询
	@SuppressWarnings("unchecked")
	public PageUtil<Object> searchMoreTablePage(String hql, PageUtil<Object> pr) {
		Query query = getSession().createQuery(hql).setCacheable(true);
		PageUtil<Object> p = new PageUtil<Object>();
		if (pr != null) {
			p.setRecTotal(query.list().size());
			query.setFirstResult(pr.getFirstRec());
			query.setMaxResults(pr.getPageSize());
		}
		p.setList(query.list());
		return p;
	}

	// 添加
	public boolean add(T t) {
		session.save(t);
		return true;
	}

	// 添加
	public boolean add2(T t) throws Exception {
		try {
			session.save(t);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	// 修改
	public boolean update(T t) {
		getSession().update(t);// 注解设置,在不需要修改的字段上加上updatable=false
		return true;
	}

	public void evit(T t) {
		getSession().evict(t);
	}

	/*
	 * public boolean updateBatch(String hql){ Query q=session.createQuery(hql);
	 * int count=q.executeUpdate(); return true; }
	 */
	public boolean delete(T t) {
		session.delete(t);
		return true;
	}

	public int updateDelete(String hql) {
		Query query = getSession().createQuery(hql);
		return query.executeUpdate();
	}

	public T getById(Integer id) {
		return (T) session.get(claz, id);
	}

	public T getById(String id) {
		return (T) session.get(claz, id);
	}

	// 原生sql

	@SuppressWarnings("rawtypes")
	public int getMaxId(String tabName) {
		int maxId = 0;
		List l = getSession().createSQLQuery("select max(id) as maxid from " + tabName).list();
		if (l.size() > 0) {
			maxId = (Integer) l.get(0);
		}
		return maxId;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Object[]> querySQL(String sql) {

		List<Object[]> list = getSession().createSQLQuery(sql).list();
		/*
		 * for(Object[] obj :list) { System.out.println(obj[0]+" -- "+
		 * obj[1]+" -- "+obj[2]); }
		 */
		return list;
	}

	public int excuteBySql(String sql) {
		int result;
		SQLQuery query = getSession().createSQLQuery(sql);
		result = query.executeUpdate();
		return result;
	}

	// 按主键id批量修改(审核)
	// 按主键id批量删除
	@SuppressWarnings("deprecation")
	public int updateORdeleteBatchByid(List<Integer> ids, String sql) {
		int count = 0;
		long l1 = System.currentTimeMillis();
		Connection conn = getSession().connection();
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(false);
			for (int i = 0; i < ids.size(); i++) {
				stmt.setInt(1, ids.get(i));
				stmt.addBatch();
			}
			count = stmt.executeBatch().length;
			conn.commit();
			// closeSession();
			long l2 = System.currentTimeMillis();
			// System.out.println("批量修改或删除:"+(l2-l1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Object getByIdAndEvict(Integer id) {
		T o = getById(id);
		if (o != null)
			evit(o);
		return o;
	}

	public Object getByIdAndEvict(String id) {
		T o = getById(id);
		if (o != null)
			evit(o);
		return o;
	}

	public List getByIds(List<Integer> ids) {
		String hql = "from " + claz.getSimpleName() + "  where id in(:ids)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	public List getByIdsDeal(List<Integer> ids) {
		String hql = "select new " + claz.getSimpleName() + "(o,t) from " + claz.getSimpleName()
				+ " o,Teacher as t  where o.id in(:ids) and o.teacherId=t.id";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public int updateStateToOk(List<Integer> ids) {
		String hql = "update " + claz.getSimpleName() + " set status='1' where id in(:ids)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);

		return query.executeUpdate();
	}

	@Override
	public Long getPages(StringBuilder hql) {
		System.out.println(hql.toString());
		int fromIndex = hql.toString().toLowerCase().indexOf("from");
		String countHql = "select count(distinct p) " + hql.substring(fromIndex);
		Query query = getSession().createQuery(countHql);
		Long result = (Long) query.uniqueResult();
		return result;
	}

	@Override
	public List<T> getPageObject(StringBuilder hql, int indexPage, int pageSize) {
		Query query = getSession().createQuery(hql.toString());
		query.setFirstResult((indexPage - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
}
