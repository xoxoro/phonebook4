package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao2 {

	@Autowired
	DataSource dataSource;//DataSource dataSource = new DataSource();

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

//	private String driver = "oracle.jdbc.driver.OracleDriver";
//	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	private String id = "phonedb";
//	private String pw = "phonedb";

	// 메소드 일반
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			//Class.forName(driver);
			
			// 2. Connection 얻어오기
			//conn = DriverManager.getConnection(url, id, pw);
			conn = dataSource.getConnection();

		} catch (SQLException e) {
			System.out.println("error." + e);
		}
	}

	private void close() {
		try {
			// 자원정리
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 등록
	public int personInsert(PersonVo personVo) {
		int count = 0;
		this.getConnection();

		try {
			String query = "";
			query += "insert into person ";
			query += " values(seq_person_id.nextval, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			count = pstmt.executeUpdate();
			System.out.println("[" + count + "건 등록되었습니다.(PhoneDao)]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}

	// 수정
	public int personUpdate(PersonVo personVo) {
		int count = 0;
		this.getConnection();

		try {
			String query = "";
			query += "update person ";
			query += " set   name = ?, ";
			query += "       hp = ?, ";
			query += "       company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

			count = pstmt.executeUpdate();
			System.out.println("[" + count + "건 수정되었습니다.(PhoneDao)]");

		} catch (SQLException e) {
			System.out.println("error." + e);
		}

		this.close();
		return count;
	}
	
	// 삭제
	public int personDelete(int index) {
		int count = 0;
		this.getConnection();

		try {
			String query = "";
			query += "delete from person ";
			query += " where person_id = ?";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, index);

			count = pstmt.executeUpdate();
			System.out.println("[" + count + "건 삭제되었습니다.(PhoneDao)]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}

	// 리스트 가져오기
	public List<PersonVo> getPersonList() {
		return getPersonList("");
	}

	// 리스트 검색
	public List<PersonVo> getPersonList(String keyword) {
		List<PersonVo> pList = new ArrayList<PersonVo>();
		this.getConnection();

		try {
			String query = "";
			query += "select  person_id, ";
			query += "        name, ";
			query += "        hp, ";
			query += "        company ";
			query += " from person ";

			if (keyword != "" || keyword == null) {
				query += " where name like ? ";
				query += " or hp like  ? ";
				query += " or company like ? ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, '%' + keyword + '%'); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, '%' + keyword + '%'); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, '%' + keyword + '%'); // ?(물음표) 중 3번째, 순서중요
			
			} else {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			}

			rs = pstmt.executeQuery();

			while (rs.next() == true) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo vo = new PersonVo(personId, name, hp, company);
				pList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return pList;
	}

	// 특정 사람의 데이터만 가져오기
	public PersonVo getPerson(int personId) {
		PersonVo vo = null;
		this.getConnection();

		try {
			String query = "";
			query += "select  name, ";
			query += "        hp, ";
			query += "        company ";
			query += " from person ";
			query += " where person_id = ?";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, personId);

			rs = pstmt.executeQuery();

			while (rs.next() == true) {
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				vo = new PersonVo(personId, name, hp, company);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return vo;
	}
}