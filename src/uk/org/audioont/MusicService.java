package uk.org.audioont;

import java.util.ArrayList;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 * 
 * Sue Smith - February 2014
 */

public class MusicService extends Service implements 
MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener {

	//media player
	private MediaPlayer player;
	//song list
	private ArrayList<Song> songs;
	//current position
	private int songPosn;
	//binder
	private final IBinder musicBind = new MusicBinder();

	public void onCreate(){
		//create the service
	     System.out.println("onCreate*************");
		super.onCreate();
		//initialize position
		songPosn=0;
		//create player
		player = new MediaPlayer();
		//initialize
		initMusicPlayer();
	}

	public void initMusicPlayer(){
		System.out.println("initMusicPlayer*************");
		//set player properties
		player.setWakeMode(getApplicationContext(), 
				PowerManager.PARTIAL_WAKE_LOCK);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		//set listeners
		player.setOnPreparedListener(this);
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
	}

	//pass song list
	public void setList(ArrayList<Song> theSongs){
		songs=theSongs;
	}

	//binder
	public class MusicBinder extends Binder {
		
		MusicService getService() { 
			System.out.println("MusicBinder*************");
			return MusicService.this;
		}
	}

	//activity will bind to service
	@Override
	public IBinder onBind(Intent intent) {
		return musicBind;
	}

	//release resources when unbind
	@Override
	public boolean onUnbind(Intent intent){
		System.out.println("MusicBinder*************");
		player.stop();
		player.release();
		return false;
	}

	//play a song
	public void playSong(){
		System.out.println("playSong*************");
		//play
		player.reset();
		//get song
		Song playSong = songs.get(songPosn);
		//get id
		long currSong = playSong.getID();
		//set uri
		Uri trackUri = ContentUris.withAppendedId(
				android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
				currSong);
		//set the data source
		try{ 
			player.setDataSource(getApplicationContext(), trackUri);
		}
		catch(Exception e){
			Log.e("MUSIC SERVICE", "Error setting data source", e);
		}
		player.prepareAsync(); 
	}

	//set the song
	public void setSong(int songIndex){
		System.out.println("setSong*************");
		songPosn=songIndex;	
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		//start playback
		mp.start();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyApp","I am here");
		PowerBroadcastReceiver receiver = new PowerBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
		registerReceiver(receiver, filter);
		return START_STICKY;
	}

}
