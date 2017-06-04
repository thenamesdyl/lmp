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
	
	
	public void startSelectionListener(){
		//this is for the selection of songs via listView. When you click a song via listview it plays the song
		fxcontroller.getSongList().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
			int mostRecentSelectedIndex = fxcontroller.getSongList().selectionModelProperty().get().getSelectedIndex();
					
			try {
					
					
				if(fxcontroller.getSongList().getItems().get(mostRecentSelectedIndex).length() > 23 && model.getCurrentSong().length() > 23){
					if(!(fxcontroller.getSongList().getItems().get(mostRecentSelectedIndex).substring(0, 23).equals(model.getCurrentSong().substring(0, 23)))){
							
						fxcontroller.getPlay().setText("⏸");
						playSpecificSong(fxcontroller.getSongList().selectionModelProperty().get().getSelectedIndex());
						
					}
				}else{
					if(!(fxcontroller.getSongList().getItems().get(mostRecentSelectedIndex).equals(model.getCurrentSong()))){
								
						fxcontroller.getPlay().setText("⏸");
						playSpecificSong(fxcontroller.getSongList().selectionModelProperty().get().getSelectedIndex());
							
					}
				}
			}catch (NullPointerException n){
				//if theres a null pointer exception on model.getCurrentSong, it means it hasnt started yet, therefore PLAY!!!
				fxcontroller.getPlay().setText("⏸");			
				playSpecificSong(fxcontroller.getSongList().selectionModelProperty().get().getSelectedIndex());
						
			}catch (ArrayIndexOutOfBoundsException a){
						
			}
		});
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
	
	
	
	//when someone clicks on song from listview. This is called by the listener
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
