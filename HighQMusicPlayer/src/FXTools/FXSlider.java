package FXTools;

import MP.FXController;
import MP.PlayerModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class FXSlider {
	
	FXController fxcontroller;
	PlayerModel model;
	public FXSlider(FXController fxcontroller, PlayerModel model){
		this.fxcontroller = fxcontroller;
		this.model = model;
	}

	//**Note** tried to do a listener, could not figure out how to do it. That is why I'm multithreading
	//this thread is responsible for progressBar, checking clicks on listview, and shuffling when song ends
	public void startSliderThread(){
		fxcontroller.setSliderThread(new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
			
				//this is an infinite loop because now I only need to make this thread once, pausing and starting it, as opposed to making many threads
				for(;;){
					Thread.sleep(200);
					
					if(model.isPlaying()){
						fxcontroller.getVolumeBar().setProgress(model.getVolume());
						
						//This is for progressbar for music
						fxcontroller.getProgressBar().setValue(fxcontroller.getController().getPercentageDone());
						if(fxcontroller.getController().getPercentageDone() == 100){
							model.shuffle();
							fxcontroller.getFXListview().onMusicChangeSettings();

						}
					}

				}
			 
			 
			}
		
		});
		 
		new Thread(fxcontroller.getSliderThread()).start(); 
		
	}
	
	
	
	public void onMouseReleased(){
		model.setPercentageDone(fxcontroller.getProgressBar().getValue());
		if(!fxcontroller.getSliderThread().isRunning()){
			startSliderThread();
		}
	}
	
	
	
	public void onMousePressed(){
		if(fxcontroller.getSliderThread() != null && fxcontroller.getSliderThread().isRunning()){
			fxcontroller.getSliderThread().cancel();
		}
	}
	
	
	
	public void onDragDetected(){
		if(fxcontroller.getSliderThread() != null && fxcontroller.getSliderThread().isRunning()){
			fxcontroller.getSliderThread().cancel();
		}
	}
	
	
	//volume progress bar
	public void setOnVolumeClicked(MouseEvent event){
        double mouseX = event.getSceneX();
        double percent = (mouseX-890)/180;

        if(percent < .2){
        	Image volumeImageMute = new Image("images/volumeButtonMute.png");
        	fxcontroller.getVolumeImage().setImage(volumeImageMute);
        }else if(percent < .8){
        	Image volumeImageSecond = new Image("images/volumeButtonSecond.png");
        	fxcontroller.getVolumeImage().setImage(volumeImageSecond);
        }else{
        	Image volumeImage= new Image("images/volumeButton.png");
        	fxcontroller.getVolumeImage().setImage(volumeImage);
        }
        
        
        fxcontroller.getVolumeBar().setProgress(percent);
        model.setVolume(percent);
	}
}
