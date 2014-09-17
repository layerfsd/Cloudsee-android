package com.jovision.audio;

import java.util.concurrent.LinkedBlockingQueue;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.jovision.Jni;

public class PlayAudio extends Thread {

	private static final int SAMPLERATE = 8000;
	private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private static final int STREAM_TYPE = AudioManager.STREAM_VOICE_CALL;
	private static final int TRACK_MODE = AudioTrack.MODE_STREAM;

	private LinkedBlockingQueue<byte[]> audioQueue;

	MICRecorder recorder;

	public PlayAudio(LinkedBlockingQueue<byte[]> queue) {
		audioQueue = queue;
		recorder = MICRecorder.getInstance();
	}

	@Override
	public void run() {
		// File file = new File(Consts.LOG_PATH + "/x.pcm");
		// if (file.exists()) {
		// file.delete();
		// }
		//
		// FileOutputStream outputStream = null;
		// try {
		// outputStream = new FileOutputStream(file);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		Jni.initAudioEncoder(1, 8000, 1, 16, 640);

		AudioTrack track = new AudioTrack(STREAM_TYPE, SAMPLERATE, CHANNEL,
				ENCODING, 1024, TRACK_MODE);
		track.play();

		byte[] data = null;
		byte[] enc = null;
		while (false == isInterrupted()) {
			if (null != track && null != audioQueue) {
				try {
					data = audioQueue.take();
					// data = recorder.start();
					if (null != data) {
						track.write(data, 0, data.length);
						// enc = Jni.encodeAudio(data);
						// if (null != enc && enc.length > 0) {
						// MyLog.v("PlayAudio", ""+enc.length);
						// // outputStream.write(enc);
						// // Jni.sendBytes(0,
						// // JVNetConst.JVN_RSP_CHATDATA, enc,
						// // enc.length);
						// }
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Jni.deinitAudioEncoder();
		// if (null != outputStream) {
		// try {
		// outputStream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		track.stop();
		track.release();
		track = null;
	}
}