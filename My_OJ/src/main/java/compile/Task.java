package compile;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.util.UUID;

/*
 * 每次提交的代码,都是一个任务(task)
 * 这个模块负责编译以及运行, 根据 question里的code进行编译
 * 返回一个结果集, 结果集包含了编译成功与否, 运行成功与否
 * */
public class Task {

    // 临时文件存放路径
    private final String DIR;
    // 类名和文件名
    private final String CLASS_NAME;
    private final String CLASS_FILE;
    // 编译错误
    private final String COMPILE_ERROR;
    // 运行的标准输出和标准错误
    private final String STD_OUT;
    private final String STD_ERR;

    public Task() {
        DIR = "./tmp/" + UUID.randomUUID() + "/";
        CLASS_NAME = "Solution";
        CLASS_FILE = DIR + "Solution.java";
        COMPILE_ERROR = DIR + "compileError.txt";
        STD_OUT = DIR + "stdout.txt";
        STD_ERR = DIR + "stderr.txt";
    }

    public TaskResult compileAndRun(Question question) {
        TaskResult taskResult = new TaskResult();
        // 0. 如果tmp目录不存在, 要创建一个
        File tmpDir = new File(DIR);
        if (!tmpDir.exists()) {
            if (!tmpDir.mkdirs())
                System.out.println("创建文件夹失败");
        }
        // 1. 把question的内容写入到Solution.java里
        ReaderAndWriter.writeFile(CLASS_FILE, question.getCode());

        // 2. 使用Javac编译
        // String compileCmd = String.format("g++ -std=c++11 %s -o %s", CLASS_FILE, DIR + CLASS_NAME);
        String compileCmd = String.format("javac -encoding utf8 %s -d %s", CLASS_FILE, DIR);
        int compileCode = 0;
        try {
            compileCode = CommandExec.run(compileCmd, null, COMPILE_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 运行失败 或者 编译的错误信息 不为空
        if (compileCode != 0 || !ReaderAndWriter.readFile(COMPILE_ERROR).equals("")) { // code不为零,说明出错了
            // 设置标志 --> COMPILE_ERROR, 编译错误
            taskResult.setErrorFlag(TaskResult.ErrorFlag.COMPILE_ERROR);
            // 设置错误信息 --> COMPILE_ERROR, 编译出错
            taskResult.setMessage(ReaderAndWriter.readFile(COMPILE_ERROR));
            return taskResult;
        }

        // 3. 使用Java运行
        // String runCmd = String.format("%s", DIR + CLASS_NAME);
        String runCmd = String.format("java -classpath %s %s", DIR, CLASS_NAME);
        int runCode = 0;
        try {
            runCode = CommandExec.run(runCmd, STD_OUT, STD_ERR);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 运行失败 或者 std_err不为空
        if (runCode != 0 || !ReaderAndWriter.readFile(STD_ERR).equals("")) { // code不为零,说明出错了
            // 设置标志 --> runner_error 运行错误
            taskResult.setErrorFlag(TaskResult.ErrorFlag.RUNNER_ERROR);
            // 设置错误信息 --> stderr 错误信息
            taskResult.setMessage(ReaderAndWriter.readFile(STD_ERR));
            return taskResult;
        }

        // 4. 执行完毕, 编译和运行都没有错误, 把程序执行的结果返回(stdout的内容)
        taskResult.setErrorFlag(TaskResult.ErrorFlag.OK);
        taskResult.setStdout(ReaderAndWriter.readFile(STD_OUT));
        return taskResult;
    }

    public static void main(String[] args) throws FilerException, InterruptedException {
        Question question = new Question();
        question.setCode("public class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.print(\"hello world\");\n" +
                "        // System.err.print(\"this is a error message\");\n" +
                "    }\n" +
                "}");
        Task task = new Task();
        TaskResult taskResult = task.compileAndRun(question);
        System.out.println(taskResult);
    }
}
