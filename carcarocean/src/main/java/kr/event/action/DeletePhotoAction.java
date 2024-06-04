package kr.event.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.event.dao.EventDao;
import kr.event.vo.EventVo;
import kr.util.FileUtil;

public class DeletePhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int event_num = Integer.parseInt(request.getParameter("event_num"));

		EventDao dao = EventDao.getDao();
		EventVo db_event = dao.detailEvent(event_num);

		//db_event에 담긴 event_photo가 NULL이 아닌 경우에만 실행
		if(db_event.getEvent_photo() != null) {
			dao.deletePhoto(event_num);
			//사진 삭제
			String[] photos = db_event.getEvent_photo().split(",");
			for(String pho : photos) {
				FileUtil.removeFile(request, pho);
			}
		}
		mapAjax.put("result", "success");

		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
