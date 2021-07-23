package com.example.application.views.mainview;

import com.example.application.data.services.CompanyActivityService;
import com.example.application.data.services.CompanyService;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.CompanyActivity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

@Route(value = "firmafaaliyetleri", layout = MainLayout.class)
@PageTitle("Firma Hareketleri")
public class CompanyActivityView extends HorizontalLayout {


    private final CompanyService companyService;

    Long loggedInSystemUserId;

    private final CompanyActivityService companyActivityService;

    Dialog dialogCompanyActivity = new Dialog();
    Binder<CompanyActivity> binder2 = new Binder<>();
    Grid<CompanyActivity> companyActivityGrid = new Grid<>(CompanyActivity.class);
    TextField txtFilter = new TextField();


    public CompanyActivityView(CompanyService companyService, CompanyActivityService companyActivityService) {
        this.companyService = companyService;
        this.companyActivityService = companyActivityService;
        addClassName("about-view");

        configureFilter();
        loggedInSystemUserId=Long.valueOf(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        dialogCompanyActivity.setModal(true);

        Notification notificationNotnull = new Notification("Lütfen tüm alanları doldurunuz !" ,4000, Notification.Position.BOTTOM_STRETCH);

        if (VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId") == null) {
            UI.getCurrent().getPage().setLocation("/login");
        } else {


            final Long systemUserId = (Long) VaadinSession.getCurrent().getSession().getAttribute(
                    "LoggedInSystemUserId");

            Notification notificationGuncelle = new Notification("İşlem Başarılı", 5000, Notification.Position.BOTTOM_STRETCH);

            Button btnFirmaHareketiEkle = new Button("Firma Hareketi Ekle", VaadinIcon.INSERT.create());
            btnFirmaHareketiEkle.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);


            btnFirmaHareketiEkle.addClickListener(buttonClickEvent -> {
                binder2.readBean(new CompanyActivity());
                dialogCompanyActivity.open();
                refreshData(txtFilter.getValue().toString(), systemUserId);
                //itemIdForEdition=0L;
            });


            TextField txtAciklama = new TextField("Açıklama", "Açıklama..");
            IntegerField intBorc = new IntegerField("Borç", "Borç giriniz");
            IntegerField intAlacak = new IntegerField("Alacak", "Alacak giriniz");
            DatePicker datePicker = new DatePicker("Hareket Tarihi"); //takvimden gün seçme
            datePicker.setPlaceholder("Hareket tarihi seçiniz.");



            Select<Company> firmaSec = new Select<>();
            firmaSec.setLabel("Firma Seç");
            firmaSec.setItems(this.companyService.getList(systemUserId));
            firmaSec.setItemLabelGenerator(Company::getFirmaAdi);
            refreshData(txtFilter.getValue().toString(), systemUserId);


            binder2.bind(firmaSec, CompanyActivity::getCompany, CompanyActivity::setCompany);
            binder2.bind(datePicker, CompanyActivity::getTarih, CompanyActivity::setTarih);
            binder2.bind(txtAciklama, CompanyActivity::getAciklama, CompanyActivity::setAciklama);
            binder2.bind(intBorc, CompanyActivity::getBorc, CompanyActivity::setBorc);
            binder2.bind(intAlacak, CompanyActivity::getAlacak, CompanyActivity::setAlacak);


            FormLayout formLayout2 = new FormLayout();
            formLayout2.add(firmaSec, datePicker, txtAciklama, intBorc, intAlacak);

            HorizontalLayout horizontalLayout2 = new HorizontalLayout();
            horizontalLayout2.setSpacing(true);

            Button btnSave2 = new Button("Kaydet");
            btnSave2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            Button btnCancel2 = new Button("İptal");
            btnCancel2.addThemeVariants(ButtonVariant.LUMO_ERROR);

            btnCancel2.addClickListener(buttonClickEvent -> {
                dialogCompanyActivity.close();
            });

            btnSave2.addClickListener(buttonClickEvent -> {

                if (firmaSec.getValue()==null||datePicker.getValue()==null||txtAciklama.getValue()==null||intBorc.getValue()==null||intAlacak.getValue()==null)
                {
                    notificationNotnull.open();
                }
                else {

                    CompanyActivity companyActivity = new CompanyActivity();
                    try {
                        binder2.writeBean(companyActivity);
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                    this.companyActivityService.save(companyActivity);
                    refreshData(txtFilter.getValue().toString(), systemUserId);
                    dialogCompanyActivity.close();
                    notificationGuncelle.open();
                    refreshData(txtFilter.getValue().toString(), systemUserId);

                }

            });


            txtFilter.setPlaceholder("Firma adı veya açıklaması yazınız..");
            txtFilter.getStyle().set("width", "480px");
            /*
            Button btnFilter = new Button("Arama", VaadinIcon.SEARCH.create());
            btnFilter.addClickListener(buttonClickEvent -> {

                refreshData(txtFilter.getValue(), systemUserId);
            });
             */
            HorizontalLayout filterGroup = new HorizontalLayout();
            filterGroup.add(txtFilter);

            dialogCompanyActivity.setModal(true);

            horizontalLayout2.add(btnCancel2, btnSave2);

            dialogCompanyActivity.add(new H3("Firma Hareketi Ekleme"), formLayout2, horizontalLayout2);





            List<CompanyActivity> companyActivityList = new ArrayList<>();
            companyActivityService.getList().forEach(companyActivity -> {
                if( companyActivity.getCompany().getSystemUser().getId().toString().equals(systemUserId.toString()) ) {
                    companyActivityList.add(companyActivity);
                }
            });
            //companyActivityList.addAll( ); // --- current user id - companies
            companyActivityGrid.setItems(companyActivityList);
            companyActivityGrid.setColumns();
            companyActivityGrid.getColumns().forEach(col -> col.setAutoWidth(true));
            companyActivityGrid.addColumn(companyActivity -> {
                Company company1 = companyActivity.getCompany();
                return company1 == null ? "-" : company1.getFirmaAdi();
            }).setHeader("Company");
            companyActivityGrid.addColumn("borc");
            companyActivityGrid.addColumn("alacak");
            companyActivityGrid.addColumn("aciklama");
            companyActivityGrid.addColumn("tarih");
            companyActivityGrid.addComponentColumn(item -> createRemoveButton(companyActivityGrid, item,systemUserId)).setHeader("Actions");

            Button btnCikis = new Button("Çıkış Yap", VaadinIcon.USER.create());
            btnCikis.addClickListener(buttonClickEvent -> {
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().getPage().setLocation("/login");
            });


            btnCikis.getStyle().set("margin-top", "0");
            btnCikis.getStyle().set("margin-left", "90%");
            btnCikis.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            add(btnCikis);

            add(new H2("Firma Faaliyetleri"), filterGroup, btnFirmaHareketiEkle, companyActivityGrid);

        }
    }

    private void configureFilter() {
        txtFilter.setValueChangeMode(ValueChangeMode.LAZY);
        txtFilter.setPlaceholder("Firma adı veya açıklama arayınız..");
        txtFilter.getStyle().set("width", "480px");
        txtFilter.addThemeVariants(TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL);
        txtFilter.addValueChangeListener(e -> updateList());
    }

    private void updateList() {

        List<CompanyActivity> filteredList = new ArrayList<>();
        companyActivityService.getList().forEach(companyActivity -> {
            if( companyActivity.getCompany().getSystemUser().getId().toString().equals(loggedInSystemUserId.toString()) ) {
                if( companyActivity.getAciklama().contains(this.txtFilter.getValue()) ) {
                    filteredList.add(companyActivity);
                } else if( companyActivity.getCompany().getFirmaAdi().contains(this.txtFilter.getValue()) ) {
                    filteredList.add(companyActivity);
                }
            }
        });
        companyActivityGrid.setItems(filteredList);
    }

    private Component createRemoveButton(Grid<CompanyActivity> companyActivityGrid, CompanyActivity item,Long systemUserId) {

        Button btnDelete = new Button("Sil");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(buttonClickEvent -> {


            ConfirmDialog dialog = new ConfirmDialog("Silmeyi Onayla",
                    "Bu kaydı silmek istediğinize emin misiniz?", "Sil", confirmEvent -> {
                companyActivityService.delete(item);
                Notification.show(item.getCompany().getFirmaAdi()+" firmasına ait olan firma hareketi silindi.Açıklama: "+item.getAciklama(),10000, Notification.Position.BOTTOM_STRETCH);
                refreshData(txtFilter.getValue().toString(), systemUserId);




            },
                    "İptal", cancelEvent -> {


            });
            dialog.setConfirmButtonTheme("error primary");

            dialog.open();

        });



        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnDelete);

        return horizontalLayout;


    }


    private void refreshData(String filter, Long systemUserId) {
        List<CompanyActivity> companyActivityList = new ArrayList<>();

        companyActivityService.getList().forEach(companyActivity -> {

            if( companyActivity.getCompany().getSystemUser().getId().toString().equals(systemUserId.toString()) ) {
                companyActivityList.add(companyActivity);
            }
        });

        /*
        if (systemUserId == null) {
            companyActivityList.addAll(companyActivityService.getList());
        } else {
            companyActivityList.addAll(companyActivityService.getList(systemUserId));
        }
        */
        companyActivityGrid.setItems(companyActivityList);
    }
}


