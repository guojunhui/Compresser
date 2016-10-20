

import com.yahoo.platform.yui.compressor.YUICompressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File root = new File("D:\\workspace\\RiskDevSrc\\marketMakerProject\\src\\main\\webapp\\WEB-INF\\views\\pages");
        loopAllFiles(root);
    }
    

    static void loopAllFiles(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                loopAllFiles(files[i]);
            }
            else {
                if(files[i].getName().matches(".+(?<!min)\\.js"))
                {
                    String out = files[i].getAbsolutePath().replaceAll("\\.(?=js)", ".gen.min.");
                    File outFile = new File(out);
                    if(outFile.exists()) {
                        outFile.delete();
                        outFile.createNewFile();
                    }
                    else {
                        outFile.createNewFile();
                    }
                    String[] args = new String[6];
                    args[0] = files[i].getAbsolutePath();
                    args[1] = "-o";
                    args[2] = outFile.getAbsolutePath();
                    args[3] = "--charset";
                    args[4] = "utf-8";
                    args[5] = "--nomunge";
//                    args[6] = "--disable-optimizations";
                    try {
                        YUICompressor.main(args);
                    }catch (Exception e){
//                        e.printStackTrace();
                        System.out.println(files[i].getAbsoluteFile());
                    }
                }
                else if(files[i].getName().matches(".+(?<!min)\\.css")){
                    String out = files[i].getAbsolutePath().replaceAll("\\.(?=css)", ".min.");
                    File outFile = new File(out);
                    if(outFile.exists()) {
                        outFile.delete();
                        outFile.createNewFile();
                    }
                    else {
                        outFile.createNewFile();
                    }
//                    System.out.println(outFile.getAbsolutePath());
                    String[] args = new String[5];
                    args[0] = files[i].getAbsolutePath();
                    args[1] = "-o";
                    args[2] = outFile.getAbsolutePath();
                    args[3] = "--charset";
                    args[4] = "utf-8";
                    try {
                        YUICompressor.main(args);
                    }catch (Exception e){
                        System.out.println(files[i].getAbsoluteFile());
//                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
