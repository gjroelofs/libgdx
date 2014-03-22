/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.tools.particleeditor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

class OptionsPanel extends EditorPanel {
	JCheckBox attachedCheckBox;
	JCheckBox continuousCheckbox;
	JCheckBox alignedCheckbox;
	JCheckBox additiveCheckbox;
	JCheckBox behindCheckbox;

	public OptionsPanel (final ParticleEditor editor, String name, String description) {
		super(null, name, description);

		initializeComponents();

		attachedCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				editor.getEmitter().setAttached(attachedCheckBox.isSelected());
			}
		});

		continuousCheckbox.setSelected(editor.getEmitter().isContinuous());
		continuousCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				editor.getEmitter().setContinuous(continuousCheckbox.isSelected());
			}
		});

		alignedCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged (ChangeEvent event) {
				editor.getEmitter().setAligned(alignedCheckbox.isSelected());
			}
		});

		additiveCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged (ChangeEvent event) {
				editor.getEmitter().setAdditive(additiveCheckbox.isSelected());
			}
		});

		behindCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged (ChangeEvent event) {
				editor.getEmitter().setBehind(behindCheckbox.isSelected());
			}
		});

		ParticleEmitter emitter = editor.getEmitter();
		attachedCheckBox.setSelected(emitter.isAttached());
		continuousCheckbox.setSelected(emitter.isContinuous());
		alignedCheckbox.setSelected(emitter.isAligned());
		additiveCheckbox.setSelected(emitter.isAdditive());
		behindCheckbox.setSelected(emitter.isBehind());
	}

	private void initializeComponents () {
		JPanel contentPanel = getContentPanel();
		contentPanel.removeAll();
		contentPanel.setLayout(new GridBagLayout());
		{
			JLabel label = new JLabel("Additive:");
			contentPanel.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(6, 0, 0, 0), 0, 0));
		}
		{
			additiveCheckbox = new JCheckBox();
			additiveCheckbox.setToolTipText("Enables additive blending for rendering, defined as: src(SRC_ALPHA), trg(ONE)");
			contentPanel.add(additiveCheckbox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(6, 6, 0, 0), 0, 0));
		}
		{
			JLabel label = new JLabel("Attached:");
			contentPanel.add(label, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(6, 30, 0, 0), 0, 0));
		}
		{
			attachedCheckBox = new JCheckBox();
			attachedCheckBox.setToolTipText("Particles move when emitter moves");
			contentPanel.add(attachedCheckBox, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(6, 6, 0, 0), 0, 0));
		}
		{
			JLabel label = new JLabel("Continuous:");
			contentPanel.add(label, new GridBagConstraints(4, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(6, 30, 0, 0), 0, 0));
		}
		{
			continuousCheckbox = new JCheckBox();
			continuousCheckbox
				.setToolTipText("<html>Emitter restarts when duration expires, instead of when all particles expire. <br> Note that the effect never finishes if at least one emitter is continuous and non-continuous emitters will not reset.</html>");
			contentPanel.add(continuousCheckbox, new GridBagConstraints(5, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(6, 6, 0, 0), 0, 0));
		}
		{
			JLabel label = new JLabel("Aligned:");
			contentPanel.add(label, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(6, 30, 0, 0), 0, 0));
		}
		{
			alignedCheckbox = new JCheckBox();
			alignedCheckbox.setToolTipText("Angle of particle is added to rotation");
			contentPanel.add(alignedCheckbox, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(6, 6, 0, 0), 0, 0));
		}
		{
			JLabel label = new JLabel("Behind:");
			
			contentPanel.add(label, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(6, 30, 0, 0), 0, 0));
		}
		{
			behindCheckbox = new JCheckBox();
			behindCheckbox.setToolTipText("Unused by LibGDX");
			contentPanel.add(behindCheckbox, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(6, 6, 0, 0), 0, 0));
		}
	}
}
