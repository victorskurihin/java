/*
 * Inside.java
 * This file was last modified at 29.11.18 11:00 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.DataGrid.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import ru.otus.gwt.client.aside.News;
import ru.otus.gwt.client.aside.CBRValutes;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.client.widget.AddView;
import ru.otus.gwt.client.widget.SearchView;
import ru.otus.gwt.client.widget.TaxView;
import ru.otus.gwt.shared.Emp;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.dom.client.Style.Unit;
import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;
import static ru.otus.gwt.shared.Constants.REQUEST_FOREX_RATES;
import static ru.otus.gwt.shared.Constants.REQUEST_NEWS;

public class Inside extends Welcome
{
    private static InsideServiceAsync service = INSTANCE.getInsideService();
    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;
    private DataGrid<Emp> table = new DataGrid<>();
    private int deckIndexListView;
    private int deckIndexAddView;
    private int deckIndexSearchView;
    private int deckIndexTaxView;
    final DeckPanel deckPanel = new DeckPanel();
    ListDataProvider<Emp> model = new ListDataProvider<>(new ArrayList<>());

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    public int getDeckIndexListView()
    {
        return deckIndexListView;
    }

    public int getDeckIndexAddView()
    {
        return deckIndexAddView;
    }

    public int getDeckIndexSearchView()
    {
        return deckIndexSearchView;
    }

    public interface GwtCssDataGridResources extends DataGrid.Resources {
        @Source({Style.DEFAULT_CSS, "gwtDataGrid.css"})
        Style dataGrid();
    }

    public static final GwtCssDataGridResources gwtCssDataGridResources = GWT.create(GwtCssDataGridResources.class);

    static {
        gwtCssDataGridResources.dataGrid().ensureInjected();
    }

    private <T> AsyncCallback<T> getEmptyAsyncCallback(String message, Class<T> c)
    {
        return new AsyncCallback<T>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(message);
            }
            @Override
            public void onSuccess(T result) { refresh(); }
        };
    }

    private void doResize()
    {
        try {
            //noinspection GwtToHtmlReferences
            int width = RootPanel.get("main").getOffsetWidth() - 41;
            width = width > 760 ? width : 759;
            table.setWidth(width + "px");
        } catch (Throwable e) {
            Window.alert(e.toString());
            table.setWidth(759 + "px");
        }
    }

    private void delete(int index, Emp emp, String value)
    {
        service.deleteEmp(
                emp.getId(), getEmptyAsyncCallback(" Error delete id: " + emp.getId(), Void.class)
        );
    }

    private void drawTable(List<Emp> result)
    {
        model.setList(result);
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Emp> idColumn = new TextColumn<Emp>() {
            @Override
            public String getValue(Emp object) {
                return Long.toString(object.getId());
            }
        };
        idColumn.setCellStyleNames("text-align-right padding-right-5px");

        Column<Emp, String> firstNameColumn = new Column<Emp, String>(new EditTextCell()) {
            @Override
            public String getValue(Emp emp) {
                return emp.getFirstName();
            }
        };

        firstNameColumn.setFieldUpdater((index, emp, value) -> service.setEmpFirstName(
                emp.getId(), value,
                getEmptyAsyncCallback("Error update: id: " + emp.getId() + " value: " + value, Void.class)
        ));

        Column<Emp, String> secondNameColumn = new Column<Emp, String>(new EditTextCell()) {
            @Override
            public String getValue(Emp emp) {
                return emp.getSecondName();
            }
        };
        secondNameColumn.setFieldUpdater((index, emp, value) -> service.setEmpFirstName(
                emp.getId(), value,
                getEmptyAsyncCallback("Error update: id: " + emp.getId() + " value: " + value, Void.class)
        ));

        TextColumn<Emp> surNameColumn = new TextColumn<Emp>() {
            @Override
            public String getValue(Emp object) {
                return object.getSurName();
            }
        };

        TextColumn<Emp> jobColumn = new TextColumn<Emp>() {
            @Override
            public String getValue(Emp object) {
                return object.getJob();
            }
        };

        TextColumn<Emp> cityColumn = new TextColumn<Emp>() {
            @Override
            public String getValue(Emp object) {
                return object.getCity();
            }
        };

        TextColumn<Emp> ageColumn = new TextColumn<Emp>() {
            @Override
            public String getValue(Emp object) {
                return object.getAge();
            }
        };

        Column<Emp, String> deleteBtn = new Column<Emp, String>( new ButtonCell()) {
            @Override
            public String getValue(Emp c) {
                return "x";
            }
        };
        deleteBtn.setFieldUpdater(this::delete);
        deleteBtn.setCellStyleNames("text-align-right padding-right-1px");

        table.addColumn(idColumn, "Id");
        table.addColumn(firstNameColumn, "First Name");
        table.addColumn(secondNameColumn, "Second Name");
        table.addColumn(surNameColumn, "Surname");
        table.addColumn(jobColumn, "Job");
        table.addColumn(cityColumn, "City");
        table.addColumn(ageColumn, "Age");
        table.addColumn(deleteBtn, "");

        table.setColumnWidth(idColumn, "32px");
        table.setColumnWidth(ageColumn, "32px");
        table.setColumnWidth(deleteBtn, "16px");
        table.setRowCount(result.size());
        table.setHeight((result.size() * 24 + 28 ) + "px");

        doResize();

        table.setTableWidth(100, Unit.PCT);
        table.setMinimumTableWidth(759, Unit.PX);
        model.addDataDisplay(table);
    }

    private DataGrid<Emp> getEmpListTable()
    {
        Inside localInside = this;
        service.getEmpsList(new AsyncCallback<List<Emp>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                drawTable(result);
            }

        });

        return table;
    }

    private <T> VerticalPanel inboxing(DataGrid<T> data) {
        VerticalPanel vp = new VerticalPanel();
        Button addButtun = new Button();
        Button refreshButtun = new Button();
        Button searchButtun = new Button();

        addButtun.setTitle("add");
        addButtun.setText("add new");
        addButtun.addClickHandler(event -> deckPanel.showWidget(getDeckIndexAddView()));

        refreshButtun.setTitle("refresh");
        refreshButtun.setText("refresh");
        refreshButtun.addClickHandler(event -> refresh());

        searchButtun.setTitle("search");
        searchButtun.setText("search");
        searchButtun.addClickHandler(event -> deckPanel.showWidget(getDeckIndexSearchView()));

        vp.setHorizontalAlignment(TextColumn.ALIGN_CENTER);
        vp.add(data);
        HorizontalPanel hp = new HorizontalPanel();
        hp.add(addButtun);
        hp.add(refreshButtun);
        hp.add(searchButtun);
        vp.add(hp);

        return vp;
    }

    public void refresh() {
        model.setList(new ArrayList<>());
        model.refresh();
        table.redraw();

        service.getEmpsList(new AsyncCallback<List<Emp>>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                showResult(result);
            }
        });
    }

    public void showResult(List<Emp> result)
    {
        model.setList(result);
        table.setRowCount(result.size());
        table.setHeight((result.size() * 24 + 28 ) + "px");
        model.refresh();
        table.redraw();
    }

    public void showSearchResult(List<Emp> result) {
        model.setList(new ArrayList<>());
        model.refresh();
        table.redraw();
        showResult(result);
        deckPanel.showWidget(getDeckIndexListView());
    }

    public void showDataGrid() {
        deckPanel.showWidget(getDeckIndexListView());
    }

    private void initMainContainer()
    {
        RootPanel rootPanel = fillDeckPanel(deckPanel);

        deckPanel.add(inboxing(getEmpListTable()));
        deckIndexListView = deckPanel.getWidgetCount() - 1;
        deckPanel.showWidget(getDeckIndexListView());

        deckPanel.add(new TaxView(service, this));
        deckIndexTaxView = deckPanel.getWidgetCount() - 1;

        deckPanel.add(new AddView(service, this));
        deckIndexAddView = deckPanel.getWidgetCount() - 1;

        deckPanel.add(new SearchView(service, this));
        deckIndexSearchView = deckPanel.getWidgetCount() - 1;

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setHorizontalAlignment(TextColumn.ALIGN_CENTER);
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 6);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    public void onModuleLoad()
    {
        super.initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/" + REQUEST_NEWS);
        new CBRValutes(GWT.getHostPageBaseURL() + "/" + REQUEST_FOREX_RATES);
        Window.addResizeHandler(event -> doResize());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
