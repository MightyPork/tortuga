package net.tortuga.gui.widgets.composite;


import java.util.ArrayList;
import java.util.List;

import net.tortuga.annotations.Internal;
import net.tortuga.gui.widgets.IRefreshable;
import net.tortuga.gui.widgets.IWidgetFactory;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.input.ScrollbarV;
import net.tortuga.gui.widgets.layout.LayoutH;
import net.tortuga.gui.widgets.layout.ScrollingLayoutV;
import net.tortuga.util.Align;


public class CompositeScrollBoxV extends LayoutH implements IRefreshable {

	private ScrollingLayoutV layoutV;
	private ScrollbarV scrollbar;


	public CompositeScrollBoxV(int size, IWidgetFactory fakeWidgetFactory, List<Widget> elements, int alignH, int alignV) {
		super(Align.CENTER, Align.TOP);

		layoutV = new ScrollingLayoutV(size, fakeWidgetFactory);
		layoutV.setAlign(alignH, alignV);

		for (Widget w : elements) {
			layoutV.addChild(w);
		}

		add(layoutV);

		add(scrollbar = new ScrollbarV((int) layoutV.getSize().y, layoutV));
		scrollbar.setMargins(3, 3, 3, 3);
	}


	public CompositeScrollBoxV(int size, IWidgetFactory fakeWidgetFactory) {
		this(size, fakeWidgetFactory, new ArrayList<Widget>(), Align.CENTER, Align.TOP);
	}


	public CompositeScrollBoxV addItem(Widget item)
	{
		item.setGuiRoot(getGuiRoot());
		layoutV.addChild(item);
		return this;
	}


	public CompositeScrollBoxV enableDelegatedScroll(boolean state)
	{
		layoutV.delegatedScrollEnabled = state;
		return this;
	}


	public ArrayList<Widget> getItems()
	{
		return layoutV.children;
	}


	public void adaptForSize(int size)
	{
		layoutV.adaptForSize(size);
		scrollbar.setMinHeight((int) layoutV.getSize().y);
	}


	/**
	 * Refresh positions and contents after some children were added or removed.
	 */
	@Override
	public void refresh()
	{
		setGuiRoot(getGuiRoot());
		layoutV.onScrollbarChange(scrollbar.getValue());
		setGuiRoot(getGuiRoot());
		//scb.setValue(0);
	}


	@Override
	@Internal
	public void add(Widget child)
	{
		super.add(child);
		child.setGuiRoot(getGuiRoot());
	}


	@Override
	public void removeAll()
	{
		layoutV.removeAll();
	}


	@Override
	public void calcChildSizes()
	{
		super.calcChildSizes();

		getGuiRoot().scheduleRefresh(this);
	}
}
