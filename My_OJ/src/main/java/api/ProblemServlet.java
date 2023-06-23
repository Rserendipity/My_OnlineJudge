package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Problem;
import dao.ProblemDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/problem")
public class ProblemServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("application/json;charset=utf8");

        ProblemDAO problemDAO = new ProblemDAO();

        String ret = req.getParameter("id");
        if (ret == null || ret.equals("")) {
            // 没找到id, 所以是主界面
            List<Problem> problems = problemDAO.selectAll();
            String jsonObj = objectMapper.writeValueAsString(problems);
            resp.getWriter().write(jsonObj);
        } else {
            // 找到了id, 获取单个题目
            Problem problem = problemDAO.selectOne(Integer.parseInt(ret));
            String jsonObj = objectMapper.writeValueAsString(problem);
            resp.getWriter().write(jsonObj);
        }

    }
}
