package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ztech.stock.dao.IncomeDao;
import com.ztech.stock.database.model.Income;


public class IncomeDaoImpl extends HibernateDaoSupport implements IncomeDao {
	
	private Logger logger = Logger.getLogger(IncomeDaoImpl.class);
	
	public void saveIncome(final Income income) {
		// The following might become stored procedure
		// Making sure there is no duplicate entry
		List<Income> incomeList = 
				(List<Income>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Income.class)
			              .add(Restrictions.eq("year", income.getYear()))
			              .createCriteria("stock")
			              .add(Restrictions.eq("id", income.getStock().getId()))
			              .list();
			}
		});
		if (incomeList.size() == 0) {
			getHibernateTemplate().save(income);
			logger.info("stock: " + income.getStock().getSymbol() + 
						" year: " + income.getYear() + " amount: " + income.getNetIncome());
		} else {
			logger.info("Entry already exists: stock: " + income.getStock().getSymbol() + 
							"  year: " + income.getYear());
		}
	}

}
