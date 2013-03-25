import java.util.Random;
import java.util.Scanner;

public class ControlTest {

	private static int status;

	public static void main(String args[]) {

		Scanner stdin = new Scanner(System.in);
		String reading = "";
		System.out.print("Enter the command (or exit to stop): ");
		status = 0;
		//Get the users input and make sure that it is valid
		while (!isNullOrEmpty((reading = stdin.nextLine()))) {
			if (reading.equalsIgnoreCase("exit")) {
				System.out.println("Aborting simulation");
				break;
			}

			/* TODO: Message will come as one continuous string (without spaces) when actually implemented
			 * Need to change it so that it splits it into an array of the correct number of parameters based on which opcode is entered
			 * In addition, need to add logic for sequence number and checksum 
			 */

			String[] params = reading.split("\\s+");
			if (params.length == 0) {
				System.out.println("No parameters found, input command again");
				System.out.print("Enter the command (or exit to stop): ");
				continue;
			}
			try {
				processInput(params);
			} catch (Exception e) {
				break;
			}
			System.out.print("Enter the command (or exit to stop): ");
		}
	}

	public static void processInput(String[] args) throws Exception {
		if (args.equals(null) || args.length == 0) {
			return;
		}
		String opcode = args[0];

		switch (opcode) {
		case ("0000"): //Move [direction][speed]
			if (verifyMove(args)) {
				System.out.println("Moving " + convertDirection(args[1])
						+ " at speed " + args[2]);
			} else {
				System.out
						.println("Invalid number of arguments entered, expected 3 but got "
								+ args.length);
			}
			break;
		case ("0001"): //MoveMotor [motorport][speed]
			if (verifyMotorMove(args)) {
				System.out.println("Moving motor on motorport " + args[1]
						+ " at speed " + args[2]);
			} else {
				System.out
						.println("Invalid number of arguments entered, expected 3 but got "
								+ args.length);
			}
			break;
		case ("0010"): //StopMovement
			if (args.length == 1) {
				System.out.println("Stopping all motors currently moving");
			} else {
				System.out
						.println("Invalid number of arguments entered, expected 1 but got "
								+ args.length);
			}
			break;
		case ("0011"): //Take reading [sensorport]
			if (args.length == 2) {
				System.out.println(generateSensorReading(args[1]));
			} else {
				System.out
						.println("Invalid number of arguments entered, expected 2 but got "
								+ args.length);
			}
			break;

		case ("0101"): //Emergency stop/resume
			if (status == 0) {
				System.out.println("Program has paused");
				status = 1;
			} else {
				System.out.println("Program has resumed");
				status = 0;
			}
			break;
		case ("0110"): //Abort
			System.out.println("Program has been aborted");
			throw new Exception();

		case ("1000"): //Execution Error
			System.out.println(generateExecutionError());
			break;

		default:
			System.out.println("Invalid string");
			break;
		}

	}

	public static String generateSensorReading(String sensorPort) {
		String message = "";
		Random generator = new Random();
		int random = generator.nextInt(101);
		message = "Found reading of " + random + " on sensor port "
				+ sensorPort;
		return message;
	}

	public static String generateExecutionError() {
		String message = "";
		Random generator = new Random();
		int random = generator.nextInt(31);
		message = "NXT produced error code " + random;
		return message;
	}

	public static boolean verifyMove(String args[]) {
		if (args[0].equals("0000")) {
			if (args.length == 3)
				return true;
			else
				return false;
		}
		return false;
	}

	public static boolean verifyMotorMove(String args[]) {
		if (args[0].equals("0001")) {
			if (args.length == 3)
				return true;
			else
				return false;
		}
		return false;
	}

	//TODO: Might change?
	public static String convertDirection(String code) {
		switch (code) {
		case ("00"):
			return "forward";

		case ("01"):
			return "backward";

		case ("10"):
			return "left";

		case ("11"):
			return "right";

		default:
			return "";
		}

	}

	public static boolean isNullOrEmpty(String input) {
		if (input.equals(null))
			return true;
		else if (input.equals(""))
			return true;

		return false;
	}
}