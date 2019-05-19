package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TGroupfamily entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TGroupfamily
 * @author MyEclipse Persistence Tools
 */
public class TGroupfamilyDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TGroupfamilyDAO.class);

	// property constants

	public void save(TGroupfamily transientInstance) {
		log.debug("saving TGroupfamily instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGroupfamily persistentInstance) {
		log.debug("deleting TGroupfamily instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGroupfamily findById(com.basic.bean.TGroupfamilyId id) {
		log.debug("getting TGroupfamily instance with id: " + id);
		try {
			TGroupfamily instance = (TGroupfamily) getSession().get(
					"com.basic.bean.TGroupfamily", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TGroupfamily instance) {
		log.debug("finding TGroupfamily instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TGroupfamily")
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
		log.debug("finding TGroupfamily instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TGroupfamily as model where model."
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
		log.debug("finding all TGroupfamily instances");
		try {
			String queryString = "from TGroupfamily";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGroupfamily merge(TGroupfamily detachedInstance) {
		log.debug("merging TGroupfamily instance");
		try {
			TGroupfamily result = (TGroupfamily) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGroupfamily instance) {
		log.debug("attaching dirty TGroupfamily instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGroupfamily instance) {
		log.debug("attaching clean TGroupfamily instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}