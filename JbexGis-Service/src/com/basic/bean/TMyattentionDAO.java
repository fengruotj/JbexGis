package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TMyattention entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TMyattention
 * @author MyEclipse Persistence Tools
 */
public class TMyattentionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TMyattentionDAO.class);

	// property constants

	public void save(TMyattention transientInstance) {
		log.debug("saving TMyattention instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMyattention persistentInstance) {
		log.debug("deleting TMyattention instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMyattention findById(com.basic.bean.TMyattentionId id) {
		log.debug("getting TMyattention instance with id: " + id);
		try {
			TMyattention instance = (TMyattention) getSession().get(
					"com.basic.bean.TMyattention", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TMyattention instance) {
		log.debug("finding TMyattention instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TMyattention")
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
		log.debug("finding TMyattention instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TMyattention as model where model."
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
		log.debug("finding all TMyattention instances");
		try {
			String queryString = "from TMyattention";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMyattention merge(TMyattention detachedInstance) {
		log.debug("merging TMyattention instance");
		try {
			TMyattention result = (TMyattention) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMyattention instance) {
		log.debug("attaching dirty TMyattention instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMyattention instance) {
		log.debug("attaching clean TMyattention instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}