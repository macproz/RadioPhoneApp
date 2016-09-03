package com.yf.phoneapp.eqsetting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.yf.phoneapp.mtksetting.Array;
//import com.yftech.main.service.sdk.SysConfigUtils;

public class DaoSound {

	public static final int STYLE_INDEX_DEFINE = 0;
	public static final int STYLE_INDEX_POP = 1;
	public static final int STYLE_INDEX_ROCK = 2;
	public static final int STYLE_INDEX_VILLAGE = 3;
	public static final int STYLE_INDEX_JAZZ = 4;
	public static final int STYLE_INDEX_TALK = 5;
	public static final int STYLE_INDEX_CLASS = 6;
	// -----------------------cg----------add-----------
	public static final int STYLE_INDEX_RENSHENG = 7;

	public int bass;
	public int middle;
	public int treble;
	// -----------------cg-----------------------
	public static final int DEF_PROGRESS = 7;

	private DaoSound(int bass, int middle, int treble) {
		this.bass = bass;
		this.middle = middle;
		this.treble = treble;
	}

	private static List<DaoSound> daoSounds = new ArrayList<DaoSound>();

//	public static DaoSound getIntance() {
//		return new DaoSound(DEF_PROGRESS, DEF_PROGRESS, DEF_PROGRESS);
//	}
	
	
	public static DaoSound getIntance(Context context)
    {
    	int[] eqvalues = null;
//    	eqvalues = SysConfigUtils.getCurEqtypeAndEqValues(context);
    	
    	if (eqvalues != null && eqvalues.length >= 3){
    		return new DaoSound(eqvalues[0], eqvalues[1], eqvalues[2]);
    	}
        return new DaoSound(DEF_PROGRESS, DEF_PROGRESS, DEF_PROGRESS);
    }

	// ----------------------------cg---------------------
	private static DaoSound pop, rock, village, jazz, talk, clazz, rensheng;

	public static DaoSound get(int index) {

		Log.d("sd", "DaoSound--index:" + index + "-----STYLE_INDEX_RENSHENG:"
				+ STYLE_INDEX_RENSHENG);

		switch (index) {
		case STYLE_INDEX_DEFINE:
			return new DaoSound(DEF_PROGRESS, DEF_PROGRESS, DEF_PROGRESS);
		case STYLE_INDEX_POP:
			if (pop == null)
				pop = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[1][2],
						DEF_PROGRESS + Array.gEQTypePos[1][6], DEF_PROGRESS
								+ Array.gEQTypePos[1][9]);
			return pop;
		case STYLE_INDEX_ROCK:
			if (rock == null)
				rock = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[2][2],
						DEF_PROGRESS + Array.gEQTypePos[2][6], DEF_PROGRESS
								+ Array.gEQTypePos[2][9]);
			return rock;
		case STYLE_INDEX_VILLAGE:
			if (village == null)
				village = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[3][2],
						DEF_PROGRESS + Array.gEQTypePos[3][6], DEF_PROGRESS
								+ Array.gEQTypePos[3][9]);
			return village;
		case STYLE_INDEX_JAZZ:
			if (jazz == null)
				jazz = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[4][2],
						DEF_PROGRESS + Array.gEQTypePos[4][6], DEF_PROGRESS
								+ Array.gEQTypePos[4][9]);
			;
			return jazz;
		case STYLE_INDEX_TALK:
			if (talk == null)
				talk = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[5][2],
						DEF_PROGRESS + Array.gEQTypePos[5][6], DEF_PROGRESS
								+ Array.gEQTypePos[5][9]);
			;
			return talk;
		case STYLE_INDEX_CLASS:
			if (clazz == null)
				clazz = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[6][2],
						DEF_PROGRESS + Array.gEQTypePos[6][6], DEF_PROGRESS
								+ Array.gEQTypePos[6][9]);
			;
			return clazz;
			// ------------------cg----------------
		case STYLE_INDEX_RENSHENG:
			if (rensheng == null)
				rensheng = new DaoSound(DEF_PROGRESS + Array.gEQTypePos[6][2],
						DEF_PROGRESS + Array.gEQTypePos[6][6], DEF_PROGRESS
								+ Array.gEQTypePos[6][9]);
			return rensheng;

		default:
			break;
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof DaoSound))
			return false;
		DaoSound other = (DaoSound) o;
		return bass == other.bass && middle == other.middle
				&& treble == other.treble;
	}

	@Override
	public String toString() {
		return String.format("DoaSound[l:%d,m:%d,h:%d]", bass, middle, treble);
	}
}
