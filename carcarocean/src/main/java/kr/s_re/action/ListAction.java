package kr.s_re.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.s_re.dao.S_ReDao;
import kr.s_re.vo.S_ReVo;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		S_ReDao dao = S_ReDao.getDao();
		int count = dao.getSellReviewCount(keyfield, keyword);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,
				Integer.parseInt(pageNum),
				count,20,10,"list.do");
		
		List<S_ReVo> list = null;
		if(count > 0) {
			list=dao.getListSellReview(page.getStartRow(),page.getEndRow(),
								keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/s_re/list.jsp";
	}
	

}
