package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.MathUtils;

public class ScaledNumericValue extends RangedNumericValue {
	private float[] scaling = {1};
	public float[] timeline = {0};
	private float highMin, highMax;
	private boolean relative = false;

	public float newHighValue () {
		return highMin + (highMax - highMin) * MathUtils.random();
	}

	public void setHigh (float value) {
		highMin = value;
		highMax = value;
	}

	public void setHigh (float min, float max) {
		highMin = min;
		highMax = max;
	}

	public float getHighMin () {
		return highMin;
	}

	public void setHighMin (float highMin) {
		this.highMin = highMin;
	}

	public float getHighMax () {
		return highMax;
	}

	public void setHighMax (float highMax) {
		this.highMax = highMax;
	}

	public float[] getScaling () {
		return scaling;
	}

	public void setScaling (float[] values) {
		this.scaling = values;
	}

	public float[] getTimeline () {
		return timeline;
	}

	public void setTimeline (float[] timeline) {
		this.timeline = timeline;
	}

	public boolean isRelative () {
		return relative;
	}

	public void setRelative (boolean relative) {
		this.relative = relative;
	}

	public float getScale (float percent) {
		int endIndex = -1;
		int n = timeline.length;
		for (int i = 1; i < n; i++) {
			float t = timeline[i];
			if (t > percent) {
				endIndex = i;
				break;
			}
		}
		if (endIndex == -1) return scaling[n - 1];
		int startIndex = endIndex - 1;
		float startValue = scaling[startIndex];
		float startTime = timeline[startIndex];
		return startValue + (scaling[endIndex] - startValue) * ((percent - startTime) / (timeline[endIndex] - startTime));
	}

	public void load (ScaledNumericValue value) {
		super.load(value);
		highMax = value.highMax;
		highMin = value.highMin;
		scaling = new float[value.scaling.length];
		System.arraycopy(value.scaling, 0, scaling, 0, scaling.length);
		timeline = new float[value.timeline.length];
		System.arraycopy(value.timeline, 0, timeline, 0, timeline.length);
		relative = value.relative;
	}
}