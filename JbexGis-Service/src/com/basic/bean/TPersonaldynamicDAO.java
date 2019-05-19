package com.basic.bean;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TPersonaldynamic entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TPersonaldynamic
 * @author MyEclipse Persistence Tools
 */
public class TPersonaldynamicDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TPersonaldynamicDAO.class);
	// property constants
	public static final String DOT_X = "dotX";
	public static final String DOT_Y = "dotY";
	public static final String DETAIL = "detail";
	public static final String PICTURE1 = "picture1";
	public static final String PICTURE2 = "picture2";

	public void save(TPersonaldynamic transientInstance) {
		log.debug("saving TPersonaldynamic instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TPersonaldynamic persistentInstance) {
		log.debug("deleting TPersonaldynamic instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TPersonaldynamic findById(java.lang.Long id) {
		log.debug("getting TPersonaldynamic instance with id: " + id);
		try {
			TPersonaldynamic instance = (TPersonaldynamic) getSession().get(
					"com.basic.bean.TPersonaldynamic", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TPersonaldynamic instance) {
		log.debug("finding TPersonaldynamic instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TPersonaldynamic")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TPersonaldynamic instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TPersonaldynamic as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDotX(Object dotX) {
		return findByProperty(DOT_X, dotX);
	}

	public List findByDotY(Object dotY) {
		return findByProperty(DOT_Y, dotY);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findByPicture1(Object picture1) {
		return findByProperty(PICTURE1, picture1);
	}

	public List findByPicture2(Object picture2) {
		return findByProperty(PICTURE2, picture2);
	}

	public List findAll() {
		log.debug("finding all TPersonaldynamic instances");
		try {
			String queryString = "from TPersonaldynamic";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TPersonaldynamic merge(TPersonaldynamic detachedInstance) {
		log.debug("merging TPersonaldynamic instance");
		try {
			TPersonaldynamic result = (TPersonaldynamic) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TPersonaldynamic instance) {
		log.debug("attaching dirty TPersonaldynamic instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TPersonaldynamic instance) {
		log.debug("attaching clean TPersonaldynamic instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}