package net.tortuga.gui.widgets.composite;


import java.util.ArrayList;
import java.util.List;

import net.tortuga.annotations.Internal;
import net.tortuga.gui.widgets.IRefreshable;
import net.tortuga.gui.widgets.IWidgetFactory;
import net.tortuga.gui.widgets.Widget;
import net.tortuga.gui.widgets.input.ScrollbarH;
import net.tortuga.gui.widgets.layout.LayoutV;
import net.tortuga.gui.widgets.layout.ScrollingLayoutH;
import net.tortuga.util.Align;


public class CompositeScrollBoxH extends LayoutV implements IRefreshable {

	private ScrollingLayoutH layoutH;
	private ScrollbarH scrollbar;


	public CompositeScrollBoxH(int size, IWidgetFactory fakeWidgetFactory, List<Widget> elements, int alignH, int alignV) {
		super(Align.CENTER, Align.TOP);

		layoutH = new ScrollingLayoutH(size, fakeWidgetFactory);
		layoutH.setAlign(alignH, alignV);

		for (Widget w : elements) {
			layoutH.addChild(w);
		}

		add(layoutH);

		add(scrollbar = new ScrollbarH((int) layoutH.getSize().x, layoutH));
		scrollbar.setMargins(3, 3, 3, 3);
	}


	public void adaptForSize(int size)
	{
		layoutH.adaptForSize(size);
		scrollbar.setMinWidth((int) layoutH.getSize().x);
	}


	public CompositeScrollBoxH enableDelegatedScroll(boolean state)
	{
		layoutH.delegatedScrollEnabled = state;
		return this;
	}


	public CompositeScrollBoxH(int size, IWidgetFactory fakeWidgetFactory) {
		this(size, fakeWidgetFactory, new ArrayList<Widget>(), Align.CENTER, Align.CENTER);
	}


	public CompositeScrollBoxH addItem(Widget item)
	{
		item.setGuiRoot(getGuiRoot());
		layoutH.addChild(item);
		return this;
	}


	public ArrayList<Widget> getItems()
	{
		return layoutH.children;
	}


	/**
	 * Refresh positions and contents after some children were added or removed.
	 */
	@Override
	public void refresh()
	{
		setGuiRoot(getGuiRoot());
		layoutH.onScrollbarChange(scrollbar.getValue());
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
		layoutH.removeAll();
	}


	@Override
	public void calcChildSizes()
	{
		super.calcChildSizes();

		getGuiRoot().scheduleRefresh(this);
	}
}
