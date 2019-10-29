package pong.cena;

import com.jogamp.opengl.GL2;

public class ColorController {

	public void getColor(Color c, GL2 gl) {
		float color[] = new float[3];
		switch (c.numCor) {
		case 0:
			color[0] = 1f;
			color[1] = 1f;
			color[2] = 1f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		case 1:
			color[0] = 0f;
			color[1] = 0f;
			color[2] = 0f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;

		case 2:
			color[0] = 1f;
			color[1] = 0f;
			color[2] = 0f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		case 3:
			color[0] = 0f;
			color[1] = 1f;
			color[2] = 0f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		case 4:
			color[0] = 0f;
			color[1] = 0f;
			color[2] = 1f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;

		case 5:
			color[0] = 1f;
			color[1] = 0f;
			color[2] = 1f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		case 6:
			color[0] = 0f;
			color[1] = 1f;
			color[2] = 1f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		case 7:
			color[0] = 1f;
			color[1] = 1f;
			color[2] = 0f;
			gl.glColor3f(color[0], color[1], color[2]);
			break;
		default:

			this.getColor(Color.WHITE, gl);
			break;
		}
	}

}
