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
 * TMessagebean entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TMessagebean
 * @author MyEclipse Persistence Tools
 */
public class TMessagebeanDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TMessagebeanDAO.class);
	// property constants
	public static final String MSG = "msg";
	public static final String TYPE = "type";

	public void save(TMessagebean transientInstance) {
		log.debug("saving TMessagebean instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMessagebean persistentInstance) {
		log.debug("deleting TMessagebean instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMessagebean findById(java.lang.Long id) {
		log.debug("getting TMessagebean instance with id: " + id);
		try {
			TMessagebean instance = (TMessagebean) getSession().get(
					"com.basic.bean.TMessagebean", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TMessagebean instance) {
		log.debug("finding TMessagebean instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TMessagebean")
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
		log.debug("finding TMessagebean instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TMessagebean as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMsg(Object msg) {
		return findByProperty(MSG, msg);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all TMessagebean instances");
		try {
			String queryString = "from TMessagebean";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMessagebean merge(TMessagebean detachedInstance) {
		log.debug("merging TMessagebean instance");
		try {
			TMessagebean result = (TMessagebean) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMessagebean instance) {
		log.debug("attaching dirty TMessagebean instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMessagebean instance) {
		log.debug("attaching clean TMessagebean instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}