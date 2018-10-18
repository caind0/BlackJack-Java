/* @ Cain
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxswingapplication1;

//imports necessary for application
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Game's logic and UI
 *
 * @author Cain
 * @version
 * 
 *
 */
public class BlackjackMain extends Application {
    
    //creates new deck, auto set without jokers
    private Deck deck = new Deck();
    
    
    //new hands for player and dealer
    private Hand dealer, player;
    
    //status message
    private Text message = new Text();
    
    //a boolean property that determines when the game can and cannot be played
    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    //integer propety that holds the 'pot' where players place their bets
    private SimpleIntegerProperty pot = new SimpleIntegerProperty(0);
    
    //boxes to hold player cards with 20 pixel spacing
    private HBox dealerCards = new HBox(20);
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        //initializes dealer hand
        dealer = new Hand(true, dealerCards.getChildren());
        //init player hand
        player = new Hand(false, playerCards.getChildren());
        
        
        //sets game background
        
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        Region background = new Region();
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        HBox rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle leftBG = new Rectangle(550, 560);
        leftBG.setArcWidth(50);
        leftBG.setArcHeight(50);
        leftBG.setFill(Color.GREEN);
        Rectangle rightBG = new Rectangle(230, 560);
        rightBG.setArcWidth(50);
        rightBG.setArcHeight(50);
        rightBG.setFill(Color.SADDLEBROWN);

        // LEFT
        VBox leftVBox = new VBox(50);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");
        Text chipCount = new Text("Chips: ");

        leftVBox.getChildren().addAll(dealerScore, dealerCards, message, playerCards, playerScore, chipCount);

        // RIGHT

        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);

        TextField bet = new TextField("BET");
        bet.setDisable(false);
        bet.setMaxWidth(50);

        Button btnBet = new Button("BET");
        Button btnPlay = new Button("PLAY");
        Button btnHit = new Button("HIT");
        Button btnStand = new Button("STAND");

        HBox buttonsHBox = new HBox(15, btnHit, btnStand);
        buttonsHBox.setAlignment(Pos.CENTER);

        rightVBox.getChildren().addAll(btnBet, bet, btnPlay, buttonsHBox);

        // ADD BOTH STACKS TO ROOT LAYOUT

        rootLayout.getChildren().addAll(new StackPane(leftBG, leftVBox), new StackPane(rightBG, rightVBox));
        root.getChildren().addAll(background, rootLayout);

        // BIND PROPERTIES

        btnPlay.disableProperty().bind(playable); //when is game playable, disable these buttons
        btnBet.disableProperty().bind(playable);
        btnHit.disableProperty().bind(playable.not()); //when the game is not playable, disable these buttons
        btnStand.disableProperty().bind(playable.not());
        
        //binds hand values
        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.cardValueProperty().asString())); 
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.cardValueProperty().asString()));
        
        //binds player chip count
        chipCount.textProperty().bind(new SimpleStringProperty("Current Number Of Chips: ").concat(player.getChipCount().asString()));
        
        
        /** Player listener, detects when hand values change and determines when to end the game*/
        player.cardValueProperty().addListener((obs, old, newValue) -> {
            System.out.println("Value Checked");
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });
          
        /** Dealer listener, detects when hand values change and determines when to end the game*/
        dealer.cardValueProperty().addListener((obs, old, newValue) -> {
            System.out.println("Value Checked");
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        // INIT BUTTONS
        
        /**Start game on play button click */
        btnPlay.setOnAction(event -> {
            startNewGame();
        });
        
        btnBet.setOnAction(event -> {
            if ((bet.getText() != null) && (!bet.getText().isEmpty())){
                try{
                    int betHere = Integer.parseInt(bet.getText());
                    pot.set(betHere);
                    message.setText("Bet placed!!!");
                } catch(NumberFormatException error){
                    message.setText("Please use a whole number!");
                }
            }
        });
        
        /** Hit(drawCard) on hitbutton click */
        btnHit.setOnAction(event -> {
            player.drawCard(deck.draw());
            player.setValue();
            System.out.println("HIT");
        });
        
        /**Move to dealer turn on stand button click */
        btnStand.setOnAction(event -> {
            //loop performs dealer turn
            
            while (dealer.cardValueProperty().intValue() < 17) {
                dealer.drawCard(deck.draw());
                dealer.setValue();
            }

            endGame(); // ends game
        });
        
        

        return root;
    }
    
    /** code to start new game */
    private void startNewGame() {
        System.out.println("Game Started!");
        //sets game to be playable 
        playable.set(true);
        
        //status message is blank
        message.setText("");
        
        //resets deck and shuffles
        deck.resetDeck();
        deck.shuffle();
        
        //clears player hands
        dealer.resetHand();
        player.resetHand();
        
        //draws starting cards for each player
        System.out.println("Dealer's Hand!");
        dealer.drawCard(deck.draw());
        dealer.drawCard(deck.draw());
        dealer.setValue(); 
        
        System.out.println("Player's Hand");
        player.drawCard(deck.draw());
        player.drawCard(deck.draw());
        player.setValue();
    }
    
    /** endgame code, determines who wins the round*/
    public void endGame() {
        //ends game
        playable.set(false);
        
        //checks if player didn't place a bet and automatically places 50 chips as an ante
        if(pot.intValue() == 0 ){
            pot.set(50);
        }
        
        //gets values for dealer and player
        int dealerValue = dealer.cardValueProperty().intValue();
        int playerValue = player.cardValueProperty().intValue();
        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

        // the order of checking is important
        // determines who wins
        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
                || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "DEALER";
            player.setChipCount(pot.intValue(), dealer);
            if(player.getChipCount().intValue() < 0){
                winner = "You're broke now just stop.\n DEALER: ";
            }
        }
        else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
            winner = "PLAYER";
            dealer.setChipCount(pot.intValue() + (pot.intValue() *2), player);
        }
        
        //clear pot before next game starts
        pot.set(0);
        //sets message to who won
        message.setText(winner + " WON");
    }
    
    //sets up window
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }
    
    //launches game
    public static void main(String[] args) {
        launch(args);
    }
}
