package pong.cena;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import pong.menu.Menu;

public class Cena implements GLEventListener, KeyListener {
	private Menu menu;
	private ColorController colorController = new ColorController();
	private Color cor = Color.WHITE;

	private int opcao = 0;
	private boolean INICIO = true;
	private boolean PAUSE = false;

	private float MOVE_Y = 0;
	private float MOVE_X = 0;
	private float VELOCIDADE = 0.01f;

	private float LIMITE_INFERIOR = -1;
	private float LIMITE_SUPERIOR = 1;
	private float LIMITE_DIREITA = 1;
	private float LIMITE_ESQUERDA = -1;

	private float BASTAO_X1 = -0.2f;
	private float BASTAO_X2 = 0.2f;
	private float BASTAO_Y1 = -0.7f;
	private float BASTAO_Y2 = -0.8f;

	private float CENTRO_TELA = 0;
	private float MEIO_CENTRO_TELA_ESQUERDO = -0.5f;
	private float MEIO_CENTRO_TELA_DIREITO = 0.5f;

	private float cX = 0.0f;
	private float cY = 0.0f;
	private float rX = 0.05f;
	private float rY = 0.075f;
	private float POSICAO_BOLA_X = cX + rX;
	private float POSICAO_BOLA_Y = cY + rY;
	private float TAMANHO_BOLA = rX;

	private float BASTAO_PONTA_ESQUERDA = BASTAO_X1;
	private float BASTAO_PONTA_DIREITA = BASTAO_X2;
	private float MOVE_BASTAO_X = 0;

	private float BASTAO_CENTRO = 0;

	private boolean SUBIR_RETO = false;
	private boolean DESCER_RETO = false;
	private boolean SUBIR_DIREITA = false;
	private boolean SUBIR_ESQUERDA = false;
	private boolean DESCER_DIREITA = false;
	private boolean DESCER_ESQUERDA = false;

	public void init(GLAutoDrawable drawable) {
	}

