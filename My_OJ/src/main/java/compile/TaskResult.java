package compile;

/*
 * 编译和运行以后的结果集
 * */
public class TaskResult {
    public enum ErrorFlag {
        OK,             // 没有错误
        COMPILE_ERROR,  // 编译错误
        RUNNER_ERROR,   // 运行终止错误(异常终止等)
        UNSAFE_CODE,    // 提交了非法代码
    }

    // 错误标志, 错误信息, 用户代码执行后的标准输出和标准错误
    private ErrorFlag errorFlag;
    private String message;
    private String stdout;

    @Override
    public String toString() {
        return "TaskResult{" +
                "errorFlag=" + this.getErrorFlag() +
                ", message='" + this.getMessage() + '\'' +
                ", stdout='" + this.getStdout() + '\'' +
                '}';
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }


    public ErrorFlag getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(ErrorFlag errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
