package com.coconut.tl.state;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.json.JSONObject;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.ClearDust;
import com.coconut.tl.effect.transition.Transition;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.objects.tile.DirectionPad;
import com.coconut.tl.objects.tile.MovementPad;
import com.coconut.tl.objects.tile.Tile;
import com.coconut.tl.record.RecordSystem;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.stages.Stage;
import com.coconut.tl.stages.Stage01;
import com.coconut.tl.stages.Stage02;
import com.coconut.tl.stages.Stage03;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;
import dev.suback.marshmallow.transform.MSTrans;

public class Game implements MSState {

	public static final int MS = 50;

	public MSTrans targetCPosition = new MSTrans(0, 0, 1);

	public RecordSystem recordSystem;

	public static ArrayList<Tile> tiles = new ArrayList<>();
	public static ArrayList<MSObject> particles = new ArrayList<>();
	public static ArrayList<TimeLine> timelines = new ArrayList<>();
	public static ArrayList<Transition> transitions = new ArrayList<>();

	public int gameState = 0;
	public int tool = 0;
	public MSSprite cursorImage;
	public Stage stage;

	public int selectedTimeLineIndx = -1;

	public boolean playerPositionReset = false;
 
	// 스테이트 전환후, 기다리는 타이머
	private double awaitTimer = 0;

	public boolean _backupPlayerDied = false;

	public boolean playerDied = false;
	public MSTrans playerDiedPosition = new MSTrans(0, 0);

	public int stageIndex = 1, chapter = 0;
	private boolean stageStarted = false;

	public int unlockedTimelineCount = 0;
	public boolean lockedInput = false;
	
	public Game(int stageIndex, int chapter) {
		this.stageIndex = stageIndex;
		this.chapter = chapter;

		tiles.clear();
		particles.clear();
		timelines.clear();
		transitions.clear();
	}

	private void setStage(int stageIndex) {
		if (chapter == 1) {
			switch (stageIndex) {
			case 1:
				stage = new Stage01(this);
				break;
			case 2:
				stage = new Stage02(this);
				break;
			case 3:
				stage = new Stage03(this);
				break;
			}
		}
	}

	@Override
	public void Init() {

		stageStarted = false;
		cursorImage = Asset.UI_CURSOR[0];

		recordSystem = new RecordSystem();

		setStage(stageIndex);

		stage.stageStarted();

		targetCPosition.SetZ(1.3);
	}

	private int timelineY = 400;
	public int targetTimelineY = 0;
	private int timelineScroll = 0;

	public int getTimeLineScroll() {
		return timelineScroll;
	}

