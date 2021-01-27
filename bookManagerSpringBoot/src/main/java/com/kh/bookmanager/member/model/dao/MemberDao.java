package com.kh.bookmanager.member.model.dao;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.rent.model.vo.Rent;
import com.kh.bookmanager.rent.model.vo.RentBook;

@Repository
public class MemberDao {

	private EntityManager em;
	
	@Autowired
	public MemberDao(EntityManager em) {
		this.em = em;
	}
	
	public void insertMember(Member member) {
		//member 정보를 테이블에 추가한다.
		em.persist(member);
	}

	public Member selectMember(String userId) {
		//반환되는 타입이 확정일때는 TypedQuery를 사용해 원하는 vo에 바로 매핑이 가능하다.
		TypedQuery<Member> q = em
				//쿼리를 작성할 때 mybatis처럼 동적으로 넣기 위해서는 ':userId' 형태로 변수를 선언하고
				//setParameter 메서드를 사용해 값을 넣을 수 있다.
				.createQuery("select m from tb_member m where userId=:userId and is_leave = 0",Member.class);
		q.setParameter("userId", userId);
		//결과값이 하나이기 때문에 getSingleResult();
		return q.getSingleResult();
	}
		
	public int selectId(String userId) {
		
		//Criteria 사용 준비
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		
		//조회 할 클래스 지정
		Root<Member> m = query.from(Member.class);
		
		//조회할 클래스의 userId가 사용자가 입력한 값과 일치하는 수를 반환
		//builder.count가 반환형이 Long이다.
		query.select(builder.count(query.from(Member.class)))
				.where(builder.equal(m.get("userId"), userId));
		
		return em.createQuery(query).getSingleResult().intValue();
	}
	
	public int selectRentCount(String userId) {
		
		//Criteria 사용 준비
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		
		//조회할 클래스 지정
		Root<RentBook> rb = query.from(RentBook.class);
		//where or절 작성
		Predicate orClause = builder.or(builder.equal(rb.get("state"),"RE00")
								,builder.equal(rb.get("state"),"RE01"));		
		query.select(builder.count(rb))
			.where(builder.equal(rb.get("userId"),userId),orClause);
		
		return em.createQuery(query).getSingleResult().intValue();
	}
}
