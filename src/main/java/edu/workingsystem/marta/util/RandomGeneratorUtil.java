package edu.workingsystem.marta.util;

import java.util.Random;

public final class RandomGeneratorUtil {

	// Private constructor to prevent instantiation
	private RandomGeneratorUtil() {
		throw new UnsupportedOperationException();
	}

	public static Random randGenerator = new Random();

	public static Integer getRandomRidersArrive(int ridersArriveLow, int ridersArriveHigh) {
		Integer randomArrive = ridersArriveLow + randGenerator.nextInt(ridersArriveHigh);
		return randomArrive;
	}

	public static Integer getRandomRidersOff(int ridersOffLow, int ridersOffHigh) {
		Integer randomOff = ridersOffLow + randGenerator.nextInt(ridersOffHigh);
		return randomOff;

	}

	public static Integer getRandomRidersOn(int ridersOnLow, int ridersOnHigh) {
		Integer randomOn = ridersOnLow + randGenerator.nextInt(ridersOnHigh);
		return randomOn;

	}

}
