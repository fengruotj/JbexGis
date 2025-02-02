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
 * TFriendrequest entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TFriendrequest
 * @author MyEclipse Persistence Tools
 */
public class TFriendrequestDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TFriendrequestDAO.class);
	// property constants
	public static final String REQUESTGROUP = "requestgroup";
	public static final String VALIDATIONMESSAGE = "validationmessage";

	public void save(TFriendrequest transientInstance) {
		log.debug("saving TFriendrequest instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TFriendrequest persistentInstance) {
		log.debug("deleting TFriendrequest instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TFriendrequest findById(java.lang.Long id) {
		log.debug("getting TFriendrequest instance with id: " + id);
		try {
			TFriendrequest instance = (TFriendrequest) getSession().get(
					"com.basic.bean.TFriendrequest", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TFriendrequest instance) {
		log.debug("finding TFriendrequest instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TFriendrequest")
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
		log.debug("finding TFriendrequest instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TFriendrequest as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRequestgroup(Object requestgroup) {
		return findByProperty(REQUESTGROUP, requestgroup);
	}

	public List findByValidationmessage(Object validationmessage) {
		return findByProperty(VALIDATIONMESSAGE, validationmessage);
	}

	public List findAll() {
		log.debug("finding all TFriendrequest instances");
		try {
			String queryString = "from TFriendrequest";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TFriendrequest merge(TFriendrequest detachedInstance) {
		log.debug("merging TFriendrequest instance");
		try {
			TFriendrequest result = (TFriendrequest) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TFriendrequest instance) {
		log.debug("attaching dirty TFriendrequest instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TFriendrequest instance) {
		log.debug("attaching clean TFriendrequest instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}