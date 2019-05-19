package com.basic.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for TUser
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.basic.bean.TUser
 * @author MyEclipse Persistence Tools
 */
public class TUserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(TUserDAO.class);
	// property constants
	public static final String EMAIL = "email";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String USER_NICKNAME = "userNickname";
	public static final String PERSON_SIGNATURE = "personSignature";
	public static final String SEX = "sex";
	public static final String SCHOOL = "school";
	public static final String ACADEMY = "academy";
	public static final String STATE = "state";
	public static final String TELEPHONE = "telephone";
	public static final String PICTURE = "picture";
	public static final String SECURITY_CONTROL = "securityControl";

	public void save(TUser transientInstance) {
		log.debug("saving TUser instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TUser persistentInstance) {
		log.debug("deleting TUser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TUser findById(java.lang.Long id) {
		log.debug("getting TUser instance with id: " + id);
		try {
			TUser instance = (TUser) getSession().get("com.basic.bean.TUser",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TUser instance) {
		log.debug("finding TUser instance by example");
		try {
			List results = getSession().createCriteria("com.basic.bean.TUser")
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
		log.debug("finding TUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TUser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByUserNickname(Object userNickname) {
		return findByProperty(USER_NICKNAME, userNickname);
	}

	public List findByPersonSignature(Object personSignature) {
		return findByProperty(PERSON_SIGNATURE, personSignature);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findBySchool(Object school) {
		return findByProperty(SCHOOL, school);
	}

	public List findByAcademy(Object academy) {
		return findByProperty(ACADEMY, academy);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByTelephone(Object telephone) {
		return findByProperty(TELEPHONE, telephone);
	}

	public List findByPicture(Object picture) {
		return findByProperty(PICTURE, picture);
	}

	public List findBySecurityControl(Object securityControl) {
		return findByProperty(SECURITY_CONTROL, securityControl);
	}

	public List findAll() {
		log.debug("finding all TUser instances");
		try {
			String queryString = "from TUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TUser merge(TUser detachedInstance) {
		log.debug("merging TUser instance");
		try {
			TUser result = (TUser) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TUser instance) {
		log.debug("attaching dirty TUser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TUser instance) {
		log.debug("attaching clean TUser instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}