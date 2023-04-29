import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static String path = "D:/Games";
    static String log = path + "/temp/temp.txt";
    static File lg = new File(log);
    static FileWriter lg2;
    static StringBuilder error = new StringBuilder();

    public Main() throws IOException {
    }

    public static void createFolder(String p) throws IOException {
        File file = new File(p);
        boolean file1 = file.mkdir();
        if (file1 == false) {
            error.append("Папка не была успешно создана " + p + "\n");
        } else {
            error.append("Папка была успешно создана " + p + "\n");
        }
    }

    public static void createFile(String p) throws IOException {
        File file = new File(p);
        boolean file1 = file.createNewFile();
        if (file1 == false) {
            error.append("Файл не был успешно создана " + p + "\n");
        } else {
            error.append("Файл был успешно создана " + p + "\n");
        }
    }

    public static void main(String[] args) throws IOException {


        createFolder(path);
        createFolder(path + "/src");
        createFolder(path + "/res");
        createFolder(path + "/savegames");
        createFolder(path + "/temp");
        createFolder(path + "/src/main");
        createFolder(path + "/src/test");
        createFolder(path + "/res/drawables");
        createFolder(path + "/res/vectors");
        createFolder(path + "/res/icons");

        createFile(path + "/src/main/Main.java");
        createFile(path + "/src/main/Utils.java");

        createFile(log);
        lg2 = new FileWriter(lg, true);
        lg2.append(error);
        lg2.close();
    }
}