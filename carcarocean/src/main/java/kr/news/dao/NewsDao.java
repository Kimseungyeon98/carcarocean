package kr.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.news.vo.NewsVo;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class NewsDao {
	//싱글턴 패턴
	private static NewsDao instance = new NewsDao();
	
	public static NewsDao getDao() {
		return instance;
	}
	private NewsDao() {}
	
	//글 등록
	public void insertNews(NewsVo news)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션풀에서 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO news (news_num, news_title, news_content, news_photo, news_hit)"
					+ " VALUES (news_seq.nextval, ?, ?, ?, ?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, news.getNews_title());
			pstmt.setString(++cnt, news.getNews_content());
			pstmt.setString(++cnt, news.getNews_photo());
			pstmt.setInt(++cnt, news.getNews_hit());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총 글의 개수, 검색 개수
	public int getNewsCount(String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE news_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE news_content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM news " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
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
	
	//글 목록
	public List<NewsVo> getListNews(int start, int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NewsVo> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE news_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE news_content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM news "
					+sub_sql + " ORDER BY news_num DESC)a) WHERE rnum >=? AND rnum <=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<NewsVo>();
			while(rs.next()) {
				NewsVo news = new NewsVo();
				news.setNews_num(rs.getInt("news_num"));
				news.setNews_title(StringUtil.useNoHTML(rs.getString("news_title")));
				news.setNews_reg(rs.getString("news_reg"));
				news.setNews_hit(rs.getInt("news_hit"));
				news.setNews_modify(rs.getString("news_modify"));
				list.add(news);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//(메인)글 목록
		public List<NewsVo> getListNewsMain(int start, int end)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<NewsVo> list = null;
			String sql = null;
			int cnt = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM news "
					+ " ORDER BY news_num DESC)a) WHERE rnum >=? AND rnum <=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<NewsVo>();
				while(rs.next()) {
					NewsVo news = new NewsVo();
					news.setNews_num(rs.getInt("news_num"));
					news.setNews_title(StringUtil.useNoHTML(rs.getString("news_title")));
					news.setNews_photo(rs.getString("news_photo"));
					news.setNews_content(StringUtil.useNoHTML(rs.getString("news_content")));
					news.setNews_reg(rs.getString("news_reg"));
					news.setNews_hit(rs.getInt("news_hit"));
					news.setNews_modify(rs.getString("news_modify"));
					list.add(news);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	
	//글 상세
	public NewsVo detailNews(int news_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVo news = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM news WHERE news_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, news_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				news = new NewsVo();
				news.setNews_num(rs.getInt("news_num"));
				news.setNews_title(rs.getString("news_title"));
				news.setNews_content(rs.getString("news_content"));
				news.setNews_photo(rs.getString("news_photo"));
				news.setNews_hit(rs.getInt("news_hit"));
				news.setNews_reg(rs.getString("news_reg"));
				news.setNews_modify(rs.getString("news_modify"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return news;
	}
	
	//상세 글 확인 시 조회 수 증가
	public void updateReadCount(int news_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE news SET news_hit = news_hit + 1 WHERE news_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, news_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//파일 삭제
	public void deletePhoto(int news_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE news SET news_photo='' WHERE news_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, news_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 수정
	public void updateNews(NewsVo news)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(news.getNews_photo() != null && !"".equals(news.getNews_photo())) {
				sub_sql += ",news_photo=?";
			}
			//SQL문 작성
			sql = "UPDATE news SET news_title=?, news_content=?, news_modify=SYSDATE" + sub_sql
					+ " WHERE news_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, news.getNews_title());
			pstmt.setString(++cnt, news.getNews_content());
			
			if(news.getNews_photo() != null && !"".equals(news.getNews_photo())) {
				pstmt.setString(++cnt, news.getNews_photo());
			}
			pstmt.setInt(++cnt, news.getNews_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}	
	}
	
	//글 삭제
	public void deleteNews(int news_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM news WHERE news_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, news_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
