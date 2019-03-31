package com.example.PrototypeVaadin;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("Buchungssatz filtern")
public class BuchungssatzFilter extends CustomComponent implements View, ClickListener {

	VerticalLayout layout = new VerticalLayout();

	@Inject
	BuchungssatzDAO buchungssatzdao;

	Navigator navigator;
	Grid grid = new Grid(exampleDataSource());

	AbsoluteLayout absolute = new AbsoluteLayout();

	@Override
	public void enter(ViewChangeEvent event) {

		navigator = getUI().getNavigator();

		IndexedContainer container = exampleDataSource();

		Grid grid = new Grid(container);
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setWidth("500px");
		grid.setHeight("300px");

		HeaderRow filterRow = grid.appendHeaderRow();

		for (Object pid : grid.getContainerDataSource().getContainerPropertyIds()) {
			HeaderCell cell = filterRow.getCell(pid);

			TextField filterField = new TextField();
			filterField.setColumns(8);
			filterField.setInputPrompt("Filter");
			filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);

			filterField.addTextChangeListener(change -> {

				container.removeContainerFilters(pid);

				if (!change.getText().isEmpty())
					container.addContainerFilter(new SimpleStringFilter(pid, change.getText(), true, false));

			});
			cell.setComponent(filterField);
		}

		absolute.setWidth("1800px");
		absolute.setHeight("800px");
		layout.addComponents(grid);
		absolute.addComponent(layout);
		setCompositionRoot(absolute);

	}

	public static IndexedContainer exampleDataSource() {
		return TableExample.generateContent();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
}