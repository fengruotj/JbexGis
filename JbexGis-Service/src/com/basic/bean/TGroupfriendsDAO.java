package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TGroupfriends entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TGroupfriends
 * @author MyEclipse Persistence Tools
 */
public class TGroupfriendsDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TGroupfriendsDAO.class);

	// property constants

	public void save(TGroupfriends transientInstance) {
		log.debug("saving TGroupfriends instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TGroupfriends persistentInstance) {
		log.debug("deleting TGroupfriends instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TGroupfriends findById(com.basic.bean.TGroupfriendsId id) {
		log.debug("getting TGroupfriends instance with id: " + id);
		try {
			TGroupfriends instance = (TGroupfriends) getSession().get(
					"com.basic.bean.TGroupfriends", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TGroupfriends instance) {
		log.debug("finding TGroupfriends instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TGroupfriends")
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
		log.debug("finding TGroupfriends instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TGroupfriends as model where model."
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
		log.debug("finding all TGroupfriends instances");
		try {
			String queryString = "from TGroupfriends";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGroupfriends merge(TGroupfriends detachedInstance) {
		log.debug("merging TGroupfriends instance");
		try {
			TGroupfriends result = (TGroupfriends) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGroupfriends instance) {
		log.debug("attaching dirty TGroupfriends instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TGroupfriends instance) {
		log.debug("attaching clean TGroupfriends instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}