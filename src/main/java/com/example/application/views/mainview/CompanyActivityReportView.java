package com.example.application.views.mainview;

import com.example.application.data.services.CompanyActivityService;
import com.example.application.data.services.CompanyService;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "firmaraporlari", layout = MainLayout.class)
@PageTitle("Firma Hareket Raporları")

public class CompanyActivityReportView extends HorizontalLayout {
    Grid<Company> companyGrid = new Grid<>(Company.class);
    Grid<Company> companyGrid2 = new Grid<>(Company.class);
    Grid<Company> companyGrid3 = new Grid<>(Company.class);


    private final CompanyService companyService;

    TextField txtFilter = new TextField();
    TextField txtFilter2 = new TextField();
    TextField txtFilter3 = new TextField();

    Dialog dialogCompany =new Dialog();


    Long loggedInSystemUserId;

    private final CompanyActivityService companyActivityService;

    Grid<CompanyActivity> companyActivityGrid=new Grid<>(CompanyActivity.class);

    public Company selectedReportCompany = null;

    public CompanyActivityReportView(CompanyService companyService, CompanyActivityService companyActivityService) {
        this.companyService = companyService;
        this.companyActivityService = companyActivityService;
        addClassName("company-report-view");
        configureFilter();
        configureFilter2();
        configureFilter3();

        System.out.println("Loggedin User ID");
        System.out.println(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        loggedInSystemUserId=Long.valueOf(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        List<Company> companyList = new ArrayList<>();
        companyList.addAll(this.companyService.getList());
        companyGrid.setItems(companyList);

        dialogCompany.setModal(true); //-----



        Button btnCikis = new Button("Çıkış Yap",VaadinIcon.USER.create()); //maine taşınacak

        btnCikis.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().setLocation("/login");
        });


        btnCikis.getStyle().set("margin-top","0");
        btnCikis.getStyle().set("margin-left","90%");
        btnCikis.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(btnCikis);

        /*
        txtFilter.setPlaceholder("Firma adı giriniz..");
        txtFilter.getStyle().set("width", "480px");

        txtFilter2.setPlaceholder("Firma adı giriniz..");
        txtFilter2.getStyle().set("width", "480px");

        txtFilter3.setPlaceholder("Firma adı giriniz..");
        txtFilter3.getStyle().set("width", "480px");
         */
        companyGrid2.getColumns().forEach(col->col.setAutoWidth(true));
        companyGrid2.removeColumnByKey("id");
        companyGrid2.setSelectionMode(Grid.SelectionMode.SINGLE);
        companyGrid2.setColumns("firmaAdi","bakiye" ,"toplamBorc","toplamAlacak");

        companyGrid3.getColumns().forEach(col->col.setAutoWidth(true));
        companyGrid3.removeColumnByKey("id");
        companyGrid3.setSelectionMode(Grid.SelectionMode.SINGLE);
        companyGrid3.setColumns("firmaAdi","bakiye" ,"toplamBorc","toplamAlacak");


       /*
        Button btnFilter=new Button("Arama",VaadinIcon.SEARCH.create());
        btnFilter.addClickListener(buttonClickEvent -> {

            refreshData(txtFilter.getValue());
        });

        */

        /*
        ********ALACAK-BORÇ*******
         */
        Dialog dialogAlacak=new Dialog();
        dialogAlacak.setSizeFull();

        Button btnDialogKapat1=new Button("Formu Kapat");
        btnDialogKapat1.addClickListener(buttonClickEvent -> {
            dialogAlacak.close();

        });

        btnDialogKapat1.setWidth("150px");
        btnDialogKapat1.setHeight("25px");
        btnDialogKapat1.addThemeVariants(ButtonVariant.LUMO_PRIMARY);



        Button btnAlacak = new Button("+");
        btnAlacak.addClickListener(buttonClickEvent-> {




            dialogAlacak.removeAll();

            updateList2();

            dialogAlacak.add(btnDialogKapat1,new H3("Alacaklı Firmalar"),txtFilter2, companyGrid2);
            dialogAlacak.open();


        });




        Dialog dialogBorc=new Dialog();
        dialogBorc.setSizeFull();

        Button btnDialogKapat2=new Button("Formu Kapat");
        btnDialogKapat2.addClickListener(buttonClickEvent -> {
            dialogBorc.close();

        });

        btnDialogKapat2.setWidth("150px");
        btnDialogKapat2.setHeight("25px");
        btnDialogKapat2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);



        Button btnBorc = new Button("-");
        btnBorc.addClickListener(buttonClickEvent-> {


            dialogBorc.removeAll();
            updateList3();
            dialogBorc.add(btnDialogKapat2,new H3("Borçlu Firmalar"),txtFilter3, companyGrid3);
            dialogBorc.open();


        });

        /*
        *****
        *******
        ********
         */

        HorizontalLayout filterGroup=new HorizontalLayout();
        filterGroup.add(txtFilter,btnAlacak,btnBorc);

        dialogCompany.setModal(true);


        refreshData(txtFilter.getValue());


