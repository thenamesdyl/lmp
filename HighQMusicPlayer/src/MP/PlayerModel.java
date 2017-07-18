package MP;

import java.io.*;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerModel {
	private int numOfFiles;
	private File collectionOfFiles[] = new File[2000];
	private File currentSong;
	private Stack<Integer> lastRandomNumber = new Stack<Integer>();
	private MediaPlayer mediaPlayer;
	private int randomNum;
	private double durationInMillisecs;
	//had to instantiate these variables up here so the garbage collector wouldn't erase these and stop the music
	private Media hit;
	private String bip;
	private boolean startLoop = false;
	private boolean firstTimePlaying = true;
	private double volumeSetting = .5;
	private int songCounter = 0;
	
	//for some reason mediaPlayer does not have an isPlaying method, so I have to use this boolean
	private boolean isPlaying = false;
	
	
    public PlayerModel() {
    	//this line is required for javafx
    	final JFXPanel fxPanel = new JFXPanel();
    	String fileName = "defaultMusicPath.txt";

        String path = "";
        
    	try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((path = bufferedReader.readLine()) != null) {
                break;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    	
    	if(path != ""){
    		setupMusic(path);
    	}else{
    		String username = System.getProperty("user.name");
    		setupMusic(new String("C:/Users/" + username + "/music"));
    	}
	}
    
    public void resetMusicPath(){
    	for(int i = 0; i < numOfFiles; i++){
    		collectionOfFiles[i] = null;
    	}
    	songCounter = 0;
    	numOfFiles = 0;
    }
    
    public void setupMusic(String path){
    	//purpose of these lines is to set path, and then to add all the songs into a collection which can be referenced later. Uses recursion if it hurts a directory
    	File f = new File(path);
        for (File file : f.listFiles()) {
                if (file.isFile()) {
                	if(file.getName().substring(file.getName().length()-3, file.getName().length()).toLowerCase().equals("wav") || file.getName().substring(file.getName().length()-3, file.getName().length()).toLowerCase().equals("mp3") || file.getName().substring(file.getName().length()-3, file.getName().length()).toLowerCase().equals("m4p")){
                		collectionOfFiles[songCounter] = file;
                		songCounter++;
                	}
                }else if(file.isDirectory()){
            		setupMusic(path + "/" + file.getName());
            	}
        }
        numOfFiles = songCounter;
    }
	
	public void play() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		if(currentSong == null){
			this.shuffle();
		}else if(isPlaying){
			mediaPlayer.pause();
			isPlaying = !isPlaying;
		}else{
			mediaPlayer.play();
			isPlaying = !isPlaying;
		}

	}
	
	//plays specific song when one clicks the listView
	public void playSpecificSong(String songName) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{

		if(isPlaying){
			mediaPlayer.stop();
		}
		int indice = 0;
		for(int i = 0; i < numOfFiles; i++){
			if(songName.length() > 23 && collectionOfFiles[i].getName().length() > 23){
				if(songName.substring(0,23).equals(collectionOfFiles[i].getName().substring(0,23))){
					indice = i;
					
				}
			}else{
				if( songName.equals(collectionOfFiles[i].getName())){
					indice = i;
				}
			}
		}
		
		currentSong = collectionOfFiles[indice];
		lastRandomNumber.add(indice);
		bip = collectionOfFiles[indice].getPath();
		
		hit = new Media(Paths.get(bip).toUri().toString());
		mediaPlayer = new MediaPlayer(hit);
		setVolume(volumeSetting);
		
		findingDurationInSeconds();
		
		mediaPlayer.setStopTime(new Duration(durationInMillisecs));
        
		mediaPlayer.play();
		
		isPlaying = true;
		
	}
	
	public void toggleLoop(){
		startLoop = !startLoop;
		
		//if the loop is now turned on it will set repeat to the current song
		if(startLoop){
			mediaPlayer.setOnRepeat((Runnable) currentSong);
		}
	}
	
	public void shuffle() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		
		//turns off toggleLoop
		if(startLoop){
			toggleLoop();
		}
		//stops songs from overlapping
		if(isPlaying){
			mediaPlayer.stop();
		}
		//used for remembering the past songs
		
		randomNum = ThreadLocalRandom.current().nextInt(0, numOfFiles);
		lastRandomNumber.add(randomNum);

		
		bip = collectionOfFiles[randomNum].getPath();
		currentSong = collectionOfFiles[randomNum];
		
		hit = new Media(Paths.get(bip).toUri().toString());
		mediaPlayer = new MediaPlayer(hit);
		setVolume(volumeSetting);
		//using JLayer, JAudioTagger, and MP3 Plugin to read mp3 files. Will add WAV functionality. It needs to be here for the getPercentageDone method
		findingDurationInSeconds();
		
        //Had to set stop time for the song because JavaFX could not grab the end time
        mediaPlayer.setStopTime(new Duration(durationInMillisecs));
		        
		mediaPlayer.play();
		
		isPlaying = true;
		


	}
	
	//finding duration in seconds for the runners slider
	private void findingDurationInSeconds() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		double durationInSeconds;
		AudioFile audioFile = AudioFileIO.read(currentSong);
		durationInSeconds = audioFile.getAudioHeader().getTrackLength();
		durationInMillisecs = durationInSeconds*1000;
	}
	
	public String getCurrentSong(){
		return currentSong.getName();
		
	}
	
	public boolean isSongBackward(){
		if (lastRandomNumber.size() <= 1){
			return false;
		}else{
			return true;
		}
	}
	
	public void skipBackward() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		
		//turns off toggleLoop
		if(startLoop){
			toggleLoop();
		}
		
		if(isPlaying){
			mediaPlayer.stop();
		}
		
		
		//Using MediaPlayer from JavaFX and a stack to get the songs that were already played
		lastRandomNumber.pop();
		int newCurrentSong = (int) lastRandomNumber.peek();
		bip = collectionOfFiles[newCurrentSong].getPath();
		currentSong = collectionOfFiles[newCurrentSong];
		
	    hit = new Media(new File(bip).toURI().toString());
		mediaPlayer = new MediaPlayer(hit);
		setVolume(volumeSetting);
		//using JLayer, JAudioTagger, and MP3 Plugin to read mp3 files. Will add WAV functionality. It needs to be here for the getPercentageDone method
		findingDurationInSeconds();
        
        //Had to set stop time for the song because JavaFX could not grab the end time
        mediaPlayer.setStopTime(new Duration(durationInMillisecs));
		
		mediaPlayer.play();

		isPlaying = true;
		
	}
	
	public void stopMusic(){
		mediaPlayer.stop();
	}
	
	public double getPercentageDone(){
		if(isPlaying){
	        
	        return (mediaPlayer.getCurrentTime().toMillis()/mediaPlayer.getStopTime().toMillis())*100;
	        
		}else{
			return 0.0;
		}
	}
	
	public void setPercentageDone(double percentage){
		
		
		
		
		//because theres no setCurrentTime method, I am stopping the song and starting it at a new stop time
		
		//first need to grab the stop time so we know the length of the song
		double stopTime = mediaPlayer.getStopTime().toMillis();
		//now use the percentage to find the current time we want to be at
		double newStartTime = stopTime*(percentage/100);
		
		mediaPlayer.stop();
		isPlaying = false;
		
	    //now to start the mediaPlayer again...
		bip = currentSong.getPath();
		
		hit = new Media(new File(bip).toURI().toString());
		mediaPlayer = new MediaPlayer(hit);
		setVolume(volumeSetting);
		mediaPlayer.setStartTime(new Duration(newStartTime));
		mediaPlayer.play();
		isPlaying = true;
		
		
	}
	
	//Using this so other classes dont have to reference local variables
	public boolean isPlaying(){
		return isPlaying;
	}
	public void setIsPlaying(boolean isPlaying){
		this.isPlaying = isPlaying;
	}
	public boolean isFirstTimePlaying() {
		return firstTimePlaying;
	}
	public void setFirstTimePlaying(boolean firstTime) {
		this.firstTimePlaying = firstTime;
	}
	public int getSongCounter() {
		return songCounter;
	}
	public void setSongCounter(int songCounter) {
		this.songCounter = songCounter;
	}
	
    public void setVolume(double percent){
    	
    	//if its the first time, mediaplayer hasnt been created yet
    	if(isFirstTimePlaying()){
    		volumeSetting = percent;
    	}else{
    		volumeSetting = percent;
    		mediaPlayer.setVolume(percent);
    	}
    }
    
    public double getVolume(){
    	return volumeSetting;
    }
    //for the ObservableList for songView in FXCollection
    public File[] getFileCollection(){
    	return collectionOfFiles;
    }
    
    public double getCurrentTimeOfMusic(){
    	return mediaPlayer.getCurrentTime().toMillis();
    }
    public int getNumOfFiles(){
    	return numOfFiles;
    }
	
	

}
