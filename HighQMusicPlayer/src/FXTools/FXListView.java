package FXTools;

import MP.FXController;
import MP.PlayerModel;

public class FXListView {
	FXController fxcontroller;
	PlayerModel model;
	
	public FXListView(FXController fxcontroller, PlayerModel model){
		this.fxcontroller = fxcontroller;
		this.model = model;
	}
	
	//Observable list is required for setting the items in listView
	public void resetObservableList(){
		fxcontroller.setFileCollection(model.getFileCollection());
		fxcontroller.getObservableList().clear();
		//for trimming the text
		String newText;
		for(int i = 0; i <model.getNumOfFiles(); i++){
			if(fxcontroller.getFileCollection()[i].getName().length() > 23){
				newText = fxcontroller.getFileCollection()[i].getName().substring(0,23) + "...";
				
			}else{
				newText = fxcontroller.getFileCollection()[i].getName();
			}
			
			fxcontroller.getObservableList().add(i,newText);
		}
		
		//this alphabatizes the list
		java.util.Collections.sort(fxcontroller.getObservableList());
	}
	
	
	
	//when someone clicks on song from listview
	public void playSpecificSong(int indice){
		if(!model.isPlaying()){
			fxcontroller.getProgressBar().setOpacity(100);
			model.setFirstTimePlaying(false);
			fxcontroller.getFXSlider().startSliderThread();
		}
		fxcontroller.getController().playSpecificSong(indice);
		onMusicChangeSettings();

	}
	
	//method is responsible for finding the song that is being played and selecting that song on the listView
		public void onMusicChangeSettings(){
			fxcontroller.setIndice(0);
			for(int i = 0; i < fxcontroller.getSongList().getItems().size(); i++){
				
				if(fxcontroller.getSongList().getItems().get(i).length() > 23 && model.getCurrentSong().length() > 23){
					if(fxcontroller.getSongList().getItems().get(i).substring(0, 23).equals(model.getCurrentSong().substring(0, 23))){
						fxcontroller.setIndice(i+1);
						break;
					}
				}else{
					if(fxcontroller.getSongList().getItems().get(i).equals(model.getCurrentSong())){
						fxcontroller.setIndice(i+1);
						break;
					}
				}
				
			}
			if(fxcontroller.getIndice() != 0){
				fxcontroller.getSongList().selectionModelProperty().get().selectRange(fxcontroller.getIndice()-1, fxcontroller.getIndice());
				fxcontroller.getArtistPane().setText(model.getCurrentSong());
			}
		}

}
