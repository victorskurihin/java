package ru.otus.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SingleSelectionModel;
import ru.otus.gwt.client.aside.News;
import ru.otus.gwt.client.aside.CBRValutes;
import ru.otus.gwt.client.service.InsideServiceAsync;
import ru.otus.gwt.shared.Emp;

import java.util.List;

import static ru.otus.gwt.client.gin.ApplicationInjector.INSTANCE;

public class Inside extends Index
{
    private static InsideServiceAsync service = INSTANCE.getInsideService();
    private TextBox loginTextBox;
    private PasswordTextBox passwordTextBox;

    public String getLogin() {
        return loginTextBox.getText();
    }

    public String getPassword() {
        return passwordTextBox.getText();
    }

    private void initMainContainer()
    {
        final DeckPanel deckPanel = new DeckPanel();

        RootPanel rootPanel = fillDeckPanel(deckPanel);

        deckPanel.add(this::drawTable);
        deckPanel.showWidget(4);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(deckPanel);

        menuAddClickHandler(deckPanel, "navigation-menu-", 5);
        menuAddClickHandler(deckPanel, "aside-menu-", 4);

        rootPanel.add(vPanel);
    }

    private CellTable<Emp> drawTable()
    {
        CellTable<Emp> table = new CellTable<>();

        service.getEmpsList(new AsyncCallback<List<Emp>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Emp> result)
            {
                table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

                TextColumn<Emp> firstNameColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return object.getFirstName();
                    }
                };
                table.addColumn(firstNameColumn, "First Name");

                TextColumn<Emp> secondNameColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return object.getSecondName();
                    }
                };
                table.addColumn(secondNameColumn, "Second Name");

                TextColumn<Emp> surNameColumn = new TextColumn<Emp>() {
                    @Override
                    public String getValue(Emp object) {
                        return object.getSurName();
                    }
                };
                table.addColumn(surNameColumn, "SurName");

                final SingleSelectionModel<Emp> selectionModel = new SingleSelectionModel<>();
                table.setRowCount(result.size(), true);
                table.setRowData(0, result);
                table.setColumnWidth(0, "110px");
                table.setColumnWidth(1, "120px");
                table.setColumnWidth(2, "120px");

            }
        });

        return table;
    }


    public void onModuleLoad()
    {
        super.initHeaderAndTitle();
        initMainContainer();
        new News(GWT.getHostPageBaseURL() + "/rbcnews");
        new CBRValutes(GWT.getHostPageBaseURL() + "/cbrforex");
    }
}
