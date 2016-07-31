
package com.badlogic.gdx.graphics.g3d.postprocessing.components.ssao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.utils.QuadComponent;

public class SsaoComponent extends QuadComponent<SsaoShader> {

	@Override
	protected SsaoShader getShader () {
		return new SsaoShader();
	}
	
	public Texture render (Texture normal, Texture depth, boolean window, int width, int height) {
		if (!window) {
			checkFrameBuffer(width, height);

			if (frameBuffer == null) {
				frameBuffer = getFrameBuffer(width, height);
			}

			frameBuffer.begin();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		shader.render(normal, depth);

		if (!window) {
			// ScreenshotFactory.saveScreenshot(frameBuffer.getWidth(), frameBuffer.getHeight(), "toto", true);
			frameBuffer.end();
			return frameBuffer.getColorBufferTexture();
		}
		return null;
	}

	public SsaoComponent setKernelSize (int kernelSize) {
		shader.setKernelSize(kernelSize);
		return this;
	}

	public SsaoComponent setRadius (float radius) {
		shader.setRadius(radius);
		return this;
	}

	public SsaoComponent setPower (float power) {
		shader.setPower(power);
		return this;
	}

	public SsaoComponent setNoiseSize (int size) {
		shader.setNoiseSize(size);
		return this;
	}
}
