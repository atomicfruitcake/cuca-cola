package resourceData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import drivers.DriverFactory;

public class TestData {

    public static String RunNumberFilePath = "src/test/resources/RunNumber.properties";

    public String getCurrentRunNumber() {
        String count;
        try {
            if (!new File(RunNumberFilePath).exists())
                return "001";
            else {
                BufferedReader br = new BufferedReader(new FileReader(new File(RunNumberFilePath)));
                count = br.readLine();
                br.close();
            }
        } catch (Exception e) {
            count = null;
            e.printStackTrace();
        }
        return count;
    }

    public void createNewRunNumber() {
        String count = getCurrentRunNumber();
        int counter = Integer.parseInt(count.substring(4));
        counter++;
        count = count.substring(0, 4) + counter;
        putNewRunNumber(count);
        System.out.println("******** THIS IS RUN NUMBER:  " + count + " ********");
    }

    public void putNewRunNumber(String count) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(RunNumberFilePath)));
            bw.write(count);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String file, String property) throws Exception {
        String value;
        switch (file) {
            case ("userProp"):
                value = DriverFactory.userProp.getProperty(property);
                break;

            case ("staticData"):
                value = DriverFactory.staticDataProp.getProperty(property);
                break;
            default:
                value = DriverFactory.staticDataProp.getProperty(property);
        }

        if (value == null)
            throw new Exception("Property not found");
        else return value;
    }
}