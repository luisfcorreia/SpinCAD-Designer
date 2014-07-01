/* SpinCAD Designer - DSP Development Tool for the Spin FV-1 
 * Copyright (C) 2013 - 2014 - Gary Worsham 
 * Based on ElmGen by Andrew Kilpatrick.  Modified by Gary Worsham 2013 - 2014.  Look for GSW in code. 
 * 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU General Public License as published by 
 *   the Free Software Foundation, either version 3 of the License, or 
 *   (at your option) any later version. 
 * 
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 *   GNU General Public License for more details. 
 * 
 *   You should have received a copy of the GNU General Public License 
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 *     
 */ 
package com.holycityaudio.SpinCAD.CADBlocks;

import com.holycityaudio.SpinCAD.SpinCADPin;
import com.holycityaudio.SpinCAD.SpinFXBlock;
import com.holycityaudio.SpinCAD.ControlBlocks.RampLFOControlPanel;

public class RampLFOCADBlock extends ControlCADBlock{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7025985649946130854L;
	int lfoRate = 1;
	int lfoWidth = 1;
	boolean invert = false;
	boolean flip = false;

	public RampLFOCADBlock(int x, int y) {
		super(x, y);
		addControlOutputPin(this, "Ramp 1");	//	Ramp 1
		addControlOutputPin(this, "Ramp 2");	//	Ramp 2
		setName("Ramp LFO");
	}

	public void generateCode(SpinFXBlock sfxb) {
		
		int ramp0 = sfxb.allocateReg();
		int ramp1 = sfxb.allocateReg();	
		sfxb.comment(getName());

		sfxb.skip(RUN,2);
		sfxb.loadRampLFO(0, 32000, 4096);
		sfxb.loadRampLFO(1, -16000, 1024);
		sfxb.chorusReadValue(RMP0);
		sfxb.writeRegister(ramp0, 0.0);
		sfxb.chorusReadValue(RMP1);
		sfxb.writeRegister(ramp1, 0.0);
		this.getPin("Ramp 1").setRegister(ramp0);
		this.getPin("Ramp 2").setRegister(ramp1);
		System.out.println("Ramp LFO code gen!");
	}

	public void editBlock(){
		RampLFOControlPanel cp = new RampLFOControlPanel(this);
	}
	//====================================================

	public int getLFORate() {
		// TODO Auto-generated method stub
		return lfoRate;
	}

	public void setLFORate(int r) {
		// TODO Auto-generated method stub
		lfoRate = r;
	}

	public void setLFOWidth(int value) {
		// TODO Auto-generated method stub
		lfoWidth = value;
	}
	public int getLFOWidth() {
		// TODO Auto-generated method stub
		return lfoWidth;
	}

}