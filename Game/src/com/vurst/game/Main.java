package com.vurst.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import com.vurst.game.input.Input;

import sun.font.CreatedFontTracker;

import static org.lwjgl.system.MemoryUtil.NULL;


public class Main implements Runnable {

	private int width = 1280;
	private int height = 720;
	
	private Thread thread;
	private boolean running = false;
	
	private long window;
	
	private Input input;
	
	public void init() {
		if(!glfwInit()) {
			
		}
		
		input = new Input();
		
		glfwWindowHint(GLFW_RESIZABLE,GL_TRUE);
		window = glfwCreateWindow(width, height, "Game", NULL, NULL);
		if(window == NULL) {
			return;
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		glfwSetWindowPos(window, (GLFWVidMode.WIDTH - width)/2, (GLFWVidMode.HEIGHT - height)/2);
		glfwSetKeyCallback(window, input);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public void run() {
		init();
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
