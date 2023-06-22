package compile;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReaderAndWriter {
    public static String readFile(String file) {
        StringBuilder buffer = new StringBuilder();
        try(FileReader fileReader = new FileReader(file)) {
            while (true) {
                int ret = fileReader.read();
                if (ret == -1) {
                    break;
                }
                buffer.append((char)ret);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static void writeFile(String file, String info) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        ReaderAndWriter.writeFile("./stdout.txt", "world");
//        System.out.println(ReaderAndWriter.readFile("./stdout.txt"));
//    }
}
