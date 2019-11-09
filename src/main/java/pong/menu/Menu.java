package pong.menu;

import com.jogamp.opengl.util.awt.TextRenderer;
import pong.Renderer;

import java.awt.*;

public class Menu {
    private TextRenderer textRenderer;

    private void voltar() {
        this.desenhaTexto(500, 10, Color.BLUE, "<- Voltar (v)", 15);
    }

    private void desenhaTexto(int xPosicao, int yPosicao, Color cor, String frase, int size) {
        textRenderer = new TextRenderer(new Font("Arial Negrito", Font.PLAIN, size));
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }

    public void principal() {
        this.desenhaTexto(225, 300, Color.BLUE, "PONG", 40);
        this.desenhaTexto(20, 20, Color.BLUE, "Regras (r)", 15);
        this.desenhaTexto(20, 40, Color.BLUE, "Jogo (Enter)", 15);
        this.desenhaTexto(520, 10, Color.BLUE, "Sair (Esc)", 15);
    }

    public void regras() {
        this.desenhaTexto(225, 500, Color.BLUE, "Regras:", 40);
        this.voltar();
    }

    public void jogo() {
        this.desenhaTexto(0, 580, Color.PINK, "Fase 1", 15);
        this.voltar();
    }

    public void pause() {
        this.desenhaTexto(225, 300, Color.PINK, "PAUSE", 40);
    }

    public void gameOver() {
        this.desenhaTexto(175, 300, Color.PINK, "GAME OVER", 40);
        this.voltar();
    }
}
