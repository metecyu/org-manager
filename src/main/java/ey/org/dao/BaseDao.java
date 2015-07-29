package ey.org.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import ey.org.bean.Pager;
import ey.org.bean.Pager3;
import ey.org.bean.Pager3.OrderType;
import ey.org.bean.Param;
import ey.org.bean.Search;

/**
 * dao的通用类 
 * @author yzp
 *
 * @param <T>
 * @param <PK>
 */
@Component
public class BaseDao<T, PK extends Serializable>{

	private Class<T> entityClass;

	protected SessionFactory sessionFactory;
	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.entityClass = null;
		//得到类的Class
        Class c = getClass();
        //返回本类的父类,包含泛型参数信息
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}
	/**
	 * 设置SessionFactory
	 * @param sessionFactory - Hibernate的SessionFactory
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 得到当前Session
	 * @return Session - Hibernate的Session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 保存实体对象
	 * @param entity - 实体
	 * @return PK - 实体分配后的ID
	 */
	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK)getSession().save(entity);
	}
	/**
	 * 保存实体对象
	 * @param entity - 实体
	 * @return PK - 实体分配后的ID
	 */
	@SuppressWarnings("unchecked")
	public PK saveAndClear(T entity) {
		Assert.notNull(entity, "entity is required");
		PK pk = (PK)getSession().save(entity);
		this.getSession().clear();
		return pk;
	}
	
	/**
	 * 根据ID获取实体对象(get方式)
	 * @param id - 实体id
	 * @return T - 实体对象
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T)getSession().get(entityClass, id);
	}
	

	/**
	 * 根据ID获取实体对象(get方式)
	 * @param id - 实体id
	 * @return T - 实体对象
	 */
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		Assert.notNull(id, "id is required");
		return (T)getSession().get(entityClass, id);
	}
	
	/**
	 * 根据ID获取实体对象(load方式)
	 * @param id - id
	 * @return T - 实体对象
	 */
	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T)getSession().load(entityClass, id);
	}
	
	/**
	 * 删除实体对象
	 * @param entity - 实体对象
	 */
	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}
	
	/**
	 * 删除实体对象
	 * @param id - id
	 */
	public void delete(PK id) {
		Assert.notNull(id, "entity is required");
		T entity = load(id);
		delete(entity);
	}
	
	/**
	 * 更改实体对象
	 * @param entity - 实体对象
	 */
	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);

	}
	
	/**
	 * 根据ID数组获取实体对象集合.
	 * @param ids - id数组
	 * @return List - 实体集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName() + " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}
	
	/**
	 * 根据属性名和属性值获取实体对象
	 * @param propertyName - 属性名
	 * @param value - 属性值
	 * @return T - 对象
	 */
	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	/**
	 * 根据属性名和属性值获取实体对象集合
	 * @param propertyName - 属性名
	 * @param value - 属性值
	 * @return List - 对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	/**
	 * 获取所有实体对象集合
	 * @return List - 实体集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * 获取所有实体对象总数.
	 * @return long - 实体对象总数
	 */
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}
	
	/**
	 * 根据ID数组删除实体对象
	 * @param ids - id数组
	 */
	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			delete(entity);
		}
	}
	
	
	
	
	
	
	/**
	 * 恢复被设为无效的对象（选用，需自己实现）
	 * @param entity
	 */
	public void recover(T entity){
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}
	
	/**
	 * 恢复被设为无效的对象（选用，需自己实现）
	 * @param entity
	 */
	public void recover(PK[] ids){
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			recover(entity);
		}
		flush();
	}

	/**
	 * 根据ID数组删除实体对象
	 * @param ids - id数组
	 */
	public boolean deleteBatch(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		try{
			for (PK id : ids) {
				T entity = load(id);
				delete(entity);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * 根据ID数组删除实体对象
	 * @param ids - id数组
	 */
	public boolean deleteBatch(List<T> ents) {
		Assert.notEmpty(ents, "ents must not be empty");
		try{
			for (T ent : ents) {
				delete(ent);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 根据Pager对象进行查询
	 * @param pager - 页面bean
	 * @return Pager - 页面bean
	 */
	public Pager3 findByPager(Pager3 pager) {
		if (pager == null) {
			pager = new Pager3();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询
	 * @param pager - 页面bean
	 * @param detachedCriteria - Hibernate的detachedCriteria
	 * @return Pager - 页面bean
	 */
	public Pager3 findByPager(Pager3 pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager3();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
	
	
	/**
	 * 根据Search和Pager来查询
	 * @param search - 查询bean
	 * @param pager - 页面bean
	 * @return Pager - 页面bean
	 */
	public Pager3 searchByProperty(Search search, Pager3 pager){
		
		if (pager == null) {
			pager = new Pager3();
		}
		if (search == null) {
			search = new Search();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		//循环取得条件(字符串类型)
		if(search.getStrProperty()!=null){
			for(int i=0; i < search.getStrProperty().size(); i++){
				String propertyStr = search.getStrProperty().get(i);
				String keywordStr = search.getStrKeyword().get(i);
				if (StringUtils.isNotEmpty(propertyStr) && StringUtils.isNotEmpty(keywordStr)) {
					if(propertyStr.indexOf(".")>=0){
						String[] st=propertyStr.split("[.]");
						for(int j=0;j<st.length-1;j++){	
							String alias = getAlias((CriteriaImpl)criteria, st[j]);
							if(alias==null){
								criteria.createAlias(st[j], st[j], CriteriaSpecification.LEFT_JOIN);
							}	
						}
					}
					if(keywordStr.equalsIgnoreCase("null")){
						criteria.add(Restrictions.isNull(propertyStr));
					}else{
						criteria.add(Restrictions.like(propertyStr, "%" + keywordStr + "%"));
					}
				}
			}
		}
		//循环取得条件(日期类型)
		if(search.getDateProperty()!=null){
			for(int i=0; i < search.getDateProperty().size(); i++){
				String propertyDate = search.getDateProperty().get(i);
				Date dateFrom = search.getDateFrom().get(i);
				Date dateTo = search.getDateTo().get(i);		
				if (dateFrom != null) {
					Calendar bdate=Calendar.getInstance();
					bdate.setTime(dateFrom);
					bdate.set(Calendar.HOUR_OF_DAY, 0);
					bdate.set(Calendar.MINUTE, 0);
					bdate.set(Calendar.SECOND, 0);
					bdate.set(Calendar.MILLISECOND, 0);
					criteria.add(Restrictions.ge(propertyDate, bdate.getTime()));
				}
				if (dateTo != null) {
					Calendar edate=Calendar.getInstance();
					edate.setTime(dateTo);
					edate.set(Calendar.HOUR_OF_DAY, 23);
					edate.set(Calendar.MINUTE, 59);
					edate.set(Calendar.SECOND, 59);
					edate.set(Calendar.MILLISECOND, 998);
					criteria.add(Restrictions.le(propertyDate, edate.getTime()));
				}
//				if (dateFrom != null && dateTo != null ) {
//					criteria.add(Restrictions.between(propertyDate, dateFrom, dateTo));
//				}
			}
		}
		//循环取得条件(整数类型)		
		if(search.getIntProperty()!=null){
			for(int i=0; i < search.getIntProperty().size(); i++){
				String propertyInt = search.getIntProperty().get(i);
				Object intFrom = search.getIntFrom()==null?null:search.getIntFrom().get(i);
				Object intTo = search.getIntTo()==null?null:search.getIntTo().get(i);
				if (intFrom != null && intTo != null) {
					criteria.add(Restrictions.between(propertyInt, intFrom, intTo));
				}else if (intFrom != null && intTo == null) {
					criteria.add(Restrictions.eq(propertyInt, intFrom));
				}else if (intFrom == null && intTo != null) {
					criteria.add(Restrictions.eq(propertyInt, intTo));
				}
			}
		}
		Long totalCountTemp = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer totalCount = Integer.parseInt(""+totalCountTemp) ;
		//Integer totalCount = 30;
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		//设定排序规则
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if(orderBy.indexOf(".")>=0){
				String[] st=orderBy.split("[.]");
				for(int i=0;i<st.length-1;i++){		
					String alias = getAlias((CriteriaImpl)criteria, st[i]);
					if(alias==null){
						criteria.createAlias(st[i], st[i], CriteriaSpecification.LEFT_JOIN);
					}
				}
			}
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
			//针对NULL值排序的问题
			if (!orderBy.equalsIgnoreCase("id")) {
				criteria.addOrder(Order.asc("id"));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 判断是否已有关联
	 * @param impl
	 * @param path
	 * @return
	 */
	public String getAlias(CriteriaImpl impl, String path) {
		Iterator iterator = impl.iterateSubcriteria();
		for (; iterator.hasNext();) {
			Subcriteria subcriteria = (Subcriteria) iterator.next();
		    if (subcriteria.getPath().equals(path)) {
		    	return subcriteria.getAlias();
		    }                    
		 }
		 return null;
	}
	
	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * @param propertyName	属性名称
	 * @param oldValue		修改前的属性值
	 * @param oldValue		修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 根据属性名判断数据是否已存在.
	 * @param propertyName	属性名称
	 * @param value			值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}
	
	/**
	 * 刷新session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 清除Session
	 */
	public void clear() {
		getSession().clear();
	}

	/**
	 * 清除某一对象.
	 */
	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}
	/**
	 * 保存/修改
	 */
	public void saveObject(Object o){
		this.getSession().save(o);
	}
	public void updateObject(Object o){
		this.getSession().update(o);
	}
	
	
	/**
	 * 根据id 找一个对象 Object
	 */
	public Object findByIdObject(String id){
		Assert.notNull(id, "id is required");
		return getSession().get(entityClass, id);
	}
	
	/**
	 * 填充分页需要的数据, 页面列表、分页信息等 ，供dao调用。 
	 * @Title: fillPageData
	 * @author yzp
	 * @date 2014-7-17 下午4:04:39
	 * @param pager	
	 * @param hql	：查询列表的hql语句
	 * @param fromHql ：查询列表的hql语句去掉 select 内容的语句,(可以为"",  如果参数值为""就从hql中自动解析fromHql)
	 * @param paramList ：query 参数列表，没有传null
	 * 		List<Param> paramList = new ArrayList();
	 *		paramList.add(new Param("属性名","属性值"));
	 * @return Pager    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	protected Pager fillPagerData(Pager pager, String hql,String fromHqlParm,List<Param> paramList) {
		if(hql==null || "".equals(hql)){
			pager=null;
			return pager;
		}
		int start = hql.indexOf("from");
		if(start==-1){
			pager=null;
			return pager;
		}
		String fromHql = hql.substring(start,hql.length());
		if(!"".equals(fromHqlParm)){
			fromHql = fromHqlParm;
		}
		// 获得数据列表
		Query query = this.getSession().createQuery(hql);
		
		query.setFirstResult((pager.getCurrentPage() - 1) * pager.getMaxRows());
		query.setMaxResults(pager.getMaxRows());
		if(paramList!=null && paramList.size()>0){
			for(Param param:paramList){
				query.setParameter(param.getName(),param.getValue());
			}
		}
		// 获得符合条件的记录中暑
		String countHql = "select count(*) " +  fromHql;
		Query queryTotal  = this.getSession().createQuery(countHql);		
		if(paramList!=null && paramList.size()>0){
			for(Param param:paramList){
				queryTotal.setParameter(param.getName(),param.getValue());
			}
		}
		Object obj  = queryTotal.uniqueResult();	
		pager.setTotalCount(Integer.parseInt(""+obj));
		pager.setList(query.list());
		return pager;
	}
	/**
	 * 填充分页需要的数据, 页面列表、分页信息等 ，供dao调用。 
	 * @Title: fillPageData
	 * @author yzp
	 * @date 2014-7-17 下午4:04:39
	 * @param pager	
	 * @param hql	：查询列表的hql语句
	 * @param fromHql ：查询列表的hql语句去掉 select 内容的语句,(可以为"",  如果参数值为""就从hql中自动解析fromHql)
	 * @return Pager    
	 * @throws 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	protected Pager fillPagerData(Pager pager, String hql,String fromHqlParm) {
		return fillPagerData(pager,hql,fromHqlParm,null);
	}
}
