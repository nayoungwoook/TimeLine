package com.coconut.tl.objects;

import com.coconut.tl.Main;
import com.coconut.tl.effect.DustParticle;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.transform.MSTrans;

public class RObject extends MSObject {

	public static enum Directions {
		UP, LEFT, DOWN, RIGHT, NULL;
	};

	public static enum Module {
		MOVE, SWITCH
	}

	public Directions direction = Directions.UP;
	public MSTrans targetPosition;
	public MSTrans simulatedPosition;
	public boolean switched = true;
	protected TimeLine timeline;
	public boolean movementPad = false;

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

			if ((Main.game.gameState == 1 && Main.game.recordSystem.run && _node != null
					&& (Main.game.recordSystem.getTimer() == Main.game.replayTimer)) || Main.game.gameState == 0) {

				SetSize(Game.MS * 2, Game.MS / 3 * 2);

				Game.particles.add(new DustParticle((int) position.GetX() + (int) Math.round(Math.random() * 20) - 10,
						(int) position.GetY() + (int) Math.round(Math.random() * 20) - 10));
			}
		}

		if (dataType.equals(RObject.Module.SWITCH)) {
			switched = !switched;
		}

		simulatedPosition.SetTransform(targetPosition.GetX(), targetPosition.GetY());
	}

}
