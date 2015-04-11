/* SpinCAD Designer - DSP Development Tool for the Spin FV-1 
 * FlangerControlPanel.java
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
		import com.holycityaudio.SpinCAD.CADBlocks.FlangerCADBlock;

		public class FlangerControlPanel extends spinCADControlPanel {
		private JFrame frame;

		private FlangerCADBlock gCB;
		// declare the controls
			JSlider delayLengthSlider;
			JLabel  delayLengthLabel;	
			JSlider rateSlider;
			JLabel  rateLabel;	
			JSlider widthSlider;
			JLabel  widthLabel;	
			private JComboBox <String> lfoSelComboBox; 

		public FlangerControlPanel(FlangerCADBlock genericCADBlock) {
		
		gCB = genericCADBlock;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame = new JFrame();
				frame.setTitle("Flanger");
				frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

			
			delayLengthSlider = new JSlider(JSlider.HORIZONTAL, (int)(16 * 1),(int) (512 * 1), (int) (gCB.getdelayLength() * 1));
				delayLengthSlider.addChangeListener(new FlangerSliderListener());
				delayLengthLabel = new JLabel();
				updatedelayLengthLabel();
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(delayLengthLabel);
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(delayLengthSlider);		
			
			rateSlider = new JSlider(JSlider.HORIZONTAL, (int)(0.0 * 100.0),(int) (511.0 * 100.0), (int) ((gCB.getrate()) * 100.0));
				rateSlider.addChangeListener(new FlangerSliderListener());
				rateLabel = new JLabel();
				updaterateLabel();
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(rateLabel);
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(rateSlider);		
			
			widthSlider = new JSlider(JSlider.HORIZONTAL, (int)(5.0 * 100.0),(int) (100.0 * 100.0), (int) (gCB.getwidth() * 100.0));
				widthSlider.addChangeListener(new FlangerSliderListener());
				widthLabel = new JLabel();
				updatewidthLabel();
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(widthLabel);
				frame.add(Box.createRigidArea(new Dimension(5,4)));			
				frame.getContentPane().add(widthSlider);		
				lfoSelComboBox = new JComboBox <String> ();
				lfoSelComboBox.addItem("LFO 0");
				lfoSelComboBox.addItem("LFO 1");
				lfoSelComboBox.setSelectedIndex(gCB.getlfoSel());
				frame.add(Box.createRigidArea(new Dimension(5,8)));			
				frame.getContentPane().add(lfoSelComboBox);
				lfoSelComboBox.addActionListener(new FlangerActionListener());
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
		class FlangerSliderListener implements ChangeListener { 
		public void stateChanged(ChangeEvent ce) {
			if(ce.getSource() == delayLengthSlider) {
			gCB.setdelayLength((double) (delayLengthSlider.getValue()/1));
				updatedelayLengthLabel();
			}
			if(ce.getSource() == rateSlider) {
			gCB.setrate((double) (rateSlider.getValue()/100.0));
				updaterateLabel();
			}
			if(ce.getSource() == widthSlider) {
			gCB.setwidth((double) (widthSlider.getValue()/100.0));
				updatewidthLabel();
			}
			}
		}

		// add item listener 
		class FlangerItemListener implements java.awt.event.ItemListener { 
		public void stateChanged(ChangeEvent ce) {
			}
			
		@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
			}
		}
		
		// add action listener 
		class FlangerActionListener implements java.awt.event.ActionListener { 
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == lfoSelComboBox) {
				gCB.setlfoSel((lfoSelComboBox.getSelectedIndex()));
			}
			}
		}
		private void updatedelayLengthLabel() {
		delayLengthLabel.setText("Delay_Time " + String.format("%4.2f", (1000 * gCB.getdelayLength())/gCB.getSamplerate()));		
		}		
		private void updaterateLabel() {
		rateLabel.setText("LFO_Rate " + String.format("%4.2f", coeffToLFORate(gCB.getrate())));		
		}		
		private void updatewidthLabel() {
		widthLabel.setText("LFO_Width " + String.format("%4.1f", gCB.getwidth()));		
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
