package com.ztech.stock.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.ztech.stock.dao.StockDao;
import com.ztech.stock.database.model.Stock;
import com.ztech.stock.util.QueryParam;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class StockDaoImpl extends HibernateDaoSupport implements StockDao {

	private static final Logger logger = Logger.getLogger(StockDaoImpl.class);
	private static final String GET_STOCK_BY_SYMBOL = 
									"select s.id from Stock s where s.symbol = '";
	
	public Stock getStockById(int id) {
		return (Stock) getHibernateTemplate().get(Stock.class, id);
	}
	
	public void updateStock(Stock stock) {
		getHibernateTemplate().update(stock);
		logger.info("stock " + stock.getSymbol() + " updated.");
	}
	
	public List<Stock> getAllStocks() {
		return (List<Stock>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Stock.class).list();
			}
		});
	}
	
	public void saveStock(Stock stock) {
		if (stockSymbolExist(stock.getSymbol())) {
			logger.warn("stock company: (" + stock.getCompany() + ") already exists.");
		} else {
			getHibernateTemplate().save(stock);
			logger.info("insertion successful - company: " + stock.getCompany() + 
						"  symbol: " + stock.getSymbol());
		}
	}
	
	public Boolean stockSymbolExist(final String symbol) {
		return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List stockList =
					session.createQuery(GET_STOCK_BY_SYMBOL + symbol + "'").list();
				return (Boolean) (stockList.size() > 0);
			}
		});		
	}

	@Override
	public Long getStockRowCount() {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return ((Long) session.createQuery("select count(*) from Stock").uniqueResult());
			}
		});		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> filterStock(final List<QueryParam> paramList, final String orderByField) {
		return (List<Stock>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Stock.class);
				for (QueryParam param: paramList) {
					criteria.add(Restrictions.gt(param.getParamName(), param.getMin()));
					criteria.add(Restrictions.lt(param.getParamName(), param.getMax()));
				}
				criteria.addOrder(Order.asc(orderByField));
				List<Stock> stockList = criteria.list();
				for (Stock stock: stockList) {
					Hibernate.initialize(stock.getAssetList());
					Hibernate.initialize(stock.getIncomeList());
					Hibernate.initialize(stock.getLiabilityList());
				}
				return criteria.list();
			}
		});
	}
		
}
