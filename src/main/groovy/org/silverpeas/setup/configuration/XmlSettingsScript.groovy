package org.silverpeas.setup.configuration

import groovy.util.slurpersupport.GPathResult
import org.silverpeas.setup.api.Logger
import org.silverpeas.setup.api.Script
import org.silverpeas.setup.api.SilverpeasSetupService
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * A script represented by an XML file in which are indicated the Silverpeas properties and XML
 * files to update and for each of them the properties to add or to update.
 * @author mmoquillon
 */
class XmlSettingsScript implements Script {

  private String script
  private Logger log

  /**
   * Constructs a new XML script for an XML settings file located at the specified absolute path.
   * @param path
   */
  XmlSettingsScript(String path) {
    script = path
  }

  /**
   * Uses the specified logger to trace this script execution.
   * @param logger a logger.
   * @return itself.
   */
  @Override
  XmlSettingsScript useLogger(final Logger logger) {
    this.log = logger
    return this
  }

  /**
   * Runs this script with the specified arguments.
   * @param args a Map of variables to pass to the scripts. The keys in the Map are the names of the
   * variables. Expected the configuration settings of Silverpeas under the name
   * <code>settings</code>.
   * @throws RuntimeException if an error occurs during the execution of the script.
   */
  @Override
  void run(def args) throws RuntimeException {
    def settingsStatements = new XmlSlurper().parse(script)
    settingsStatements.fileset.each { GPathResult fileset ->
      String dir = SilverpeasSetupService.replaceVariables(fileset.@root.text())
      fileset.children().each { GPathResult file ->
        String status = '[OK]'
        String filename = file.@name
        log.info "${dir}/${filename} processing..."
        try {
          switch (file.name()) {
            case 'configfile':
              updateConfigurationFile("${dir}/${filename}", file.parameter, args.settings)
              break
            case 'xmlfile':
              updateXmlFile("${dir}/${filename}", file.parameter, args.settings)
          }
        } catch (Exception ex) {
          status = '[FAILURE]'
          throw new RuntimeException(ex)
        } finally {
          log.info "${dir}/${filename} processing: ${status}"
        }
      }
    }
  }

  private void updateConfigurationFile(String configurationFilePath, GPathResult parameters, def settings) {
    def properties = [:]
    parameters.each { GPathResult parameter ->
      properties[parameter.@key.text()] = parameter.text()
    }
    properties = VariableReplacement.parseParameters(properties, settings)
    SilverpeasSetupService.updateProperties(configurationFilePath, properties)
  }

  private void updateXmlFile(String xmlFilePath, GPathResult parameters, def settings) {
    Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFilePath))
    XPath xpath = XPathFactory.newInstance().newXPath()
    parameters.each { GPathResult parameter ->
      def nodes = xpath.evaluate(parameter.@key.text(), xml, XPathConstants.NODESET)
      nodes.each { Node node ->
        if (node.nodeType == Node.ATTRIBUTE_NODE) {
          node.nodeValue = parameter.text()
        } else if (node.nodeType == Node.ELEMENT_NODE) {
          node.textContent = parameter.text()
        }
      }
    }

    Transformer transformer = TransformerFactory.newInstance().newTransformer()
    transformer.transform(new DOMSource(xml), new StreamResult(new File(xmlFilePath)))
  }
}