package com.coconut.tl.state;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.MSMain;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;

import com.coconut.tl.Main;

public class VideoSplash implements MSState {

	@Override
	public void Init() {
		
		MSDisplay.compo.setBackground(new Color(0, 0, 0));
		player.play();
		VFXPanel.setScene(scene);

		System.out.println("play");

		Main.display.setLayout(new BorderLayout());
		Main.display.add(VFXPanel, "Center");
		MSDisplay.compo.setVisible(false);
	}

	private MediaPlayer player;
	public static Media m;
	private MediaView viewer;
	private JFXPanel VFXPanel;
	public MSMain compo;
	private Scene scene;

	public void getVideo() {

		System.out.println("Getting the splash video...");

		VFXPanel = new JFXPanel();
		Main.display.setBackground(new Color(0, 0, 0));
		VFXPanel.setBackground(new Color(0, 0, 0));

		File video_source = null;
//		try {
			video_source = new File( "splash.mp4");
//			video_source = Paths.get(url.toURI()).toFile();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}

		m = new Media(video_source.toURI().toString());
		player = new MediaPlayer(m);
		viewer = new MediaView(player);

		StackPane root = new StackPane();
		scene = new Scene(root);
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();

		viewer.setX(screen.getWidth() - Main.display.getWidth() / 2);
		viewer.setY(screen.getHeight() - Main.display.getHeight() / 2);
		viewer.setPreserveRatio(true);
		root.getChildren().add(viewer);

		System.out.println("Getting the splash video... : done!");
	}

	@Override
	public void Update() {
		if (player.getCurrentTime().equals(player.getStopTime())) {
			MSDisplay.compo.setVisible(true);
			Main.display.getContentPane().remove(VFXPanel);
			Main.title = new Title();
			MSState.SetState(Main.title);
		}
	}

	@Override
	public void Render() {
		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, MSDisplay.width * 2, MSDisplay.height * 2);
	}
}
