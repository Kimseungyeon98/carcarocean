package kr.b_re.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.b_re.vo.B_ReVo;
import kr.util.DBUtil;

public class B_ReDao {
	private static B_ReDao dao = new B_ReDao();
	public static B_ReDao getDao() {
		return dao;
	}
	private B_ReDao() {}
	
	//구매 후기 게시판 글 등록
	public void insertBuyReview(B_ReVo b_re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO b_re(b_re_num,buy_num,b_re_title,b_re_content,b_re_photo) "
					+ "VALUES(b_re_seq.nextval,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, b_re.getBuy_num());
			pstmt.setString(++cnt, b_re.getB_re_title());
			pstmt.setString(++cnt, b_re.getB_re_content());
			pstmt.setString(++cnt, b_re.getB_re_photo());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//회원 번호로 구매 내역을 조회
	public List<B_ReVo> getBuyList(int mem_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<B_ReVo> list = null;
	    String sql = null;
	    try {
	        conn = DBUtil.getConnection();

	        sql = "SELECT car_num,car_name,buy_num,buy_reg FROM buy JOIN car USING(car_num) WHERE mem_num = ? AND buy_status=1";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, mem_num);
	        
	        rs = pstmt.executeQuery();
	        list = new ArrayList<B_ReVo>();
	        while (rs.next()) {
	            B_ReVo buy = new B_ReVo();
	            buy.setBuy_num(rs.getInt("buy_num"));
	            buy.setCar_num(rs.getInt("car_num"));
	            buy.setCar_name(rs.getString("car_name"));
	            buy.setBuy_reg(rs.getDate("buy_reg"));
	            
	            list.add(buy);
	        }

	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}
	//구매 후기 게시판 총 글의 개수, 검색 개수
		public int getB_reCount()throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				conn = DBUtil.getConnection();
				
				
				sql = "SELECT COUNT(*) FROM b_re";
				
				pstmt = conn.prepareStatement(sql);
				
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
		//구매 후기 게시판 글 목록, 검색 글 목록
		public List<B_ReVo> getListB_re(int startRow, int endRow)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			List<B_ReVo> list = null;
			int cnt = 0;
			try {
				conn = DBUtil.getConnection();
				
				
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM"
						+ "(SELECT * FROM b_re r "
				        + "JOIN buy b ON r.buy_num = b.buy_num "
				        + "JOIN car c ON b.car_num = c.car_num "
				        + "ORDER BY b_re_num DESC)a) "
				        + "WHERE rnum >= ? AND rnum <= ?";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<B_ReVo>();
				while(rs.next()) {
					B_ReVo b_re = new B_ReVo();
					b_re.setB_re_num(rs.getInt("b_re_num"));
					b_re.setBuy_num(rs.getInt("buy_num"));
					b_re.setCar_num(rs.getInt("car_num"));
					b_re.setCar_maker(rs.getString("car_maker"));
					b_re.setCar_name(rs.getString("car_name"));
					b_re.setCar_photo(rs.getString("car_photo"));
					b_re.setB_re_title(rs.getString("b_re_title"));
					b_re.setB_re_content(rs.getString("b_re_content"));
					b_re.setB_re_reg(rs.getString("b_re_reg"));
					
					list.add(b_re);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	//구매 후기 게시판 총 글의 개수, 검색 개수
	public int getB_reCount2(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE b_re_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE car_maker LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT COUNT(*) FROM b_re r "
				+ "JOIN buy b ON r.buy_num = b.buy_num "
				+ "JOIN member m ON b.mem_num = m.mem_num "
				+ "JOIN car c ON b.car_num = c.car_num " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
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
	//구매 후기 게시판 글 목록, 검색 글 목록
	public List<B_ReVo> getListB_re2(int startRow, int endRow,String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		List<B_ReVo> list = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE b_re_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE car_maker LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM"
					+ "(SELECT * FROM b_re r "
			        + "JOIN buy b ON r.buy_num = b.buy_num "
			        + "JOIN car c ON b.car_num = c.car_num "
			        + "JOIN member m ON b.mem_num = m.mem_num "+sub_sql
			        + " ORDER BY b_re_num DESC)a) "
			        + "WHERE rnum >= ? AND rnum <= ?";

			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, startRow);
			pstmt.setInt(++cnt, endRow);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<B_ReVo>();
			while(rs.next()) {
				B_ReVo b_re = new B_ReVo();
				b_re.setB_re_num(rs.getInt("b_re_num"));
				b_re.setBuy_num(rs.getInt("buy_num"));
				b_re.setCar_num(rs.getInt("car_num"));
				b_re.setCar_maker(rs.getString("car_maker"));
				b_re.setCar_name(rs.getString("car_name"));
				b_re.setCar_photo(rs.getString("car_photo"));
				b_re.setB_re_title(rs.getString("b_re_title"));
				b_re.setB_re_content(rs.getString("b_re_content"));
				b_re.setB_re_reg(rs.getString("b_re_reg"));
				b_re.setMem_id(rs.getString("mem_id"));
				
				list.add(b_re);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//구매 후기 게시판 글 상세
	public B_ReVo getB_Re(int b_re_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		B_ReVo b_re = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM b_re r "
				+ "JOIN buy b ON r.buy_num = b.buy_num "
				+ "JOIN member m ON b.mem_num = m.mem_num "
				+ "JOIN member_detail d ON m.mem_num = d.mem_num "
				+ "JOIN car c ON b.car_num = c.car_num "
				+ "JOIN checker k ON c.checker_num = k.checker_num "
				+ "WHERE b_re_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, b_re_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				b_re = new B_ReVo();
				b_re.setMem_num(rs.getInt("mem_num"));
				b_re.setMem_id(rs.getString("mem_id"));
				b_re.setMem_photo(rs.getString("mem_photo"));
				b_re.setB_re_num(rs.getInt("b_re_num"));
				b_re.setB_re_title(rs.getString("b_re_title"));
				b_re.setB_re_reg(rs.getString("b_re_reg"));
				b_re.setB_re_modify(rs.getString("b_re_modify"));
				b_re.setCar_photo(rs.getString("car_photo"));
				b_re.setCar_mile(rs.getInt("car_mile"));
				b_re.setCar_cnumber(rs.getString("car_cnumber"));
				b_re.setCar_maker(rs.getString("car_maker"));
				b_re.setCar_name(rs.getString("car_name"));
				b_re.setB_re_photo(rs.getString("b_re_photo"));
				b_re.setB_re_content(rs.getString("b_re_content"));
				b_re.setChecker_photo(rs.getString("checker_photo"));
				b_re.setChecker_name(rs.getString("checker_name"));
				b_re.setCar_price(rs.getInt("car_price"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return b_re;
	}
	//파일 삭제
	public void deleteFile(int b_re_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
	        conn = DBUtil.getConnection();
	        //SQL문 작성 (파일 삭제)
	        sql = "UPDATE b_re SET b_re_photo='' WHERE b_re_num=?";
	        //PreparedStatment 객체 생성
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(++cnt, b_re_num);
	        
	        pstmt.executeUpdate();
	        
	     }catch(Exception e) {
	        throw new Exception(e);
	     }finally {
	        DBUtil.executeClose(null, pstmt, conn);
	     }
	}
	
	//구매 후기 게시판 글 수정
	public void updateB_Re(B_ReVo b_re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
         //커넥션풀로부터 커넥션 할당
         conn = DBUtil.getConnection();
         
         if(b_re.getB_re_photo()!=null && !"".equals(b_re.getB_re_photo())) {
        	 sub_sql += ",b_re_photo=?";
         }
         //SQL문 작성
         sql = "UPDATE b_re SET buy_num=?,b_re_title=?,b_re_content=?,b_re_modify=SYSDATE"+sub_sql+" WHERE b_re_num=?";
         
         //PreparedStatment 객체 생성
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(++cnt, b_re.getBuy_num());
         pstmt.setString(++cnt, b_re.getB_re_title());
         pstmt.setString(++cnt, b_re.getB_re_content());
         if(b_re.getB_re_photo()!=null && !"".equals(b_re.getB_re_photo())) {
        	 pstmt.setString(++cnt, b_re.getB_re_photo());
         }
         pstmt.setInt(++cnt, b_re.getB_re_num());
         
         pstmt.executeUpdate();
         
	     }catch(Exception e) {
	        throw new Exception(e);
	     }finally {
	        DBUtil.executeClose(null, pstmt, conn);
	     }
	}
	//구매 후기 게시판 글 삭제
	
}
