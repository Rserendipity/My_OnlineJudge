package compile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommandExec {
    public static int run(String cmd, String stdoutPath, String stderrPath) throws InterruptedException {
        /*
         * 得到Runtime实例, 执行 exec
         * 获取标准输出并写入文件
         * 获取标准错误并写入文件
         * 等待子进程结束, 拿到状态码
         * */
        try {
            // 获取进程实例并执行command
            Process process = Runtime.getRuntime().exec(cmd);

            // 将标准输出写入到文件
            InputStream getStdout = process.getInputStream();
            WriteStdToFile(stdoutPath, getStdout);
            getStdout.close();

            // 将标准错误写入到文件
            InputStream getStderr = process.getErrorStream();
            WriteStdToFile(stderrPath, getStderr);
            getStderr.close();

            // 等待进程结束, 拿状态码
            return process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 内部错误, 理论来说, 走不到这一行
        return -1;
    }

    private static void WriteStdToFile(String path, InputStream file) throws IOException {
        if (path != null) {
            FileOutputStream writer = new FileOutputStream(path);
            while (true) {
                int ret = file.read();
                if (ret == -1) {
                    break;
                }
                writer.write(ret);
            }
            writer.close();
        }
    }


//  for test
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println(run("g++ --version", "./stdout.txt", "./stderr.txt"));
//    }
}