        refreshData(txtFilter.getValue());
        companyGrid.getColumns().forEach(col->col.setAutoWidth(true));
        companyGrid.removeColumnByKey("id");
        companyGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        companyGrid.setColumns("firmaAdi","bakiye" ,"toplamBorc","toplamAlacak");
        //companyGrid.addComponentColumn(item -> createRemoveButton(companyGrid,item)).setHeader("Actions");



        Dialog dialogCompanyActivity2=new Dialog();
        Button btnCancel3=new Button("Geri");
        btnCancel3.addClickListener(buttonClickEvent -> {
            dialogCompanyActivity2.close();

        });


        refreshData(txtFilter.getValue());


        Label label1=new Label("Tek bir firmaya ait faaliyet raporlarını görmek için tablo üzerinden firma seçiniz.");
        label1.addClassName("lbl");


        Button btnCikis2=new Button("Formu Kapat");
        btnCikis2.setWidth("150px");
        btnCikis2.setHeight("25px");
        btnCikis2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);



        Dialog dialogNew=new Dialog();

        dialogNew.setSizeFull();

        btnCikis2.addClickListener(buttonClickEvent -> {

            dialogNew.close();
        });


        // SELECTION
        companyGrid.addSelectionListener(item -> {
            this.selectedReportCompany = item.getFirstSelectedItem().get();

            companyActivityGrid.removeAllColumns();

            List<CompanyActivity> companyActivityList2 = companyActivityService.getList()
                    .stream().filter(obj -> this.selectedReportCompany.getId().equals(obj.getCompany().getId())).collect(Collectors.toList());

            companyActivityGrid.setItems(companyActivityList2);
            companyActivityGrid.setColumns();
            companyActivityGrid.getColumns().forEach(col->col.setAutoWidth(true));
            companyActivityGrid.addColumn(companyActivity -> {
                Company company1 = companyActivity.getCompany();
                return company1 == null ? "-" : company1.getFirmaAdi();
            }).setHeader("Company");

            companyActivityGrid.addColumn("borc");
            companyActivityGrid.addColumn("alacak");
            companyActivityGrid.addColumn("aciklama");
            companyActivityGrid.addColumn("tarih");

            dialogNew.removeAll();
            dialogNew.add(btnCikis2, new H3("Firma Raporu - " + this.selectedReportCompany.getFirmaAdi()), companyActivityGrid);
            dialogNew.open();
        });


        add(new H2("Firma Faaliyet Raporları"),filterGroup,label1, companyGrid);



    }

    private void configureFilter() {
        txtFilter.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter.setPlaceholder("Firma adı giriniz..");
        txtFilter.getStyle().set("width", "480px");
        txtFilter.addThemeVariants(TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL);
        txtFilter.addValueChangeListener(e -> updateList());
    }

    private void configureFilter2() {
        txtFilter2.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter2.setPlaceholder("Firma adı giriniz..");
        txtFilter2.getStyle().set("width", "480px");
        txtFilter2.addThemeVariants(TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL);
        txtFilter2.addValueChangeListener(e -> updateList2());
    }

    private void configureFilter3() {
        txtFilter3.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter3.setPlaceholder("Firma adı giriniz..");
        txtFilter3.getStyle().set("width", "480px");
        txtFilter3.addThemeVariants(TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL);
        txtFilter3.addValueChangeListener(e -> updateList3());
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

    private void updateList2() {
        List<Company> filteredList = new ArrayList<>();
        companyService.getList().forEach(company -> {
            if( company.getSystemUser().getId().toString().equals(loggedInSystemUserId.toString()) ) {
                if( company.getFirmaAdi().contains(this.txtFilter2.getValue()) ) {
                    if(company.getBakiye() != null && company.getBakiye() > 0) {
                        filteredList.add(company);
                    }
                }
            }
        });
        companyGrid2.setItems(filteredList);
    }

    private void updateList3() {
        List<Company> filteredList = new ArrayList<>();
        companyService.getList().forEach(company -> {
            if( company.getSystemUser().getId().toString().equals(loggedInSystemUserId.toString()) ) {
                if( company.getFirmaAdi().contains(this.txtFilter3.getValue()) ) {
                    if(company.getBakiye() != null && company.getBakiye() < 0) {
                        filteredList.add(company);
                    }
                }
            }
        });
        companyGrid3.setItems(filteredList);
    }


    private void refreshData(String filter){
        List<Company> companyList = new ArrayList<>();
        companyList.addAll(companyService.getList(filter,filter,filter,filter,filter,loggedInSystemUserId));
        //companyList.addAll(companyService.search(filter, loggedInSystemUserId));
        companyGrid.setItems(companyList);
        companyGrid2.setItems(companyList);
        companyGrid3.setItems(companyList);

    }
}








// dialogNew.add(btnCikis2,new H3("Firma Raporu"),companyActivityGrid); //diyalog pencere düzeni


        /*
        btnRapor.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                dialogNew.open();
            }

        });

         */


//dialogCompanyActivity.setModal(true);


