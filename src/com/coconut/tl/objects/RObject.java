package com.coconut.tl.objects;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.ClearParticle;
import com.coconut.tl.effect.DustParticle;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.transform.MSTrans;

public class RObject extends MSObject {

	public static enum Directions {
		UP, LEFT, DOWN, RIGHT, NULL;
	};

	public static enum Module {
		NONE, MOVE, SWITCH
	}

	protected int plusBir = 0;

	public Directions direction = Directions.UP;
	public MSTrans targetPosition;
	public MSTrans simulatedPosition;
	public boolean switched = true;
	protected TimeLine timeline;
	public boolean movementPad = false;
	public boolean destroyed = false;

	public RObject(RObject.Directions direction, int x, int y, TimeLine timeline) {
		super(x, y, Game.MS, Game.MS);
		this.direction = direction;
		this.timeline = timeline;
		targetPosition = new MSTrans(x, y);
		simulatedPosition = new MSTrans(x, y);
	}

	@Override
	public void Update() {

		double _cwv = (Game.MS - GetWidth()) / 5;
		double _chv = (Game.MS - GetHeight()) / 5;

		SetWidth((int) (GetWidth() + _cwv));
		SetHeight((int) (GetHeight() + _chv));

		if (MSMath.GetDistance(targetPosition, position) >= Game.MS * 3)
			position = targetPosition;

		double _cxv = (targetPosition.GetX() - position.GetX()) / 6,
				_cyv = (targetPosition.GetY() - position.GetY()) / 6;

		position.Translate(_cxv, _cyv);

		if (!getClass().equals(Player.class)) {
			int xx = (int) Math.abs(MSInput.mousePointer.GetX() - position.GetX());
			int yy = (int) Math.abs(MSInput.mousePointer.GetY() + Game.MS / 3 - position.GetY());
			if (xx <= Game.MS / 2 && yy <= Game.MS / 2) {
				if (MSInput.mouseLeft) {

					if (Game.timelines.size() > 3) {
						Main.game.timelineScroll = timeline.getLineIndex();

						if (Main.game.timelineScroll < 0)
							Main.game.timelineScroll = 0;

						if (Main.game.timelineScroll > Game.timelines.size() - 3 - 1)
							Main.game.timelineScroll = Game.timelines.size() - 3 - 1;

						int xxx = Game.MS * 2;
						int yyy = (MSDisplay.height - (Game.MS / 7 * 9) - Game.MS * 2
								+ Game.MS * (timeline.getLineIndex() - Main.game.timelineScroll));

						for (int i = 0; i < (int) Math.round(Math.random() * 5) + 3; i++)
							Game.particles.add(new ClearParticle(xxx, yyy));

						MSInput.mouseLeft = false;
					}
				}
			}
		}

		if (this.timeline.getLineIndex() == Main.game.selectedTimeLineIndex && Main.game.gameState == 1
				&& !Main.game.recordSystem.run) {
			plusBir = 70;
		} else {
			plusBir = 0;
		}

		arrowTimer += 0.02;

		if (arrowTimer >= (int) (Math.PI * 2 * 10))
			arrowTimer = 0;

		SetBrightness(plusBir);
	}

	protected void setRotateDir() {
		if (direction == Directions.UP)
			SetRotation(0);
		if (direction == Directions.LEFT)
			SetRotation((float) Math.toRadians(-90));
		if (direction == Directions.DOWN)
			SetRotation((float) Math.toRadians(-180));
		if (direction == Directions.RIGHT)
			SetRotation((float) Math.toRadians(-270));
	}

	private double arrowTimer;

	@Override
	public void Render() {
		if (destroyed)
			return;
		super.Render();

		if (Main.game.selectedTimeLineIndex == timeline.getLineIndex() && !Main.game.recordSystem.run
				&& Main.game.gameState == 1 && !(MSInput.mouseLeft || MSInput.mouseRight)) {
			MSShape.RenderImage(Asset.UI_ARROW_MARKER, (int) position.GetX(),
					(int) position.GetY() - Game.MS / 3 * 2 - (int) (Math.sin(arrowTimer * 10) * 15), 2.5, Game.MS,
					Game.MS);
		}
	}

	public void turn(RObject.Module dataType) {
		if (dataType.equals(RObject.Module.MOVE)) {
			if (!movementPad) {
				if (direction == Directions.UP)
					targetPosition.Translate(0, -Game.MS);
				if (direction == Directions.LEFT)
					targetPosition.Translate(-Game.MS, 0);
				if (direction == Directions.DOWN)
					targetPosition.Translate(0, Game.MS);
				if (direction == Directions.RIGHT)
					targetPosition.Translate(Game.MS, 0);

			} else {
				movementPad = false;
			}

			TimeBundle _bundle = timeline.getBundleByTime(Main.game.recordSystem.getTimer());
			TimeNode _node = null;
			if (_bundle != null)
				_node = _bundle.getNodeByTime(Main.game.recordSystem.getTimer());

			if (!destroyed && (Main.game.gameState == 0 || (Main.game.gameState == 1 && Main.game.recordSystem.run
					&& Main.game.replayTimer == Main.game.recordSystem.getTimer())) && _node != null) {

				SetSize(Game.MS * 2, Game.MS / 3 * 2);

				Game.particles.add(new DustParticle((int) position.GetX() + (int) Math.round(Math.random() * 20) - 10,
						(int) position.GetY() + (int) Math.round(Math.random() * 20) - 10));
			}
		}

		if (dataType.equals(RObject.Module.SWITCH)) {
			switched = !switched;
		}

		simulatedPosition.SetTransform(targetPosition.GetX(), targetPosition.GetY());

		Main.game.checkCollision();
	}

}
