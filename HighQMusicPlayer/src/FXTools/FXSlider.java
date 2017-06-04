package FXTools;

import MP.FXController;
import MP.PlayerModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
				boolean fxApplicationThread = Platform.isFxApplicationThread();
				System.out.println("Is call on FXApplicationThread: " + fxApplicationThread);
			
				//this is an infinite loop because now I only need to make this thread once, pausing and starting it, as opposed to making many threads
				for(;;){
					Thread.sleep(200);
					
					if(model.isPlaying()){
						fxcontroller.getVolumeBar().setProgress(model.getVolume());
						
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
        System.out.println(mouseX);
        double percent = (mouseX-890)/180;

        System.out.println(percent);
        fxcontroller.getVolumeBar().setProgress(percent);
        model.setVolume(percent);
	}
}
