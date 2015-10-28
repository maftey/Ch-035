package com.crsms.dao;

import com.crsms.domain.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petro Andriets
 */

@Repository("testDao")
public class TestDaoImpl implements TestDao {
    private static Logger logger = LogManager.getLogger(TestDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public TestDaoImpl() {
    }

    @Override
    public void saveTest(Test test) {
    	if (test != null) {
            logger.info("TestDao. Creating a new test.");
            Session session = sessionFactory.getCurrentSession();
            session.persist(test);
            logger.info("TestDao. Creating a new test successfully.");
    	} else {
    		throw new IllegalArgumentException("TestDao. Illegal argument received when test saving.");
    	}
    }

    @Override
    public Test getTestById(Long id) {
    	if (id != null) {
            logger.info("TestDao. Reading test by ID: " + id + ".");
            Session session = sessionFactory.getCurrentSession();
            Test test = (Test) session.load(Test.class, new Long(id));
            Hibernate.initialize(test.getModule());
            logger.info("TestDao. Reading test by ID: " + id + " successfully.");
            return test;
    	} else throw new IllegalArgumentException("TestDao. Illegal argument received when test by ID gettting.");	
    }

    @Override
    public List<Test> getAllTests() {
    	logger.info("TestDao. Reading all tests.");
        List<Test> testList = new ArrayList<Test>();
        String hql = "FROM Test";
        Session session = sessionFactory.getCurrentSession();
        testList = new ArrayList<Test>(session.createQuery(hql).list());
        logger.info("TestDao. Reading all tests successfully.");
        return testList;
    }
    
	@Override
	public List<Test> getAllByModuleId(Long id) {
		if (id != null) {
			logger.info("TestDao. Reading all tests by Module ID.");
			List<Test> testList = new ArrayList<Test>();
			String hql = "FROM Test WHERE module_id = :id order by id asc";
	        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
	        testList = query.list();
	        logger.info("TestDao. Reading all tests by ID successfully.");
			return testList;
		} else throw new IllegalArgumentException("TestDao. Illegal argument received when test by Module ID gettting.");	
	}

    @Override
    public void updateTest(Test test) {
    	if (test != null) {
            logger.info("TestDao. Updating test.");
            Session session = sessionFactory.getCurrentSession();
            session.update(test);
            logger.info("TestDao. Updating test successfully.");
    	} else throw new IllegalArgumentException("TestDao. Illegal argument received when test updating.");
    }

    @Override
    public void deleteTest(Test test) {
    	if (test != null) {
            logger.info("TestDao. Deleting test.");
            Session session = sessionFactory.getCurrentSession();
            session.delete(test);
            logger.info("TestDao. Deleting test successfully.");
    	} else throw new IllegalArgumentException("TestDao. Illegal argument received when test deleting.");
    }

    @Override
    public void deleteTestById(Long id) {
    	if (id != null) {
            logger.info("TestDao. Deleting test by ID: " + id + ".");
            Session session = sessionFactory.getCurrentSession();
            Test test = (Test) session.load(Test.class, new Long(id));
            if (test != null) {
                session.delete(test);
            }
            logger.info("TestDao. Deleting test by ID: " + id + " successfully.");
    	} else throw new IllegalArgumentException("TestDao. Illegal argument received when test by ID deleting.");
    }

}