/*

        TextField txtFirmaAdi = new TextField("Firma Adı","Firma Adı giriniz");
        TextField txtAdres = new TextField("Adres","Adres giriniz");
        TextField txtVergiDairesi = new TextField("Vergi Dairesi","Vergi Dairesi giriniz");
        IntegerField txtVergiNo = new IntegerField("Vergi No","Vergi No giriniz");
        IntegerField txtPhoneNumber = new IntegerField("Telefon Numarası","Telefon Numarası giriniriz");
*/
//TextField txtGuncelBakiye = new TextField("Güncel Bakiye","Güncel Bakiye");



// LocalDate now = LocalDate.now();
// datePicker.setValue(now);
//datePicker.setMin(LocalDate.now());


/*
        binder.bind(txtFirmaAdi, Company::getFirmaAdi, Company::setFirmaAdi);
        binder.bind(txtAdres, Company::getAdres, Company::setAdres);
        binder.bind(txtVergiDairesi, Company::getVergiDairesi, Company::setVergiDairesi);
        binder.bind(txtVergiNo, Company::getVergiNo, Company::setVergiNo);
        binder.bind(txtPhoneNumber, Company::getPhoneNumber, Company::setPhoneNumber);
        //  binder.bind(txtGuncelBakiye, Company::getGuncelBakiye, Company::setGuncelBakiye);


        FormLayout formLayout = new FormLayout();
        formLayout.add(txtFirmaAdi, txtAdres,txtVergiDairesi,txtVergiNo,txtPhoneNumber);
*/
/*
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        //buton boşluklar ve yeri






        Button btnSave = new Button("Kaydet");
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button btnCancel = new Button("İptal");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);


        btnCancel.addClickListener(buttonClickEvent -> {
            dialogCompany.close();
        });



        btnSave.addClickListener(buttonClickEvent -> {

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

            this.companyService.save(company);

            refreshData(txtFilter.getValue().toString());

            dialogCompany.close();

            notificationGuncelle.open();
            refreshData(txtFilter.getValue().toString());

        });


        horizontalLayout.add(btnCancel,btnSave);





        dialogCompany.add(new H3("Firma Ekle"),formLayout,horizontalLayout);
        */

        /*
        btnFirmaEkle.addClickListener(buttonClickEvent -> { //Add butonu tıkladığımızda firma eklicez.

            refreshData(txtFilter.getValue().toString());
            itemIdForEdition=0L;
            binder.readBean(new Company());
            dialogCompany.open();
            refreshData(txtFilter.getValue().toString());

        });

*/


        /*List<Person> personList=new ArrayList<>();
        personList.add(new Person(1l,"Ali","Duru","444555"));
        personList.add(new Person(2l,"Aliye","Duru","2222"));
        personList.add(new Person(3l,"Test","Test","444"));
*/

// grid.removeColumnByKey("id");
// grid.setSelectionMode(Grid.SelectionMode.NONE);

// The Grid<>(Person.class) sorts the properties and in order to
// reorder the properties we use the 'setColumns' method.
// grid.setColumns("firmaAdi", "adres", "vergiDairesi","vergiNo","phoneNumber");

// grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Hareketler");

        /*grid.addItemClickListener(personItemClickEvent -> {

            Notification.show("Item Clicked : " + personItemClickEvent.getItem());

        });*/ //grid tıklama

        /*  TextField txtMessage= new TextField();
        txtMessage.setLabel("Write Your Message");
        Button button = new Button("Click Me");
        button.addClickListener(buttonClickEvent -> {
            Notification.show(txtMessage.getValue());
        });?
        add(txtMessage,button);*/


        /*
        List<CompanyActivity> companyActivityList = new ArrayList<>();
        companyActivityList.addAll(companyActivityService.getList());

        companyActivityGrid.setItems(companyActivityList);
        companyActivityGrid.setColumns();
        companyActivityGrid.getColumns().forEach(col->col.setAutoWidth(true));
        companyActivityGrid.addColumn(companyActivity -> {
            Company company1 = companyActivity.getCompany();
            return company1 == null ? "-" : company1.getFirmaAdi();
        }).setHeader("Company");
        companyActivityGrid.addColumn("borc");
        companyActivityGrid.addColumn("alacak");
        companyActivityGrid.addColumn("aciklama");
        companyActivityGrid.addColumn("tarih");
         */




/*
    private void refreshData2(String filter) {
        List<CompanyActivity> companyActivityList = new ArrayList<>();
        companyActivityList.addAll(companyActivityService.getList());
        companyActivityGrid.setItems(companyActivityList);
    }
*/
/*
    private HorizontalLayout createRemoveButton(Grid<Company> grid, Company item) {

        //Button btnRapor = new Button("Rapor");
        //btnRapor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //Dialog dialogNew=new Dialog();
        //FormLayout formLayout = new FormLayout(); //açılan diyalog penceresi
        //formLayout.add(companyActivityGrid);
        //dialogNew.add(new H3("Firma Raporu"),formLayout); //diyalog pencere düzeni


/*

        btnRapor.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                dialogNew.open();

                companyActivityService.getList(item);


            }



        });




        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add();


        return horizontalLayout;
    }
*/







