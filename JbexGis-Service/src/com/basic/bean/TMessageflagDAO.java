package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TMessageflag entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TMessageflag
 * @author MyEclipse Persistence Tools
 */
public class TMessageflagDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TMessageflagDAO.class);
	// property constants
	public static final String FLAG = "flag";

	public void save(TMessageflag transientInstance) {
		log.debug("saving TMessageflag instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMessageflag persistentInstance) {
		log.debug("deleting TMessageflag instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMessageflag findById(java.lang.Long id) {
		log.debug("getting TMessageflag instance with id: " + id);
		try {
			TMessageflag instance = (TMessageflag) getSession().get(
					"com.basic.bean.TMessageflag", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TMessageflag instance) {
		log.debug("finding TMessageflag instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TMessageflag")
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
		log.debug("finding TMessageflag instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TMessageflag as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFlag(Object flag) {
		return findByProperty(FLAG, flag);
	}

	public List findAll() {
		log.debug("finding all TMessageflag instances");
		try {
			String queryString = "from TMessageflag";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMessageflag merge(TMessageflag detachedInstance) {
		log.debug("merging TMessageflag instance");
		try {
			TMessageflag result = (TMessageflag) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMessageflag instance) {
		log.debug("attaching dirty TMessageflag instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMessageflag instance) {
		log.debug("attaching clean TMessageflag instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}