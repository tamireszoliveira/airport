package controller;
import java.util.Random;

import java.util.concurrent.Semaphore;
public class Airport extends Thread {

	private static final int qtairplanes = 12;
	private final Semaphore northtrack = new Semaphore(1);
	private final Semaphore southtrack = new Semaphore(1);
	private final Semaphore takeoffarea = new Semaphore(2);
	
	
		private final int id;
		private final Semaphore chosentrack;
		private final String track;
		private final Random random = new Random();
		
		public Airport(int id, Semaphore chosentrack, String track) {
			this.id = id;
			this.chosentrack = chosentrack;

			this.track = track;
		}
		
		
	// inicializando as decolagens
	public void takeoff() {
		for(int i = 1; i<= qtairplanes ; i++) {
			boolean chosenNorthtrack = Math.random()< 0.5;
			String track = chosenNorthtrack ? "Norte":"Sul";
			Semaphore chosentrack;
			if(chosenNorthtrack) {
				chosentrack = northtrack;
			} else {
				chosentrack = southtrack;
			}
			// criando e inicializando a thread do aviao
			Airport airport = new Airport(i, chosentrack,  track );
			airport.start(); 
		}
	}
		@Override
		public void run() {
			try {
				// entrar na area de decolagem
				takeoffarea.acquire();
				System.out.println("Avião " + id + " entrando na área de decolagem pela pista " + track);
				Thread.sleep(random.nextInt(401) + 300);
				// taxiar
				System.out.println("Avião " + id + " taxiando...");
                Thread.sleep(random.nextInt(501) + 500);
                //decolagem
                chosentrack.acquire();
                System.out.println("Avião " + id + " decolando pela pista " + track);
                Thread.sleep(random.nextInt(201) + 600); 
                //afastamento
                System.out.println("Avião " + id + " afastando-se da área de decolagem...");
                Thread.sleep(random.nextInt(501) + 300); 
            	chosentrack.release();
            	takeoffarea.release();
            	System.out.println("Avião " + id + " decolou");
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		} 
		
	}


