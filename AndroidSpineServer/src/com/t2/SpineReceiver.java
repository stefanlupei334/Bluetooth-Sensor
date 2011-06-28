package com.t2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast intent receiver for the main activity.
 * This class receives STATUS messages from the AndroidBTService.
 * 
 * Note that in older versions of this software sensor data was
 * also send using broadcast intents. This is no longer the case
 * but commented out code is left to show how to do it in a
 * pinch.
 * 
 * @author scott.coleman
 *
 */
public class SpineReceiver extends BroadcastReceiver {
	private static final String TAG = Constants.TAG;
	private OnBioFeedbackMessageRecievedListener messageRecievedListener;
	
	/**
	 * Sets up a listener for inbound messages.
	 * 
	 * @param omrl	Listener to call when message is received
	 */
	public SpineReceiver(OnBioFeedbackMessageRecievedListener omrl) {
		this.messageRecievedListener = omrl;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("com.t2.biofeedback.service.status.BROADCAST")) {
			if(messageRecievedListener != null) {
				messageRecievedListener.onStatusReceived(BioFeedbackStatus.factory(intent));
			}
		} 

		// The following is legacy code for when we used to send sensor data using
		// broadcast intents:
		
		//		else if(intent.getAction().equals("com.t2.biofeedback.service.data.BROADCAST")) {
		//			// This is data sent directly from the service - NOT THROUGH SPINE - NOT RECOMMENDED		
		//			if(messageRecievedListener != null) {
		//				messageRecievedListener.onDataReceived(BioFeedbackData.factory(intent));
		//			}
		//		}  
		//		else if(intent.getAction().equals("com.t2.biofeedback.service.zephyrdata.BROADCAST")) {
		//			if(messageRecievedListener != null) {
		//				messageRecievedListener.onZephyrDataReceived(ZephyrData.factory(intent));
		//			}
		//		}
	}

	public boolean isDataMessage(Intent i) {
		return i.getStringExtra("messageId").startsWith("DATA_");
	}
	
	/**
	 * Interface for message received listener
	 * @author scott.coleman
	 *
	 */
	public interface OnBioFeedbackMessageRecievedListener {
		/**
		 * Called when a status message is received from the AndroidBTService
		 * 
		 * @param bfs	Status data
		 */
		public void onStatusReceived(BioFeedbackStatus bfs);
		// The following is legacy code for when we used to send sensor data using
		// broadcast intents:

		//		public void onDataReceived(BioFeedbackData bfmd);
		//		public void onZephyrDataReceived(ZephyrData bfmd);
	}
	
	public abstract static class BioFeedbackMessage {
		public String address;
		public String name;
		public String messageType;
		public String messageId;
		public Double messageValue;
	}
	
	/**
	 * Message used to communicate biofeedback status from the AndroidBTService to the server
	 * 
	 * @author scott.coleman
	 *
	 */
	public static class BioFeedbackStatus extends BioFeedbackMessage {

		public static BioFeedbackStatus factory(Intent i) {

			BioFeedbackStatus m = new BioFeedbackStatus();
			m.address = i.getStringExtra("address");
			m.name = i.getStringExtra("name");
			m.messageType = i.getStringExtra("messageType");
			m.messageId = i.getStringExtra("messageId");
			m.messageValue = i.getDoubleExtra("messageValue", -12345678901234567890.12);
			
			if(m.messageValue == -12345678901234567890.12) {
				m.messageValue = null;
			}
			
			return m;
		}
	}
	
	// The following is legacy code for when we used to send sensor data using
	// broadcast intents:
	
	//	public static class BioFeedbackSpineData extends BioFeedbackMessage {
	//	public byte[] msgBytes;
	//	public long currentTimestamp;
	//	
	//	public static BioFeedbackSpineData factory(Intent i) {
	//		BioFeedbackSpineData m = new BioFeedbackSpineData();
	//		m.address = i.getStringExtra("address");
	//		m.name = i.getStringExtra("name");
	//		m.messageType = i.getStringExtra("messageType");
	//		m.messageId = i.getStringExtra("messageId");
	//		m.msgBytes = i.getByteArrayExtra("msgBytes");
	//		m.currentTimestamp = i.getLongExtra("currentTimestamp", 0);
	//		
	//		return m;
	//	}
	//}

	//	public static class ZephyrData extends BioFeedbackMessage {
	//	public byte[] msgBytes;
	//	public long currentTimestamp;
	//	
	//	public static ZephyrData factory(Intent i) {
	//		ZephyrData m = new ZephyrData();
	//		m.address = i.getStringExtra("address");
	//		m.name = i.getStringExtra("name");
	//		m.messageType = i.getStringExtra("messageType");
	//		m.messageId = i.getStringExtra("messageId");
	//		m.msgBytes = i.getByteArrayExtra("msgBytes");
	//		m.currentTimestamp = i.getLongExtra("currentTimestamp", 0);			
	//		
	//		return m;
	//	}
	//}	
	
	//	public static class BioFeedbackData extends BioFeedbackMessage {
	//	public double avgValue;
	//	public double currentValue;
	//	public double[] sampleValues;
	//	
	//	public long currentTimestamp;
	//	public long[] sampleTimestamps;
	//	
	//	public static BioFeedbackData factory(Intent i) {
	//		BioFeedbackData m = new BioFeedbackData();
	//		m.address = i.getStringExtra("address");
	//		m.name = i.getStringExtra("name");
	//		m.messageType = i.getStringExtra("messageType");
	//		m.messageId = i.getStringExtra("messageId");
	//		
	//		m.avgValue = i.getDoubleExtra("avgValue", 0.00);
	//		m.currentValue = i.getDoubleExtra("currentValue", 0.00);
	//		m.sampleValues = i.getDoubleArrayExtra("sampleValues");
	//		
	//		m.currentTimestamp = i.getLongExtra("currentTimestamp", 0);
	//		m.sampleTimestamps = i.getLongArrayExtra("sampleTimestamps");
	//		
	//		return m;
	//	}
	//}	
}
