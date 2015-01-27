/* SpinCAD Designer - DSP Development Tool for the Spin FV-1 
 * TripleTapControlPanel.java
 * Copyright (C) 2015 - Gary Worsham 
 * Based on ElmGen by Andrew Kilpatrick 
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
		package com.holycityaudio.SpinCAD.ControlPanel;
		import javax.swing.JFrame;
		import javax.swing.SwingUtilities;
		import javax.swing.event.ChangeEvent;
		import javax.swing.event.ChangeListener;
		import java.awt.event.WindowEvent;
		import java.awt.event.WindowListener;
		import java.awt.event.ItemEvent;
		import javax.swing.BoxLayout;
		import javax.swing.JSlider;
		import javax.swing.JLabel;
		import javax.swing.JCheckBox;
		
		import com.holycityaudio.SpinCAD.CADBlocks.TripleTapCADBlock;

		public class TripleTapControlPanel {
		private JFrame frame;

		private TripleTapCADBlock gCB;
		// declare the controls
			JSlider inputGainSlider;
			JLabel  inputGainLabel;	
			JSlider delayLengthSlider;
			JLabel  delayLengthLabel;	
			JSlider tap1RatioSlider;
			JLabel  tap1RatioLabel;	
			JSlider tap2RatioSlider;
			JLabel  tap2RatioLabel;	
			JSlider tap3RatioSlider;
			JLabel  tap3RatioLabel;	

		public TripleTapControlPanel(TripleTapCADBlock genericCADBlock) {
		
		gCB = genericCADBlock;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame = new JFrame();
				frame.setTitle("ThreeTap");
				frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

			
			inputGainSlider = new JSlider(JSlider.HORIZONTAL, (int)(0.0 * 1000.0),(int) (1.0 * 1000.0), (int) (gCB.getinputGain() * 1000.0));
			inputGainSlider.addChangeListener(new TripleTapSliderListener());
			inputGainLabel = new JLabel();
			updateinputGainLabel();
			frame.getContentPane().add(inputGainLabel);
			frame.getContentPane().add(inputGainSlider);		
			
			delayLengthSlider = new JSlider(JSlider.HORIZONTAL, (int)(0 * 1),(int) (32767 * 1), (int) (gCB.getdelayLength() * 1));
			delayLengthSlider.addChangeListener(new TripleTapSliderListener());
			delayLengthLabel = new JLabel();
			updatedelayLengthLabel();
			frame.getContentPane().add(delayLengthLabel);
			frame.getContentPane().add(delayLengthSlider);		
			
			tap1RatioSlider = new JSlider(JSlider.HORIZONTAL, (int)(0.0 * 1000.0),(int) (1.0 * 1000.0), (int) (gCB.gettap1Ratio() * 1000.0));
			tap1RatioSlider.addChangeListener(new TripleTapSliderListener());
			tap1RatioLabel = new JLabel();
			updatetap1RatioLabel();
			frame.getContentPane().add(tap1RatioLabel);
			frame.getContentPane().add(tap1RatioSlider);		
			
			tap2RatioSlider = new JSlider(JSlider.HORIZONTAL, (int)(0.0 * 1000.0),(int) (1.0 * 1000.0), (int) (gCB.gettap2Ratio() * 1000.0));
			tap2RatioSlider.addChangeListener(new TripleTapSliderListener());
			tap2RatioLabel = new JLabel();
			updatetap2RatioLabel();
			frame.getContentPane().add(tap2RatioLabel);
			frame.getContentPane().add(tap2RatioSlider);		
			
			tap3RatioSlider = new JSlider(JSlider.HORIZONTAL, (int)(0.0 * 1000.0),(int) (1.0 * 1000.0), (int) (gCB.gettap3Ratio() * 1000.0));
			tap3RatioSlider.addChangeListener(new TripleTapSliderListener());
			tap3RatioLabel = new JLabel();
			updatetap3RatioLabel();
			frame.getContentPane().add(tap3RatioLabel);
			frame.getContentPane().add(tap3RatioSlider);		
				
				frame.addWindowListener(new MyWindowListener());
				frame.setVisible(true);		
				frame.pack();
				frame.setResizable(false);
				frame.setLocation(gCB.getX() + 100, gCB.getY() + 100);
				frame.setAlwaysOnTop(true);
			}
		});
		}

		// add change listener for Sliders 
		class TripleTapSliderListener implements ChangeListener { 
		public void stateChanged(ChangeEvent ce) {
			if(ce.getSource() == inputGainSlider) {
				gCB.setinputGain((double) (inputGainSlider.getValue()/1000.0));
				updateinputGainLabel();
			}
			if(ce.getSource() == delayLengthSlider) {
				gCB.setdelayLength((double) (delayLengthSlider.getValue()/1));
				updatedelayLengthLabel();
			}
			if(ce.getSource() == tap1RatioSlider) {
				gCB.settap1Ratio((double) (tap1RatioSlider.getValue()/1000.0));
				updatetap1RatioLabel();
			}
			if(ce.getSource() == tap2RatioSlider) {
				gCB.settap2Ratio((double) (tap2RatioSlider.getValue()/1000.0));
				updatetap2RatioLabel();
			}
			if(ce.getSource() == tap3RatioSlider) {
				gCB.settap3Ratio((double) (tap3RatioSlider.getValue()/1000.0));
				updatetap3RatioLabel();
			}
			}
		}
		// add item listener for Bool (CheckbBox) 
		class TripleTapItemListener implements java.awt.event.ItemListener { 
		public void stateChanged(ChangeEvent ce) {
			}
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			
		}
	}

		private void updateinputGainLabel() {
		inputGainLabel.setText("Input_Gain " + String.format("%4.2f", gCB.getinputGain()));		
		}		
		private void updatedelayLengthLabel() {
		delayLengthLabel.setText("Delay_Time " + String.format("%4.0f", (1000 * gCB.getdelayLength())/gCB.getSamplerate()));		
		}		
		private void updatetap1RatioLabel() {
		tap1RatioLabel.setText("Tap_1_Time " + String.format("%4.2f", gCB.gettap1Ratio()));		
		}		
		private void updatetap2RatioLabel() {
		tap2RatioLabel.setText("Tap_2_Time " + String.format("%4.2f", gCB.gettap2Ratio()));		
		}		
		private void updatetap3RatioLabel() {
		tap3RatioLabel.setText("Tap_3_Time " + String.format("%4.2f", gCB.gettap3Ratio()));		
		}		
		
		class MyWindowListener implements WindowListener
		{
		@Override
			public void windowActivated(WindowEvent arg0) {
			}

		@Override
			public void windowClosed(WindowEvent arg0) {
			}

		@Override
			public void windowClosing(WindowEvent arg0) {
				gCB.clearCP();
			}

		@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}
	}
		
	}