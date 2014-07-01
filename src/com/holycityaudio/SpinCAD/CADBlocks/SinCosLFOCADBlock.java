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
import com.holycityaudio.SpinCAD.ControlBlocks.SinCosLFOControlPanel;

public class SinCosLFOCADBlock extends ControlCADBlock{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7025985649946130854L;
	int lfoRate = 1;
	int lfoWidth = 1;
	int lfoSel = 0;		// 0 or 1

	public SinCosLFOCADBlock(int x, int y) {
		super(x, y);
		hasControlPanel = true;
		addControlInputPin(this, "Speed");	//	speed
		addControlInputPin(this, "Width");	//	speed
		addControlOutputPin(this, "Sine");	//	SIN
		addControlOutputPin(this, "Cosine");	//	COS
		setName();
	}

	public void setName() {
		setName("LFO " + lfoSel);
	}

	public void generateCode(SpinFXBlock sfxb) {

		int sin = sfxb.allocateReg();
		int cos = sfxb.allocateReg();	
		sfxb.comment(getName());

		sfxb.skip(RUN,1);
		sfxb.loadSinLFO(lfoSel, lfoRate, lfoWidth);

		int speedIn = -1;
		SpinCADPin p = this.getPin("Speed").getPinConnection();
		if (p != null) {
			speedIn = p.getRegister();			
			sfxb.readRegister(speedIn, lfoRate/511.0);	// get left signal, add to register, scale by current rate setting
			if(lfoSel == 0) {
				sfxb.writeRegister(SIN0_RATE, 0.0);
			}
			else {
				sfxb.writeRegister(SIN1_RATE, 0.0);				
			}
		}

		int widthIn = -1;
		p = this.getPin("Width").getPinConnection();
		if (p != null) {
			widthIn = p.getRegister();			
			sfxb.readRegister(widthIn, lfoWidth/32767.0);	// get left signal, add to register, scale by 1.0
			if(lfoSel == 0) {
				sfxb.writeRegister(SIN0_RANGE, 0.0);
			}
			else {
				sfxb.writeRegister(SIN1_RANGE, 0.0);				
			}
		}
		
		sfxb.chorusReadValue(0 + lfoSel);
		sfxb.writeRegister(sin, 0.0);
		sfxb.chorusReadValue(8 + lfoSel);
		sfxb.writeRegister(cos, 0.0);
		this.getPin("Sine").setRegister(sin);
		this.getPin("Cosine").setRegister(cos);
		System.out.println("LFO code gen! Rate/width:" + lfoRate + " /" + lfoWidth);
	}

	public void editBlock(){
		SinCosLFOControlPanel cp = new SinCosLFOControlPanel(this);
	}
	//====================================================

	public int getLFORate() {
		return lfoRate;
	}

	public void setLFORate(int r) {
		lfoRate = r;
	}

	public int getLFOSel() {
		return lfoSel;
	}

	public void setLFOSel(int r) {
		lfoSel = r;
	}

	public void setLFOWidth(int value) {
		lfoWidth = value;
	}
	public int getLFOWidth() {
		return lfoWidth;
	}

}