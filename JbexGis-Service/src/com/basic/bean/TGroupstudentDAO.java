package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TGroupstudent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TGroupstudent
 * @author MyEclipse Persistence Tools
 */
public class TGroupstudentDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TGroupstudentDAO.class);

	// property constants

	public void save(TGroupstudent transientInstance) {
		log.debug("saving TGroupstudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGroupstudent persistentInstance) {
		log.debug("deleting TGroupstudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGroupstudent findById(com.basic.bean.TGroupstudentId id) {
		log.debug("getting TGroupstudent instance with id: " + id);
		try {
			TGroupstudent instance = (TGroupstudent) getSession().get(
					"com.basic.bean.TGroupstudent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TGroupstudent instance) {
		log.debug("finding TGroupstudent instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TGroupstudent")
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
		log.debug("finding TGroupstudent instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TGroupstudent as model where model."
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
		log.debug("finding all TGroupstudent instances");
		try {
			String queryString = "from TGroupstudent";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGroupstudent merge(TGroupstudent detachedInstance) {
		log.debug("merging TGroupstudent instance");
		try {
			TGroupstudent result = (TGroupstudent) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGroupstudent instance) {
		log.debug("attaching dirty TGroupstudent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGroupstudent instance) {
		log.debug("attaching clean TGroupstudent instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}