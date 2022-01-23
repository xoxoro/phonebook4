package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {
//쿼리문은 xml에 놔두고 어떤 쿼리문을 쓸건지 메소드는 다오에서 짠다
	@Autowired
	//DataSource dataSource;//DataSource dataSource = new DataSource();
	private SqlSession sqlSession;
	
	//전체 리스트 가져오기
	public List<PersonVo> getPersonList() {
		System.out.println("PhoneDao.getPersonList()");
		
		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList");
		System.out.println(personList);
		return personList;
		
	}
	
	//전화번호 추가
	public int personInsert(PersonVo personVo) {
		System.out.println("PhoneDao.personInsert()");
		
		int count = sqlSession.insert("phonebook.insert", personVo);
		System.out.println(count+"건 저장되었습니다.");
		
		return count;
		//return sqlSession.insert("phonebook.insert", personVo);
		
	}
	
	//전화번호 삭제
	public int personDelete(int personId) {
		System.out.println("PhoneDao.personDelete()");
		
		int count = sqlSession.delete("phonebook.delete", personId);
		
		return count;
	}
	
	//전화번호 수정
	public int personUpdate(PersonVo personVo) {
		System.out.println("PhoneDao.personUpdate()");
		
		int count = sqlSession.update("phonebook.update", personVo);
		
		return count;
	}
	
	// 전화번호 1명정보
	public PersonVo getPerson(int personId) {
		System.out.println("PhoneDao.getPerson()");

		//PersonVo personVo = sqlSession.selectOne("phonebook.selectPerson", personId);
		Map  <String, Object> personMap =  sqlSession.selectOne("phonebook.selectPerson2", personId);
		System.out.println(personMap.keySet());
		
		System.out.println(personMap.get("PERSON_ID"));
		System.out.println(personMap.get("NAME"));
		System.out.println(personMap.get("HP"));
		System.out.println(personMap.get("COMPANY"));
		
		return null;
	}

	
}