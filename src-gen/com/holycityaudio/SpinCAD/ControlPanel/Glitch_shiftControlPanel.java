/* SpinCAD Designer - DSP Development Tool for the Spin FV-1 
 * Glitch_shiftControlPanel.java
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
		import java.awt.event.ActionEvent;
		import java.awt.event.WindowEvent;
		import java.awt.event.WindowListener;
		import java.awt.event.ItemEvent;
		import javax.swing.BoxLayout;
		import javax.swing.JSlider;
		import javax.swing.JSpinner;
		import javax.swing.JLabel;
		import javax.swing.JCheckBox;
		import javax.swing.JComboBox;
		import javax.swing.Box;
		import java.awt.Dimension;
		import com.holycityaudio.SpinCAD.spinCADControlPanel;
		import com.holycityaudio.SpinCAD.CADBlocks.Glitch_shiftCADBlock;

		public class Glitch_shiftControlPanel extends spinCADControlPanel {
		private JFrame frame;

		private Glitch_shiftCADBlock gCB;
		// declare the controls
			JSlider pitchCoeffSlider;
			JLabel  pitchCoeffLabel;	
			private JComboBox <String> lfoSelComboBox; 
			private JComboBox <String> lfoWidthComboBox; 

		public Glitch_shiftControlPanel(Glitch_shiftCADBlock genericCADBlock) {
		
		gCB = genericCADBlock;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame = new JFrame();
				frame.setTitle("Glitch_Shift");
				frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

			
			pitchCoeffSlider = new JSlider(JSlider.HORIZONTAL, (int)(-8192 * 1.0),(int) (32767 * 1.0), (int) (gCB.getpitchCoeff() * 1.0));
				pitchCoeffSlider.addChangeListener(new Glitch_shiftSliderListener());
				pitchCoeffLabel = new JLabel();
				updatepitchCoeffLabel();
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(pitchCoeffLabel);
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(pitchCoeffSlider);		
				lfoSelComboBox = new JComboBox <String> ();
				lfoSelComboBox.addItem("Ramp 0");
				lfoSelComboBox.addItem("Ramp 1");
				lfoSelComboBox.setSelectedIndex(gCB.getlfoSel());
				frame.add(Box.createRigidArea(new Dimension(5,8)));			
				frame.getContentPane().add(lfoSelComboBox);
				lfoSelComboBox.addActionListener(new Glitch_shiftActionListener());
				lfoWidthComboBox = new JComboBox <String> ();
				lfoWidthComboBox.addItem("4096");
				lfoWidthComboBox.addItem("2048");
			lfoWidthComboBox.addItem("1024");
			lfoWidthComboBox.addItem("512");
				lfoWidthComboBox.setSelectedIndex(gCB.getlfoWidth());
				frame.add(Box.createRigidArea(new Dimension(5,8)));			
				frame.getContentPane().add(lfoWidthComboBox);
				lfoWidthComboBox.addActionListener(new Glitch_shiftActionListener());
				frame.addWindowListener(new MyWindowListener());
				frame.pack();
				frame.setResizable(false);
				frame.setLocation(gCB.getX() + 100, gCB.getY() + 100);
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);		
			}
		});
		}

		// add change listener for Sliders 
		class Glitch_shiftSliderListener implements ChangeListener { 
		public void stateChanged(ChangeEvent ce) {
			if(ce.getSource() == pitchCoeffSlider) {
			gCB.setpitchCoeff((double) (pitchCoeffSlider.getValue()/1.0));
				updatepitchCoeffLabel();
			}
			}
		}

		// add item listener 
		class Glitch_shiftItemListener implements java.awt.event.ItemListener { 
		public void stateChanged(ChangeEvent ce) {
			}
			
		@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
			}
		}
		
		// add action listener 
		class Glitch_shiftActionListener implements java.awt.event.ActionListener { 
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == lfoSelComboBox) {
				gCB.setlfoSel((lfoSelComboBox.getSelectedIndex()));
			}
			if(arg0.getSource() == lfoWidthComboBox) {
				gCB.setlfoWidth((lfoWidthComboBox.getSelectedIndex()));
			}
			}
		}
		private void updatepitchCoeffLabel() {
		pitchCoeffLabel.setText("Pitch_Coefficient " + String.format("%4.0f", gCB.getpitchCoeff()));		
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
