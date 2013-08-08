package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztech.stock.dao.LiabilityDao;
import com.ztech.stock.database.model.Income;
import com.ztech.stock.database.model.Liability;


public class LiabilityDaoImpl extends HibernateDaoSupport implements LiabilityDao {

	public void saveLiability(final Liability liability) {
		// The following might become stored procedure
		// Making sure there is no duplicate entry
		List<Liability> liabilityList = 
				(List<Liability>) getHibernateTemplate().execute(new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.createCriteria(Liability.class)
								.add(Restrictions.eq("year", liability.getYear()))
								.createCriteria("stock")
								.add(Restrictions.eq("id", liability.getStock().getId()))
								.list();
					}
				});
		if (liabilityList.size() == 0) {
			getHibernateTemplate().save(liability);
			logger.info("stock: " + liability.getStock().getSymbol() + 
					" year: " + liability.getYear() + " current liability: " + liability.getTotalCurrentLiability());
		} else {
			logger.info("Entry already exists: stock: " + liability.getStock().getSymbol() + 
					"  year: " + liability.getYear());
		}
	}
}
