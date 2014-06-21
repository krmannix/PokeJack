package com.example.secondattempt;

public class RandomArray {

	public int[] returnArray() {
		  int[] cards = new int[52];
		  int[] check = new int[52];
		  boolean moveOn;
		  for (int i = 0; i < 52; i++) {
		    check[i] = i;
		    cards[i] = 0;
		}
		  for (int i = 0; i < 52; i++) {
		    do {
		   int j = (int)(Math.random()*100)%52;
		   if (cards[j] == 0) {
		     cards[j] = i;
		     moveOn = true;
		   } else {
		     moveOn = false;
		    }
		    } while(!moveOn);
		  }
		 return cards;
		 }
}
