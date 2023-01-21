package ru.soft.webclient;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@EnableFeignClients
@SpringBootApplication
@Theme(value = "workoutassistant")
@PWA(name = "Workout assistant", shortName = "Workout assistant", offlineResources = {})
@NpmPackage(value = "@adobe/lit-mobx", version = "2.0.0")
@NpmPackage(value = "mobx", version = "^6.3.5")
@NpmPackage(value = "lit-icon", version = "1.0.1")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class WebClientApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class, args);
    }
}
