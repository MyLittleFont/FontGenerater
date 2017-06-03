package kr.ac.kaist.team888;

import com.google.gson.JsonParser;
import fontastic.FPoint;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Network {
    ServerSocket serverSocket;
    JsonParser jsonParser;
    Generator generator;
    int OFFSET = 2;

    public Network(int port) {
        try {
            System.err.println("Server listening on " + port);
            serverSocket = new ServerSocket(port);
            jsonParser = new JsonParser();
            generator = new Generator();

            while (true) {
                try {
                    // Synchronous Accept
                    Socket socket = serverSocket.accept();
                    System.err.println(socket.getInetAddress().toString() + " is connected");

                    BufferedReader networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    ArrayList<GlyphData> glyphDatas = new ArrayList<>();
                    String fontName = "";

                    String readline;
                    while ((readline = networkReader.readLine()) != null) {
                        if (readline.contains("$$")) {
                            if (readline.trim().length() > 2) {
                                fontName = readline.replace("$", "");
                                continue;
                            } else {
                                break;
                            }
                        }

                        GlyphData glyphData = new GlyphData();

                        readline = readline.trim();
                        if (readline.trim().isEmpty()) {
                            continue;
                        }

                        String[] datas = readline.split(";");
//                        System.out.println(Arrays.toString(datas));

                        char key = datas[0].trim().charAt(0);
                        int width = (int)Double.parseDouble(datas[1].trim());

                        glyphData.letter = key;
                        glyphData.width = width;

                        for (int i = OFFSET; i < datas.length; i++) {
                            String contours = datas[i];
                            String[] contourData = contours.trim().split(",");
                            FPoint[] contour = new FPoint[contourData.length];

                            for (int j = 0; j < contourData.length; j++) {
                                String[] points = contourData[j].trim().split(" ");

                                if (points.length == 2) {
                                    contour[j] = new FPoint(
                                            Double.parseDouble(points[0]),
                                            Double.parseDouble(points[1]));
                                } else if (points.length == 4) {
                                    contour[j] = new FPoint(
                                            new FPoint(Double.parseDouble(points[0]),
                                                    Double.parseDouble(points[1])),
                                            new FPoint(Double.parseDouble(points[2]),
                                                    Double.parseDouble(points[3])));
                                }
                            }

                            glyphData.contours.add(contour);
                        }

                        glyphDatas.add(glyphData);
                    }

                    File fontFile = generator.generate(glyphDatas, fontName);
                    fontFile.setReadable(true);

                    FileInputStream fileInputStream = new FileInputStream(fontFile);
                    OutputStream outputStream = socket.getOutputStream();

                    byte[] lengthBuffer = ByteBuffer.allocate(8).putLong(fontFile.length()).array();
                    outputStream.write(lengthBuffer);

                    byte[] bytes = new byte[1024];
                    int readbyte;
                    while ((readbyte = fileInputStream.read(bytes)) > 0) {
                        outputStream.write(bytes, 0, readbyte);
                    }

                    networkReader.close();
                    fileInputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
