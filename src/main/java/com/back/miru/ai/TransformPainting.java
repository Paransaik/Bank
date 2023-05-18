package com.back.miru.ai;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TransformPainting {

    public static void main(String[] args) throws Exception {
        System.out.println(transform("0", "purple.jpg", "cat.mp4"));
    }

    public static String transform(String optionNum, String styleFilePath, String contentFilePath) throws Exception {
        int on = Integer.parseInt(optionNum);
        Map<Integer, String> m = new HashMap<>();
        m.put(1, "candy");
        m.put(2, "composition_vii");
        m.put(3, "escher_sphere");
        m.put(4, "feathers");
        m.put(5, "frida_kahlo");
        m.put(6, "la_muse");
        m.put(7, "mosaic");
        m.put(8, "mosaic_ducks_massimo");
        m.put(9, "pencil");
        m.put(10, "picasso");
        m.put(11, "portrait");
        m.put(12, "rain_princess");
        m.put(13, "seated_nude");
        m.put(14, "shipwreck");
        m.put(15, "starry_night");
        m.put(16, "stars");
        m.put(17, "strip");
        m.put(18, "the_scream");
        m.put(19, "udnie");
        m.put(20, "wave");
        m.put(21, "woman");

//        python main.py optim --content-image t.jpg --style-image m.jpeg --cuda=0
//        python main.py eval --content-image t.jpg --style-image m.jpeg --model models/21styles.model --content-size 1024 --cuda=0
        String root = "src/main/java/com/back/miru/ai/";
        String[] command = new String[12];
        command[0] = "python";
        command[1] = root + "main.py";
        command[2] = "eval";
        command[3] = "--content-image";
        command[4] = root + contentFilePath;
        command[5] = "--style-image";

        if (on == 0) {
            command[6] = root + styleFilePath;
        } else {
            command[6] = root + "21styles/" + m.get(on) + ".jpg";
        }

        command[7] = "--model";
        command[8] = root + "models/21styles.model";
        command[9] = "--content-size";
        command[10] = "1024";
        command[11] = "--cuda=0";

        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        System.out.println(commandLine);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        System.out.println("output: " + outputStream);
        return "src/output.jpg";
    }
}
