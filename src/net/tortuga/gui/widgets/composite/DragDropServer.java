package net.tortuga.gui.widgets.composite;


import java.util.HashSet;
import java.util.Set;

import net.tortuga.gui.widgets.Widget;
import net.tortuga.level.program.tiles.ProgTileGrain;
import net.tortuga.sounds.Effects;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.porcupine.coord.Coord;


/**
 * Drag'n'Drop server widget (should be placed below all widgets that use it) -
 * handles dragging and rendering.
 * 
 * @author MightyPork
 */
public class DragDropServer extends Widget {

	private Set<IDragSlot> slots = new HashSet<IDragSlot>();

	/** Dragged stone */
	public IDragStone dragged = null;
	private IDragSlot dragOriginSlot = null;
	/**
	 * Paint mode (click does not discard the dragged piece, allowing multiple
	 * to be built)
	 */
	public boolean paintMode = false;
	public boolean paintOneOnly = false;

	private IDragSlot lastEventSlot = null;


	public void registerSlot(IDragSlot slot)
	{
		slots.add(slot);
	}


	/**
	 * New DND server
	 */
	public DragDropServer() {
		setMargins(0, 0, 0, 0);
		setRenderIndex(1000);
	}


	@Override
	public void render(Coord mouse)
	{
		// draw dragged stone
		if (isDragging()) {
			dragged.render(mouse);
		}

	}


	/**
	 * Get if can start dragging (free hands)
	 * 
	 * @return can start dragging
	 */
	public boolean canStartDrag()
	{
		return !isDragging();
	}


	/**
	 * Select stone for dragging
	 * 
	 * @param iDragStone
	 */
	public void startDrag(IDragStone iDragStone)
	{
		dragged = iDragStone;
		paintMode = false;
		paintOneOnly = false;
	}


	/**
	 * Enter paint mode (mouse went up after going down to start dragging - only
	 * in shops!)
	 */
	public void enterPaintMode()
	{
		paintMode = true;
	}


	/**
	 * Get if is dragging
	 * 
	 * @return is dragging
	 */
	public boolean isDragging()
	{
		return dragged != null;
	}


	/**
	 * Get dragged stone
	 * 
	 * @return the dragged stone, if any
	 */
	public IDragStone getDragged()
	{
		return dragged;
	}


	public void notifySlotsStoneCreated(IDragStone stone)
	{
		if (stone == null) return;
		if (stone instanceof DragStone) {
			for (IDragSlot slot : slots) {
				if (slot.isShopSlot()) slot.onStoneCreated((DragStone) stone);
			}
		} else {
			DragStoneExtended dse = (DragStoneExtended) stone;
			notifySlotsStoneCreated(dse.getTopStone());
			notifySlotsStoneCreated(dse.getMiddleStone());
			notifySlotsStoneCreated(dse.getBottomStone());
		}
	}


	public void notifySlotsStoneDestroyed(IDragStone stone)
	{
		if (stone == null) {
			return;
		}
		if (stone instanceof DragStone) {
			for (IDragSlot slot : slots) {
				if (slot.isShopSlot()) slot.onStoneDestroyed((DragStone) stone);
			}
		} else {
			DragStoneExtended dse = (DragStoneExtended) stone;
			notifySlotsStoneDestroyed(dse.getTopStone());
			notifySlotsStoneDestroyed(dse.getMiddleStone());
			notifySlotsStoneDestroyed(dse.getBottomStone());
		}
	}


	public static void playDrag()
	{
		Effects.play("gui.program.grab");
	}


	public static void playDrop()
	{
		Effects.play("gui.program.drop");
	}


	public static void playDelete()
	{
		Effects.play("gui.program.delete");
	}


