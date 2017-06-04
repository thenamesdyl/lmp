package FXTools;

import MP.FXController;
import MP.PlayerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
			//Multithreading with JavaFX. Essentially this other thread will check the slider to make sure its on track.
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

}
