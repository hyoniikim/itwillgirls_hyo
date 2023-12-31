package kr.co.itwill.orderform;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderformDAO {

	public OrderformDAO() {
		System.out.println("-----OrderformDAO()객체 생성됨");
	}
	
	@Autowired
	private JdbcTemplate jt;
	
	StringBuilder sql = null;
	
	//orderform에 필요한 정보 등록
	public int orderInsert(String p_id, int order_cnt, int tot_price) {
		int cnt = 0;
		
		try {
			sql = new StringBuilder();
			sql.append(" INSERT INTO orderform (order_no, tot_price, order_cnt, p_id) ");
			sql.append(" 						SELECT CONCAT(YEAR(NOW()), DATE_FORMAT(NOW(), '%m%d%H%i%s'), ");
			sql.append(" 						'-', LPAD((SELECT COUNT(*) + 1 FROM orderform), 3, '0')), " + tot_price + ", " + order_cnt + " , '" + p_id + "' ");			
			
			cnt = jt.update(sql.toString(), p_id, order_cnt, tot_price);
		}catch(Exception e) {
			System.out.println("orderform 행 삽입 실패 :" + e);
		}
		
		return cnt;
	}//orderInsert() end
	
	//orderform.jsp에 정보 띄우기 --> read(p_id=?인 행 조회)
	public OrderformDTO orderRead(String p_id){
		OrderformDTO dto = null;
		
		try {
			sql = new StringBuilder();
			sql.append(" SELECT order_no, p_id, order_cnt, tot_price, payc");
			sql.append(" FROM orderform ");
			sql.append(" WHERE p_id = '" + p_id + "' AND payc='N'");
			
			RowMapper<OrderformDTO> rowMapper = new RowMapper<OrderformDTO>() {
				@Override
				public OrderformDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					OrderformDTO dto = new OrderformDTO();
					
					dto.setOrder_no(rs.getString("order_no"));
					dto.setP_id(rs.getString("p_id"));
					dto.setOrder_cnt(rs.getInt("order_cnt"));
					dto.setTot_price(rs.getInt("tot_price"));
					dto.setPayc(rs.getString("payc"));
					
					return dto;					
				}//mapRow() end
			};//rowMapper end
			
			dto = jt.queryForObject(sql.toString(), rowMapper);
		}catch(Exception e) {
			System.out.println("orderForm에서 상세조회 실패 : " + e);
		}
		
		return dto;
	}//orderRead() end
	
	//p_id=? 인 order_no 가져오기
	public String getorderno(String p_id) {
		String order_no = null;
		
		try {
			sql = new StringBuilder();
			sql.append(" SELECT order_no ");
			sql.append(" FROM orderform ");
			sql.append(" WHERE p_id = '" + p_id + "' AND payc='N'");
			
			order_no = jt.queryForObject(sql.toString(), new Object[]{p_id}, String.class);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 order_no 조회 실패 : " + e);
		}
		
		return order_no;
	}//getorderno() end
	
	//장바구니 삭제(결제 후)
	public int delete(String order_no) {
		int cnt = 0;
		
		try {
			sql = new StringBuilder();
			sql.append(" DELETE FROM cart ");
			sql.append(" WHERE order_no = ? ");
			
			cnt = jt.update(sql.toString(), order_no);
			
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 결제 후 장바구니 삭제 실패 : " + e);
		}
		return cnt;
	}//delete() end
	
	//orderform의 결제상태 'Y'로 업데이트
	public int update(String order_no) {
		int cnt = 0;
		
		try {
			sql = new StringBuilder();
			sql.append(" UPDATE orderform ");
			sql.append(" SET payc = 'Y' ");
			sql.append(" WHERE order_no = ? ");
			
			cnt = jt.update(sql.toString(), order_no);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 결제상태 수정 실패 : " + e);
		}
		
		return cnt;
	}//update() end
	
	//program의 pro_obj읽는 함수
	public String pobjread(String pro_name) {
		String pro_obj = null;
		
		try {
			sql = new StringBuilder();
			sql.append(" SELECT pro_obj ");
			sql.append(" FROM program_info ");
			sql.append(" WHERE pro_name = '" + pro_name +"' ");
			
			pro_obj = jt.queryForObject(sql.toString(), new Object[]{pro_name}, String.class);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 pro_obj 조회 실패 : " + e);
		}
		return pro_obj;
	}//pobjeread() end
	
	//order_prodetail에 행 추가
	public int prodetail(int cart_price, String order_no, String pro_obj, int cart_cnt, String pro_name, String p_id) {
		int cnt = 0;
		
		try {
			sql = new StringBuilder();
			sql.append(" INSERT INTO order_prodetail (order_prodetailno, pro_fee, order_no, pro_obj, pro_cnt, pro_name, p_id) ");
			sql.append(" VALUES (CONCAT(DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')), ?, ?, ?, ?, ?, ?) ");
			
			cnt = jt.update(sql.toString(), cart_price, order_no, pro_obj, cart_cnt, pro_name, p_id);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 prodetail행 삽입 실패 : " + e);
		}
		
		return cnt;
	}//prodetail() end
	
	//performance의 p_code읽는 함수
	public String pcoderead(String per_name) {
		String per_code = null;
		
		try {
			sql = new StringBuilder();
			sql.append(" SELECT per_code ");
			sql.append(" FROM performance ");
			sql.append(" WHERE per_name = '" + per_name + "' ");
			
			per_code = jt.queryForObject(sql.toString(), new Object[]{per_name}, String.class);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 p_code 조회 실패 : " + e);
		}
		
		return per_code;
	}//pcoderead() end
	
	//order_perdetail에 행 추가
	public int perdetail(int cart_price, String order_no, String per_code, String seat_no, int cart_cnt, String per_name, String p_id) {
		int cnt = 0;
		
		try {
			sql = new StringBuilder();
			sql.append(" INSERT INTO order_perdetail (order_perdetailno, per_fee, order_no, per_code, seat_no, per_cnt, per_name, p_id) ");
			sql.append(" VALUES (CONCAT(DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')), ?, ?, ?, ?, ?, ?, ?) ");
			
			cnt = jt.update(sql.toString(), cart_price, order_no, per_code, seat_no, cart_cnt, per_name, p_id);
		}catch(Exception e) {
			System.out.println("OrderformDAO에서 perdetail 행 삽입 실패 : " + e);
		}
		
		return cnt;
	}//perdetail() end
}//Class end
