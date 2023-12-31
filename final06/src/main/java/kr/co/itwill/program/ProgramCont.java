package kr.co.itwill.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.itwill.like.LikeDTO;
import kr.co.itwill.member.MemberDTO;
import kr.co.itwill.protime.ProtimeDTO;
import kr.co.itwill.review.ReviewDTO;
import kr.co.itwill.service.PerformanceService;
import kr.co.itwill.service.ProgramService;
import kr.co.itwill.teacher.TeacherDTO;
import net.utility.UploadSaveManager;

@Controller
public class ProgramCont {

	@Autowired
	private ProgramDAO dao;

	@Autowired
	private ProgramService programservice;
	PerformanceService performanceservice;

	public ProgramCont() {
		System.out.println("-----ProgramCont()객체 생성됨");
	}

	// 목록
	@RequestMapping("/program/list.do")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("program/list");
		List<ProgramDTO> list = dao.list();
		mav.addObject("list", list);
		
		//각 프로그램의 찜 개수 가져오기
		List<Integer> likeCnts = new ArrayList<>();
		for(ProgramDTO program : list) {
			int likecnt = dao.likecnt(program.getPro_obj());
			likeCnts.add(likecnt);
		}//for end
		mav.addObject("likeCnts", likeCnts);

		return mav;
	}// list() end

	// 프로그램 등록
	@RequestMapping(value = "/program/create.do", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("program/createForm");

		return mav;
	}// createForm() end

	@RequestMapping(value = "/program/create.do", method = RequestMethod.POST)
	public ModelAndView createProc(@ModelAttribute ProgramDTO dto, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("program/msgView");

		MultipartFile pro_posterMF = dto.getPro_posterMF();
		// System.out.println(pro_posterMF);
		MultipartFile pro_imgMF = dto.getPro_imgMF();
		String pro_poster = pro_posterMF.getOriginalFilename();
		String pro_img = pro_imgMF.getOriginalFilename();
		dto.setPro_poster(pro_poster);
		dto.setPro_img(pro_img);
		
		//입력받은 obj_code로 pro_obj생성하기
		String obj_code = req.getParameter("obj_code");
		//System.out.println(obj_code);
		
		//prefix라는 문자열에 라디오버튼이 해당되는 문자값 대입
		String prefix;
		if (obj_code.equals("A")) {
		    prefix = "A";
		} else if (obj_code.equals("S")) {
		    prefix = "S";
		} else if (obj_code.equals("E")) {
		    prefix = "E";
		} else if (obj_code.equals("M")) {
		    prefix = "M";
		} else {
		    // 예외 처리: 잘못된 obj_code 값인 경우
		    throw new IllegalArgumentException("Invalid obj_code");
		}
		
		String pro_obj;
		//기존 데이터와 해당 prefix로 시작하는 코드 중 가장 큰 숫자를 찾아 다음 숫자 계산
		int nextNum = dao.getNextnum(prefix);
		pro_obj = prefix + String.format("%02d", nextNum); //pro_obj의 형식 (A01)
		
		
		int cnt = dao.create(dto, pro_obj);

		if (cnt == 0) {
			mav.setViewName("program/msgView");
			String msg1 = "<p>프로그램 등록 실패</p>";
			String link1 = "<input type='button' value='다시시도' onclick='javascript:history.back()'>";
			String link2 = "<input type='button' value='목록으로' onclick='location.href=\"list.do\"'>"; // 수정
			mav.addObject("msg1", msg1);
			mav.addObject("link1", link1);
			mav.addObject("link2", link2);
		} else {
			mav.setViewName("redirect:/program/list.do");
		} // if end

		return mav;
	}// createProc() end

	// 상세보기
	@RequestMapping("/program/read.do")
	public ModelAndView read(String pro_obj, @RequestParam(value = "p_id", required = false) String p_id,
			HttpSession session) {
		// @RequestParam(value = "p_id", required = false) String p_id : p_id 값이 null이어도
		// 상관 없음
		ModelAndView mav = new ModelAndView();
		ProgramDTO dto = dao.read(pro_obj);
		mav.setViewName("program/read");

		mav.addObject("dto", dto);

		////////////// ProramDAO에 선언한 ptread()함수 추가 (program_time에서 pro_obj=?인 행 조회)
		List<ProtimeDTO> ptlist = dao.ptlist(pro_obj);
		mav.addObject("ptlist", ptlist);
		//System.out.println(ptlist);
		
		//강사코드와 이름 목록 조회
		List<TeacherDTO> tlist = dao.tlist();
		mav.addObject("tlist", tlist);

		////////////// 조회수 증가하는 함수
		dao.incrementCnt(pro_obj);

		////////////// 로그인 한 p_id 가져오기
		Object obj = session.getAttribute("member_dto");
		MemberDTO memdto = (MemberDTO) obj;
		// System.out.println(p_id);
		// System.out.println(memdto);

		////////////// p_id가 null값(로그아웃)이어도 read페이지 볼 수 있게하기
		if (memdto != null) {
			p_id = memdto.getP_id();
		} else {
			// 로그인하지 않은 경우에 대한 처리 (p_id를 원하는 값으로 설정)
			p_id = "guest";
		}
		// System.out.println(p_id);
		////////////// ProgramDAO에 선언한 likeread()함수 추가(like_program에서 pro_obj=? and
		// p_id=?인 행 조회해서 개수 반환)
		int likedto = dao.likeread(pro_obj, p_id);
		mav.addObject("likedto", likedto);
		// System.out.println(likedto);

		////////////// ProgramDAO에 선언한 pro_obj=?의 likecnt()함수 추가 (해당 pro_obj의 찜 개수)
		int likecnt = dao.likecnt(pro_obj);
		mav.addObject("likecnt", likecnt);
		
		//pro_day의 값을 한글로 바꿔서 뷰단에 보여주기
		//1. dto.pro_day의 값을 한글로 변환하기
		String pro_day = kor_proday(dto.getPro_day());
		mav.addObject("pro_day", pro_day);
		
		
		//해당 pro_obj의 댓글리스트 불러오기
		List<ReviewDTO> rvlist = dao.rvlist(pro_obj);
		mav.addObject("rvlist", rvlist);

		return mav;
	}// read() end
	
	//dto.pro_day를 한글로 변환
	private String kor_proday(String pro_day) {
		Map<String, String> prodayMap = new HashMap<>();
		prodayMap.put("Tue", "화요일");
		prodayMap.put("Wed", "수요일");
		prodayMap.put("Thu", "목요일");
		prodayMap.put("Fri", "금요일");
		prodayMap.put("Sat", "토요일");
		prodayMap.put("Sun", "일요일");
		
		//쉼표 구분
		String[] days = pro_day.split(",");
		
		//각 요일을 한글로 변환하여 새로운 문자열로 조합
		StringBuilder kor_proday = new StringBuilder();
		
		for(String day : days) {
			String korday = prodayMap.getOrDefault(day.trim(), day);
			kor_proday.append(korday).append(", ");
		}
		
		//마지막 쉼표와 공백 제거
		if(kor_proday.length()>0) {
			kor_proday.delete(kor_proday.length()-2, kor_proday.length());
		}
		
		return kor_proday.toString();
	}//kor_proday() end

	// 수정
	@RequestMapping(value = "/program/update.do", method = RequestMethod.GET)
	public ModelAndView update(String pro_obj) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("program/updateForm");
		ProgramDTO dto = dao.read(pro_obj);
		mav.addObject("dto", dto);

		return mav;
	}// update() end

	@RequestMapping(value = "/program/update.do", method = RequestMethod.POST)
	public ModelAndView updateProc(@ModelAttribute ProgramDTO dto, HttpServletRequest req) {
		MultipartFile pro_posterMF = dto.getPro_posterMF();
		MultipartFile pro_imgMF = dto.getPro_imgMF();

		String pro_poster = pro_posterMF.getOriginalFilename();
		String pro_img = pro_imgMF.getOriginalFilename();

		dto.setPro_poster(pro_poster);
		dto.setPro_img(pro_img);

		ModelAndView mav = new ModelAndView();

		int cnt = dao.update(dto);

		if (cnt == 0) {
			mav.setViewName("program/msgView");
			String msg1 = "<p>수정실패</p>";
			String link1 = "<input type='button' value='다시시도' onclick='javascript:history.back()'>";
			String link2 = "<input type='button' value='목록으로' onclick='location.href=\"list.do\"'>";

			mav.addObject("msg1", msg1);
			mav.addObject("link1", link1);
			mav.addObject("link2", link2);
		} else {
			mav.setViewName("redirect:/program/list.do");
		} // if end

		return mav;
	}// updateProc() end
	
	//삭제
	@RequestMapping(value="/program/delete.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView deleteProc(String pro_obj) {
		ModelAndView mav = new ModelAndView();
		
		int cnt = dao.delete(pro_obj);
		
		if(cnt==0) {
			String msg1 = "<p>프로그램 삭제 실패</p>";
			String link1 = "<input type='button' value='다시시도' onclick='javascript:history.back()'>";
			String link2 = "<input type='button' value='목록으로' onclick='location.href=\"list.do\"'>";
			
			mav.addObject("msg1", msg1);
			mav.addObject("link1", link1);
			mav.addObject("link2", link2);
		}else {
			mav.setViewName("redirect:/program/list.do");
		}
		return mav;
	}//deleteProc() end
	

	// 여기서부터 추가합니다~
	// 메인 페이지 이동(메인 기본값: 최신순 보여주기라서 이 함수가 필요함)
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public void mainPageGET(Model model) throws Exception {
		model.addAttribute("current", programservice.currentSelect());
		// System.out.println(model);
	}// mainPageGET() end
	
	// 좋아요순 보여주기 페이지 이동
	@RequestMapping(value = "/likeSelect.do", method = RequestMethod.GET)
	public ModelAndView likeSelect() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("program/home_like");
		mav.addObject("like", dao.list2());
		return mav;
	}// likeSelect() end

	// 조회순 보여주기 페이지 이동
	@RequestMapping(value = "/clickSelect.do", method = RequestMethod.GET)
	public String clickSelect(Model model) {
		model.addAttribute("click", programservice.clickSelect());
		return "/program/home_click";
	}// clickSelect() end
	
	// 최신순 보여주기 페이지 이동
	@RequestMapping(value = "/currentSelect.do", method = RequestMethod.GET)
	public String currentSelect(Model model) {
		model.addAttribute("current", programservice.currentSelect());
		return "/program/home_current";
	}// currentSelect() end

}// class end
