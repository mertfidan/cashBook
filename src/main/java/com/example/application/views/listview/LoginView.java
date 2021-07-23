package com.example.application.views.listview;

import com.example.application.data.entity.SystemUser;
import com.example.application.data.services.SystemUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route
public class LoginView extends VerticalLayout {

    private final SystemUserService systemUserService;
    Dialog dialogNewUser = new Dialog();
    Binder<SystemUser> systemUserBinder = new Binder<>();


    public LoginView(SystemUserService systemUserService){
        this.systemUserService = systemUserService;

        setHeightFull();
        setAlignItems(Alignment.CENTER);//puts button in horizontal  center
        setJustifyContentMode(JustifyContentMode.CENTER);//puts button in vertical center

        Button btnNewUser = new Button("Create User", VaadinIcon.USER.create());
        btnNewUser.getStyle().set("width", "310px");
        btnNewUser.getStyle().set("height", "30px");
        btnNewUser.getStyle().set("font-size", "12px");
        btnNewUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnNewUser.addClickListener(buttonClickEvent -> {
            dialogNewUser.open();
        });

        btnNewUser.getStyle().set("box-shadow", "rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px");



        FormLayout formLayoutNewUser = new FormLayout();
        TextField txtUsername = new TextField("Email", "Enter Email");
        PasswordField txtPassword = new PasswordField( "Password", "Enter Password");
        Button btnSaveUser = new Button("Save User", VaadinIcon.PLUS.create());
        Button btnCancelSaveUser = new Button("Cancel", VaadinIcon.CLOSE.create());
        btnSaveUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        formLayoutNewUser.add(txtUsername, txtPassword);
        VerticalLayout formLayout = new VerticalLayout();
        HorizontalLayout dialogButtonLayouts = new HorizontalLayout();
        dialogButtonLayouts.add(btnSaveUser, btnCancelSaveUser);
        formLayout.add(formLayoutNewUser, dialogButtonLayouts);
        dialogNewUser.add(new H2("New User"),formLayout);

        btnCancelSaveUser.addClickListener(buttonClickEvent -> {
            dialogNewUser.close();
        });

        systemUserBinder.bind(txtUsername, SystemUser::getEmail, SystemUser::setEmail);
        systemUserBinder.bind(txtPassword, SystemUser::getPassword, SystemUser::setPassword);

        Notification notificationSaveUser = new Notification("New User Created!", 3000);
        Notification notificationEmptyField = new Notification("Please Fill in All Fields", 3000);

        btnSaveUser.addClickListener(buttonClickEvent -> {
            if (txtPassword.getValue().equals("") || txtUsername.getValue().equals("")) {
                notificationEmptyField.open();
            } else {
                SystemUser systemUser = new SystemUser();
                try {
                    systemUserBinder.writeBean(systemUser);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                systemUserService.save(systemUser);
                dialogNewUser.close();
                notificationSaveUser.open();
            }
        });

        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(loginEvent -> {


            SystemUser result = systemUserService.login(loginEvent.getUsername(),loginEvent.getPassword());

            if (result.getId()!=null){

                VaadinSession.getCurrent().getSession().setAttribute("LoggedInSystemUserId", result.getId());
                UI.getCurrent().getPage().setLocation("/");

            }
            else{

                loginForm.setError(true);
            }




        });



        add(loginForm,btnNewUser);
    }
}
