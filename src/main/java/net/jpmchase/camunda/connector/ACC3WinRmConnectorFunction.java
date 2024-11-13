package net.jpmchase.camunda.connector;

import com.google.gson.Gson;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@OutboundConnector(
    name = "WinRMConnector",
    inputVariables = {"script", "controller", "authentication"},
    type = "net.jpmchase:winrm:1")
public class ACC3WinRmConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(ACC3WinRmConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) throws Exception {
    var connectorRequest = context.bindVariables(ACC3WinRmConnectorRequest.class);
    return executeConnector(connectorRequest);
  }


  private ACC3WinRmConnectorResult executeConnector(final ACC3WinRmConnectorRequest connectorRequest) {

    LOGGER.info("WinRM connector: Start. MyConnectorRequest=" + connectorRequest);

    WinRmToolResponse executePs = null;
    WinRmTool tool = null;
    WinRmClientContext context = null;
    try {
      context = WinRmClientContext.newInstance();
      List<String> userDetails = List.of(connectorRequest.getAuthentication().getUser().split("\\\\")).stream().filter(e -> StringUtils.isNoneEmpty(e)).collect(Collectors.toList());
      String user = userDetails.get(1);
      String domain = userDetails.get(0);
      LOGGER.info("WinRM connector: user = {}", user);
      LOGGER.info("WinRM connector: domain = {}", domain);

      WinRmTool.Builder builder = WinRmTool.Builder.builder(connectorRequest.getController(), domain, user, connectorRequest.getAuthentication().getToken());
      builder.setAuthenticationScheme("NTLM");
      builder.port(5986);
      builder.useHttps(true);
      builder.context(context);
      builder.disableCertificateChecks(true);
      tool = builder.build();
      tool.setRetriesForConnectionFailures(1);
      tool.setOperationTimeout(10L);
      //String s1 = "Invoke-Command -ScriptBlock { asnp Citrix.Broker.Admin.V2; Get-BrokerSite | ConvertTo-Json }";
      String s1 = connectorRequest.getScript();
      LOGGER.info("script used:  = {}", s1);
      executePs = tool.executePs(s1);
      LOGGER.info("Out put>>>{},Error >>{}",executePs.getStdOut() , executePs.getStdErr());
    } catch (Throwable e) {
      Throwable cause = e.getCause();
      LOGGER.info("Exception: {}", e.getMessage());
    }  finally {
      context.shutdown();
    }


    // TODO: implement connector logic
    LOGGER.info("Executing my connector with request {}", connectorRequest);
    String message = executePs.getStdOut();
    if (message != null && message.toLowerCase().startsWith("fail")) {
      throw new ConnectorException("FAIL", "My property started with 'fail', was: " + message);
    }
    var result = new ACC3WinRmConnectorResult();
    Gson gson = new Gson();

    try {
      new JSONObject(message);
      result.setResultProperty(gson.fromJson(message, Object.class));
    } catch (JSONException e) {
      LOGGER.error("Not a json");
      result.setResultProperty(message);
    }

    LOGGER.info("WinRM connector: End.");

    return result;
  }
}