	public void display(GLAutoDrawable drawable) {
		// obtem o contexto Opengl
		GL2 gl = drawable.getGL().getGL2();
		// define a cor da janela (R, G, G, alpha)
		gl.glClearColor(0, 0, 0, 1);

		// limpa a janela com a cor especificada
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity(); // lê a matriz identidade

		switch (opcao) {
		case 0:
			this.menu = new Menu();
			this.menu.principal();
			break;
		case 1:
			this.menu = new Menu();
			this.menu.regras();
			break;
		case 2:
			double lim = 2 * Math.PI;

			gl.glPushMatrix();
			colorController.getColor(cor, gl);
			if (INICIO) {
//                    this.gameOver();
				this.inicio(gl);
			}

			this.gameOver();

			POSICAO_BOLA_Y = -(cY + rY) + MOVE_Y;
			POSICAO_BOLA_X = -(cX + rX) + MOVE_X;
			System.out.println("-----------------------------------------------------");
			System.out.println("BOLA X: " + POSICAO_BOLA_X + " BOLA Y: " + POSICAO_BOLA_Y);
			System.out.println("CENTRO DO BASTÃO: " + BASTAO_CENTRO);
			System.out.println("PONTA ESQUERDA: " + BASTAO_PONTA_ESQUERDA + " PONTA DIREITA: " + BASTAO_PONTA_DIREITA);

			if (POSICAO_BOLA_Y <= BASTAO_Y1) {
				DESCER_DIREITA = false;
				DESCER_ESQUERDA = false;
				DESCER_RETO = false;

				BASTAO_CENTRO = (BASTAO_PONTA_ESQUERDA + BASTAO_PONTA_DIREITA) / 2;

				if (POSICAO_BOLA_X == -0.05f && BASTAO_CENTRO == 0.0f || POSICAO_BOLA_X == BASTAO_CENTRO) {
					SUBIR_RETO = true;
				}

				if (POSICAO_BOLA_X > BASTAO_CENTRO) {
					if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_ESQUERDO) {
						SUBIR_DIREITA = true;
					}

					if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO < MEIO_CENTRO_TELA_ESQUERDO) {
						SUBIR_ESQUERDA = true;
					}

					if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO < MEIO_CENTRO_TELA_DIREITO) {
						SUBIR_DIREITA = true;
					}

					if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_DIREITO) {
						SUBIR_ESQUERDA = true;
					}
				}

				if (POSICAO_BOLA_X < BASTAO_CENTRO) {
					if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_ESQUERDO) {
						SUBIR_ESQUERDA = true;
					}

					if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO < MEIO_CENTRO_TELA_ESQUERDO) {
						SUBIR_DIREITA = true;
					}

					if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO < MEIO_CENTRO_TELA_DIREITO) {
						SUBIR_ESQUERDA = true;
					}

					if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_DIREITO) {
						SUBIR_DIREITA = true;
					}
				}
			}

			if (SUBIR_ESQUERDA) {
				this.subirEsquerda(gl);
			}

			if (SUBIR_DIREITA) {
				this.subirDireita(gl);
			}

			if (DESCER_ESQUERDA) {
				this.descerEsquerda(gl);
			}

			if (DESCER_DIREITA) {
				this.descerDireita(gl);
			}

			if (SUBIR_RETO) {
				this.subirReto(gl);
			}

			if (DESCER_RETO) {
				this.descerReto(gl);
			}

			gl.glBegin(GL2.GL_POLYGON);
			for (float i = 0; i < lim; i += 0.01) {
				gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));
			}

			gl.glEnd();
			gl.glPopMatrix();

			gl.glPushMatrix();
			colorController.getColor(Color.MAGENTA, gl);
			gl.glTranslatef(MOVE_BASTAO_X, 0, 0);
			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex2d(BASTAO_X1, BASTAO_Y1);
			gl.glVertex2d(BASTAO_X2, BASTAO_Y1);
			gl.glVertex2d(BASTAO_X2, BASTAO_Y2);
			gl.glVertex2d(BASTAO_X1, BASTAO_Y2);
			gl.glEnd();

			gl.glPopMatrix();

			this.menu = new Menu();
			this.menu.jogo();
			break;
		case 3:
			this.menu = new Menu();
			this.menu.pause();
			break;
		case 4:
			this.menu = new Menu();
			this.menu.gameOver();
			break;
		}

		gl.glFlush();
	}

	private void subirDireita(GL2 gl) {
		System.out.println("SUBINDO PARA A DIREITA");
		gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
		if (POSICAO_BOLA_X >= LIMITE_DIREITA) {
			SUBIR_ESQUERDA = true;
			SUBIR_DIREITA = false;
		}

		if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
			DESCER_DIREITA = true;
			SUBIR_ESQUERDA = false;
			SUBIR_DIREITA = false;
		}
	}

	private void subirEsquerda(GL2 gl) {
		System.out.println("SUBINDO PARA A ESQUERDA");
		gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
		if (POSICAO_BOLA_X <= LIMITE_ESQUERDA) {
			SUBIR_ESQUERDA = false;
			SUBIR_DIREITA = true;
		}

		if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
			DESCER_ESQUERDA = true;
			SUBIR_ESQUERDA = false;
			SUBIR_DIREITA = false;
		}
	}

	private void descerEsquerda(GL2 gl) {
		System.out.println("DESCENDO PARA ESQUERDA");
		gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_X <= LIMITE_ESQUERDA) {
			DESCER_ESQUERDA = false;
			DESCER_DIREITA = true;
		}
	}

	private void descerDireita(GL2 gl) {
		System.out.println("DESCENDO PARA A DIREITA");
		gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_X >= LIMITE_DIREITA) {
			DESCER_DIREITA = false;
			DESCER_ESQUERDA = true;
		}
	}

	private void subirReto(GL2 gl) {
		System.out.println("SUBINDO RETO");
		gl.glTranslatef(0, MOVE_Y += VELOCIDADE, 0);
		if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
			SUBIR_RETO = false;
			DESCER_RETO = true;
		}
	}

	private void descerReto(GL2 gl) {
		System.out.println("DESCENDO RETO");
		gl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0);
	}

	private void inicio(GL2 gl) {
		System.out.println("BATEU NA ALTURA DO BASTÃO");
		gl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0);
		if (POSICAO_BOLA_Y <= BASTAO_Y1) {
			INICIO = false;
		}
	}

	private void gameOver() {
		if (POSICAO_BOLA_X < BASTAO_PONTA_ESQUERDA) {
			System.out.println("GAME OVER ESQUERDA");
			System.exit(0);
			opcao = 4;
		}

		if (POSICAO_BOLA_X > BASTAO_PONTA_DIREITA) {
			System.out.println("GAME OVER DIREITA");
			System.exit(0);
			opcao = 4;
		}
	}

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

	public void dispose(GLAutoDrawable drawable) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_RIGHT:
			if (BASTAO_PONTA_DIREITA < 1) {
				MOVE_BASTAO_X += 0.1f;
				BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X) + 0.05f;
				BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X) + 0.05f;
			}
			break;

		case KeyEvent.VK_LEFT:
			if (BASTAO_PONTA_ESQUERDA > -1) {
				MOVE_BASTAO_X -= 0.1f;
				BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X);
				BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X);
			}
			break;
		case KeyEvent.VK_R:
			opcao = 1;
			break;
		case KeyEvent.VK_ENTER:
			opcao = 2;
			break;
		case KeyEvent.VK_P:
			PAUSE = !PAUSE;
			if (PAUSE) {
				opcao = 3;
			} else {
				opcao = 2;
			}
			break;
		case KeyEvent.VK_V:
			opcao = 0;
			break;

		// Cor da bola
		case KeyEvent.VK_0:
			cor = Color.WHITE;
			break;
		case KeyEvent.VK_1:
			cor = Color.BLACK;
			break;
		case KeyEvent.VK_2:
			cor = Color.RED;
			break;
		case KeyEvent.VK_3:
			cor = Color.GREEN;
			break;
		case KeyEvent.VK_4:
			cor = Color.BLUE;
			break;
		case KeyEvent.VK_5:
			cor = Color.MAGENTA;
			break;
		case KeyEvent.VK_6:
			cor = Color.CYAN;
			break;
		case KeyEvent.VK_7:
			cor = Color.YELLOW;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}
}