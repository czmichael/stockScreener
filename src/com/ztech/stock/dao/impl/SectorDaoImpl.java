package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztech.stock.dao.SectorDao;
import com.ztech.stock.database.model.Sector;


public class SectorDaoImpl extends HibernateDaoSupport implements SectorDao {

	@Override
	public Sector getSectorByName(final String name) {
		List<Sector> sectorList = (List<Sector>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Sector.class)
						.add(Restrictions.eq("name", name))
						.list();
			}
		});
		
		if (sectorList.size() == 0) {
			Sector sector = new Sector();
			sector.setName(name);
			saveSectory(sector);
			return sector;
		} else {
			return sectorList.get(0);
		}
	}

	@Override
	public void saveSectory(Sector sector) {
		getHibernateTemplate().save(sector);
	}
}
