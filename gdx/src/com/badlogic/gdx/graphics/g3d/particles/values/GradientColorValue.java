package com.badlogic.gdx.graphics.g3d.particles.values;


public class GradientColorValue extends ParticleValue {
		static private float[] temp = new float[4];

		private float[] colors = {1, 1, 1};
		public float[] timeline = {0};

		public float[] getTimeline () {
			return timeline;
		}

		public void setTimeline (float[] timeline) {
			this.timeline = timeline;
		}

		public float[] getColors () {
			return colors;
		}

		public void setColors (float[] colors) {
			this.colors = colors;
		}

		public float[] getColor (float percent) {
			int startIndex = 0, endIndex = -1;
			float[] timeline = this.timeline;
			int n = timeline.length;
			for (int i = 1; i < n; i++) {
				float t = timeline[i];
				if (t > percent) {
					endIndex = i;
					break;
				}
				startIndex = i;
			}
			float startTime = timeline[startIndex];
			startIndex *= 3;
			float r1 = colors[startIndex];
			float g1 = colors[startIndex + 1];
			float b1 = colors[startIndex + 2];
			if (endIndex == -1) {
				temp[0] = r1;
				temp[1] = g1;
				temp[2] = b1;
				return temp;
			}
			float factor = (percent - startTime) / (timeline[endIndex] - startTime);
			endIndex *= 3;
			temp[0] = r1 + (colors[endIndex] - r1) * factor;
			temp[1] = g1 + (colors[endIndex + 1] - g1) * factor;
			temp[2] = b1 + (colors[endIndex + 2] - b1) * factor;
			return temp;
		}

		public void load (GradientColorValue value) {
			super.load(value);
			colors = new float[value.colors.length];
			System.arraycopy(value.colors, 0, colors, 0, colors.length);
			timeline = new float[value.timeline.length];
			System.arraycopy(value.timeline, 0, timeline, 0, timeline.length);
		}
	}