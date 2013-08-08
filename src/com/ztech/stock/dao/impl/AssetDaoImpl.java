package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztech.stock.dao.AssetDao;
import com.ztech.stock.database.model.Asset;


public class AssetDaoImpl extends HibernateDaoSupport implements AssetDao {

	public void saveAsset(final Asset asset) {
		List<Asset> assetList = 
				(List<Asset>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Asset.class)
			              .add(Restrictions.eq("year", asset.getYear()))
			              .createCriteria("stock")
			              .add(Restrictions.eq("id", asset.getStock().getId()))
			              .list();
			}
		});
		if (assetList.size() == 0) {
			getHibernateTemplate().save(asset);
			logger.info("stock: " + asset.getStock().getSymbol() + 
						" year: " + asset.getYear() + " current asset: " + asset.getTotalCurrentAsset());
		} else {
			logger.info("Entry already exists: stock: " + asset.getStock().getSymbol() + 
							"  year: " + asset.getYear());
		}
	}
}