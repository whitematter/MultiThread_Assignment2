package com.app.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	List<Integer> list = new ArrayList<Integer>();
	Random random = new Random();
	static volatile boolean var = true;

	public static void main(String[] args) {
		final Main main = new Main();

		Thread producer = new Thread(new Runnable() {

			@Override
			public void run() {
				main.produce();
			}
		});

		Thread consumer = new Thread(new Runnable() {

			@Override
			public void run() {
				main.consume();
			}
		});
		System.out.println("Press any key to stop..!!");
		producer.start();
		consumer.start();
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		var = false;
		System.out.println("The process has stopped.");

		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void produce() {
		synchronized (this) {
			while (var) {

				if (list.size() < 20) {
					list.add(random.nextInt(100));
					this.notify();

				} else {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void consume() {
		synchronized (this) {
			while (var) {

				if (list.size() > 0) {
					System.out.println("The removed number is:"+ list.remove(0));
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.notify();
				} else {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
