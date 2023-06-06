package com.example.implementation;

import com.example.implementation.Controller.LoginController;
import com.example.implementation.Model.*;
import com.example.implementation.Repository.*;
import com.example.implementation.Repository.utis.Hibernate;
import com.example.implementation.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;

public class Main extends Application {
    private static SessionFactory sessionFactory;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        SessionFactory sessionFactory = Hibernate.initialize();
        controller.setService(Service.getInstance(sessionFactory));
        stage.setTitle("Monitoring");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
