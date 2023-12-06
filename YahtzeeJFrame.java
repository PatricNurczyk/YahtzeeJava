import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class YahtzeeJFrame extends JFrame {
    private int numberOfPlayers = 2;
    private MainMenuJPanel mainMenu;
    private GameBoardJPanel gameBoard;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    YahtzeeJFrame(){
        super("Yahtzee");
        mainMenu = new MainMenuJPanel();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(mainMenu, "mainMenu");
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 1600, 900 ); 
        cardLayout.show(mainPanel, "mainMenu");
    }
    private void startGame(){
		gameBoard = new GameBoardJPanel();
		mainPanel.add(gameBoard, "gameBoard");
        cardLayout.show(mainPanel, "gameBoard");
    }
    class MainMenuJPanel extends JPanel{
        public MainMenuJPanel() {
            // Set layout for the panel
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            Icon logo = new ImageIcon( getClass().getResource( "assets/logo.png" ) );
            JLabel logoLabel = new JLabel(logo);
            // Create buttons
            JButton singlePlayerButton = new JButton("Single Player");
            MultiplayerJPanel multiplayerButton = new MultiplayerJPanel();
    
            // Add buttons to the panel
            add(logoLabel,gbc);
            gbc.gridy = 1;
            add(singlePlayerButton,gbc);
            gbc.gridy = 2;
            add(multiplayerButton,gbc);
    
            // ActionListener for the Single Player button
            singlePlayerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Single Player button clicked");
                    numberOfPlayers = 1;
					startGame();
                }
            });
        }
        class MultiplayerJPanel extends JPanel{
    
            public MultiplayerJPanel() {
                setLayout(new FlowLayout());
        
                // Create a button for multiplayer
                JButton multiplayerButton = new JButton("Multiplayer");
                add(multiplayerButton);
        
                // Create a label to display the number of players
                JLabel playerCountLabel = new JLabel("Number of Players: " + numberOfPlayers);
                add(playerCountLabel);
        
                // Create buttons for increasing and decreasing player count
                JButton increaseButton = new JButton("+");
                JButton decreaseButton = new JButton("-");
        
                // Add buttons to the panel
                add(increaseButton);
                add(decreaseButton);

        
                // ActionListener for increasing player count
                increaseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numberOfPlayers++;
                        playerCountLabel.setText("Number of Players: " + numberOfPlayers);
                    }
                });
        
                // ActionListener for decreasing player count
                decreaseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (numberOfPlayers > 2) {
                            numberOfPlayers--;
                            playerCountLabel.setText("Number of Players: " + numberOfPlayers);
                        }
                    }
                });
        
                // ActionListener for the multiplayer button
                multiplayerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add logic for the multiplayer button here
                        // For example, start a multiplayer game
                        System.out.println("Multiplayer button clicked. Players: " + numberOfPlayers);
                        startGame();
                    }
                });
            }
        }
    }
    class GameBoardJPanel extends JPanel{
		private int currentPlayer;
		private int currentPlayerCounter;
		private int winningPlayer = 0;
		private int totalTurns = 0;
		private Player[] players;
		private PlayerLabelPanel [] playerScores;
		private Dice[] dice = new Dice[5];
		private int currRoll = 1;
		private JLabel playerTurnLabel;
		private JButton button_aces;
		private JButton button_twos;
		private JButton button_threes;
		private JButton button_fours;
		private JButton button_fives;
		private JButton button_sixes;
		private JButton button_threeKind;
		private JButton button_fourKind;
		private JButton button_fullHouse;
		private JButton button_smStraight;
		private JButton button_lgStraight;
		private JButton button_yahtzee;
		private JButton button_chance;
		private boolean isTie;

		JButton keep_1;
		JButton keep_2;
		JButton keep_3;
		JButton keep_4;
		JButton keep_5;

		JButton roll;
		Icon counter_icon;
		JLabel roll_1;
		JLabel roll_2;
		JLabel roll_3;
		JLabel[] diceIcons;

        GameBoardJPanel(){
			players = new Player[numberOfPlayers];
			for (int i = 0; i < numberOfPlayers; ++i){
				players[i] = new Player();
			}
			for (int i = 0; i < 5; ++i){
				dice[i] = new Dice();
			}
		// Set layout for the game panel
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			currentPlayer = 0; //numberOfPlayers - 1
			currentPlayerCounter = 1;
        // Make buttons for each of the score selections
			button_aces = new JButton("Aces");
			button_twos = new JButton("Twos");
			button_threes = new JButton("Threes");
			button_fours = new JButton("Fours");
			button_fives = new JButton("Fives");
			button_sixes = new JButton("Sixes");
			button_threeKind = new JButton("Three of a Kind");
			button_fourKind = new JButton("Four of a Kind");
			button_fullHouse = new JButton("Full House");
			button_smStraight = new JButton("Small Straight");
			button_lgStraight = new JButton("Large Straight");
			button_yahtzee = new JButton("Yahtzee");
			button_chance = new JButton("Chance");
			
			button_aces.setEnabled(false);
			button_twos.setEnabled(false);
			button_threes.setEnabled(false);
			button_fours.setEnabled(false);
			button_fives.setEnabled(false);
			button_sixes.setEnabled(false);
			button_threeKind.setEnabled(false);
			button_fourKind.setEnabled(false);
			button_fullHouse.setEnabled(false);
			button_smStraight.setEnabled(false);
			button_lgStraight.setEnabled(false);
			button_yahtzee.setEnabled(false);
			button_chance.setEnabled(false);
			
		// Make dice faces and keep buttons
			ImageIcon [] diceImage = new ImageIcon[6];
			for (int i = 1; i <= 6; ++i){
				diceImage[i-1] = new ImageIcon( getClass().getResource(String.format("assets/dice_%d.png",i)));
			}
			keep_1 = new JButton("Keep");
			keep_2 = new JButton("Keep");
			keep_3 = new JButton("Keep");
			keep_4 = new JButton("Keep");
			keep_5 = new JButton("Keep");
			
		// Make roll button and X markers
			roll = new JButton("Roll");
			counter_icon = new ImageIcon( getClass().getResource( "assets/marker.png" ) );
			roll_1 = new JLabel(counter_icon);
			roll_2 = new JLabel(counter_icon);
			roll_3 = new JLabel(counter_icon);
			diceIcons = new JLabel[5];
			for (int i = 0; i < 5; ++i){
				diceIcons[i] = new JLabel(diceImage[i]);
			}
  			roll_1.setVisible(false);
			roll_2.setVisible(false);
			roll_3.setVisible(false);
			
		// Make player scorecard button columns
			playerScores = new PlayerLabelPanel[numberOfPlayers];
			for(int i = 0; i < numberOfPlayers; i++){
				playerScores[i] = new PlayerLabelPanel();
				playerScores[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			
		// Make additional components
			JLabel scorecard = new JLabel("Scorecard");
			JButton total = new JButton ("<html><b>Total:</b></html>");
			JLabel empty_cell = new JLabel("                     ");
			playerTurnLabel = new JLabel();
			JButton upperBonus = new JButton("<html><b>Upper Bonus\n(Upper Section >= 63 Pts.):</b></html>");
			JButton upperTotal = new JButton("<html><b>Total Upper Section:</b></html>");
			JButton yahtzeeBonus = new JButton("<html><b>Yahtzee Bonus:</b></html>");
			upperBonus.setEnabled(false);
			upperTotal.setEnabled(false);
			yahtzeeBonus.setEnabled(false);
			total.setEnabled(false);
			if(numberOfPlayers == 1)
				playerTurnLabel = new JLabel();
			else
				playerTurnLabel = new JLabel(String.format("Turn: Player %d", currentPlayerCounter));
			
		// Add all components
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(scorecard, gbc);
			add(button_aces, gbc);
			add(button_twos, gbc);
			add(button_threes, gbc);
			add(button_fours, gbc);
			add(button_fives, gbc);
			add(button_sixes, gbc);
			add(upperBonus, gbc);
			add(upperTotal, gbc);
			add(button_threeKind, gbc);
			add(button_fourKind, gbc);
			add(button_fullHouse, gbc);
			add(button_smStraight, gbc);
			add(button_lgStraight, gbc);
			add(button_yahtzee, gbc);
			add(button_chance, gbc);
			add(yahtzeeBonus, gbc);
			add(total, gbc);
			for(int j = 0; j < numberOfPlayers; j++){
				int player_num = j+1;
				JLabel player_label = new JLabel("P" + player_num);
				gbc.gridx = j + 1;
				gbc.gridy = 0;
				gbc.gridheight = 1;
				add(player_label, gbc);

				gbc.gridy = 1;
				gbc.gridheight = 19;
				add(playerScores[j], gbc);
				System.out.println("Printing player scorecard " + j);
			}

			int offset = numberOfPlayers + 1;	// Used for spacing components to the right of scorecard
			gbc.gridx = offset + 1;
			gbc.gridy = 0;
			add(empty_cell, gbc);
			
			gbc.gridx = offset + 3;
			gbc.gridy = 11;
			add(roll_1, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 11;
			add(roll_2, gbc);
			
			gbc.gridx = offset + 5;
			gbc.gridy = 11;
			add(roll_3, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 8;
			add(roll, gbc);

			for (int i = 0; i < 5; ++i){
				gbc.gridx = offset + 2 + i;
				gbc.gridy = 5;
				add(diceIcons[i], gbc);
			}
			
			gbc.gridx = offset + 2;
			gbc.gridy = 2;
			keep_1.setEnabled(false);
			add(keep_1, gbc);
			
			gbc.gridx = offset + 3;
			gbc.gridy = 2;
			keep_2.setEnabled(false);
			add(keep_2, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 2;
			keep_3.setEnabled(false);
			add(keep_3, gbc);
			
			gbc.gridx = offset + 5;
			gbc.gridy = 2;
			keep_4.setEnabled(false);
			add(keep_4, gbc);
			
			gbc.gridx = offset + 6;
			gbc.gridy = 2;
			keep_5.setEnabled(false);
			add(keep_5, gbc);
			
			gbc.gridx = offset + 2;
			gbc.gridwidth = 3;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridy = 0;
			add(playerTurnLabel, gbc);
			
		// Action listener for the Roll button, 
			roll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Rolls an amount of random dice values
					Random rand = new Random();
					
					if(totalTurns == 0){
						button_aces.setEnabled(true);
						button_twos.setEnabled(true);
						button_threes.setEnabled(true);
						button_fours.setEnabled(true);
						button_fives.setEnabled(true);
						button_sixes.setEnabled(true);
						button_threeKind.setEnabled(true);
						button_fourKind.setEnabled(true);
						button_fullHouse.setEnabled(true);
						button_smStraight.setEnabled(true);
						button_lgStraight.setEnabled(true);
						button_yahtzee.setEnabled(true);
						button_chance.setEnabled(true);
					}
					if (currRoll == 1){
						keep_1.setEnabled(true);
						keep_2.setEnabled(true);
						keep_3.setEnabled(true);
						keep_4.setEnabled(true);
						keep_5.setEnabled(true);
						for (int i = 0; i < 5; ++i){
							if (!(dice[i].checkKept())){
								int newnum = rand.nextInt(6) + 1;
								dice[i].setValue(newnum);
								diceIcons[i].setIcon(diceImage[newnum - 1]);

							}
						}
						roll_1.setVisible(true);
						chooseScore();
						currRoll++;
					}
					else if (currRoll == 2){
						for (int i = 0; i < 5; ++i){
							if (!(dice[i].checkKept())){
								int newnum = rand.nextInt(6) + 1;
								dice[i].setValue(newnum);
								diceIcons[i].setIcon(diceImage[newnum - 1]);

							}
						}
						currRoll++;
						roll_2.setVisible(true);
					}
					else{
						for (int i = 0; i < 5; ++i){
							if (!(dice[i].checkKept())){
								int newnum = rand.nextInt(6) + 1;
								dice[i].setValue(newnum);
								diceIcons[i].setIcon(diceImage[newnum - 1]);

							}
						}
						roll.setEnabled(false);
						keep_1.setEnabled(false);
						keep_2.setEnabled(false);
						keep_3.setEnabled(false);
						keep_4.setEnabled(false);
						keep_5.setEnabled(false);
						roll_3.setVisible(true);
					}
                }
            });
			
		// Action listener for the five Keep buttons
			keep_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keep_1.setEnabled(false);
					dice[0].setKept(true);
                }
            });
			keep_2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keep_2.setEnabled(false);
					dice[1].setKept(true);
                }
            });
			keep_3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keep_3.setEnabled(false);
					dice[2].setKept(true);
                }
            });
			keep_4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keep_4.setEnabled(false);
					dice[3].setKept(true);
                }
            });
			keep_5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keep_5.setEnabled(false);
					dice[4].setKept(true);
                }
            });
		// Action listener for each of the 13 scorecard choices
			button_aces.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 1){
							counter++;
						}
					}
					players[currentPlayer].setAces(counter);
					players[currentPlayer].addSelected(0);
					resetTurn();
                }
            });
			button_twos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 2){
							counter += 2;
						}
					}
					players[currentPlayer].setTwos(counter);
					players[currentPlayer].addSelected(1);
					resetTurn();
                }
            });
			button_threes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 3){
							counter += 3;
						}
					}
					players[currentPlayer].setThrees(counter);
					players[currentPlayer].addSelected(2);
					resetTurn();
                }
            });
			button_fours.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 4){
							counter+=4;
						}
					}
					players[currentPlayer].setFours(counter);
					players[currentPlayer].addSelected(3);
					resetTurn();
                }
            });
			button_fives.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 5){
							counter+=5;
						}
					}
					players[currentPlayer].setFives(counter);
					players[currentPlayer].addSelected(4);
					resetTurn();
                }
            });
			button_sixes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 6){
							counter+=6;
						}
					}
					players[currentPlayer].setSixes(counter);
					players[currentPlayer].addSelected(5);
					resetTurn();
                }
            });
			button_threeKind.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] == 3){
							for (Dice d : dice){
								counter+=d.getValue();
							}
						}
					}
					players[currentPlayer].setThreeKind(counter);
					players[currentPlayer].addSelected(6);
					resetTurn();
                }
            });
			button_fourKind.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] == 4){
							for (Dice d : dice){
								counter+=d.getValue();
							}
						}
					}
					players[currentPlayer].setFourKind(counter);
					players[currentPlayer].addSelected(7);
					resetTurn();
                }
            });
			button_fullHouse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					//Full House Logic
					int counter = 0;
					boolean two = false;
					boolean three = false;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] == 2){
							two = true;
						}
						if (diceCounter[i] == 3){
							three = true;
						}
					}
					if (two == true && three == true){
						counter = 25;
					}
					players[currentPlayer].setFullHouse(counter);
					players[currentPlayer].addSelected(8);
					resetTurn();
                }
            });
			button_smStraight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					//Straight Logic
					int longestStraight = 0;
					int temp = 0;
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] >= 1){
							temp++;
						}
						else{
							if (temp > longestStraight){
								longestStraight = temp;
							}
							temp = 0;
						}
					}
					if (temp > longestStraight){
						longestStraight = temp;
					}
					if (longestStraight >= 4){
						counter = 30;
					}
                    players[currentPlayer].setSmStraight(counter);
					players[currentPlayer].addSelected(9);
					resetTurn();
                }
            });
			button_lgStraight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					//Straight Logic
					int longestStraight = 0;
					int temp = 0;
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] >= 1){
							temp++;
						}
						else{
							if (temp > longestStraight){
								longestStraight = temp;
							}
							temp = 0;
						}
					}
					if (temp > longestStraight){
						longestStraight = temp;
					}
					if (longestStraight >= 5){
						counter = 40;
					}
                    players[currentPlayer].setLgStraight(counter);
					players[currentPlayer].addSelected(10);
					resetTurn();
                }
            });
			button_yahtzee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] == 5){
							counter = 50;
						}
					}
                    players[currentPlayer].setYahtzee(counter);
					players[currentPlayer].addSelected(11);
					resetTurn();
                }
            });
			button_chance.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int counter = 0;
					for (Dice d : dice){
						counter+=d.getValue();
					}
					players[currentPlayer].setChance(counter);
					players[currentPlayer].addSelected(12);
					resetTurn();
                }
            });
			// End of game
        }

		public void nextPlayer(){
			playerScores[currentPlayer].label_aces.setText(String.format("   %d   ",players[currentPlayer].getAces()));
			playerScores[currentPlayer].label_twos.setText(String.format("   %d   ",players[currentPlayer].getTwos()));
			playerScores[currentPlayer].label_threes.setText(String.format("   %d   ",players[currentPlayer].getThrees()));
			playerScores[currentPlayer].label_fours.setText(String.format("   %d   ",players[currentPlayer].getFours()));
			playerScores[currentPlayer].label_fives.setText(String.format("   %d   ",players[currentPlayer].getFives()));
			playerScores[currentPlayer].label_sixes.setText(String.format("   %d   ",players[currentPlayer].getSixes()));
			playerScores[currentPlayer].label_threeKind.setText(String.format("   %d   ",players[currentPlayer].getThreeKind()));
			playerScores[currentPlayer].label_fourKind.setText(String.format("   %d   ",players[currentPlayer].getFourKind()));
			playerScores[currentPlayer].label_fullHouse.setText(String.format("   %d   ",players[currentPlayer].getFullHouse()));
			playerScores[currentPlayer].label_smStraight.setText(String.format("   %d   ",players[currentPlayer].getSmStraight()));
			playerScores[currentPlayer].label_lgStraight.setText(String.format("   %d   ",players[currentPlayer].getLgStraight()));
			playerScores[currentPlayer].label_yahtzee.setText(String.format("   %d   ",players[currentPlayer].getYahtzee()));
			playerScores[currentPlayer].label_chance.setText(String.format("   %d   ",players[currentPlayer].getChance()));
			playerScores[currentPlayer].label_upperBonus.setText(String.format("   %d   ",players[currentPlayer].getUpperBonus()));
			playerScores[currentPlayer].label_upperTotal.setText(String.format("   %d   ",players[currentPlayer].getUpper()));
			playerScores[currentPlayer].label_yahtzeeBonus.setText(String.format("   %d   ",players[currentPlayer].getYahtzeeBonus()));
			playerScores[currentPlayer].label_total.setText(String.format("   %d   ",players[currentPlayer].getTotal()));

			currentPlayer = (currentPlayer + 1)%numberOfPlayers;
			totalTurns++;
			button_aces.setEnabled(false);
			button_twos.setEnabled(false);
			button_threes.setEnabled(false);
			button_fours.setEnabled(false);
			button_fives.setEnabled(false);
			button_sixes.setEnabled(false);
			button_threeKind.setEnabled(false);
			button_fourKind.setEnabled(false);
			button_fullHouse.setEnabled(false);
			button_smStraight.setEnabled(false);
			button_lgStraight.setEnabled(false);
			button_yahtzee.setEnabled(false);
			button_chance.setEnabled(false);
		}
		public void chooseScore(){
			button_aces.setEnabled(players[currentPlayer].checkSelected(0));
			button_twos.setEnabled(players[currentPlayer].checkSelected(1));
			button_threes.setEnabled(players[currentPlayer].checkSelected(2));
			button_fours.setEnabled(players[currentPlayer].checkSelected(3));
			button_fives.setEnabled(players[currentPlayer].checkSelected(4));
			button_sixes.setEnabled(players[currentPlayer].checkSelected(5));
			button_threeKind.setEnabled(players[currentPlayer].checkSelected(6));
			button_fourKind.setEnabled(players[currentPlayer].checkSelected(7));
			button_fullHouse.setEnabled(players[currentPlayer].checkSelected(8));
			button_smStraight.setEnabled(players[currentPlayer].checkSelected(9));
			button_lgStraight.setEnabled(players[currentPlayer].checkSelected(10));
			button_yahtzee.setEnabled(players[currentPlayer].checkSelected(11));
			button_chance.setEnabled(players[currentPlayer].checkSelected(12));
		}
		public void resetTurn(){
			currRoll = 1;
			for (Dice d: dice){
				d.setKept(false);
			}
			roll.setEnabled(true);
			keep_1.setEnabled(false);
			keep_2.setEnabled(false);
			keep_3.setEnabled(false);
			keep_4.setEnabled(false);
			keep_5.setEnabled(false);
			roll_1.setVisible(false);
			roll_2.setVisible(false);
			roll_3.setVisible(false);
			nextPlayer();
			if(numberOfPlayers == 1)
				playerTurnLabel.setVisible(false);
			else{
				if(totalTurns == 0){
					currentPlayerCounter = 1;
					playerTurnLabel.setText(String.format("Turn: Player %d", currentPlayerCounter));
				}
				else{
					currentPlayerCounter = totalTurns%(numberOfPlayers) + 1;
					playerTurnLabel.setText(String.format("Turn: Player %d", currentPlayerCounter));
				}
			}
			// End game condition
			if(totalTurns == (13 * numberOfPlayers)){
				// Singleplayer
				if(numberOfPlayers == 1){
					playerTurnLabel.setVisible(true);
					playerTurnLabel.setText(String.format("Your score: %d", players[currentPlayer].getTotal()));
				}
				// Multiplayer
				else{
					// Find highest score
					int [] finalScores = new int[numberOfPlayers];
					for(int m = 0; m < numberOfPlayers; m++){
						finalScores[m] = players[m].getTotal();
					}
					for(int n = 0; n < numberOfPlayers; n++){
						if(finalScores[n] > finalScores[winningPlayer]){
							winningPlayer = n;
							isTie = false;
							/*if(finalScores[n] == finalScores[winningPlayer]){
								isTie = true;
							}*/
						}
					}
					if(!isTie)
						playerTurnLabel.setText(String.format("Winner is Player %d with score of %d", (winningPlayer + 1), players[winningPlayer].getTotal()));
					else
						playerTurnLabel.setText(String.format("Winning score was a tie!"));
				}
				roll.setEnabled(false);
				// Game has ended
			}
		}
    }
	class PlayerLabelPanel extends JPanel{

		JLabel label_aces;
		JLabel label_twos;
		JLabel label_threes;
		JLabel label_fours;
		JLabel label_fives;
		JLabel label_sixes;
		JLabel label_threeKind;
		JLabel label_fourKind;
		JLabel label_fullHouse;
		JLabel label_smStraight;
		JLabel label_lgStraight;
		JLabel label_yahtzee;
		JLabel label_chance;
		JLabel label_upperBonus;
		JLabel label_upperTotal;
		JLabel label_yahtzeeBonus;
		JLabel label_total;

		PlayerLabelPanel(){
			String unselected = "   0   ";
			setLayout(new GridBagLayout());
			GridBagConstraints label_gbc = new GridBagConstraints();
			// Make empty labels for each option
			label_aces = new JLabel(unselected);
			label_twos = new JLabel(unselected);
			label_threes = new JLabel(unselected);
			label_fours = new JLabel(unselected);
			label_fives = new JLabel(unselected);
			label_sixes = new JLabel(unselected);
			label_threeKind = new JLabel(unselected);
			label_fourKind = new JLabel(unselected);
			label_fullHouse = new JLabel(unselected);
			label_smStraight = new JLabel(unselected);
			label_lgStraight = new JLabel(unselected);
			label_yahtzee = new JLabel(unselected);
			label_chance = new JLabel(unselected);
			label_upperBonus = new JLabel(unselected);
			label_upperTotal = new JLabel(unselected);
			label_yahtzeeBonus = new JLabel(unselected);
			label_total = new JLabel(unselected);
			// Add components
			label_gbc.gridx = 0;
			label_gbc.gridy = 0;
			label_gbc.gridy = GridBagConstraints.RELATIVE;
			label_gbc.ipady = 10;
			add(label_aces, label_gbc);
			add(label_twos, label_gbc);
			add(label_threes, label_gbc);
			add(label_fours, label_gbc);
			add(label_fives, label_gbc);
			add(label_sixes, label_gbc);
			add(label_upperBonus, label_gbc);
			add(label_upperTotal,label_gbc);
			add(label_threeKind, label_gbc);
			add(label_fourKind, label_gbc);
			add(label_fullHouse, label_gbc);
			add(label_smStraight, label_gbc);
			add(label_lgStraight, label_gbc);
			add(label_yahtzee, label_gbc);
			add(label_chance, label_gbc);
			add(label_yahtzeeBonus, label_gbc);
			add(label_total, label_gbc);
		}
	}
}