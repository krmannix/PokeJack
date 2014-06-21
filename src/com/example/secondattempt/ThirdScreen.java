package com.example.secondattempt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class ThirdScreen extends Activity implements SeekBar.OnSeekBarChangeListener, OnClickListener {

	// Declare variables
	// For the seek bar
	SeekBar mSeekBar;
    TextView mProgressText;
    TextView mTrackingText;
 //   TextView mCheckingValue;
 //   TextView mCheckingValueDealer;
    
    // 4 action buttons
	Button backbutton; 
	ImageButton hitbutton, staybutton;
	ImageView playerCard3, playerCard4, playerCard5;
	
	// Card deck
	static int[] cards = new int[52];
	
	// Player's score arrays
	static int[] dealer = new int[5];
	static int[] player = new int[5];
	
	// Counting how many cards have been called by player
	static int cardCounter = 0;
	
	// Counting how many cards have been called by dealer
	static int dealerIndex = 3;
	
	// All the strings needed for the cards
	static String dealerCard1, dealerCard2;
	static String dealerCard3;
	static String dealerCard4;
	static String dealerCard5;
	static String yourCard1, yourCard2, yourCard3, yourCard4, yourCard5;
	
	// The boolean 'checks' to see if a player 'busts'
	static boolean checkDealA = false;
	static boolean checkDeal = false;
	static boolean checkPlayA = false;
	static boolean checkPlay = false;
	static boolean didPlayerBust = false;
	static boolean didDealerBust = false;
	
	// Create the Drawable types for all 10 images
	static Drawable Dcard1, Dcard2, Dcard3, Dcard4, Dcard5;
	static Drawable Pcard1, Pcard2, Pcard3, Pcard4, Pcard5;
	
	// Create ImageSource variables for all Drawable types
	static int D1, D2, D3, D4, D5;
	static int P1, P2, P3, P4, P5;
	
	// Define the "betting" variables
	static int currentMoney = 1000;
	static double currentBet;
	static boolean betPressed = false;

	static int whoWon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_screen);
	
		// Get the extra variable sent by the other activities to restore the money value to default
		int resetMoney = getIntent().getExtras().getInt("MoneyValue");
		currentMoney = resetMoney;
		// Make the variables for the seek bar
		mSeekBar = (SeekBar)findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView)findViewById(R.id.progress);
        mTrackingText = (TextView)findViewById(R.id.tracking);
		backbutton = (Button) findViewById(R.id.backbutton2); 
		hitbutton = (ImageButton) findViewById(R.id.buttonhit);
		staybutton = (ImageButton) findViewById(R.id.buttonstay);
		playerCard3 = (ImageView) findViewById(R.id.you3);
		playerCard4 = (ImageView) findViewById(R.id.you4);
		playerCard5 = (ImageView) findViewById(R.id.you5);
