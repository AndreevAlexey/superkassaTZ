package com.AlekseyAndreev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        File fileJson = new File("src/com/AlekseyAndreev/json.txt");
        getFullStrings(fileJson);
    }

    public static void getFullStrings(File jsonFile) {
        try(FileInputStream fis = new FileInputStream(jsonFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            /*
            ObjectMapper mapper = new ObjectMapper();
            String[][] str = mapper.readValue(br, String[][].class);
            Integer[] binaryStr = Arrays.stream(str).map(Main::convertToInteger).toArray(Integer[]::new);
            */

            List<String[]> values =
                    br.lines()
                            .map(line -> line.replaceAll("[\\W&&[^,]]", ""))
                            .filter(line -> !line.isEmpty())
                            .map(line -> line.split(","))
                            .toList();

            values.stream().map(x -> String.join(", ", x)).forEach(System.out::println);

            List<Integer> binaryVals =
                    values.stream()
                            .map(array -> Integer.valueOf(convertToBinaryString(array)))
                            .toList();

            int sum = Integer.parseInt(repeat('1', values.get(0).length));
            for(int i = 0; i < binaryVals.size(); i++) {
                for(int j = i + 1; j < binaryVals.size(); j++) {
                    if(binaryVals.get(i) + binaryVals.get(j) == sum) {
                        System.out.println("-----------------");
                        printResult(values.get(i), values.get(j));
                    }
                }
            }

        } catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    public static void printResult(String[] array1, String[] array2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array1.length; i++) {
            stringBuilder.append((array1[i].equals("null")) ? array2[i] : array1[i]).append(", ");
        }
        System.out.println(stringBuilder);
    }

    public static String repeat(char simbol, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, simbol);
        return new String(chars);
    }

    public static String convertToBinaryString(String[] array) {
        return
                Arrays.stream(array)
                        .map(x -> ("null".equals(x)) ? "0" : "1")
                        .collect(Collectors.joining());
    }
}
