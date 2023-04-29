import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGames(String fileName, GameProgress gProgress) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream out1 = new ObjectOutputStream(new BufferedOutputStream
                (fileOutputStream));
        out1.writeObject(gProgress);
        out1.close();
    }

    public static void zipFiles(String zipName, String[] fileMassiv) throws IOException {
        File f = new File(zipName);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        File file11;

        for (int i = 0; i < 3; i++) {
            file11 = new File(fileMassiv[i]);
            ZipEntry e = new ZipEntry(file11.getName());
            out.putNextEntry(e);
            byte[] data = Files.readAllBytes(file11.toPath());
            out.write(data, 0, data.length);
            out.closeEntry();
        }
        out.close();
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    public static void openZip(String zipName, String zipNameTarget) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                File file = new File(zipNameTarget, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                        int bufferSize = Math.toIntExact(entry.getSize());
                        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                        int location;
                        while ((location = zis.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                        }
                    }
                }
                entry = zis.getNextEntry();
            }
        }
    }

    public static GameProgress openProgress(String pathFileSaveGames) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(pathFileSaveGames);
        ObjectInputStream ois = new ObjectInputStream(fileIn);
        GameProgress gP = (GameProgress) ois.readObject();
        return gP;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GameProgress g1 = new GameProgress(1, 1, 1, 1.0);
        GameProgress g2 = new GameProgress(2, 2, 2, 2.0);
        GameProgress g3 = new GameProgress(3, 3, 3, 3.0);


        System.out.println(g2.toString());

        saveGames("/D:/Games/savegames/save1.dat", g1);
        saveGames("/D:/Games/savegames/save2.dat", g2);
        saveGames("/D:/Games/savegames/save3.dat", g3);


        String[] massiv = {"/D:/Games/savegames/save1.dat", "/D:/Games/savegames/save2.dat",
                "/D:/Games/savegames/save3.dat"};

        zipFiles("/D:/Games/savegames/my.zip", massiv);
        deleteFile("/D:/Games/savegames/save1.dat");
        deleteFile("/D:/Games/savegames/save2.dat");
        deleteFile("/D:/Games/savegames/save3.dat");

        openZip("/D:/Games/savegames/my.zip", "/D:/Games/savegames");
        GameProgress g4;
        g4 = openProgress("/D:/Games/savegames/save2.dat");
        System.out.println(g4.toString());

    }
}