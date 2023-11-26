import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YahtzeeJFrame extends JFrame {
    private int numberOfPlayers = 2;
    private MainMenuJPanel mainMenu;
    private GameBoardJPanel gameBoard;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    YahtzeeJFrame(){
        super("Yahtzee");
        mainMenu = new MainMenuJPanel();
        gameBoard = new GameBoardJPanel();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(mainMenu, "mainMenu");
        mainPanel.add(gameBoard, "gameBoard");
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 1600, 900 ); 
        cardLayout.show(mainPanel, "mainMenu");
    }
    private void startGame(){
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
        GameBoardJPanel(){
            JLabel label = new JLabel("This is the Game Menu");
            add(label);
        }
    }
}


