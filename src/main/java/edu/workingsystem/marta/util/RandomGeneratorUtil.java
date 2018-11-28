package edu.workingsystem.marta.util;

import java.util.Random;

public final class RandomGeneratorUtil {

	// Private constructor to prevent instantiation
	private RandomGeneratorUtil() {
		throw new UnsupportedOperationException();
	}

	public static Random randGenerator = new Random();
	public static Integer ridersArriveHigh = 10;
	public static Integer ridersArriveLow = 1;
	public static Integer ridersOffHigh = 10;
	public static Integer ridersOffLow = 1;
	public static Integer ridersOnHigh = 10;
	public static Integer ridersOnLow = 1;

	public static Integer getRandomRidersArrive() {
		Integer randomArrive = ridersArriveLow + randGenerator.nextInt(ridersArriveHigh);
		return randomArrive;
	}

	public static Integer getRandomRidersOff() {
		Integer randomOff = ridersOffLow + randGenerator.nextInt(ridersOffHigh);
		return randomOff;

	}

	public static Integer getRandomRidersOn() {
		Integer randomOn = ridersOnLow + randGenerator.nextInt(ridersOnHigh);
		return randomOn;

	}

}
