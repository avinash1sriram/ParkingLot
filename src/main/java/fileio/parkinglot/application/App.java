package fileio.parkinglot.application;

import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.processor.RequestProcessor;
import fileio.parkinglot.service.impl.ParkingServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        //args[0] - will be given in the command line. The args[0] absolute path of the file
        File inputFile = new File("/Users/jumbotail/WorkSpace/ParkingLot/src/main/resources/input.txt");

        RequestProcessor requestProcessor = new RequestProcessor(new ParkingServiceImpl());

        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile));
            String input;

            //Reading the lines in the input file one by one
            while ((input = bufferReader.readLine()) != null) {

                try {
                    input = input.trim();
                    requestProcessor.validate(input);
                    System.out.println(requestProcessor.execute(input));

                } catch (ParkingLotException ex) {

                    System.out.println("Exception: " + ex.getMessage());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid File");
        }

    }

}
