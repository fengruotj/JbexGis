package com.basic.bean;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TJbrxfriend entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TJbrxfriend
 * @author MyEclipse Persistence Tools
 */
public class TJbrxfriendDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TJbrxfriendDAO.class);

	// property constants

	public void save(TJbrxfriend transientInstance) {
		log.debug("saving TJbrxfriend instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TJbrxfriend persistentInstance) {
		log.debug("deleting TJbrxfriend instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TJbrxfriend findById(com.basic.bean.TJbrxfriendId id) {
		log.debug("getting TJbrxfriend instance with id: " + id);
		try {
			TJbrxfriend instance = (TJbrxfriend) getSession().get(
					"com.basic.bean.TJbrxfriend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TJbrxfriend instance) {
		log.debug("finding TJbrxfriend instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TJbrxfriend")
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
		log.debug("finding TJbrxfriend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TJbrxfriend as model where model."
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
		log.debug("finding all TJbrxfriend instances");
		try {
			String queryString = "from TJbrxfriend";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TJbrxfriend merge(TJbrxfriend detachedInstance) {
		log.debug("merging TJbrxfriend instance");
		try {
			TJbrxfriend result = (TJbrxfriend) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TJbrxfriend instance) {
		log.debug("attaching dirty TJbrxfriend instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TJbrxfriend instance) {
		log.debug("attaching clean TJbrxfriend instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}