	@Override
	public Widget onMouseButton(Coord pos, int button, boolean down)
	{
		boolean up = !down;
		boolean left = button == 0;
		boolean right = button == 1;
		boolean dragging = isDragging();

		IDragSlot hoverSlot = null;
		for (IDragSlot slot : slots) {
			if (slot.isMouseOver()) {
				hoverSlot = slot;
				break;
			}
		}

		if (!dragging && hoverSlot != null && Keyboard.isKeyDown(Keyboard.KEY_DELETE) && left && down) {
			if (hoverSlot.isShopSlot()) return this;
			if (!hoverSlot.hasStone()) return this;
			playDelete();
			notifySlotsStoneDestroyed(hoverSlot.getStoneInSlot());
			hoverSlot.deleteStone();
		}

		if (!dragging && hoverSlot != null && left && down) {
			// start drag if there's anything in this slot
			if (hoverSlot.hasStone()) {
				playDrag();
				startDrag(hoverSlot.grabStone());
				lastEventSlot = hoverSlot;
				dragOriginSlot = hoverSlot;
			}

			return this;
		}

		if (dragging && !paintMode && hoverSlot != null && hoverSlot == lastEventSlot && left && up) {
			// paint mode, one only

			enterPaintMode();
			lastEventSlot = null;

			if (!hoverSlot.isShopSlot() || ((DragStone) dragged).getStoneBase().isSingleInstance()) {
				paintOneOnly = true;
			}
			return this;
		}

		if (dragging && !paintMode && (hoverSlot == null || !hoverSlot.acceptsStone(dragged)) && up && left) {
			// mouse up outside slots - drop dragged stone
			dragOriginSlot.returnStone(dragged);
			dragged = null;
			paintMode = false;
			lastEventSlot = null;

			playDrop();
			return this;
		}

		if (dragging && paintMode && hoverSlot != null && down && left) {
			if (hoverSlot.isShopSlot()) {
				playDelete();
				notifySlotsStoneDestroyed(dragged);
				dragged = null;
				paintMode = false;
				lastEventSlot = null;

				if (hoverSlot.hasStone()) {
					startDrag(hoverSlot.grabStone());
					lastEventSlot = hoverSlot;
					dragOriginSlot = hoverSlot;
				}

				return this;
			}

			// place one of the paint mode
			if (hoverSlot.acceptsStone(dragged)) {
				if (!hoverSlot.hasStone()) {
					playDrop();
					hoverSlot.putStone(dragged.copy());
					if (paintOneOnly) {
						dragged = null;
						paintMode = false;
						lastEventSlot = null;
					}
				} else {
					// has stone.
					if (paintOneOnly) {
						playDrop();
						IDragStone taken = hoverSlot.grabStone();
						hoverSlot.putStone(dragged);
						dragged = taken;
						lastEventSlot = null;
						dragOriginSlot = hoverSlot;
					}
				}
			}
			return this;
		}

		if (dragging && paintMode && down && right) {
			// right click - discard dragging
			playDrop();
			dragOriginSlot.returnStone(dragged);
			dragged = null;
			paintMode = false;
			lastEventSlot = null;
			return this;
		}

		if (dragging && !paintMode && hoverSlot != null && up && left) {
			if (hoverSlot.isShopSlot()) {
				playDelete();
				notifySlotsStoneDestroyed(dragged);
				dragged = null;
				paintMode = false;
				lastEventSlot = null;
				return this;
			}

			// drop in slot
			if (hoverSlot.acceptsStone(dragged)) {
				if (!hoverSlot.hasStone()) {
					playDrop();
					hoverSlot.putStone(dragged);
					dragged = null;
					paintMode = false;
					lastEventSlot = null;
				} else {
					playDrop();
					IDragStone taken = hoverSlot.grabStone();
					hoverSlot.putStone(dragged);
					dragOriginSlot.putStone(taken);
					dragged = null;
					paintMode = false;
					lastEventSlot = null;
				}
			}
			return this;
		}

//		if(dragging && !paintMode && hoverSlot != null && down && left) {
//			// drop in slot
//			if(hoverSlot.acceptsStone(dragged)) {
//				hoverSlot.putStone(dragged);
//				dragged = null;
//				paintMode = false;
//				lastEventSlot = null;
//			}
//			return this;			
//		}		

		return null;
	}


	public boolean isInNumberEditMode()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
				|| Mouse.isButtonDown(1);
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		if (!isInNumberEditMode()) {
			return null;
		}

		IDragSlot hoverSlot = null;
		for (IDragSlot slot : slots) {
			if (slot.isMouseOver()) {
				hoverSlot = slot;
				break;
			}
		}

		if (hoverSlot == null) return null;
		if (!hoverSlot.hasStone()) return null;
		if (hoverSlot.isShopSlot()) return null;

		IDragStone ids = hoverSlot.getStoneInSlot();
		if (!(ids instanceof DragStone)) return null;
		DragStone ds = (DragStone) ids;

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			scroll *= 10;
		}

		if (ds.isGrain()) {
			ProgTileGrain grain = ds.getHexagonStone();
			if (grain.hasExtraNumber()) {
				grain.setExtraNumber(grain.getExtraNumber() + scroll);
			}
		}

		return null;
	}


	@Override
	public Widget onKey(int key, char chr, boolean down)
	{
		if (down && key == Keyboard.KEY_DELETE) {
			if (dragged != null) {
				playDelete();
				notifySlotsStoneDestroyed(dragged);
			}
			dragged = null;
			paintMode = false;
			lastEventSlot = null;
			return this;
		}

		if (down && key == Keyboard.KEY_ADD) {
			onScroll(null, 1);
		}

		if (down && key == Keyboard.KEY_SUBTRACT) {
			onScroll(null, -1);
		}

		return null;
	}


	@Override
	public void calcChildSizes()
	{
		rect.setTo(0, 0, 0, 0); // technical widget
	}

}
