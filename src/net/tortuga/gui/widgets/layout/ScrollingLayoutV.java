package net.tortuga.gui.widgets.layout;


import java.util.ArrayList;

import net.tortuga.gui.widgets.IScrollable;
import net.tortuga.gui.widgets.IScrollbar;
import net.tortuga.gui.widgets.IWidgetFactory;
import net.tortuga.gui.widgets.LeftTopRightBottom;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.util.Log;

import org.lwjgl.input.Keyboard;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Rect;


public class ScrollingLayoutV extends LayoutV implements IScrollable {

	public boolean delegatedScrollEnabled = true;

	private IScrollbar scrollbar;

	private ArrayList<ChildPos> positions;

	private IWidgetFactory factory;


	/**
	 * Create scrolling layout V.<br>
	 * After creating, this layout must be filled with at least the minimal
	 * amount of children to stretch it accordingly.<br>
	 * It does not work well with children of different sizes.
	 * 
	 * @param childrenShown number of shown children
	 * @param blankChildFactory children factory
	 */
	public ScrollingLayoutV(int childrenShown, IWidgetFactory blankChildFactory) {
		super();
		this.childrenShown = childrenShown;

		for (int i = 1; i <= childrenShown; i++)
			addChild(blankChildFactory.getWidget());
		rememberPositions();
		removeAll();
		factory = blankChildFactory;
	}

	private class ChildPos {

		public ChildPos(Widget w) {
			this.rect = w.rect.copy();
			this.margins = w.margins.copy();
			this.minSize = w.minSize.copy();
		}


		public void modify(Widget w)
		{
			w.rect.setTo(this.rect);
			w.margins.setTo(this.margins);
			w.minSize.setTo(this.minSize);
		}

		public Rect rect;
		public LeftTopRightBottom margins;
		public Coord minSize;
	}


	public void adaptForSize(int childrenCount)
	{
		ArrayList<Widget> childrenBackup = new ArrayList<Widget>(allChildren);
		removeAll();
		setMinSize(0, 0);

		this.childrenShown = childrenCount;

		for (int i = 1; i <= childrenShown; i++) {
			addChild(factory.getWidget());
		}

		rememberPositions();
		removeAll();

		for (Widget w : childrenBackup) {
			addChild(w);
		}
	}


	private void rememberPositions()
	{
		calcChildSizes();
		positions = new ArrayList<ChildPos>(childrenShown);
		for (Widget ch : children) {
			if (ch == null) {
				Log.w("Null child in ScrollingLayoutV!");
			} else {
				positions.add(new ChildPos(ch));
			}
		}

		//Collections.reverse(positions);
		setMinSize(getSize());
	}

	private int childrenShown = 1;

	private ArrayList<Widget> allChildren = new ArrayList<Widget>();


	public void addChild(Widget widget)
	{
		allChildren.add(widget);
		if (children.size() < childrenShown) {
			children.add(widget);
		} else {
			widget.setVisible(false);
		}
	}


	@Override
	@Deprecated
	public void add(Widget child)
	{}


	@Override
	public double getContentSize()
	{
		return allChildren.size();
	}


	@Override
	public double getViewSize()
	{
		return childrenShown;
	}


	@Override
	public void onScrollbarChange(double value)
	{
		int startEntry = (int) ((getContentSize() - getViewSize()) * value);

		for (Widget w : children) {
			w.setVisible(false);
		}

		children.clear();
		if (allChildren.size() < childrenShown) {
			for (int i = 0, c = 0; i < allChildren.size(); i++, c++) {
				try {
					Widget w;
					children.add(w = allChildren.get(i));
					w.setGuiRoot(getGuiRoot());
					w.setVisible(true);
					positions.get(c).modify(w);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			for (int i = startEntry, c = 0; i < startEntry + childrenShown; i++, c++) {
				try {
					Widget w;
					children.add(w = allChildren.get(i));
					w.setGuiRoot(getGuiRoot());
					w.setVisible(true);
					positions.get(c).modify(w);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//calcChildSizes();
	}


	@Override
	public void removeAll()
	{
		children.clear();
		allChildren.clear();
	}


	@Override
	public Widget onScroll(Coord pos, int scroll)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
			return null;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			return null;
		}
		if (!delegatedScrollEnabled) return null;
		if (!isMouseOver(pos)) return null;
		return scrollbar.onScrollDelegate(scroll);
	}


	@Override
	public void onScrollbarConnected(IScrollbar scrollbar)
	{
		this.scrollbar = scrollbar;
	}

}
