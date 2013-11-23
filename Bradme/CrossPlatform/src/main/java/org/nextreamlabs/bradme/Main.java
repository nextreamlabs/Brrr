package org.nextreamlabs.bradme;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nextreamlabs.bradme.controllers.AppController;
import org.nextreamlabs.bradme.factories.MVCFactory;
import org.nextreamlabs.bradme.support.L10N;
import org.nextreamlabs.bradme.support.Logging;

/**
 * The application entry point.
 */
@SuppressWarnings({"PublicConstructor", "PublicMethodNotExposedInInterface"})
public class Main extends Application {

  private static final int MAIN_SCENE_WIDTH;
  private static final int MAIN_SCENE_HEIGHT;

  static {
    MAIN_SCENE_WIDTH = 640;
    MAIN_SCENE_HEIGHT = 480;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Logging.info("Application starting");
    Parent root = MVCFactory.getInstance().createForTemplate("app", new MVCFactory.IControllerFactory() {
      @Override
      public AppController createController() {
        return new AppController();
      }
    }, Parent.class);
    primaryStage.setTitle(L10N.t("bradme_title"));
    primaryStage.setScene(new Scene(root, MAIN_SCENE_WIDTH, MAIN_SCENE_HEIGHT));
    primaryStage.show();
    Logging.info("Application started");
  }

  @Override
  public void stop() throws Exception {
    Logging.info("Application stopping");
    super.stop();
    Logging.info("Application stopped");
  }

  public static void main(String[] args) {
    launch(args);
  }

}
