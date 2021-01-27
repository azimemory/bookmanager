package com.kh.bookmanager.cart.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.book.model.vo.QBook;
import com.kh.bookmanager.cart.model.vo.Cart;
import com.kh.bookmanager.cart.model.vo.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CartDao {
	
	@Autowired
	JPAQueryFactory jpaQuery;
	@Autowired
	EntityManager em;
	
	QCart c = QCart.cart;
	QBook b = QBook.book;
	
	
	public List<Cart> selectCart(Cart cart){
		return jpaQuery
				.select(c)
				.from(c)
				.innerJoin(b).on(c.bIdx.eq(b.bIdx))
				.where(c.userId.eq(cart.getUserId())
						.or(c.sessionId.eq(cart.getSessionId())))
				.fetch();
	}
	
	public void insertCart(Cart cart){
		em.persist(cart);
	}
	
	public int deleteCart(int bcIdx){
		return (int) jpaQuery.delete(c).where(c.bcIdx.eq(bcIdx)).execute();
	}

}
