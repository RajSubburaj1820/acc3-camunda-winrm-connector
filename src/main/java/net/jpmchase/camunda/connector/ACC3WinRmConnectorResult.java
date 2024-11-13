package net.jpmchase.camunda.connector;


import java.util.Objects;

public class ACC3WinRmConnectorResult {

  // TODO: define connector result properties, which are returned to the process engine
  private Object resultProperty;

  public Object getResultProperty() {
    return resultProperty;
  }

  public void setResultProperty(Object resultProperty) {
    this.resultProperty = resultProperty;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ACC3WinRmConnectorResult that = (ACC3WinRmConnectorResult) o;
    return Objects.equals(resultProperty, that.resultProperty);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resultProperty);
  }

  @Override
  public String toString() {
    return "ACC3WinRmConnectorResult [resultProperty=" + resultProperty + "]";
  }

}
