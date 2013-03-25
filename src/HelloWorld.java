import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class HelloWorld {

	public static void main(String[] args) {
		LCD.drawString("HelloWorld", 3, 4);
		long time = 2000; //2 seconds
		float speed = 720; //2 RPM
		try {
			moveForward(time, speed);
			Thread.sleep(1000);
			moveBackward(time, speed);
		} catch (InterruptedException e) {
			LCD.drawString("There was a problem", 3, 4);
			e.printStackTrace();
		}

	}

	/**
	 * Given time and speed, causes the robot to move motors A and B forward
	 * @param time
	 * @param speed
	 * @throws InterruptedException
	 */
	public static void moveForward(long time, float speed)
			throws InterruptedException {
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		Motor.A.forward();
		Motor.B.forward();
		Thread.sleep(time);
		Motor.A.stop();
		Motor.B.stop();
	}

	/**
	 * Given time and speed, causes the robot to move motors A and B backward
	 * @param time
	 * @param speed
	 * @throws InterruptedException
	 */
	public static void moveBackward(long time, float speed)
			throws InterruptedException {
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		Motor.A.backward();
		Motor.B.backward();
		Thread.sleep(time);
		Motor.A.stop();
		Motor.B.stop();
	}
}
