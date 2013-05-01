package Classes;

public class Controller {

	/**
	 * Uses the Communicator class to establish a PC Connection
	 * @return Whether the connection was established 
	 */
	public boolean establishConnection() {
		boolean b = false;
		try {
			b =  Communicator.establishPCConnection();
			return b;
		} catch (Exception e) {
			//System.out.println("Cannot establish connection.");
			return b;
		}
	}

	/**
	 * Crafts the message to move backwards and then transmits
	 * @return Whether the robot was able to move backwards
	 */
	public boolean moveBackward() {
		byte[] message;
		int speed = 50;
		String moveOpcode = "0000";
		String direction = "01";
		try {
			message = Communicator.convertMessageToByteArray(moveOpcode + " "
					+ direction + " " + speed);
			Communicator.transmitMessage(message);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Crafts the message to stop and then transmits
	 * @return Whether the robot was able to stop
	 */
	public boolean stop() {
		try {
			String stopOpcode = "0010";
			byte[] message = Communicator.convertMessageToByteArray(stopOpcode);
			if (Communicator.transmitMessage(message) == -1)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Crafts the message to move forward and then transmits
	 * @return Whether the robot was able to move forwards
	 */
	public boolean moveForward() {
		int speed = 50;
		if (speed == 0) {
			stop();
		} else {
			byte[] message;
			try {
				String moveOpcode = "0000";
				String direction = "00";
				message = Communicator.convertMessageToByteArray(moveOpcode
						+ " "
						+ direction
						+ " "
						+ String.format("%d%d%d", speed / 100,
								(speed % 100) / 10, (speed % 10)));
				if (Communicator.transmitMessage(message) == -1)
					return false;
			} catch (Exception e) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Crafts the message to turn left and then transmits
	 * @return Whether the robot was able to turn left
	 */
	public boolean turnLeft() {
		String turnOpcode = "0001";
		String message = turnOpcode + " A " + 720;
		String message2 = turnOpcode + " C " + -360;
		try {
			byte[] array = Communicator.convertMessageToByteArray(message);
			Communicator.transmitMessage(array);
			if (Communicator.transmitMessage(Communicator
					.convertMessageToByteArray(message2)) == -1)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Crafts the message to turn left and then transmits
	 * @return Whether the robot was able to turn right
	 */
	public boolean turnRight() {
		String turnOpcode = "0001";
		String message = turnOpcode + " C " + 720;
		String message2 = turnOpcode + " A " + -360;
		try {
			byte[] array = Communicator.convertMessageToByteArray(message);
			Communicator.transmitMessage(array);
			if (Communicator.transmitMessage(Communicator
					.convertMessageToByteArray(message2)) == -1)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Crafts the message to get the sensor reading on the given port and transmits
	 * @param port The port to take the reading on 
	 * @return The value that the sensor returns
	 */
	public float getReading(char port) {
		float reading = -1;
		String sensorOpcode = "0011";
		String message = sensorOpcode + " " + port;
		try {
			byte[] array = Communicator.convertMessageToByteArray(message);
			reading = Communicator.transmitMessage(array);
		} catch (Exception e) {
			return -2;
		}
		return reading;
	}

	/**
	 * Crafts the message to emergency stop and transmits
	 * @return Whether the robot was able to emergency stop
	 */
	public boolean EmergencyStop() {
		try {
			String emergencyStopOpcode = "0101";
			byte[] array = Communicator
					.convertMessageToByteArray(emergencyStopOpcode);
			if (Communicator.transmitMessage(array) == -1)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void exit() {
		try {
			String abortOpcode = "0110";
			byte[] array = Communicator.convertMessageToByteArray(abortOpcode);
			Communicator.transmitMessage(array);
		}
		catch (Exception e) {
		}
	}
}
