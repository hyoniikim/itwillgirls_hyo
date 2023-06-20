package kr.co.itwill.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // 현재 클래스를 스프링에서 관리하는 dao bean으로 등록
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	SqlSession sqlSession; // mybatis 실행 객체

	// 회원가입
	@Override
	public void memberJoin(MemberDTO dto) {
	}// memberJoin() end

	// 아이디 중복확인
	@Override
	public int idCheck(String p_id) {
		return 0;
	}// idCheck() end

	// 회원탈퇴
	@Override
	public void memberDelete(MemberDTO dto) throws Exception {
		sqlSession.delete("member.memberDelete", dto);
	}// memberDelete() end

	// 회원정보 수정
	@Override
	public void memberUpdate(MemberDTO dto) throws Exception {
		sqlSession.update("member.memberUpdate", dto);
	}//memberUpdate() end

}// class end
