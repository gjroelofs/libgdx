
package com.badlogic.gdx.graphics.g3d.postprocessing.components.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.postprocessing.PostProcessingSystem;
import com.badlogic.gdx.graphics.g3d.postprocessing.components.PostProcessingComponent;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public abstract class QuadComponent implements PostProcessingComponent {
	protected PostProcessingSystem system;
	protected FrameBuffer frameBuffer;
	protected QuadShader shader;

	public QuadComponent () {
		frameBuffer = getFrameBuffer();
		shader = getShader();
		shader.init(this);
	}

	protected FrameBuffer getFrameBuffer () {
		return new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}

	abstract protected QuadShader getShader ();

	@Override
	public Texture render (Texture input, boolean window) {
		if (!window) frameBuffer.begin();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		shader.render(input);
		if (!window) frameBuffer.end();
		return frameBuffer.getColorBufferTexture();
	}

	@Override
	public void init (PostProcessingSystem system) {
		this.system = system;
	}

	@Override
	public PostProcessingSystem getSystem () {
		return system;
	}

	@Override
	public boolean needsMainTexture () {
		return false;
	}
}
