package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TMycollections entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TMycollections
 * @author MyEclipse Persistence Tools
 */
public class TMycollectionsDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TMycollectionsDAO.class);

	// property constants

	public void save(TMycollections transientInstance) {
		log.debug("saving TMycollections instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMycollections persistentInstance) {
		log.debug("deleting TMycollections instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMycollections findById(com.basic.bean.TMycollectionsId id) {
		log.debug("getting TMycollections instance with id: " + id);
		try {
			TMycollections instance = (TMycollections) getSession().get(
					"com.basic.bean.TMycollections", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TMycollections instance) {
		log.debug("finding TMycollections instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TMycollections")
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
		log.debug("finding TMycollections instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TMycollections as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all TMycollections instances");
		try {
			String queryString = "from TMycollections";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMycollections merge(TMycollections detachedInstance) {
		log.debug("merging TMycollections instance");
		try {
			TMycollections result = (TMycollections) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMycollections instance) {
		log.debug("attaching dirty TMycollections instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMycollections instance) {
		log.debug("attaching clean TMycollections instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}