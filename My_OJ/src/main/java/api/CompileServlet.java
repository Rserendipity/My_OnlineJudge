package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.ProblemNotFoundException;
import common.UnsafeCodeException;
import compile.Question;
import compile.Task;
import compile.TaskResult;
import dao.Problem;
import dao.ProblemDAO;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/compile")
public class CompileServlet extends HttpServlet {
    static class ProblemRequest {
        public int id;
        public String userCode;
    }

    static class CompileResponse {
        public int errorFlag;
        public String errReason;
        public String stdout;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("application/json;charset=utf8");

        CompileResponse compileResponse = new CompileResponse();
        try {
            // 1. 将通过网络传输过来的二进制文件解析成文本文件
            String body = readBody(req);

            // 2. 将文本文件进行json解析到ProblemRequest对象里
            ProblemRequest problemRequest = objectMapper.readValue(body, ProblemRequest.class);

            // 3. 查找数据库中对应题目的测试代码
            ProblemDAO problemDAO = new ProblemDAO();
            Problem problem = problemDAO.selectOne(problemRequest.id);
            if (problem == null) {
                throw new ProblemNotFoundException();
            }

            // 4. 把测试代码和用户提交过来的代码进行合并
            String mergedCode = mergeCode(problem.getCodeTest(), problemRequest.userCode);
            if (mergedCode == null) {
                throw new UnsafeCodeException();
            }

            // 测试请求的代码合并是否正确
            // System.out.println(mergedCode);

            // 5. 执行代码
            Question question = new Question();
            question.setCode(mergedCode);
            Task task = new Task();
            TaskResult taskResult = task.compileAndRun(question);
            // 6. 将执行写入对象
            compileResponse.errorFlag = 0;
            compileResponse.errReason = taskResult.getMessage();
            compileResponse.stdout = taskResult.getStdout();

        } catch (ProblemNotFoundException e) {
            compileResponse.errorFlag = 1;
            compileResponse.errReason = "题目未找到";
        } catch (UnsafeCodeException e) {
            compileResponse.errorFlag = 2;
            compileResponse.errReason = "代码非法";
        } finally {
            // 7. 把对象转成json-String
            String json = objectMapper.writeValueAsString(compileResponse);
            // 8. 返回响应
            resp.getWriter().write(json);
        }
    }

    private static String mergeCode(String codeTest, String userCode) {
        int index = userCode.lastIndexOf('}');
        if (index == -1) {
            return null;
        }
        return userCode.substring(0, index) + codeTest + "\n}";
    }

    private static String readBody(HttpServletRequest req) {
        int contentLength = req.getContentLength(); // 得到长度
        byte[] buffer = new byte[contentLength];    // 创建buffer
        try (ServletInputStream inputStream = req.getInputStream()) {
            int ret = inputStream.read(buffer);// 从stream读取到buffer
            if (ret == -1) {
                System.out.println("写文件失败");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer, StandardCharsets.UTF_8); // 把buffer中的二进制转换成文本文件
    }

}
