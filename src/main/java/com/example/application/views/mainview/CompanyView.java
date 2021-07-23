package com.example.application.views.mainview;

import com.example.application.data.services.CompanyActivityService;
import com.example.application.data.services.CompanyService;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.example.application.data.entity.SystemUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

@Route(value = "firmalar", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Firmalar")
public class CompanyView extends HorizontalLayout {
    Grid<Company> companyGrid = new Grid<>(Company.class);

    private final CompanyService companyService;

    TextField txtFilter = new TextField();

    // private TextField filterText = new TextField();


    Dialog dialogCompany =new Dialog();
    Binder<Company> binder = new Binder<>();
    Long itemIdForEdition=0L;
    Long loggedInSystemUserId;
    String email;

    private final CompanyActivityService companyActivityService;

    Binder<CompanyActivity> binder2 = new Binder<>();
    Dialog dialogCompanyActivity=new Dialog();


    public CompanyView(CompanyService companyService, CompanyActivityService companyActivityService) {

        configureFilter();

        this.companyService = companyService;
        this.companyActivityService = companyActivityService;
        addClassName("hello-world-view");



        Notification notificationUpdate = new Notification("İşlem başarılı " ,4000, Notification.Position.BOTTOM_STRETCH);
        Notification notificationNotnull = new Notification("Lütfen tüm alanları doldurunuz !" ,4000, Notification.Position.BOTTOM_STRETCH);

        System.out.println("Loggedin User ID");
        System.out.println(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        loggedInSystemUserId=Long.valueOf(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());

        List<Company> companyList = new ArrayList<>();
        companyList.addAll(companyService.getList());
        companyGrid.setItems(companyList);

        dialogCompany.setModal(true); //-----

        Button btnFirmaEkle = new Button("Firma Ekle", VaadinIcon.INSERT.create());
        btnFirmaEkle.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);


        //Button btnFilter=new Button("Arama",VaadinIcon.SEARCH.create());
        //btnFilter.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_CONTRAST);
        //btnFilter.getStyle().set("background-color","#D9A300");
        //btnFilter.addClickListener(buttonClickEvent -> {

          //  refreshData(txtFilter.getValue());
        //});


        Button btnCikis = new Button("Çıkış Yap",VaadinIcon.USER.create()); //maine taşınacak

        HorizontalLayout filterGroup=new HorizontalLayout();
        filterGroup.add(txtFilter);

        dialogCompany.setModal(true);


        TextField txtFirmaAdi = new TextField("Firma Adı","Firma Adı yazınız");
        TextField txtAdres = new TextField("Adres","Adres giriniz");
        TextField txtVergiDairesi = new TextField("Vergi Dairesi","Vergi Dairesi giriniz");
        IntegerField txtVergiNo = new IntegerField("Vergi No","Vergi No giriniz");
        IntegerField txtPhoneNumber = new IntegerField("Telefon Numarası","Telefon Numarası giriniriz");


        binder.bind(txtFirmaAdi, Company::getFirmaAdi, Company::setFirmaAdi);
        binder.bind(txtAdres, Company::getAdres, Company::setAdres);
        binder.bind(txtVergiDairesi, Company::getVergiDairesi, Company::setVergiDairesi);
        binder.bind(txtVergiNo, Company::getVergiNo, Company::setVergiNo);
        binder.bind(txtPhoneNumber, Company::getPhoneNumber, Company::setPhoneNumber);


        FormLayout formLayout = new FormLayout();
        formLayout.add(txtFirmaAdi, txtAdres,txtVergiDairesi,txtVergiNo,txtPhoneNumber);


        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.setSpacing(true);


        Button btnSave = new Button("Kaydet");
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button btnCancel = new Button("İptal");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);


        btnCancel.addClickListener(buttonClickEvent -> {
            dialogCompany.close();
        });



        btnSave.addClickListener(buttonClickEvent -> {

            if (txtFirmaAdi.getValue()==null||txtAdres.getValue()==null||txtVergiNo.getValue()==null||txtVergiDairesi.getValue()==null||txtPhoneNumber.getValue()==null)
            {
                notificationNotnull.open();
            }
            else{
                Company company =new Company();
                try {
                    binder.writeBean(company);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }

                company.setId(itemIdForEdition);
                SystemUser loggedInSystemUser=new SystemUser();
                loggedInSystemUser.setId(loggedInSystemUserId);
                company.setSystemUser(loggedInSystemUser);

                companyService.save(company);

                refreshData(txtFilter.getValue().toString());

                dialogCompany.close();

                notificationUpdate.open();
                refreshData(txtFilter.getValue().toString());



            }



        });


        horizontalLayout.add(btnCancel,btnSave);


        dialogCompany.add(new H3("Firma"),formLayout,horizontalLayout);



        btnFirmaEkle.addClickListener(buttonClickEvent -> { //Add butonu tıkladığımızda firma eklicez.

            refreshData(txtFilter.getValue().toString());
            itemIdForEdition=0L;
            binder.readBean(new Company());
            dialogCompany.open();
            refreshData(txtFilter.getValue().toString());

        });

        refreshData(txtFilter.getValue());



        refreshData(txtFilter.getValue().toString());
        companyGrid.getColumns().forEach(col->col.setAutoWidth(true));
        companyGrid.removeColumnByKey("id");
        companyGrid.setSelectionMode(Grid.SelectionMode.NONE);
        companyGrid.setColumns("firmaAdi","adres" ,"vergiDairesi","vergiNo","phoneNumber");
        companyGrid.addComponentColumn(item -> createRemoveButton(companyGrid,item)).setHeader("Actions");



        Dialog dialogCompanyActivity2=new Dialog();
        //Div div = new Div();
        //HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        Button btnCancel3=new Button("Geri");
        btnCancel3.addClickListener(buttonClickEvent -> {
            dialogCompanyActivity2.close();

        });


        refreshData(txtFilter.getValue().toString());


        btnCikis.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().setLocation("/login");
        });


        btnCikis.getStyle().set("margin-top","0");
        btnCikis.getStyle().set("margin-left","90%");
        btnCikis.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(btnCikis);


        add(new H2("Firma Listesi"),filterGroup,btnFirmaEkle, companyGrid);


    }


    /*
     * search input ayarları
     * */
    private void configureFilter() {
        txtFilter.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter.setPlaceholder("Firma adı giriniz..");
        txtFilter.getStyle().set("width", "480px");
        txtFilter.addThemeVariants(TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL);
        txtFilter.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        List<Company> filteredList = new ArrayList<>();
        companyService.getList().forEach(company -> {
            if( company.getSystemUser().getId().toString().equals(loggedInSystemUserId.toString()) ) {
                if( company.getFirmaAdi().contains(this.txtFilter.getValue()) ) {
                    filteredList.add(company);
                }


            }
        });
        companyGrid.setItems(filteredList);
    }
    /*
     * .... search
     * */

    private void refreshData(String filter){
        List<Company> companyList = new ArrayList<>();
        companyList.addAll(companyService.getList(filter,filter,filter,filter,filter,loggedInSystemUserId));
        companyGrid.setItems(companyList);

    }

    private HorizontalLayout createRemoveButton(Grid<Company> grid, Company item) {
        @SuppressWarnings("unchecked")
        Button btnDelete = new Button("Sil");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(buttonClickEvent -> {


            ConfirmDialog dialog = new ConfirmDialog("Silmeyi Onayla",
                    "Bu kaydı silmek istediğinize emin misiniz?", "Sil", confirmEvent -> {
                companyService.delete(item);
                Notification notificationDelete = new Notification("Firma silindi : "+item.getFirmaAdi() ,4000, Notification.Position.BOTTOM_STRETCH);
                notificationDelete.open();

                refreshData(txtFilter.getValue().toString());

            },
                    "İptal", cancelEvent -> {


            });
            dialog.setConfirmButtonTheme("error primary");

            dialog.open();


        });


        Button btnUpdate = new Button("Güncelle");
        btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        btnUpdate.addClickListener(buttonClickEvent -> {

            itemIdForEdition=item.getId();
            binder.readBean(item);
            dialogCompany.open();

        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnUpdate,btnDelete);


        return horizontalLayout;
    }

}
