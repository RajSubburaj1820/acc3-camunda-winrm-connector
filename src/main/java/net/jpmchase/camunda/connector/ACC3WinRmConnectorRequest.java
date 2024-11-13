package net.jpmchase.camunda.connector;

import jakarta.validation.Valid;

import java.util.Objects;

public class ACC3WinRmConnectorRequest {

  private String script;

  private String controller;

  @Valid
  private Authentication authentication;


  public Authentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  @Override
  public int hashCode() {
    return Objects.hash(authentication, script, controller);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ACC3WinRmConnectorRequest other = (ACC3WinRmConnectorRequest) obj;
    return Objects.equals(authentication, other.authentication)
        && Objects.equals(script, other.script);
  }

  @Override
  public String toString() {
    return "ACC3WinRmConnectorRequest [script=" + script + ", authentication=" + authentication + ", controller=" + controller + "]";
  }

  public String getController() {
    return controller;
  }

  public void setController(String controller) {
    this.controller = controller;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }
}
