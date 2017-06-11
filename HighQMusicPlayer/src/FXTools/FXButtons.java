package FXTools;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import MP.FXController;
import MP.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

//decided not to use inherency because inherency is evil
public class FXButtons {
	FXController fxcontroller;
	PlayerModel model;
	
	public FXButtons(FXController fxcontroller, PlayerModel model){
		this.fxcontroller = fxcontroller;
		this.model = model;
	}
	
	public void clickedShuffleButton(){
			    
		if(model.isFirstTimePlaying() || (!model.isPlaying())){
			fxcontroller.getProgressBar().setOpacity(100);
			fxcontroller.getFXSlider().startSliderThread();
			model.setFirstTimePlaying(false);
		}
				
	    
				
		//this will start the music on the next song
		fxcontroller.getPlay().setText("⏸");
		fxcontroller.getController().shuffle();
		fxcontroller.getFXListview().onMusicChangeSettings();
		
	}
	
	
	public void clickedPlayButton(){
		
		
		if(model.isFirstTimePlaying()){
			fxcontroller.getProgressBar().setOpacity(100);
			fxcontroller.getController().play(); //this will pause/start the music
			fxcontroller.getFXListview().onMusicChangeSettings(); //selects song thats playing
			model.setFirstTimePlaying(false);
		}else{
			fxcontroller.getController().play();
		}
		
		if(!model.isPlaying()){
			fxcontroller.getPlay().setText("▶");
			fxcontroller.getSliderThread().cancel();
			
			
		}else{
			
			//unfortunately have to create another thread as I could not figure out how to simply pause the thread
			fxcontroller.getPlay().setText("⏸");
			fxcontroller.getFXSlider().startSliderThread();
		}
	}
	
	
	
	public void clickedBackButton(){

		if(model.isSongBackward()){
			fxcontroller.getController().skipBackward();
			if(!fxcontroller.getSliderThread().isRunning()){
				fxcontroller.getFXSlider().startSliderThread();
			}
			fxcontroller.getFXListview().onMusicChangeSettings();
		}
		
	}
	
	public void  clickedBrowseButton(){
		
		DirectoryChooser DirectoryChooser= new DirectoryChooser();
		DirectoryChooser.setTitle("Open Resource File");
		File file = DirectoryChooser.showDialog(fxcontroller.getPrimaryStage());
		if (file != null) {
			BufferedWriter bw; 
			try {
				File defaultMusicPath = new File("defaultMusicPath.txt");
				
				//using printwriter because it overwrites anything thats currently in the file.
				PrintWriter writer = new PrintWriter(defaultMusicPath);
				writer.print(file.getAbsolutePath());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
            fxcontroller.resetDirectory(file.getAbsolutePath());
        }
		
		
	}
	
	public void clickedDownloadMusicButton(){
		String textLink = "";
		System.out.println(fxcontroller.getYoutubeLinkTextArea().getText());
		if(fxcontroller.getYoutubeLinkTextArea().getText() != null && !(fxcontroller.getYoutubeLinkTextArea().getText().equals(""))){
			textLink = fxcontroller.getYoutubeLinkTextArea().getText();
		}else{
			System.out.println("here");
			textLink = fxcontroller.getWebView().getEngine().getLocation();
		}
		
		System.out.println(textLink);
		
		
	}

}
