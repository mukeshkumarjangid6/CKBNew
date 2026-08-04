package com.example.Exception_Library_App.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.example.Exception_Library_App.config.ActionSpec;
import com.example.Exception_Library_App.config.ConfigView;

/**
 * Reads business-defined XML policies and converts them into Java objects.
 * Provide read-only access to parsed config. Invoke every time an exception
 * occurs. DOM parser that reads the existing XML structure EXACTLY as-is:
 * <ExceptionLib><Project name="..."><module name="..."><exception name=
 * "..."><email .../><log
 * .../></exception>...</module>...</Project>...</ExceptionLib>
 */
public final class XmlConfigLoader {
	private XmlConfigLoader() {
	}

	public static ConfigView load(InputStream xmlStream) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		Document doc = dbf.newDocumentBuilder().parse(xmlStream);
		Element root = doc.getDocumentElement(); // "ExceptionLib" (case-sensitive)
		// Project → Module → Exception → List of Actions
		Map<String, Map<String, Map<String, List<ActionSpec>>>> data = new LinkedHashMap<String, Map<String, Map<String, List<ActionSpec>>>>();

		NodeList rootChildren = root.getChildNodes();
		for (int i = 0; i < rootChildren.getLength(); i++) {
			Node pNode = rootChildren.item(i);
			if (!(pNode instanceof Element))
				continue;
			Element projectEl = (Element) pNode; // tag "Project" (as in your XML)

			if (!"Project".equals(projectEl.getTagName()))
				continue;
			String projectName = projectEl.getAttribute("name");
			Map<String, Map<String, List<ActionSpec>>> modules = new LinkedHashMap<String, Map<String, List<ActionSpec>>>();

			NodeList projectChildren = projectEl.getChildNodes();
			for (int j = 0; j < projectChildren.getLength(); j++) {
				Node mNode = projectChildren.item(j);
				if (!(mNode instanceof Element))
					continue;
				Element moduleEl = (Element) mNode; // tag "module" (lowercase in your XML)
				if (!"module".equals(moduleEl.getTagName()))
					continue;

				String moduleName = moduleEl.getAttribute("name");
				Map<String, List<ActionSpec>> exceptions = new LinkedHashMap<String, List<ActionSpec>>();

				NodeList moduleChildren = moduleEl.getChildNodes();
				for (int k = 0; k < moduleChildren.getLength(); k++) {
					Node eNode = moduleChildren.item(k);
					if (!(eNode instanceof Element))
						continue;
					Element exceptionEl = (Element) eNode; // tag "exception"
					if (!"exception".equals(exceptionEl.getTagName()))
						continue;

					String exceptionName = exceptionEl.getAttribute("name");
					List<ActionSpec> actions = new ArrayList<ActionSpec>();

					NodeList exceptionChildren = exceptionEl.getChildNodes();
					for (int a = 0; a < exceptionChildren.getLength(); a++) {
						Node actionNode = exceptionChildren.item(a);
						if (!(actionNode instanceof Element))
							continue;
						Element actionEl = (Element) actionNode;

						String type = actionEl.getTagName(); // "email", "log" (and "sms" if present)
						Map<String, String> attrs = new LinkedHashMap<String, String>();
						NamedNodeMap atts = actionEl.getAttributes();
						for (int idx = 0; idx < atts.getLength(); idx++) {
							Node att = atts.item(idx);
							attrs.put(att.getNodeName(), att.getNodeValue());
						}
						actions.add(new ActionSpec(type, attrs));
					}
					exceptions.put(exceptionName, actions);
				}
				modules.put(moduleName, exceptions);
			}
			data.put(projectName, modules);
		}
		return new ConfigView(data);
	}
}