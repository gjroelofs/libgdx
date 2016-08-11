
package com.badlogic.gdx.graphics.g3d.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.depth.DepthComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.normaldepth.NormalDepthComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao.SsaoComponent;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao_composer.SsaoComposerComponent;
import com.sun.org.apache.xml.internal.serializer.OutputPropertyUtils;

public class SsaoEffect extends BasePostProcessingEffect {
	protected NormalDepthComponent normalDepthComponent;
	protected SsaoComponent ssaoComponent;
	protected SsaoComposerComponent ssaoComposerComponent;
	protected DepthComponent depthComponent;

	public SsaoEffect () {
		init();
	}

	public SsaoEffect (int kernelSize, float radius, float power, int noiseSize, int blurSize) {
		init();
		ssaoComponent.setKernelSize(kernelSize).setRadius(radius).setPower(power).setNoiseSize(noiseSize);
		ssaoComposerComponent.setBlurSize(blurSize);
	}

	private void init () {
		normalDepthComponent = new NormalDepthComponent();
		depthComponent = new DepthComponent();
		ssaoComponent = new SsaoComponent();
		ssaoComposerComponent = new SsaoComposerComponent();

		addComponent(normalDepthComponent);
		addComponent(ssaoComponent);
		addComponent(depthComponent);
		addComponent(ssaoComposerComponent);
	}
	
	public Texture render (Texture input, boolean window) {
		Texture output = null;
		int width = input.getWidth(), height = input.getHeight();

		Texture depth = depthComponent.render(input, false, width, height);
		width = depthComponent.getWidth();
		height = depthComponent.getHeight();

		Texture normalDepth = normalDepthComponent.render(input, false, width, height);
		width = normalDepthComponent.getWidth();
		height = normalDepthComponent.getHeight();		

		Texture ssao = ssaoComponent.render(normalDepth, depth, true, width, height);
		width = ssaoComponent.getWidth();
		height = ssaoComponent.getHeight();				

		//output = ssaoComposerComponent.render(ssao, true, width, height);	

		return ssao;
	}

	public SsaoEffect setKernelSize (int kernelSize) {
		ssaoComponent.setKernelSize(kernelSize);
		return this;
	}

	public SsaoEffect setRadius (float radius) {
		ssaoComponent.setRadius(radius);
		return this;
	}

	public SsaoEffect setPower (float power) {
		ssaoComponent.setPower(power);
		return this;
	}

	public SsaoEffect setNoiseSize (int noiseSize) {
		ssaoComponent.setNoiseSize(noiseSize);
		return this;
	}

	public SsaoEffect setBlurSize (int blurSize) {
		ssaoComposerComponent.setBlurSize(blurSize);
		return this;
	}

}
