package application;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

// Client/server application program for determining if a number entered is prime or not.
// Jennifer Berner

public class PrimeServer extends Application {  
	@Override  
	public void start(Stage primaryStage) {        
	TextArea ta = new TextArea();    
	Scene scene = new Scene(new ScrollPane(ta), 450, 200);
	primaryStage.setTitle("Server");   
	primaryStage.setScene(scene);  
	primaryStage.show();  
	new Thread( () -> {      
	try {              
		ServerSocket serverSocket = new ServerSocket(8000);       
		Platform.runLater(() ->        
		ta.appendText("Server started at " + new Date() + '\n'));       
		Socket socket = serverSocket.accept();           
		DataInputStream inputFromClient = new DataInputStream(       
				socket.getInputStream());       
		DataOutputStream outputToClient = new DataOutputStream(        
				socket.getOutputStream()); 
		while (true) {               
			double num = inputFromClient.readDouble();
			int i; 
			int flag=0;
			if(num==0||num==1) {
				String result = "is not prime.";	
				outputToClient.writeDouble(num);  
				Platform.runLater(() -> {     
					ta.appendText("Number received from client to check prime number is: " + num + '\n');         
					ta.appendText(num + " " + result + '\n');  
					});
		       }else {
		       for(i=2;i<=num/2;i++) {
		    	   if(num%i==0) {
		    		   String result = "is not prime.";
		    		   flag=1;
		    		   outputToClient.writeDouble(num);  
		   			Platform.runLater(() -> {     
		   				ta.appendText("Number received from client to check prime number is: " + num + '\n');         
		   				ta.appendText(num + " " + result + '\n');  
		   				});
		   			break;
		    	   }
		       }
		           if(flag==0) {
		        	   String result = "is prime.";
		        	   outputToClient.writeDouble(num);  
		   			Platform.runLater(() -> {     
		   				ta.appendText("Number received from client to check prime number is: " + num + '\n');         
		   				ta.appendText(num + " " + result + '\n'); 
		   				});
		           }}}}      
	catch(IOException ex) {       
		ex.printStackTrace();
		}
	}).start();
	}   
public static void main(String[] args) { 
	launch(args); 
	}
}