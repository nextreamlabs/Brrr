package org.nextreamlabs.bradme.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.nextreamlabs.bradme.controllers.IController;
import org.nextreamlabs.bradme.exceptions.CannotCreateViewException;
import org.nextreamlabs.bradme.exceptions.TemplateNotFoundException;
import org.nextreamlabs.bradme.support.Logging;

import java.io.IOException;
import java.net.URL;

public abstract class ViewWithTemplate<TRootNode extends Node> implements IViewWithTemplate<TRootNode> {

  protected static final String TEMPLATE_PATH;

  private final String templateName;
  private final FXMLLoader loader;
  private final TRootNode rootNode;

  // { Construction

  static {
    TEMPLATE_PATH = "/org/nextreamlabs/bradme/templates/%s.fxml";
  }

  protected ViewWithTemplate(String templateName, IController controller) {
    this.templateName = templateName;
    this.loader = new FXMLLoader(this.getTemplateURL());
    if (controller != null) {
      this.loader.setController(controller);
    }
    Object loadedObject = null;
    try {
      loadedObject = loader.load();
    } catch (IOException e) {
      Logging.error(String.format("Cannot load the template: %s", this.templateName));
    }
    if (loadedObject != null) {
      try {
        this.rootNode = (TRootNode) loadedObject;
      } catch(ClassCastException exc) {
        Logging.error("The template have a wrong root node");
        throw CannotCreateViewException.create(this.getTemplateName());
      }
    } else {
      throw CannotCreateViewException.create(this.getTemplateName());
    }
  }

  // }

  // { IViewWithTemplate implementation

  public TRootNode getRootNode() {
    return this.rootNode;
  }

  // }

  protected String getTemplateName() {
    return this.templateName;
  }

  protected URL getTemplateURL() {
    String templatePath = String.format(TEMPLATE_PATH, this.getTemplateName());
    URL templateURL = this.getClass().getResource(templatePath);
    if (templateURL == null) {
      throw TemplateNotFoundException.create(templatePath);
    }
    return templateURL;
  }

}
