package com.aps.pong;

import javax.sound.midi.Soundbank;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

/**
 *
 * @author siabr
 * @modify lucaaslb
 */
public class Cena implements GLEventListener, KeyListener {
	private float POS_ATUAL_X = 0;

	private boolean INICIO = true;

	private float MOVE_Y = 0;
	private float MOVE_X = 0;
	private float VELOCIDADE = 0.01f;

	private float limite_inferior = -1;
	private float limite_superior = 1;
	private float limite_direita = 1;
	private float limite_esquerda = -1;

	private float BASTAO_X1 = -0.2f;
	private float BASTAO_X2 = 0.2f;
	private float BASTAO_Y1 = -0.7f;
	private float BASTAO_Y2 = -0.8f;

	private float CENTRO_TELA = 0;

	private float cX = 0.0f;
	private float cY = 0.0f;
	private float rX = 0.05f;
	private float rY = 0.075f;
	private float POSICAO_BOLA_X = cX + rX;
	private float POSICAO_BOLA_Y = cY + rY;

	private float BASTAO_PONTA_ESQUERDA = BASTAO_X1;
	private float BASTAO_PONTA_DIREITA = BASTAO_X2;
	private float MOVE_BASTAO_X = 0;

	private float BASTAO_CENTRO = (BASTAO_PONTA_ESQUERDA + BASTAO_PONTA_DIREITA) / 2;

	private boolean tem_que_subir = false;
	private boolean vai_pro_topo = false;
	private boolean tem_que_descer = false;
	private boolean vai_pra_baixo = false;

	@Override
	public void init(GLAutoDrawable drawable) {
		// dados iniciais da cena
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// obtem o contexto Opengl
		GL2 gl = drawable.getGL().getGL2();
		// define a cor da janela (R, G, G, alpha)
		gl.glClearColor(0, 0, 0, 1);

		// limpa a janela com a cor especificada
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity(); // lê a matriz identidade

		/*
		 * degl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0); // POSICAO_BOLA_Y = -(cY + rY)
		 * + MOVE_Y;senho da cena
		 *
		 */
		gl.glColor3f(0.5f, 0.3f, 0.8f);

		double lim = 2 * Math.PI;

		gl.glPushMatrix();

		this.gameOver();
		if (INICIO) {
			this.inicio(gl);
		}

		POSICAO_BOLA_Y = -(cY + rY) + MOVE_Y;
		POSICAO_BOLA_X = -(cX + rX) + MOVE_X;
		System.out.println("BOLA X: " + POSICAO_BOLA_X + " BOLA Y: " + POSICAO_BOLA_Y);

		if (POSICAO_BOLA_Y <= BASTAO_Y1) {
			tem_que_subir = true;
		}

		if (tem_que_subir) {
			direitaCima(gl);
		}

		if (vai_pro_topo) {
			esquerdaCima(gl);
		}

		if (tem_que_descer) {
			esquerdaBaixo(gl);
		}

		if (vai_pra_baixo) {
			direitaBaixo(gl);
		}

		gl.glBegin(GL2.GL_POLYGON);
		for (float i = 0; i < lim; i += 0.01) {
			gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));
			// System.out.println(cX + rX * Math.cos(i) + " : " + cY + rY * Math.sin(i));
		}
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(MOVE_BASTAO_X, 0, 0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2d(BASTAO_X1, BASTAO_Y1);
		gl.glVertex2d(BASTAO_X2, BASTAO_Y1);
		gl.glVertex2d(BASTAO_X2, BASTAO_Y2);
		gl.glVertex2d(BASTAO_X1, BASTAO_Y2);
		gl.glEnd();

		gl.glPopMatrix();

		gl.glFlush();
	}

	private void direitaCima(GL2 gl) {
		System.out.println("direitaCima");
		gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
		if (POSICAO_BOLA_X > limite_direita) {
			tem_que_subir = false;
			vai_pro_topo = true;

		}

	}

	private void esquerdaCima(GL2 gl) {
		System.out.println("esquerdaCima");
		gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
		if (POSICAO_BOLA_Y > limite_superior) {
			vai_pro_topo = false;
			tem_que_descer = true;

		}

	}

	private void esquerdaBaixo(GL2 gl) {
		System.out.println("esquerdaBaixo");
		gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_X < limite_esquerda) {
			tem_que_descer = false;
			vai_pra_baixo = true;

		}
	}

	private void direitaBaixo(GL2 gl) {
		System.out.println("direitaBaixo");
		gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_Y <= BASTAO_Y1) {
			vai_pra_baixo = false;

		}
	}

	private void inicio(GL2 gl) {
		gl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_Y <= BASTAO_Y1) {
			INICIO = false;
			System.out.println("BATEU NA ALTURA DO BASTÃO");
		}
	}

	private void gameOver() {
		if (POSICAO_BOLA_Y <= limite_inferior) {
			System.out.println("game over");
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// obtem o contexto grafico Opengl
		GL2 gl = drawable.getGL().getGL2();
		// ativa a matriz de projeção
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity(); // lê a matriz identidade
		// projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
		gl.glOrtho(-1, 1, -1, 1, -1, 1);
		// ativa a matriz de modelagem
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		System.out.println("Reshape: " + width + ", " + height);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_RIGHT:
			if (BASTAO_PONTA_DIREITA < 1) {
				MOVE_BASTAO_X += 0.1f;
				BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X);
				BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X);
			}
			break;

		case KeyEvent.VK_LEFT:
			if (BASTAO_PONTA_ESQUERDA > -1) {
				MOVE_BASTAO_X -= 0.1f;
				BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X);
				BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