	private void renderTimeLine(TimeLine timeline, int index) {

		int TIME_NODE_SIZE = MS / 16 * 2;

		int len = stage.playerNodeSize * TIME_NODE_SIZE / MS + 3;

		int tLen = timelines.size();
		if (tLen > 4)
			tLen = 4;

		if (!timeline.getPlayerTimeLine()) {
			MSShape.RenderUIImage(Asset.UI_TIMELINE_BG[2], (1) * MS,
					(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 3, MS,
					MS);

			MSSprite img = null;
			if (timeline.getObject().equals("rock")) {
				img = Asset.ROCK;
			}
			if (timeline.getObject().equals("directionpad")) {
				img = Asset.DUNGEON_TILE[14];
			}
			if (timeline.getObject().equals("movementpad")) {
				img = Asset.DUNGEON_TILE[15];
			}

			if (img != null)
				MSShape.RenderUIImage(img, (1) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 3.2,
						MS / 3 * 2, MS / 3 * 2);

			if (timeline.locked) {
				MSShape.RenderUIImage(Asset.UI_DIE_MARKER, (1) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 4,
						MS, MS);
			}
		}

		for (int i = 0; i < len; i++) {
			if (!timeline.getPlayerTimeLine()) {

				MSSprite _img = Asset.UI_TIMELINE_BG[0];
				if (i == len - 1) {
					_img = Asset.UI_TIMELINE_BG[1];
				}

				MSShape.RenderUIImage(_img, (i + 2) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 3,
						MS, MS);

			} else {
				MSShape.RenderUIImage(Asset.UI_PLAYER_TIMELINE_BG, (i + 2) * MS, (MSDisplay.height - (MS / 2 * 3))
						+ (index - timelineScroll - (tLen - 1)) * MS + MS / 3 + timelineY, 3, MS, MS);
			}

			if (timeline.locked && !timeline.getPlayerTimeLine()) {
				MSShape.RenderUIImage(Asset.UI_DARK, (i + 2) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 3.2,
						MS, MS);
			}
		}

		for (int i = 0; i < timeline.bundles.size(); i++) {
			TimeBundle _bundle = timeline.bundles.get(i);

			int selected = 0;
			if (_bundle.onMouse)
				selected = 1;

			for (int j = 0; j < _bundle.nodes.size(); j++) {
				MSSprite _image = Asset.UI_TIMELINE[selected][0];
				if (!timeline.getPlayerTimeLine()) {
					if (j == 0)
						_image = Asset.UI_TIMELINE[selected][2];
					else if (j == _bundle.nodes.size() - 1)
						_image = Asset.UI_TIMELINE[selected][1];
					if (_bundle.nodes.size() == 1)
						_image = Asset.UI_TIMELINE[selected][3];
				} else {
					_image = Asset.UI_PLAYER_TIMELINE[0];
					if (j == 0)
						_image = Asset.UI_PLAYER_TIMELINE[2];
					else if (j == _bundle.nodes.size() - 1)
						_image = Asset.UI_PLAYER_TIMELINE[1];
				}

				if (!timeline.getPlayerTimeLine()) {
					MSShape.RenderUIImage(_image, 2 * MS + TIME_NODE_SIZE * j + _bundle.startPosition * TIME_NODE_SIZE,
							(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY,
							3, MS, MS);
				} else {
					MSShape.RenderUIImage(_image, 2 * MS + TIME_NODE_SIZE * j + _bundle.startPosition * TIME_NODE_SIZE,
							(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + MS / 3
									+ timelineY,
							3, MS, MS);
				}
			}
		}

		if (!timeline.getPlayerTimeLine())
			MSShape.RenderUIImage(Asset.UI_MARKER, 2 * MS + TIME_NODE_SIZE * (recordSystem.getTimer()),
					(MSDisplay.height - (MS / 2 * 3)) + (index - timelineScroll - (tLen - 1)) * MS + timelineY, 3.2, MS,
					MS);
	}

	private boolean renderTimeLineButton(int index) {

		int len = timelines.size();
		if (len > 4)
			len = 4;
		int x = (2 + index) * MS;
		int y = (MSDisplay.height - (MS / 2 * 3)) + (0 - (len)) * MS + MS / 3 * 2 + timelineY;

		if (index == 0) {
			// is play
			if (recordSystem.run)
				MSShape.RenderUIImage(Asset.UI_BUTTON[1], x, y, 3, MS, MS);
			else
				MSShape.RenderUIImage(Asset.UI_BUTTON[0], x, y, 3, MS, MS);
		} else if (index == 1) {
			MSShape.RenderUIImage(Asset.UI_BUTTON[2], x, y, 3, MS, MS);
		} else if (index == 2) {
			MSShape.RenderUIImage(Asset.UI_BUTTON[3], x, y, 3, MS, MS);
		}

		return Math.abs(MSInput.mousePointer.GetX() - x) / 2 <= MS / 5
				&& Math.abs(MSInput.mousePointer.GetY() - y) / 2 <= MS / 5 && MSInput.mouseLeft;
	}

	private void renderUi() {

		if (playerDied) {
			MSShape.RenderImage(Asset.UI_DIE_MARKER, (int) playerDiedPosition.GetX(), (int) playerDiedPosition.GetY(),
					3, MS, MS);
		}

		// BUTTON
		if (renderTimeLineButton(0)) {
			recordSystem.run = !recordSystem.run;
			if (recordSystem.run)
				recordSystem.resetTimer();
			MSInput.mouseLeft = false;
		}

		if (renderTimeLineButton(1)) {
			tool = 0;
			MSInput.mouseLeft = false;
		}

		if (renderTimeLineButton(2)) {
			tool = 1;
			MSInput.mouseLeft = false;
		}

		int len = timelines.size();
		if (len > 4)
			len = 4;

		if (gameState == 1) {
			if (MSInput.keys[KeyEvent.VK_W]) {

				if (timelineScroll > 0)
					timelineScroll--;

				MSInput.keys[KeyEvent.VK_W] = false;
			}

			if (MSInput.keys[KeyEvent.VK_S]) {
				if (timelineScroll + 3 < timelines.size() - 1)
					timelineScroll++;

				MSInput.keys[KeyEvent.VK_S] = false;
			}
		}

		renderTimeLine(timelines.get(0), timelineScroll);
		for (int i = 0; i < len - 1; i++) {
			renderTimeLine(timelines.get(i + timelineScroll + 1), i + 1 + timelineScroll);
		}

		if (!stageStarted) {
			MSShape.SetColor(new Color(0, 0, 0));
			MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 4, MSDisplay.width * 2, MSDisplay.height * 2);

			MSShape.SetColor(new Color(255, 255, 255));
			MSShape.SetFont(Asset.FONT[2]);

			JSONObject obj = Main.langManager.langData;

			MSShape.RenderText(obj.getJSONObject("STAGES").getString(stageIndex + ""), MSDisplay.width / 2,
					MSDisplay.height / 2, 8);
		}

		stage.render();

		// CURSOR
		MSShape.RenderUIImage(cursorImage, (int) MSInput.mousePointer.GetX() + 15, (int) MSInput.mousePointer.GetY(),
				10, MS, MS);

		// STAGE CLEARED
		if (stage.cleared && stage.clearTimer >= 1) {
			MSShape.SetColor(new Color(20, 20, 20, 150));
			MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, MSDisplay.width * 2, MSDisplay.height * 2);

			MSShape.RenderImage(Asset.UI_STAGE_CLEARED, MSDisplay.width / 2, MSDisplay.height / 2 + 5, 3, MS * 8, MS);

			MSShape.SetFont(Asset.FONT[1]);

			int y = MSDisplay.height / 7 * 5;
			if (Math.abs(y - MSInput.mousePointer.GetY() - 13) <= 15 && stageIndex + 1 <= 5) {
				MSShape.SetColor(new Color(255, 255, 255));
				if (MSInput.mouseLeft && transitions.size() == 0) {
					Main.game = new Game(stageIndex + 1, 1);
					MSState.SetState(Main.game);

					MSInput.mouseLeft = false;
				}
			} else {
				MSShape.SetColor(new Color(155, 155, 155));
			}

			MSShape.RenderText("next stage", MSDisplay.width / 2, y, 3);

			y = MSDisplay.height / 7 * 5 + 40;
			if (Math.abs(y - MSInput.mousePointer.GetY() - 13) <= 15) {
				MSShape.SetColor(new Color(255, 255, 255));
				if (MSInput.mouseLeft && transitions.size() == 0) {
					Main.select = new StageSelect();
					MSState.SetState(Main.select);

					MSInput.mouseLeft = false;
				}
			} else {
				MSShape.SetColor(new Color(155, 155, 155));
			}

			MSShape.RenderText("stage select", MSDisplay.width / 2, y, 3);

			for (int i = -2; i < 3; i++) {
				if ((int) Math.round(Math.random() * 4) == 0)
					particles.add(new ClearDust(MSDisplay.width / 2 + i * 100, MSDisplay.height / 2));
			}
		}
	}

	public void playerDie() {
		if (!_backupPlayerDied) {
			MSCamera.position.Translate((int) Math.round(Math.random() * 50) - 25,
					(int) Math.round(Math.random() * 50) - 25);

			_backupPlayerDied = true;
		}

		if (gameState == 0)
			recordSystem.changeRecording();

		if (gameState == 1) {
			recordSystem.run = false;
		}
	}

	@Override
	public void Render() {

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Render();

		if (awaitTimer < 0.1) {
			MSShape.SetColor(new Color(0, 0, 0));
			MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 3, MSDisplay.width * 2, MSDisplay.height * 2);
		}

		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Render();

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Render();

		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).ownerObject != null) {
				timelines.get(i).ownerObject.Render();
			}
		}

		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 0.9, MSDisplay.width * 2, MSDisplay.height * 2);
		MSShape.RenderImage(Asset.STAGES[stageIndex - 1], MSDisplay.width / 2, MSDisplay.height / 2, 1, MS * 24,
				MS * 13);

		renderUi();
	}

	private double timer = 0;

	private void cameraMovement() {
		timer += 0.025;

		double rotValue = Math.sin(timer) / 200;
		MSCamera.rotation = (float) rotValue;
		MSCamera.position.Translate(Math.sin(timer / 10) * 1.2, Math.cos(timer / 10) * 1.2 + 3);

		if (timer > Math.PI * 2 * 10)
			timer = 0;

		if (gameState == 0) {
			if (timelines.size() > 0 && timelines.get(0).ownerObject != null)
				targetCPosition.SetTransform(timelines.get(0).ownerObject.position.GetX() - MSDisplay.width / 2,
						timelines.get(0).ownerObject.position.GetY() - MSDisplay.height / 2, 1.6);
		} else {
			if (recordSystem.run && !stage.cleared) {
				if (targetCPosition != null && timelines.get(0) != null && timelines.get(0).ownerObject != null)
					targetCPosition.SetTransform(timelines.get(0).ownerObject.position.GetX() - MSDisplay.width / 2,
							timelines.get(0).ownerObject.position.GetY() - MSDisplay.height / 2, 1.6);
			} else {
				targetCPosition.SetTransform(0, 0, 1);
			}
		}

		double _cxv = (targetCPosition.GetX() - MSCamera.position.GetX()) / 10,
				_cyv = (targetCPosition.GetY() - MSCamera.position.GetY()) / 10;
		double _czv = (targetCPosition.GetZ() - MSCamera.position.GetZ()) / 40;

		MSCamera.position.Translate(_cxv, _cyv, _czv);
	}

	// 턴
	private double turnTimer = 0;
	public static double turnSpeed = 0.045;

	public void changeGameState(int state) {
		gameState = state;

		// 에디트 모드로 이동하기
		if (state == 1) {
			turnTimer = 0;
			recordSystem.resetTimer();
		}
	}

	private void updateTurn() {

		turnTimer += turnSpeed;
		if (turnTimer >= 1) {
			turnTimer = 0;

			if (!stage.cleared && recordSystem.run && playerPositionReset && this.awaitTimer >= 0.1)
				Asset.WAV_MOVE.play();

			if (recordSystem != null) {
				if (gameState == 0)
					updateRecordTurn();
				else if (gameState == 1)
					updateReplayTurn();
			}
		}
	}

	public int replayTimer = 0;

	public void updateReplayTurn() {

		// 현재 노드에 따라 화면 구성하기 (리플레이)
		for (int i = 0; i < timelines.size(); i++) {
			TimeBundle _curBundle = timelines.get(i).getBundleByTime(recordSystem.getTimer());
			TimeNode _curNode = null;

			if (_curBundle != null) {
				_curNode = _curBundle.getNodeByTime(recordSystem.getTimer());
			}

			if (_curNode != null) {
				recordSystem.createPausedGame();
			}
		}

		// 타이머 돌리기
		if (recordSystem.run)
			recordSystem.runTimer();
	}

	public void checkCollision() {

		// 업데이트 하고 미리 충돌 체킹 (레코딩 부분)
		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).ownerObject != null && timelines.get(i).ownerObject.getClass() == Rock.class) {
				((Rock) timelines.get(i).ownerObject).checkInGameCollision();
			}
			if (timelines.get(i).ownerObject != null && timelines.get(i).ownerObject.getClass() == DirectionPad.class) {
				((DirectionPad) timelines.get(i).ownerObject).checkInGameCollision();
			}
			if (timelines.get(i).ownerObject != null && timelines.get(i).ownerObject.getClass() == MovementPad.class) {
				((MovementPad) timelines.get(i).ownerObject).checkInGameCollision();
			}
		}

		if (stage != null) {
			for (int i = 0; i < stage.colboxes.size(); i++) {
				stage.colboxes.get(i).checkCollision();
			}
		}
	}

	public void updateRecordTurn() {
		if (recordSystem.isRecording()) {
			checkCollision();

			recordSystem.record();

			if (_backupPlayerDied) {
				return;
			}

			for (int i = 0; i < timelines.size(); i++) {
				if (timelines.get(i).ownerObject != null) {
					if (timelines.get(i).getBundleByTime((int) timer) != null)
						timelines.get(i).ownerObject.turn(
								timelines.get(i).getBundleByTime((int) timer).getNodeByTime((int) timer).getDataType());
				}
			}
		}
	}

	@Override
	public void Update() {

		if (gameState == 0) {
			targetTimelineY = 400;
		} else if (gameState == 1) {
			if (recordSystem.run)
				targetTimelineY = 400;
			else
				targetTimelineY = -20;
		}

		if (stage.cleared)
			targetTimelineY = 400;

		if (tool == 0)
			cursorImage = Asset.UI_CURSOR[0];
		else if (tool == 1)
			cursorImage = Asset.UI_CURSOR[1];

		timelineY += (targetTimelineY - timelineY) / 20;
		
		if(MSInput.keys[KeyEvent.VK_S] && MSInput.keys[KeyEvent.VK_CONTROL]) {
			System.out.println("s");
			Main.saveLoader.writeSaveFile();
		}
		
		if (MSInput.keys[KeyEvent.VK_SPACE] && !stageStarted) {

			if (!stageStarted) {
				awaitTimer = 0;

				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 9; j++) {
						transitions.add(new Transition(transitions, true, (i - 1) * MS * 2, MS * 2 * (j - 1)));
					}
				}
			}

			stageStarted = true;

			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}

		if (awaitTimer >= 1 && stageStarted) {
			recordSystem.update();
			updateTurn();
			cameraMovement();
		} else {
			awaitTimer += 0.02;
		}

		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Update();

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Update();

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Update();

		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).ownerObject != null)
				timelines.get(i).ownerObject.Update();
		}

		selectedTimeLineIndx = -1;
		for (int i = 0; i < timelines.size(); i++) {
			timelines.get(i).update();
			for (int j = 0; j < timelines.get(i).bundles.size(); j++) {
				timelines.get(i).bundles.get(j).update();
			}
		}

		if (stage != null)
			stage.update();

	}

}