//		mCheckingValue = (TextView) findViewById(R.id.checkingValue);
//		mCheckingValueDealer = (TextView) findViewById(R.id.checkingValueDealer);
		mProgressText.setText("Current amount of money: $" + currentMoney);
		
        // Randomize the deck
        cards = returnArray();
        
        // Create all the cards for the dealer and player
        dealerCard1 = card(cards, 0);
        yourCard1 = card(cards, 1);
        dealerCard2 = card(cards, 2);
        yourCard2 = card(cards, 3);
        dealerCard3 = card(cards, 4);
        yourCard3 = card(cards, 5);
        dealerCard4 = card(cards, 6);
        yourCard4 = card(cards, 7);
        dealerCard5 = card(cards, 8);
        yourCard5 = card(cards, 9);
        
        // Add the first two values for both dealer and player to array
        dealer[0] = checkValue(dealerCard1);
        dealer[1] = checkValue(dealerCard2);
        player[0] = checkValue(yourCard1);
        player[1] = checkValue(yourCard2);
        
        // Get variables that reference all of the possible cards
        final ImageView firstDealer = (ImageView) findViewById(R.id.dealer1);
        final ImageView secondDealer = (ImageView) findViewById(R.id.dealer2);
        final ImageView thirdDealer = (ImageView) findViewById(R.id.dealer3);
        final ImageView fourthDealer = (ImageView) findViewById(R.id.dealer4);
        final ImageView fifthDealer = (ImageView) findViewById(R.id.dealer5);  
        final ImageView firstPlayer = (ImageView) findViewById(R.id.you1);
        final ImageView secondPlayer = (ImageView) findViewById(R.id.you2);
        final ImageView thirdPlayer = (ImageView) findViewById(R.id.you3);
        final ImageView fourthPlayer = (ImageView) findViewById(R.id.you4);
        final ImageView fifthPlayer = (ImageView) findViewById(R.id.you5); 
        
        // Set all the images to the right values
        D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
        Dcard1 = getResources().getDrawable(D1);
        D2 = getResources().getIdentifier(returnPath(dealerCard2), null, getPackageName());
        Dcard2 = getResources().getDrawable(D2);
        secondDealer.setImageDrawable(Dcard2);
        D3 = getResources().getIdentifier(returnPath(dealerCard3), null, getPackageName());
        Dcard3 = getResources().getDrawable(D3);
        thirdDealer.setImageDrawable(Dcard3);
        D4 = getResources().getIdentifier(returnPath(dealerCard4), null, getPackageName());
        Dcard4 = getResources().getDrawable(D4);
        fourthDealer.setImageDrawable(Dcard4);
        D5 = getResources().getIdentifier(returnPath(dealerCard5), null, getPackageName());
        Dcard5 = getResources().getDrawable(D5);
        fifthDealer.setImageDrawable(Dcard5);
        P1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard1 = getResources().getDrawable(P1);
        firstPlayer.setImageDrawable(Pcard1);
        P2 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard2 = getResources().getDrawable(P2);
        secondPlayer.setImageDrawable(Pcard2);
        P3 = getResources().getIdentifier(returnPath(yourCard3), null, getPackageName());
        Pcard3 = getResources().getDrawable(P3);
        thirdPlayer.setImageDrawable(Pcard3);
        P4 = getResources().getIdentifier(returnPath(yourCard4), null, getPackageName());
        Pcard4 = getResources().getDrawable(P4);
        fourthPlayer.setImageDrawable(Pcard4);
        P5 = getResources().getIdentifier(returnPath(yourCard5), null, getPackageName());
        Pcard5 = getResources().getDrawable(P5);
        fifthPlayer.setImageDrawable(Pcard5);
                
        try {
            View.OnClickListener handler = new View.OnClickListener(){
                public void onClick(final View v) {
                	
                	// Create the alert dialogues
            		AlertDialog.Builder betBeforeHit = new AlertDialog.Builder(v.getContext());
            		betBeforeHit.setMessage("You must place a bet before you can hit!")
            		.setTitle("Alert")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				
            			}
            		});
            		AlertDialog dialog_card_betBeforeHit = betBeforeHit.create();
                    dialog_card_betBeforeHit.getWindow().setGravity(Gravity.BOTTOM);
                    
                    AlertDialog.Builder betBeforeStay = new AlertDialog.Builder(v.getContext());
            		betBeforeStay.setMessage("You must place a bet before you can stay!")
            		.setTitle("Alert")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				
            			}
            		});
            		AlertDialog dialog_card_betBeforeStay = betBeforeStay.create();
                    dialog_card_betBeforeStay.getWindow().setGravity(Gravity.BOTTOM);
                    
                    AlertDialog.Builder betBeforeDouble = new AlertDialog.Builder(v.getContext());
            		betBeforeDouble.setMessage("You must place a bet before you can double down!")
            		.setTitle("Alert")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				
            			}
            		});
            		AlertDialog dialog_card_betBeforeDouble = betBeforeDouble.create();
                    dialog_card_betBeforeDouble.getWindow().setGravity(Gravity.BOTTOM);
                    
                    AlertDialog.Builder doubleDown = new AlertDialog.Builder(v.getContext());
            		doubleDown.setMessage("You can only double down if you have not yet hit!")
            		.setTitle("Alert")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				
            			}
            		});
            		AlertDialog dialog_card_doubleDown = doubleDown.create();
                    dialog_card_doubleDown.getWindow().setGravity(Gravity.BOTTOM);

            		AlertDialog.Builder playerBusted = new AlertDialog.Builder(v.getContext());
            		playerBusted.setMessage("Unfortunately, the value of your cards is over 21. You busted!")
            		.setTitle("Uh-oh!")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				resetEverything(false, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer, 
                    				firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
            			}
            		});
            		            		
            		AlertDialog dialog_card_playerBusted = playerBusted.create();
                    dialog_card_playerBusted.getWindow().setGravity(Gravity.BOTTOM);
            		
            		AlertDialog.Builder dealerBusted = new AlertDialog.Builder(v.getContext());
            		dealerBusted.setMessage("The value of the dealer's cards is over 21! You won the hand!")
            		.setTitle("Lucky You!")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				resetEverything(true, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer, 
                    				firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
            			}
            		});
            		
            		AlertDialog dialog_card_dealerBusted = dealerBusted.create();
                    dialog_card_dealerBusted.getWindow().setGravity(Gravity.BOTTOM);
                    
            		AlertDialog.Builder playerWon = new AlertDialog.Builder(v.getContext());
            		playerWon.setMessage("You won the hand!")
            		.setTitle("Congrats!")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				resetEverything(true, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer, 
                    				firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
            			}
            		});
            		
            		AlertDialog dialog_card_playerWon = playerWon.create();
                    dialog_card_playerWon.getWindow().setGravity(Gravity.BOTTOM);
            		
            		AlertDialog.Builder dealerWon = new AlertDialog.Builder(v.getContext());
            		dealerWon.setMessage("You lost the hand...")
            		.setTitle("Sorry!")	
            		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				resetEverything(false, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer, 
                    				firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
            			}
            		});
            		
            		AlertDialog dialog_card_dealerWon = dealerWon.create();
                    dialog_card_dealerWon.getWindow().setGravity(Gravity.BOTTOM);
            		
            		           		
            		// Does this work here??
            		
            		
                    //we will use switch statement and just
                    //get thebutton's id to make things easier
            		
                    switch (v.getId()) {
                    
                    

                        case R.id.backbutton2: //Go back to mainScreen
                        		Intent intent = new Intent(v.getContext(), MainActivity.class);
                        		startActivity(intent);  
                        		break;
                        case R.id.buttonhit: // See next player card
                       // 	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                       // 	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        	if (betPressed) {                        	
                        	if (cardCounter == 0) {
                        		playerCard3.setVisibility(View.VISIBLE);
                        		player[2] = checkValue(yourCard3);
                        	//	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                        	//	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        		cardCounter++;
                        		didPlayerBust = bust(player);
                        		if (didPlayerBust) {
                        			dialog_card_playerBusted.show();
                        			break;
                        		}
                        	} else if (cardCounter == 1) {
                        		playerCard4.setVisibility(View.VISIBLE);
                        		player[3] = checkValue(yourCard4);
                        	//	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                        	//	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        		cardCounter++;
                        		didPlayerBust = bust(player);
                        		if (didPlayerBust) {
                        			dialog_card_playerBusted.show();
                        			break;
                        		}
                        	} else if (cardCounter == 2) {
                        		playerCard5.setVisibility(View.VISIBLE);
                        		player[4] = checkValue(yourCard5);
                        	//	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                        	//	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        		cardCounter++;
                        		didPlayerBust = bust(player);
                        		if (didPlayerBust) {
                        			dialog_card_playerBusted.show();
                        			break;
                        		} else {
                        		firstDealer.setImageDrawable(Dcard1);
                        		whoWon = lastHand(thirdDealer, fourthDealer, fifthDealer);  
                        		if (whoWon == 1) {
                        			dialog_card_dealerBusted.show();
                        		} else if(whoWon == 2) {
                        			dialog_card_dealerWon.show();
                        		} else if(whoWon == 3){
                        			dialog_card_playerWon.show();
                        		}
                        		}
                        	}  
                        	} else {
                        	betBeforeHit.show();	
                        	}
                        	break;
                        case R.id.buttonstay: 
                       // 	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                      //  	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        	if (betPressed) {
                        		D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
                                Dcard1 = getResources().getDrawable(D1);
                        		firstDealer.setImageDrawable(Dcard1);
                        	whoWon = lastHand(thirdDealer, fourthDealer, fifthDealer);  
                    		if (whoWon == 1) {
                    			dialog_card_dealerBusted.show();
                    		} else if(whoWon == 2) {
                    			dialog_card_dealerWon.show();
                    		} else {
                    			dialog_card_playerWon.show();
                    		}
                        	} else {
                        		betBeforeStay.show();
                        	}
                        	break;
                        case R.id.betbutton:
                        //	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                        //	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        	if (!betPressed) {
                        		betPressed = true;
                        		currentBet = (((double)mSeekBar.getProgress())/100)*(currentMoney); 
                        		if (currentBet == 0) {
                        			currentBet = 1;
                        		}
                        		P1 = getResources().getIdentifier(returnPath(yourCard1), null, getPackageName());
                                Pcard1 = getResources().getDrawable(P1);
                                firstPlayer.setImageDrawable(Pcard1);
                                P2 = getResources().getIdentifier(returnPath(yourCard2), null, getPackageName());
                                Pcard2 = getResources().getDrawable(P2);
                                secondPlayer.setImageDrawable(Pcard2);
                        		mProgressText.setText("Current bet: $" + currentBet);                     		
                        	 } 
                        	break; 
                        	
                        case R.id.buttondouble:
                        	
                        	if (betPressed) {
                        		if (cardCounter == 0) {
                        			currentBet *= 2;
                        			D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
                                    Dcard1 = getResources().getDrawable(D1);
                            		firstDealer.setImageDrawable(Dcard1);
                        		playerCard3.setVisibility(View.VISIBLE);
                        		player[2] = checkValue(yourCard3);
                        	//	mCheckingValue.setText("Values. 1) " + player[0] + " 2) " + player[1] + " 3) " + player[2] + " 4) " + player[3] + " 5) " + player[4]);
                        	//	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
                        		cardCounter++;
                        		didPlayerBust = bust(player);
                        		if (didPlayerBust) {
                        			dialog_card_playerBusted.show();
                        			break;
                        		}
                        		firstDealer.setImageDrawable(Dcard1);
                        	whoWon = lastHand(thirdDealer, fourthDealer, fifthDealer);  
                    		if (whoWon == 1) {
                    			dialog_card_dealerBusted.show();
                    		} else if(whoWon == 2) {
                    			dialog_card_dealerWon.show();
                    		} else {
                    			dialog_card_playerWon.show();
                    		}
                        	} else {
                        		doubleDown.show();
                        	}
                        	} else {
                        		betBeforeDouble.show();
                        	}
                        	break;
                    }
                }
            };
                
            //we will set the listeners
            findViewById(R.id.backbutton2).setOnClickListener(handler);
            findViewById(R.id.buttonhit).setOnClickListener(handler);
            findViewById(R.id.buttonstay).setOnClickListener(handler);
            findViewById(R.id.betbutton).setOnClickListener(handler);
            findViewById(R.id.buttondouble).setOnClickListener(handler);
                
            }catch(Exception e){
                 Log.e("Android Button Tutorial", e.toString());
            }  
        
	}
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		if (betPressed == false) {
        mProgressText.setText(
        getString(R.string.seekbar_from_touch) + " $" + (((double)mSeekBar.getProgress())/100)*(currentMoney) + "?");
		}
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
     mTrackingText.setText(getString(R.string.seekbar_tracking_on));
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
     mTrackingText.setText(getString(R.string.seekbar_tracking_off));
    }
    
    public static int[] returnArray() {
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
    
    public static String card(int[] inputArray, int num) {
        int j = inputArray[num];
        switch (j) {
          case 0: return "ba";
          case 1: return "b2";
          case 2: return "b3";
          case 3: return "b4";
          case 4: return "b5";
          case 5: return "b6";
          case 6: return "b7";
          case 7: return "b8";
          case 8: return "b9";
          case 9: return "b10";
          case 10: return "bj";
          case 11: return "bq";
          case 12: return "bk";
          case 13: return "ra";
          case 14: return "r2";
          case 15: return "r3";
          case 16: return "r4";
          case 17: return "r5";
          case 18: return "r6";
          case 19: return "r7";
          case 20: return "r8";
          case 21: return "r9";
          case 22: return "r10";
          case 23: return "rj";
          case 24: return "rq";
          case 25: return "rk";
          case 26: return "ga";
          case 27: return "g2";
          case 28: return "g3";
          case 29: return "g4";
          case 30: return "g5";
          case 31: return "g6";
          case 32: return "g7";
          case 33: return "g8";
          case 34: return "g9";
          case 35: return "g10";
          case 36: return "gj";
          case 37: return "gq";
          case 38: return "gk";
          case 39: return "pa";
          case 40: return "p2";
          case 41: return "p3";
          case 42: return "p4";
          case 43: return "p5";
          case 44: return "p6";
          case 45: return "p7";
          case 46: return "p8";
          case 47: return "p9";
          case 48: return "p10";
          case 49: return "pj";
          case 50: return "pq";
          case 51: return "pk";
          }
      return "ba";
      }
    
    public static String returnPath(String str) {
    	return "drawable/" + str;
    }
    
    public static int checkValue(String val) {
    	if (val.equals("ba") || val.equals("ra") || val.equals("ga") || val.equals("pa")) {
    		return 1;
    		// This returns 1 to indicate to the algorithm that if can be either a 1 or 11
    	} else if (val.equals("b2") || val.equals("r2") || val.equals("g2") || val.equals("p2")) {
    		return 2;
    	} else if (val.equals("b3") || val.equals("r3") || val.equals("g3") || val.equals("p3")) {
    		return 3;
    	} else if (val.equals("b4") || val.equals("r4") || val.equals("g4") || val.equals("p4")) {
    		return 4;
    	} else if (val.equals("b5") || val.equals("r5") || val.equals("g5") || val.equals("p5")) {
    		return 5;
    	} else if (val.equals("b6") || val.equals("r6") || val.equals("g6") || val.equals("p6")) {
    		return 6;
    	} else if (val.equals("b7") || val.equals("r7") || val.equals("g7") || val.equals("p7")) {
    		return 7;
    	} else if (val.equals("b8") || val.equals("r8") || val.equals("g8") || val.equals("p8")) {
    		return 8;
    	} else if (val.equals("b9") || val.equals("r9") || val.equals("g9") || val.equals("p9")) {
    		return 9;
    	} else if (val.equals("b10") || val.equals("r10") || val.equals("g10") || val.equals("p10")) {
    		return 10;
    	} else {
    	return 10;	
    	}
    	}

    	public static boolean calculateWinner(int[] dealer, int[] player) {
    		// true if dealer, false if player
    		// totaldealerA and totalplayerA are played as if A = 11
    		int totaldealerA = 0, totaldealer = 0, totalplayerA = 0, totalplayer = 0;
    		
    		// our final boolean for the winner
    		boolean winner;
    		
    		// Once we choose which hand is better, we will use these variables are our final check
    		int finalDealerTotal = 0, finalPlayerTotal = 0;
    		
    		for (int i = 0; i < dealer.length; i++) {
    			totaldealer += dealer[i];
    			if (dealer[i] == 1) {
    				totaldealerA += 11;
    			} else {
    				totaldealerA += dealer[i];
    			}
    		}
    		for (int i = 0; i < player.length; i++) {
    			totalplayer += player[i];
    			if (player[i] == 1) {
    				totalplayerA += 11;
    			} else {
    				totalplayerA += player[i];
    			}
    		}
    		// If any of these are over 21, set them equal to true
    		if (totaldealerA > 21) {
    			checkDealA = true;
    		}
    		if (totaldealer > 21) {
    			checkDeal = true;
    		}
    		if (totalplayerA > 21) {
    			checkPlayA = true;
    		}
    		if (totalplayer > 21) {
    			checkPlay = true;
    		}
    		
    		// If they are equal, set the final total to one of the values
    		// If not equal, set the one with the higher value
    		if (totaldealerA == totaldealer) {
    			finalDealerTotal = totaldealerA;
    		} else if (totaldealerA > totaldealer) {
    			finalDealerTotal = totaldealerA;
    		} else if (totaldealerA < totaldealer) {
    			finalDealerTotal = totaldealer;
    		}
    		if (totalplayerA == totalplayer) {
    			finalPlayerTotal = totalplayerA;
    		} else if (totalplayerA > totalplayer) {
    			finalPlayerTotal = totalplayerA;
    		} else if (totalplayerA < totalplayer) {
    			finalPlayerTotal = totalplayer;
    		}
    		// See if a player has busted. If so, set the value of the hand equal to zero
    		// If one has busted (but not both), set the value to the hand not busted
    		if (checkDealA && checkDeal) {
    			finalDealerTotal = 0;
    		} else if (checkDealA) {
    			finalDealerTotal = totaldealer;
    		} else if (checkDeal) {
    			finalDealerTotal = totaldealerA;
    		}
    		if (checkPlayA && checkPlay) {
    			finalPlayerTotal = 0;
    			// Will only reach here if one is not busted
    		} else if (checkPlayA) {
    			finalPlayerTotal = totalplayer;
    		} else if (checkPlay) {
    			finalPlayerTotal = totalplayerA;
    		}
    	
    		if (finalPlayerTotal == finalDealerTotal) {
    			winner = true;
    		} else if (finalPlayerTotal < finalDealerTotal) {
    			winner = true;
    		} else {
    			winner = false;
    		}
    		
    		return winner;    		
    	}

    	public static boolean shouldDealerHit(int[] hand) {
    		// Total has the Ace as 1
    		int total = 0;
    		// TotalA has the Ace as 11
    		int totalA = 0;
    		for (int i = 0; i < hand.length; i++) {
    			total += hand[i];
    			if (hand[i] == 1) {
    				totalA += 11;
    			} else {
    				totalA += hand[i];
    			}
    		}
    		if (totalA > 21 && total < 17) {
    			return true;
    		} else if (totalA >= 17) {
    			return false;
    		} else {
    			return true;
    		}
    	}
    	
    	public static int lastHand(ImageView thirdDealer, ImageView fourthDealer, ImageView fifthDealer) {
    		boolean didDealerBust = false;
    		boolean hit;
        	 do {
        		hit = shouldDealerHit(dealer);
        		if (hit && dealerIndex < 6) {
        			switch (dealerIndex) {
        			case 3: 
        				dealer[2] = checkValue(dealerCard3);
        				didDealerBust = bust(dealer);
        				thirdDealer.setVisibility(View.VISIBLE);
        				dealerIndex++;
        				break;
        			case 4:
        				dealer[3] = checkValue(dealerCard4);
        				didDealerBust = bust(dealer);
        				fourthDealer.setVisibility(View.VISIBLE);
        				dealerIndex++;
        				break;
        			case 5:
        				dealer[4] = checkValue(dealerCard5);
        				didDealerBust = bust(dealer);
        				fifthDealer.setVisibility(View.VISIBLE);
        				dealerIndex++;
        				break;
        			} 
        		}
        	} while(hit); 
        	 
        	 if (didDealerBust) {
        		 // Dealer won hand
        		 return 1;
        	 } else {
        		 boolean win = calculateWinner(dealer, player);
        		 if (win) {
        			 return 2;
        		 } else {
        			 return 3;
        		 }
        	 }
    	} 
    	
    	public static boolean bust(int[] arr) {
    		int total = 0;
    		for (int i = 0; i < arr.length; i++) {
    			total += arr[i];
    		}
    		if (total > 21) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	public void resetEverything(boolean win, ImageView firstPlayer, ImageView secondPlayer, ImageView thirdPlayer, ImageView fourthPlayer,
    			ImageView fifthPlayer, ImageView firstDealer, ImageView secondDealer, ImageView thirdDealer, ImageView fourthDealer, ImageView 
    			fifthDealer, View v) {
    		if (win) {
    			currentMoney += currentBet;
    		} else {
    			currentMoney -= currentBet;
    		}
    		 if (currentMoney <= 0) {
         		Intent intent1 = new Intent(v.getContext(), LoseScreen.class);
         		startActivity(intent1); 
         		} else if (currentMoney > 10000) {
         			Intent intent2 = new Intent(v.getContext(), WinScreen.class);
             		startActivity(intent2); 
         		}
    		
    		currentBet = 0;
    		cards = returnArray();
    		dealerCard1 = card(cards, 0);
            yourCard1 = card(cards, 1);
            dealerCard2 = card(cards, 2);
            yourCard2 = card(cards, 3);
            dealerCard3 = card(cards, 4);
            yourCard3 = card(cards, 5);
            dealerCard4 = card(cards, 6);
            yourCard4 = card(cards, 7);
            dealerCard5 = card(cards, 8);
            yourCard5 = card(cards, 9);
            dealer[0] = checkValue(dealerCard1);
            dealer[1] = checkValue(dealerCard2);
            player[0] = checkValue(yourCard1);
            player[1] = checkValue(yourCard2);
          
            // set rest of array equal to zero
            dealer[2] = 0;
            dealer[3] = 0;
            dealer[4] = 0;
            player[2] = 0;
            player[3] = 0;
            player[4] = 0;
              
            D1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
            Dcard1 = getResources().getDrawable(D1);
            firstDealer.setImageDrawable(Dcard1);
            D2 = getResources().getIdentifier(returnPath(dealerCard2), null, getPackageName());
            Dcard2 = getResources().getDrawable(D2);
            secondDealer.setImageDrawable(Dcard2);
            D3 = getResources().getIdentifier(returnPath(dealerCard3), null, getPackageName());
            Dcard3 = getResources().getDrawable(D3);
            thirdDealer.setImageDrawable(Dcard3);
            D4 = getResources().getIdentifier(returnPath(dealerCard4), null, getPackageName());
            Dcard4 = getResources().getDrawable(D4);
            fourthDealer.setImageDrawable(Dcard4);
            D5 = getResources().getIdentifier(returnPath(dealerCard5), null, getPackageName());
            Dcard5 = getResources().getDrawable(D5);
            fifthDealer.setImageDrawable(Dcard5);
            P1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
            Pcard1 = getResources().getDrawable(P1);
            firstPlayer.setImageDrawable(Pcard1);
            P2 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
            Pcard2 = getResources().getDrawable(P2);
            secondPlayer.setImageDrawable(Pcard2);
            P3 = getResources().getIdentifier(returnPath(yourCard3), null, getPackageName());
            Pcard3 = getResources().getDrawable(P3);
            thirdPlayer.setImageDrawable(Pcard3);
            P4 = getResources().getIdentifier(returnPath(yourCard4), null, getPackageName());
            Pcard4 = getResources().getDrawable(P4);
            fourthPlayer.setImageDrawable(Pcard4);
            P5 = getResources().getIdentifier(returnPath(yourCard5), null, getPackageName());
            Pcard5 = getResources().getDrawable(P5);
            fifthPlayer.setImageDrawable(Pcard5);
            
            // Set the non-initial cards invisible
           thirdPlayer.setVisibility(View.INVISIBLE);
           fourthPlayer.setVisibility(View.INVISIBLE);
           fifthPlayer.setVisibility(View.INVISIBLE);
           thirdDealer.setVisibility(View.INVISIBLE);
           fourthDealer.setVisibility(View.INVISIBLE);
           fifthDealer.setVisibility(View.INVISIBLE);
            
            cardCounter = 0;
            dealerIndex = 3;
            betPressed = false;
            mProgressText.setText("Current amount of money: $" + currentMoney);
           
        //    mCheckingValue.setText("Values. 1) " + player[0] + " 2. " + player[1] + " 3. " + player[2] + " 4. " + player[3] + " 5. " + player[4]);
        //	mCheckingValueDealer.setText("Values. 1) " + dealer[0] + " 2) " + dealer[1] + " 3) " + dealer[2] + " 4) " + dealer[3] + " 5) " + dealer[4]);
             
             
    	}
    	  	
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
