import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.border.EmptyBorder;

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
	private void returnGame(){
		numberOfPlayers = 2;
		cardLayout.show(mainPanel, "mainMenu");
		setJMenuBar(null);
	}
	private void restartGame(){
		cardLayout.show(mainPanel, "mainMenu");
		startGame();
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
		private boolean joker = false;
		private Player[] players;
		private PlayerLabelPanel [] playerScores;
		private Dice[] dice = new Dice[5];
		ImageIcon [] diceImage;
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
		private static JMenuBar menuBar;
		private static JMenu menu;
		private static JMenuItem restart, returnToMenu;

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

		GameBoardActionListener e;

        GameBoardJPanel(){
			e = new GameBoardActionListener();
			menuBar = new JMenuBar();
			menu = new JMenu("Options");
			restart = new JMenuItem("Restart Game");
			restart.setActionCommand("Restart");
			restart.addActionListener(e);
			returnToMenu = new JMenuItem("Return To Menu");
			returnToMenu.setActionCommand("Return");
			returnToMenu.addActionListener(e);
			menu.add(restart);
			menu.add(returnToMenu);
			menuBar.add(menu);
			setJMenuBar(menuBar);

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
			diceImage = new ImageIcon[6];
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
			LabelButton total = new LabelButton ("Total:");
			JLabel empty_cell = new JLabel("                     ");
			playerTurnLabel = new JLabel();
			LabelButton upperBonus = new LabelButton("Upper Bonus(Upper Section >= 63 Pts.):");
			LabelButton upperTotal = new LabelButton("Total Upper Section:");
			LabelButton yahtzeeBonus = new LabelButton("Yahtzee Bonus:");
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
			gbc.gridy = 13;
			add(roll_1, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 13;
			add(roll_2, gbc);
			
			gbc.gridx = offset + 5;
			gbc.gridy = 13;
			add(roll_3, gbc);
			
			gbc.gridx = offset + 4;
			gbc.gridy = 10;
			add(roll, gbc);

			for (int i = 0; i < 5; ++i){
				gbc.gridx = offset + 2 + i;
				gbc.gridy = 6;
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
			gbc.gridwidth = 5;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridy = 0;

			playerTurnLabel.setFont(playerTurnLabel.getFont().deriveFont(Font.BOLD, 20f));
			playerTurnLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
			add(playerTurnLabel, gbc);
			
		// Action listener for the Roll button, 
			roll.setActionCommand("Roll");
			roll.addActionListener(e);
			
		// Action listener for the five Keep buttons
			keep_1.setActionCommand("Keep1");
			keep_1.addActionListener(e);
			keep_2.setActionCommand("Keep2");
			keep_2.addActionListener(e);
			keep_3.setActionCommand("Keep3");
			keep_3.addActionListener(e);
			keep_4.setActionCommand("Keep4");
			keep_4.addActionListener(e);
			keep_5.setActionCommand("Keep5");
			keep_5.addActionListener(e);
		// Action listener for each of the 13 scorecard choices
			button_aces.setActionCommand("Aces");
			button_aces.addActionListener(e);
			button_twos.setActionCommand("Twos");
			button_twos.addActionListener(e);
			button_threes.setActionCommand("Threes");
			button_threes.addActionListener(e);
			button_fours.setActionCommand("Fours");
			button_fours.addActionListener(e);
			button_fives.setActionCommand("Fives");
			button_fives.addActionListener(e);
			button_sixes.setActionCommand("Sixes");
			button_sixes.addActionListener(e);
			button_threeKind.setActionCommand("ThreeKind");
			button_threeKind.addActionListener(e);
			button_fourKind.setActionCommand("FourKind");
			button_fourKind.addActionListener(e);
			button_fullHouse.setActionCommand("FullHouse");
			button_fullHouse.addActionListener(e);
			button_smStraight.setActionCommand("SmStraight");
			button_smStraight.addActionListener(e);
			button_lgStraight.setActionCommand("LgStraight");
			button_lgStraight.addActionListener(e);
			button_yahtzee.setActionCommand("Yahtzee");
			button_yahtzee.addActionListener(e);
			button_chance.setActionCommand("Chance");
			button_chance.addActionListener(e);
			// Set Player 1
			playerScores[currentPlayer].setBorder(BorderFactory.createLineBorder(Color.RED, 4));
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
			for(int i = 0; i < numberOfPlayers; i++){
				playerScores[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			playerScores[currentPlayer].setBorder(BorderFactory.createLineBorder(Color.RED, 4));
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
			int yahtzeeCounter = 0;
			int lastNum = 0;
			keep_1.setEnabled(true);
			keep_2.setEnabled(true);
			keep_3.setEnabled(true);
			keep_4.setEnabled(true);
			keep_5.setEnabled(true);
			for (Dice d : dice){
				d.setKept(false);
				if (d.getValue() == lastNum)
					yahtzeeCounter++;
				else
					lastNum = d.getValue();
			}
			if (yahtzeeCounter == 4){
				if (players[currentPlayer].checkSelected(11) == false && players[currentPlayer].getYahtzee() == 50){
					//Yahtzee BONUS
					players[currentPlayer].yahtzeeBonus();
					joker = true;
					if (players[currentPlayer].checkSelected(lastNum - 1) == true){
						joker = false;
						button_threeKind.setEnabled(false);
						button_fourKind.setEnabled(false);
						button_fullHouse.setEnabled(false);
						button_smStraight.setEnabled(false);
						button_lgStraight.setEnabled(false);
						button_yahtzee.setEnabled(false);
						button_chance.setEnabled(false);
					}
					else{
						boolean bottomTaken = true;
						for (int i = 6; i < 13; ++i){
							if (players[currentPlayer].checkSelected(i) == true){
								bottomTaken = false;
								break;
							}
						}
						if (bottomTaken){
							button_aces.setEnabled(players[currentPlayer].checkSelected(0));
							button_twos.setEnabled(players[currentPlayer].checkSelected(1));
							button_threes.setEnabled(players[currentPlayer].checkSelected(2));
							button_fours.setEnabled(players[currentPlayer].checkSelected(3));
							button_fives.setEnabled(players[currentPlayer].checkSelected(4));
							button_sixes.setEnabled(players[currentPlayer].checkSelected(5));
						}
						else{
							button_aces.setEnabled(false);
							button_twos.setEnabled(false);
							button_threes.setEnabled(false);
							button_fours.setEnabled(false);
							button_fives.setEnabled(false);
							button_sixes.setEnabled(false);
							button_threeKind.setEnabled(players[currentPlayer].checkSelected(6));
							button_fourKind.setEnabled(players[currentPlayer].checkSelected(7));
							button_fullHouse.setEnabled(players[currentPlayer].checkSelected(8));
							button_smStraight.setEnabled(players[currentPlayer].checkSelected(9));
							button_lgStraight.setEnabled(players[currentPlayer].checkSelected(10));
							button_yahtzee.setEnabled(players[currentPlayer].checkSelected(11));
							button_chance.setEnabled(players[currentPlayer].checkSelected(12));
						}
						
					}
				}
			}
			else{
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
					int max = 0;
					String winner = "";
					boolean isTie = false;
					for(int m = 0; m < numberOfPlayers; m++){
						if (players[m].getTotal() > max)
							max = players[m].getTotal();
					}
					for(int n = 0; n < numberOfPlayers; n++){
						if(players[n].getTotal() == max){
							if (winner.length() > 1){
								isTie = true;
								winner += String.format(" ,Player %d",(n+1));
							}
							else{
								winner = String.format("Player %d",(n+1));
							}
						}
					}
					if(!isTie)
						playerTurnLabel.setText(winner + " WINS!!!" + String.format(" | Score: %d",max));
					else
						playerTurnLabel.setText(winner + " TIED!!!" + String.format(" | Score: %d",max));
				}
				roll.setEnabled(false);
				// Game has ended
			}
		}
		//I like how the buttons look on the scoreboard but I want these buttons to always be disabled and have readable text
		public class LabelButton extends JButton {
			public LabelButton(String text) {
				super(text);
				setEnabled(false);
			}
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				FontMetrics metrics = g2d.getFontMetrics();
				int x = (getWidth() - metrics.stringWidth(getText())) / 2;
				int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
				g2d.drawString(getText(), x, y);
			}
		}
		class GameBoardActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				String button = e.getActionCommand();
				//Roll Logic
				if (button == "Roll"){
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
						chooseScore();
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
						roll_3.setVisible(true);
						chooseScore();
						keep_1.setEnabled(false);
						keep_2.setEnabled(false);
						keep_3.setEnabled(false);
						keep_4.setEnabled(false);
						keep_5.setEnabled(false);
					}
				}
				//Keep Logic
				else if(button == "Keep1"){
					keep_1.setEnabled(false);
					dice[0].setKept(true);
				}
				else if(button == "Keep2"){
					keep_2.setEnabled(false);
					dice[1].setKept(true);
				}
				else if(button == "Keep3"){
					keep_3.setEnabled(false);
					dice[2].setKept(true);
				}
				else if(button == "Keep4"){
					keep_4.setEnabled(false);
					dice[3].setKept(true);
				}
				else if(button == "Keep5"){
					keep_5.setEnabled(false);
					dice[4].setKept(true);
				}

				//Scoreboard Logic
				else if(button == "Aces"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 1){
							counter++;
						}
					}
					players[currentPlayer].setAces(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(0);
					resetTurn();
				}
				else if(button == "Twos"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 2){
							counter += 2;
						}
					}
					players[currentPlayer].setTwos(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(1);
					resetTurn();
				}
				else if(button == "Threes"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 3){
							counter += 3;
						}
					}
					players[currentPlayer].setThrees(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(2);
					resetTurn();
				}
				else if(button == "Fours"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 4){
							counter+=4;
						}
					}
					players[currentPlayer].setFours(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(3);
					resetTurn();
				}
				else if(button == "Fives"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 5){
							counter+=5;
						}
					}
					players[currentPlayer].setFives(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(4);
					resetTurn();
				}
				else if(button == "Sixes"){
					int counter = 0;
                    for (Dice d : dice){
						if (d.getValue() == 6){
							counter+=6;
						}
					}
					players[currentPlayer].setSixes(counter);
					if (players[currentPlayer].getUpper() >= 63){
						players[currentPlayer].setUpperBonus();
					}
					players[currentPlayer].addSelected(5);
					resetTurn();
				}
				else if(button == "ThreeKind"){
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] >= 3){
							for (Dice d : dice){
								counter+=d.getValue();
							}
						}
					}
					players[currentPlayer].setThreeKind(counter);
					players[currentPlayer].addSelected(6);
					resetTurn();
				}
				else if(button == "FourKind"){
					int counter = 0;
					int[] diceCounter = new int[6];
					for (Dice d : dice){
						diceCounter[d.getValue() - 1]++;
					}
					for (int i = 0; i < 6; ++i){
						if (diceCounter[i] >= 4){
							for (Dice d : dice){
								counter+=d.getValue();
							}
						}
					}
					players[currentPlayer].setFourKind(counter);
					players[currentPlayer].addSelected(7);
					resetTurn();
				}
				else if (button == "FullHouse"){
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
					if ((two == true && three == true) || joker == true){
						counter = 25;
					}
					players[currentPlayer].setFullHouse(counter);
					players[currentPlayer].addSelected(8);
					resetTurn();
				}
				else if (button == "SmStraight"){
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
					if (longestStraight >= 4 || joker == true){
						counter = 30;
					}
                    players[currentPlayer].setSmStraight(counter);
					players[currentPlayer].addSelected(9);
					resetTurn();
				}
				else if (button == "LgStraight"){
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
					if (longestStraight >= 5 || joker == true){
						counter = 40;
					}
                    players[currentPlayer].setLgStraight(counter);
					players[currentPlayer].addSelected(10);
					resetTurn();
				}
				else if (button == "Yahtzee"){
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
				else if (button == "Chance"){
					int counter = 0;
					for (Dice d : dice){
						counter+=d.getValue();
					}
					players[currentPlayer].setChance(counter);
					players[currentPlayer].addSelected(12);
					resetTurn();
				}
				else if (button == "Restart"){
					restartGame();
				}
				else if (button == "Return"){
					returnGame();
				}
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