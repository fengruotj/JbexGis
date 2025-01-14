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
 * TPublicinfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.basic.bean.TPublicinfo
 * @author MyEclipse Persistence Tools
 */
public class TPublicinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TPublicinfoDAO.class);
	// property constants
	public static final String DOT_X = "dotX";
	public static final String DOT_Y = "dotY";
	public static final String TITLE = "title";
	public static final String DETAIL = "detail";
	public static final String LABEL = "label";
	public static final String PICTURE1 = "picture1";
	public static final String PICTURE2 = "picture2";

	public void save(TPublicinfo transientInstance) {
		log.debug("saving TPublicinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TPublicinfo persistentInstance) {
		log.debug("deleting TPublicinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TPublicinfo findById(java.lang.Long id) {
		log.debug("getting TPublicinfo instance with id: " + id);
		try {
			TPublicinfo instance = (TPublicinfo) getSession().get(
					"com.basic.bean.TPublicinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TPublicinfo instance) {
		log.debug("finding TPublicinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.basic.bean.TPublicinfo")
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
		log.debug("finding TPublicinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TPublicinfo as model where model."
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

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByDetail(Object detail) {
		return findByProperty(DETAIL, detail);
	}

	public List findByLabel(Object label) {
		return findByProperty(LABEL, label);
	}

	public List findByPicture1(Object picture1) {
		return findByProperty(PICTURE1, picture1);
	}

	public List findByPicture2(Object picture2) {
		return findByProperty(PICTURE2, picture2);
	}

	public List findAll() {
		log.debug("finding all TPublicinfo instances");
		try {
			String queryString = "from TPublicinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TPublicinfo merge(TPublicinfo detachedInstance) {
		log.debug("merging TPublicinfo instance");
		try {
			TPublicinfo result = (TPublicinfo) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TPublicinfo instance) {
		log.debug("attaching dirty TPublicinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TPublicinfo instance) {
		log.debug("attaching clean TPublicinfo instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}