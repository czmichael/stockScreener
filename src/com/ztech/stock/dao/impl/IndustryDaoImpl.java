package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztech.stock.dao.IndustryDao;
import com.ztech.stock.database.model.Industry;



public class IndustryDaoImpl extends HibernateDaoSupport implements IndustryDao {

	@Override
	public Industry getIndustryByName(final String name) {
		List<Industry> industryList = (List<Industry>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Industry.class)
						.add(Restrictions.eq("name", name))
						.list();
			}
		});
		
		if (industryList.size() == 0) {
			Industry industry = new Industry();
			industry.setName(name);
			saveIndustry(industry);
			return industry;
		} else {
			return industryList.get(0);
		}
	}

	@Override
	public void saveIndustry(Industry industry) {
		getHibernateTemplate().save(industry);
	}
}
