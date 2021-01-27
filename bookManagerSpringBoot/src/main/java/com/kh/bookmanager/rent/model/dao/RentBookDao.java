package com.kh.bookmanager.rent.model.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.book.model.vo.QBook;
import com.kh.bookmanager.common.util.Paging;
import com.kh.bookmanager.rent.model.vo.QRent;
import com.kh.bookmanager.rent.model.vo.QRentBook;
import com.kh.bookmanager.rent.model.vo.Rent;
import com.kh.bookmanager.rent.model.vo.RentBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RentBookDao {
	
	@Autowired
	JPAQueryFactory jpaQuery;
	@Autowired
	EntityManager em;
	
	QRent r = QRent.rent;
	
	public int totalCountInRent(String userId){
		return (int)jpaQuery.select(r).from(r).where(r.userId.eq(userId)).fetchCount();
	}
	
	public List<Rent> selectRentinfo(Map<String,Object> commandMap){
		String userId = (String)commandMap.get("userId");
		Paging p = (Paging)commandMap.get("paging");
		List<Rent> result = 
				jpaQuery.select(r)
					.from(r)
					.where(r.userId.eq(userId))
					.orderBy(r.rmIdx.desc())
					.offset(p.getStart()-1)
					.limit(p.getCntPerPage())
					.fetch();
		
		return result;
	}
	
	public List<RentBook> selectRentbookinfo(Map<String,Object> commandMap){
		
		BooleanBuilder builder = new BooleanBuilder();
		String searchType = (String)commandMap.get("searchType");
		QRentBook rb = QRentBook.rentBook;
		QBook b = QBook.book;
		
		if(searchType.equals("rmIdx")) {
			builder.and(rb.rmIdx.eq((int)commandMap.get("rmIdx")));
		}else {
			builder.and(rb.rbIdx.eq((int)commandMap.get("rbIdx")));
		}
		
		List<RentBook> resultList =
				jpaQuery.select(rb)
				.from(rb)
				.innerJoin(rb).on(rb.bIdx.eq(b.bIdx))
				.where(builder).fetch();
		return resultList;
	}

	public void insertRentInfo(Rent rent){
		em.persist(rent);
		//엔티티메니저를 통해 rent를 영속성 컨테긋트에 저장
		//현재는 1차캐시에 저장되어 있는 상태이다. 
		//보통 commit을 하면 1차캐시에 있는 데이터가 DB로 넘어가고 commit 작업까지 진행된다.
		//하지만 대여정보와 대여도서정보의 경우 한 트랜잭션안에서 발생해야하고
		//대여정보가 DB에 반영되어야지만 대여도서정보를 insert하는 프로시저가 정상작동한다.
		//이럴때 flush() 메서드를 사용하면 commit은 하지 않고 insert만 진행이 가능하다.
		em.flush();
	}

	public void insertRentBookInfo(Rent rent){
		StoredProcedureQuery query = em.createStoredProcedureQuery("SP_RENT_INSERT");
		query.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.OUT);
		query.setParameter(0, rent.getBook().getbIdx());
		int rmIdx = (int)query.getOutputParameterValue(1);
		rent.setRmIdx(rmIdx);
	}

	public void updateReturnRentBook(Rent rent){
		StoredProcedureQuery query = em.createStoredProcedureQuery("SP_RENT_RETURN");
		query.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
		query.setParameter(0, rent.getRmIdx());
		query.setParameter(1, rent.getRbIdx());
		query.setParameter(2, rent.getUserId());
		query.execute();
	}
	
	public void updateExtendRentState(Rent rent){
		StoredProcedureQuery query = em.createStoredProcedureQuery("SP_RENT_EXTEND");
		query.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
		query.setParameter(0, rent.getRmIdx());
		query.setParameter(1, rent.getRbIdx());
		query.execute();
	}
	
	//연체도서 검사
	public int selectLateCnt(String userId){
		QRentBook rb = QRentBook.rentBook;
		return (int)jpaQuery.select(rb)
				.from(rb)
				.where(rb.userId.eq(userId)
						.and(rb.state.eq("RE02")))
				.fetchCount();
	}
}
