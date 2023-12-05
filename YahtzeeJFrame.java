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
	private Dice[] dice = new Dice[5];
	private Player[] players;
	private int currRoll = 1;
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
		for (int i = 0; i < 5; ++i){
			dice[i] = new Dice();
		}
    }
    private void startGame(){
		gameBoard = new GameBoardJPanel();
		players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; ++i){
			players[i] = new Player();
		}
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
		int currentPlayer = 0;
		PlayerLabelPanel [] playerScores;
        GameBoardJPanel(){
		// Set layout for the game panel
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
        // Make labels for each of the score selections
			JButton button_aces = new JButton("Aces");
			JButton button_twos = new JButton("Twos");
			JButton button_threes = new JButton("Threes");
			JButton button_fours = new JButton("Fours");
			JButton button_fives = new JButton("Fives");
			JButton button_sixes = new JButton("Sixes");
			JButton button_threeKind = new JButton("Three of a Kind");
			JButton button_fourKind = new JButton("Four of a Kind");
			JButton button_fullHouse = new JButton("Full House");
			JButton button_smStraight = new JButton("Small Straight");
			JButton button_lgStraight = new JButton("Large Straight");
			JButton button_yahtzee = new JButton("Yahtzee");
			JButton button_chance = new JButton("Chance");
			
		// Make dice faces and keep buttons
			ImageIcon [] diceImage = new ImageIcon[6];
			for (int i = 1; i <= 6; ++i){
				diceImage[i-1] = new ImageIcon( getClass().getResource(String.format("assets/dice_%d.png",i)));
			}
			/*
			ImageIcon dice1 = new ImageIcon( getClass().getResource( "assets/dice_1.png" ) );
			ImageIcon dice2 = new ImageIcon( getClass().getResource( "assets/dice_2.png" ) );
			ImageIcon dice3 = new ImageIcon( getClass().getResource( "assets/dice_3.png" ) );
			ImageIcon dice4 = new ImageIcon( getClass().getResource( "assets/dice_4.png" ) );
			ImageIcon dice5 = new ImageIcon( getClass().getResource( "assets/dice_5.png" ) );
			ImageIcon dice6 = new ImageIcon( getClass().getResource( "assets/dice_6.png" ) );
			*/
			// Dice faces will be loaded randomly into five dice JLabels later during the roll listener 
			JButton keep_1 = new JButton("Keep");
			JButton keep_2 = new JButton("Keep");
			JButton keep_3 = new JButton("Keep");
			JButton keep_4 = new JButton("Keep");
			JButton keep_5 = new JButton("Keep");
			
		// Make roll button and X markers
			JButton roll = new JButton("Roll");
			Icon counter_icon = new ImageIcon( getClass().getResource( "assets/marker.png" ) );
			JLabel roll_1 = new JLabel(counter_icon);
			JLabel roll_2 = new JLabel(counter_icon);
			JLabel roll_3 = new JLabel(counter_icon);
			JLabel[] diceIcons = new JLabel[5];
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
			JLabel total = new JLabel ("TOTAL:");
			JLabel curr_player = new JLabel("Player 1");
			JLabel empty_cell = new JLabel("               ");
			
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
			add(button_threeKind, gbc);
			add(button_fourKind, gbc);
			add(button_fullHouse, gbc);
			add(button_smStraight, gbc);
			add(button_lgStraight, gbc);
			add(button_yahtzee, gbc);
			add(button_chance, gbc);
			add(total, gbc);
			for(int j = 0; j < numberOfPlayers; j++){
				int player_num = j+1;
				JLabel player_label = new JLabel("P" + player_num);
				gbc.gridx = j + 1;
				gbc.gridheight = 1;
				add(player_label, gbc);
				
				gbc.gridy = 0;
				gbc.gridheight = 15;
				add(playerScores[j], gbc);
				System.out.println("Printing player scorecard " + j);
			}

			int offset = numberOfPlayers + 1;	// Used for spacing components to the right of scorecard
			gbc.gridx = offset + 1;
			gbc.gridy = 0;
			add(empty_cell, gbc);
			
			gbc.gridx = offset + 3;
			gbc.gridy = 10;
			add(roll_1, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 10;
			add(roll_2, gbc);
			
			gbc.gridx = offset + 5;
			gbc.gridy = 10;
			add(roll_3, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 7;
			add(roll, gbc);

			for (int i = 0; i < 5; ++i){
				gbc.gridx = offset + 2 + i;
				gbc.gridy = 4;
				add(diceIcons[i], gbc);
			}
			
			gbc.gridx = offset + 2;
			gbc.gridy = 0;
			keep_1.setEnabled(false);
			add(keep_1, gbc);
			
			gbc.gridx = offset + 3;
			gbc.gridy = 0;
			keep_2.setEnabled(false);
			add(keep_2, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 0;
			keep_3.setEnabled(false);
			add(keep_3, gbc);
			
			gbc.gridx = offset + 5;
			gbc.gridy = 0;
			keep_4.setEnabled(false);
			add(keep_4, gbc);
			
			gbc.gridx = offset + 6;
			gbc.gridy = 0;
			keep_5.setEnabled(false);
			add(keep_5, gbc);
			
		// Action listener for the Roll button, 
			roll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Rolls an amount of random dice values equal to (6 - how many dice are being kept)
					Random rand = new Random();
					
					// Adds to reroll_counter, which the visual roll indicators are dependent on
					/*
					if(first roll)
						then roll_1 is set visible
					if(second roll)
						then roll_2 is set visible
					if(third roll)
						then roll_3 is set visible
						force a choice and then turn is incremented and rolls reset, roll markers are set invisible again
					*/
					if (currRoll == 1){
						System.out.println("Rolling 1");
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
						currRoll++;
					}
					else if (currRoll == 2){
						System.out.println("Rolling 2");
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
						System.out.println("Roll 3");
						for (int i = 0; i < 5; ++i){
							if (!(dice[i].checkKept())){
								int newnum = rand.nextInt(6) + 1;
								dice[i].setValue(newnum);
								diceIcons[i].setIcon(diceImage[newnum - 1]);

							}
						}
						roll.setEnabled(false);
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
			
		// Action listener for each of the 12 scorecard choices
			button_aces.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_twos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_threes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_fours.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_fives.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_sixes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_threeKind.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_fourKind.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_fullHouse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_smStraight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_lgStraight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_yahtzee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
			button_chance.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
        }

		public void nextPlayer(){
			currentPlayer = (currentPlayer + 1)%numberOfPlayers;
			String temp = String.format("%d",players[currentPlayer].getAces());
			playerScores[currentPlayer].label_aces.setText(temp);
			temp = String.format("%d",players[currentPlayer].getTwos());
			playerScores[currentPlayer].label_twos.setText(temp);
			temp = String.format("%d",players[currentPlayer].getThrees());
			playerScores[currentPlayer].label_threes.setText(temp);
			temp = String.format("%d",players[currentPlayer].getFours());
			playerScores[currentPlayer].label_fours.setText(temp);
			temp = String.format("%d",players[currentPlayer].getFives());
			playerScores[currentPlayer].label_fives.setText(temp);
			temp = String.format("%d",players[currentPlayer].getSixes());
			playerScores[currentPlayer].label_sixes.setText(temp);
			temp = String.format("%d",players[currentPlayer].getThreeKind());
			playerScores[currentPlayer].label_threeKind.setText(temp);
			temp = String.format("%d",players[currentPlayer].getFourKind());
			playerScores[currentPlayer].label_fourKind.setText(temp);
			temp = String.format("%d",players[currentPlayer].getFullHouse());
			playerScores[currentPlayer].label_fullHouse.setText(temp);
			temp = String.format("%d",players[currentPlayer].getSmStraight());
			playerScores[currentPlayer].label_smStraight.setText(temp);
			temp = String.format("%d",players[currentPlayer].getLgStraight());
			playerScores[currentPlayer].label_lgStraight.setText(temp);
			temp = String.format("%d",players[currentPlayer].getYahtzee());
			playerScores[currentPlayer].label_yahtzee.setText(temp);
			temp = String.format("%d",players[currentPlayer].getChance());
			playerScores[currentPlayer].label_chance.setText(temp);
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

		PlayerLabelPanel(){
			String unselected = "  XX  ";
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
			// Add components
			label_gbc.gridx = 0;
			label_gbc.gridy = 1;
			label_gbc.gridy = GridBagConstraints.RELATIVE;
			label_gbc.ipady = 10;
			add(label_aces, label_gbc);
			add(label_twos, label_gbc);
			add(label_threes, label_gbc);
			add(label_fours, label_gbc);
			add(label_fives, label_gbc);
			add(label_sixes, label_gbc);
			add(label_threeKind, label_gbc);
			add(label_fourKind, label_gbc);
			add(label_fullHouse, label_gbc);
			add(label_smStraight, label_gbc);
			add(label_lgStraight, label_gbc);
			add(label_yahtzee, label_gbc);
			add(label_chance, label_gbc);
		}
	}
}