package com.t2.compassionMeditation;

//Need the following import to get access to the app resources, since this
//class is in a sub-package.
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.t2.R;
import com.t2.biomap.SharedPref;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class PreferenceActivity extends Activity{
	private static final String TAG = "BFDemo";

	EditText mSessionLengthEdit;
	EditText mAlphaGainEdit;
	CheckBox mShowInstructionsCheckbox;
	CheckBox mAllowCommentsCheckbox;
	CheckBox mSaveRawWaveCheckbox;
	CheckBox mShowAlphaGainCheckbox;
	Spinner mBandOfInterestSpinner;
	Spinner mBioHarnessParametersSpinner;
	
//	Dao<PreferenceData, Integer> mPeferenceDao;
//	PreferenceData mPreferenceData;
//	ArrayList<Boolean> mBioHarnessParameters; 	
	
	
	
	
	protected SharedPreferences sharedPref;
	
	public void onButtonClick(View v)
	{
		 final int id = v.getId();
		    switch (id) {
		    case R.id.buttonReScale:
		    	
		    	SharedPref.setIntValues(sharedPref, "BandScales", ",", new int[] {1,1,1,1,1,1,1,1});    		    	
		    	
		    	
		    	finish();
		    	break;
		    	
		    case R.id.buttonGotoUserModeActivity:
				Intent intent2 = new Intent(this, UserModeActivity.class);
				this.startActivityForResult(intent2, com.t2.compassionMeditation.Constants.USER_MODE_ACTIVITY);		
		    	finish();
		    	break;
		    	
//		    case R.id.buttonBioHarnessParameters:
//
//		    	String[] measureNames = new String[] {"Heart Rate", "Respiration Rate", "Skin Temp"};
//		    	//final boolean toggleArray[] = new boolean[measureNames.length];		    	
//		    	
//		    	try {
//					mPeferenceDao.refresh(mPreferenceData);
//					
//			    	Object toggleBArray[] =  mPreferenceData.mBioHarnessParameters.toArray();
//			    	
////			    	Boolean toggleBArray[] = (Boolean[]) mPreferenceData.mBioHarnessParameters.toArray();
//			    	final boolean toggleArray[] = new boolean[measureNames.length];	
//
//			    	for (int i = 0; i < toggleBArray.length; i++) {
//			    		Boolean b = (Boolean) toggleBArray[i];
//			    		toggleArray[i] = b.booleanValue();
//			    	}
//			    	                          
//					
//			    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
//			    	alert.setTitle("Select Parameters to Use");
//					    	alert.setMultiChoiceItems(measureNames,
//			    			toggleArray,
//		                    new DialogInterface.OnMultiChoiceClickListener() {
//
//			    			public void onClick(DialogInterface dialog, int whichButton,boolean isChecked) {
//			    				toggleArray[whichButton] = isChecked;
//		                 		
//		                        }
//		                    });
//			    	alert.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
//		                public void onClick(DialogInterface dialog, int whichButton) {
//
//		//                	ArrayList<Boolean> dd = new ArrayList<Boolean>({true});
//
//		                	mPreferenceData.mBioHarnessParameters.clear();
//		                	for (Boolean b : toggleArray) {
//		                		mPreferenceData.mBioHarnessParameters.add(b);
//		                	}
//		                	try {
//								mPeferenceDao.update(mPreferenceData);
//							} catch (SQLException e) {
//								Log.e(TAG, "Database Error", e);
//							}	                	
//
//		                }
//		            });
//		
//					alert.show();					
//					
//				} catch (SQLException e) {
//					Log.e(TAG, "Database Error", e);
//				}		    	
//
//		    			    	
//		    	break;
		    	
		    }
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // This needs to happen BEFORE setContentView
        setContentView(R.layout.preferences);
		super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());   
        
//		try {
//			mPeferenceDao = getHelper().getPreferenceDao();
//			List<PreferenceData> list = mPeferenceDao.queryForAll();	
//			
//			// There should be 1 and only 1
//			if (list.size() == 0) {
//				mPreferenceData= new PreferenceData();
//				mPeferenceDao.create(mPreferenceData);
//			}
//			else if (list.size() == 1) {
//				mPreferenceData = list.get(0);
//			}
//			else {
//				Log.e(TAG, "Database Error");				
//			}
//			
//		} catch (SQLException e) {
//			Log.e(TAG, "Database Error", e);
//		}
//        
		
		
		try {
			mSessionLengthEdit = (EditText) findViewById(R.id.editTextSessionLength);
			int sessionLengthSecs = SharedPref.getInt(this, Constants.PREF_SESSION_LENGTH, 	Constants.PREF_SESSION_LENGTH_DEFAULT);
			int sessionLengthMins = sessionLengthSecs / 60;
			mSessionLengthEdit.setText(Integer.toString(sessionLengthMins));

			mAlphaGainEdit = (EditText) findViewById(R.id.editTextAlphaGain);
			mAlphaGainEdit.setText(Float.toString(SharedPref.getFloat(this, Constants.PREF_ALPHA_GAIN, 	Constants.PREF_ALPHA_GAIN_DEFAULT)));

			mShowInstructionsCheckbox = (CheckBox) findViewById(R.id.checkBoxShowInstructions);
			mShowInstructionsCheckbox.setChecked(SharedPref.getBoolean(this, Constants.PREF_INSTRUCTIONS_ON_START, Constants.PREF_INSTRUCTIONS_ON_START_DEFAULT));

			mAllowCommentsCheckbox = (CheckBox) findViewById(R.id.checkBoxAllowComments);
			mAllowCommentsCheckbox.setChecked(SharedPref.getBoolean(this, Constants.PREF_COMMENTS, Constants.PREF_COMMENTS_DEFAULT));

			mSaveRawWaveCheckbox = (CheckBox) findViewById(R.id.checkBoxSaveRawWave);
			mSaveRawWaveCheckbox.setChecked(SharedPref.getBoolean(this, Constants.PREF_SAVE_RAW_WAVE, Constants.PREF_SAVE_RAW_WAVE_DEFAULT));

			mShowAlphaGainCheckbox = (CheckBox) findViewById(R.id.checkBoxAlphaGainVisible);
			mShowAlphaGainCheckbox.setChecked(SharedPref.getBoolean(this, Constants.PREF_SHOW_A_GAIN, Constants.PREF_SHOW_A_GAIN_DEFAULT));

						
			
						
			// Mindset band of interest
			mBandOfInterestSpinner = (Spinner) findViewById(R.id.spinnerBandOfInterest);		
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
			        this, R.array.bands_of_interest_array, android.R.layout.simple_spinner_item);
			
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBandOfInterestSpinner.setAdapter(adapter)	;	
			mBandOfInterestSpinner.setSelection(SharedPref.getInt(this, Constants.PREF_BAND_OF_INTEREST , 	
					Constants.PREF_BAND_OF_INTEREST_DEFAULT));


			// BioHarness parameter of interest
			mBioHarnessParametersSpinner = (Spinner) findViewById(R.id.spinnerBioHarnessParameters);		
			adapter = ArrayAdapter.createFromResource(
			        this, 
			        R.array.bioharness_parameters_array, 
			        android.R.layout.simple_spinner_item);
			
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mBioHarnessParametersSpinner.setAdapter(adapter)	;	
			mBioHarnessParametersSpinner.setSelection(SharedPref.getInt(this, Constants.PREF_BIOHARNESS_PARAMETER_OF_INTEREST , 	
					Constants.PREF_BIOHARNESS_PARAMETER_OF_INTEREST_DEFAULT));

		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onDestroy() {

		try {
			String s = mSessionLengthEdit.getText().toString();
			int i = Integer.parseInt(s);
			SharedPref.putInt(this, Constants.PREF_SESSION_LENGTH, 	i * 60);
			SharedPref.putFloat(this, Constants.PREF_ALPHA_GAIN, Float.parseFloat(mAlphaGainEdit.getText().toString())	);

			SharedPref.putBoolean(this, Constants.PREF_SAVE_RAW_WAVE, mSaveRawWaveCheckbox.isChecked() );
			SharedPref.putBoolean(this, Constants.PREF_COMMENTS, mAllowCommentsCheckbox.isChecked() );
			SharedPref.putBoolean(this, Constants.PREF_INSTRUCTIONS_ON_START, mShowInstructionsCheckbox.isChecked() );
			SharedPref.putBoolean(this, Constants.PREF_SHOW_A_GAIN, mShowAlphaGainCheckbox.isChecked() );

			
			
			SharedPref.putInt(this, Constants.PREF_BAND_OF_INTEREST , 	
					mBandOfInterestSpinner.getSelectedItemPosition());
			SharedPref.putInt(this, Constants.PREF_BIOHARNESS_PARAMETER_OF_INTEREST , 	
					mBioHarnessParametersSpinner.getSelectedItemPosition());

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		super.onDestroy();
	}

}
