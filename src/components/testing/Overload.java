package components.testing;

import components.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * DESCRIPTION
 * This class adds the components to a hashmap based on a file, and asks for user input to provide commands
 * @author Shane Doherty
 */
public class Overload {
    HashMap<String, Component> houseMap = new HashMap<>();
    String displayVar = " ";

    public void overload(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] splitArray = line.strip().split(" ");
            if (splitArray[0].equals("PowerSource")) {
                PowerSource powerSource = new PowerSource(splitArray[1]);
                if (!houseMap.containsKey(splitArray[1])) {
                    houseMap.put(splitArray[1], powerSource);
                    displayVar = splitArray[1];
                }
            }
            if (splitArray[0].equals("CircuitBreaker")) {
                if (houseMap.containsKey(splitArray[2])) {
                    CircuitBreaker circuitBreaker = new CircuitBreaker(splitArray[1], houseMap.get(splitArray[2]), Integer.parseInt(splitArray[3]));
                    houseMap.put(splitArray[1], circuitBreaker);
                } else {
                    Reporter.usageError(4, "The source component was never found");
                }

            } else if (splitArray[0].equals("Outlet")) {
                if (houseMap.containsKey(splitArray[2])) {
                    Outlet outlet = new Outlet(splitArray[1], houseMap.get(splitArray[2]));
                    houseMap.put(splitArray[1], outlet);
                } else {
                    Reporter.usageError(4, "The source component was never found");
                }
            } else if (splitArray[0].equals("Appliance")) {
                if (houseMap.containsKey(splitArray[2])) {
                    Appliance appliance = new Appliance(splitArray[1], houseMap.get(splitArray[2]), Integer.parseInt(splitArray[3]));
                    houseMap.put(splitArray[1], appliance);
                } else {
                    Reporter.usageError(4, "The source component was never found");
                }

            }
        }
        System.out.println("Please enter a command, along with the associated component! Enter 'quit' to end.");
        Scanner cm = new Scanner(System.in);
        boolean isQuit = false;
        while (!isQuit) {
            String line = cm.nextLine();
            String[] splitArray = line.split(" ");
            if (splitArray[0].equals("toggle")) {
                Component actedComp = houseMap.get(splitArray[1]);
                if (actedComp.isOn && actedComp instanceof Switchable) {
                    Switchable newCompOff = (Switchable) actedComp;
                    newCompOff.turnOff();
                }
                else if (!actedComp.isOn && actedComp instanceof Switchable) {
                    Switchable newCompOn = (Switchable) actedComp;
                    newCompOn.turnOn();
                } else {
                    Reporter.usageError(8, "The component is not switchable");
                }


            } else if (splitArray[0].equals("connect")){
                Component actedComp = houseMap.get(splitArray[3]);
                Appliance appliance = new Appliance(splitArray[2], actedComp, Integer.parseInt(splitArray[4]));
                houseMap.put(splitArray[2], appliance);
            } else if (splitArray[0].equals("display")){
                Component displayElement = houseMap.get(displayVar);
                displayElement.display();
            } else if (splitArray[0].equals("quit")){
                isQuit = true;
            }

        }

    }

    public static final int BAD_ARGS = 1;
    public static final int FILE_NOT_FOUND = 2;
    public static final int BAD_FILE_FORMAT = 3;
    public static final int UNKNOWN_COMPONENT = 4;
    public static final int REPEAT_NAME = 5;
    public static final int UNKNOWN_COMPONENT_TYPE = 6;
    public static final int UNKNOWN_USER_COMMAND = 7;
    public static final int UNSWITCHABLE_COMPONENT = 8;
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String[] NO_STRINGS = new String[ 0 ];
    private static final String PROMPT = "? ";

    static {
        Reporter.addError(
                BAD_ARGS, "Usage: java components.Overload <configFile>" );
        Reporter.addError( FILE_NOT_FOUND, "Config file not found" );
        Reporter.addError( BAD_FILE_FORMAT, "Error in config file" );
        Reporter.addError(
                UNKNOWN_COMPONENT,
                "Reference to unknown component in config file"
        );
        Reporter.addError(
                REPEAT_NAME,
                "Component name repeated in config file"
        );
        Reporter.addError(
                UNKNOWN_COMPONENT_TYPE,
                "Reference to unknown type of component in config file"
        );
        Reporter.addError(
                UNKNOWN_USER_COMMAND,
                "Unknown user command"
        );
    }

    /**
     *This main method creates a new overload type, and runs the entire program
     */
    public static void main( String[] args ) throws FileNotFoundException {
        System.out.println( "Overload Project, CS2" );
        Overload myLoad = new Overload();
        String theFile = args[0];
        myLoad.overload(theFile);



    }

}
