package com.example.application.views;

import java.util.Optional;

import com.example.application.data.services.CompanyActivityService;
import com.example.application.data.services.CompanyService;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.example.application.views.mainview.CompanyActivityReportView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.example.application.views.mainview.CompanyView;
import com.example.application.views.mainview.CompanyActivityView;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "My App", shortName = "My App", enableInstallPrompt = false)
@Theme(themeFolder = "myapp", variant = Lumo.DARK)
public class MainLayout extends AppLayout {



    Grid<Company> grid = new Grid<>(Company.class);

    private final CompanyService companyService;

    TextField txtFilter = new TextField();
    Dialog dialogCompany =new Dialog();

    Binder<Company> binder = new Binder<>();
    Long itemIdForEdition=0L;
    Long loggedInSystemUserId;

    private final CompanyActivityService companyActivityService;

    Binder<CompanyActivity> binder2 = new Binder<>();
    Dialog dialogCompanyActivity=new Dialog();


    private Tabs menu;

    public MainLayout(CompanyService companyService, CompanyActivityService companyActivityService) {


        this.companyService = companyService;
        this.companyActivityService = companyActivityService;


        if (VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId") == null) {
            UI.getCurrent().getPage().setLocation("/login");
        } else {
            HorizontalLayout header = createHeader();
            menu = createMenuTabs();
            addToNavbar(createTopBar(header, menu));


        }

    }


    private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(header, menu);
        return layout;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setClassName("topmenu-header");
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        Image logo = new Image("images/logo.png", "My App logo");
        logo.setId("logo");
        header.add(logo);
        Avatar avatar = new Avatar();
        avatar.addClassNames("ms-auto", "me-m");
        header.add(new H1("Cari Hesap 'Kalamoza' Defteri"));
        header.add(avatar);
        return header;

    }


    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{createTab("Firmalar", CompanyView.class), createTab("Firma Faaliyetleri", CompanyActivityView.class),createTab("Firma Faaliyet Raporları", CompanyActivityReportView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }
}
