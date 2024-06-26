package kr.checker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVo;
import kr.checker.vo.CheckerVo;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class CheckerDao {
	private static CheckerDao dao = new CheckerDao();
	public static CheckerDao getDao() {
		return dao;
	}
	private CheckerDao() {};
	
	//검수자 등록
	public void insertChecker(CheckerVo checker) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;

		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO checker (checker_num, checker_name, checker_photo, checker_company, checker_phone) VALUES (checker_seq.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, checker.getChecker_name());
			pstmt.setString(++cnt, checker.getChecker_photo());
			pstmt.setString(++cnt, checker.getChecker_company());
			pstmt.setString(++cnt, checker.getChecker_phone());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//검수자 목록
	public List<CheckerVo>getCheckerAllList() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CheckerVo> checkerList;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM checker";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			checkerList = new ArrayList<>();
			
			while(rs.next()) {
				CheckerVo checker = new CheckerVo();
				checker.setChecker_num(rs.getInt("checker_num"));
				checker.setChecker_name(rs.getString("checker_name"));
				checker.setChecker_photo(rs.getString("checker_photo"));
				checker.setChecker_company(rs.getString("checker_company"));
				checker.setChecker_phone(rs.getString("checker_phone"));
				checkerList.add(checker);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return checkerList;
	}
	
	//검수자 한 건의 정보 조회
	public CheckerVo getChecker(int checker_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		CheckerVo checker = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM checker WHERE checker_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, checker_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				checker = new CheckerVo();
				checker.setChecker_num(rs.getInt("checker_num"));
				checker.setChecker_name(rs.getString("checker_name"));
				checker.setChecker_company(rs.getString("checker_company"));
				checker.setChecker_phone(rs.getString("checker_phone"));
				checker.setChecker_photo(rs.getString("checker_photo"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return checker;
	}
	
	//검수자 정보 수정
	public void updateChecker(CheckerVo checker) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE CHECKER SET checker_name=?, checker_company=?, checker_phone=?, checker_photo=? WHERE checker_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, checker.getChecker_name());
			pstmt.setString(++cnt, checker.getChecker_company());
			pstmt.setString(++cnt, checker.getChecker_phone());
			pstmt.setString(++cnt, checker.getChecker_photo());
			pstmt.setInt(++cnt, checker.getChecker_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//검수자 정보 삭제
	public void deleteChecker(int checker_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM checker WHERE checker_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, checker_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//총 글의 수, 검색
	public int getCheckerListCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			sql = "SELECT COUNT(*) FROM checker " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//글 목록, 검색 글 목록
	public List<CheckerVo> getCheckerList(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CheckerVo> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn  = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM checker " + sub_sql
					+ " ORDER BY checker_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CheckerVo>();
			while(rs.next()) {
				CheckerVo checker = new CheckerVo();
				checker.setChecker_num(rs.getInt("checker_num"));
				checker.setChecker_name(rs.getString("checker_name"));
				checker.setChecker_company(rs.getString("checker_company"));
				checker.setChecker_phone(rs.getString("checker_phone"));
				checker.setChecker_photo(rs.getString("checker_photo"));
				
				list.add(checker);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
}
