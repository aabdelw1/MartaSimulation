package edu.workingsystem.marta.util;

import java.util.Random;

public final class RandomGeneratorUtil {

	// Private constructor to prevent instantiation
	private RandomGeneratorUtil() {
		throw new UnsupportedOperationException();
	}

	public static Random randGenerator = new Random();

	public static Integer getRandomRidersArrive(int ridersArriveLow, int ridersArriveHigh) {
		Integer randomArrive;
		if (!(ridersArriveHigh == 0)){
			randomArrive = ridersArriveLow + randGenerator.nextInt(ridersArriveHigh);
		} else {
			randomArrive = 0;
		}
		return randomArrive;
	}

	public static Integer getRandomRidersOff(int ridersOffLow, int ridersOffHigh) {
		Integer randomOff;
		if (!(ridersOffHigh == 0)){
			randomOff = ridersOffLow + randGenerator.nextInt(ridersOffHigh);
		} else {
			randomOff = 0;
		}
		return randomOff;
	}

	public static Integer getRandomRidersOn(int ridersOnLow, int ridersOnHigh) {
		Integer randomOn;
		if (!(ridersOnHigh == 0)){
			randomOn = ridersOnLow + randGenerator.nextInt(ridersOnHigh);
		} else {
			randomOn = 0;
		}
		return randomOn;
	}

}
