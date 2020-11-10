import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.security.KeyStore.Entry.Attribute;

public class ParseXML{

   // building a document from the XML file
   // returns a Document object after loading the book.xml file.
   public static Document getDocFromFile(String filename) throws ParserConfigurationException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = null;
           
      try{
         doc = db.parse(filename);
      } catch (Exception ex){
         System.out.println("XML parse failure");
         ex.printStackTrace();
      }
   
      return doc;
   }  
        
   public static void parseRoleDataTo (Document d, ArrayList<Role> rolebank, String type) {

      Element root = d.getDocumentElement();
      NodeList rolebanks = root.getElementsByTagName(type); // rolebank or extrabank
      for (int i = 0; i < rolebanks.getLength(); i++) {
         NodeList roles = rolebanks.item(i).getChildNodes();
         for (int j = 0; j < roles.getLength(); j++) {
            Node role = roles.item(j);
            String name = "";
            String desc = "";
            int rank = 0;

            NodeList nodes = role.getChildNodes();
            for (int k = 0; k < nodes.getLength(); k++) {
               Node node = nodes.item(k);
               if ("name".equals(node.getNodeName())) {
                  name = node.getTextContent();
               }
               else if ("desc".equals(node.getNodeName())) {
                  desc = node.getTextContent();
               }
               else if ("rank".equals(node.getNodeName())) {
                  rank = Integer.parseInt(node.getTextContent());
               }
            }

            Role newRole = new Role(name, desc, rank);
            rolebank.add(newRole);
         }
      }
   }

   public static void parseSceneDataTo (Document d, ArrayList<Scene> scenebank, ArrayList<Role> rolebank) {

      Element root = d.getDocumentElement();
      NodeList scenebanks = root.getElementsByTagName("scenebank"); // rolebank or extrabank
      for (int i = 0; i < scenebanks.getLength(); i++) {
         NodeList scenes = scenebanks.item(i).getChildNodes();
         for (int j = 0; j < scenes.getLength(); j++) {
            Node scene = scenes.item(j);
            String name = "";
            String desc = "";
            ArrayList<Role> roles = new ArrayList<Role>();

            NodeList nodes = scene.getChildNodes();
            for (int k = 0; k < nodes.getLength(); k++) {
               Node node = nodes.item(k);
               if ("name".equals(node.getNodeName())) {
                  name = node.getTextContent();
               }
               else if ("desc".equals(node.getNodeName())) {
                  desc = node.getTextContent();
               }
               else if ("roleidx".equals(node.getNodeName())) {
                  int idx = Integer.parseInt(node.getTextContent());
                  roles.add(rolebank.get(idx));
               }
            }

            Scene newScene = new Scene(name, desc, roles);
            scenebank.add(newScene);
         }
      }
   }

   public static void parseSetDataTo (Document d, ArrayList<Set> setbank, ArrayList<Scene> scenebank, ArrayList<Role> rolebank) {

      Element root = d.getDocumentElement();
      NodeList setbanks = root.getElementsByTagName("setbank"); // rolebank or extrabank
      for (int i = 0; i < setbanks.getLength(); i++) {
         NodeList sets = setbanks.item(i).getChildNodes();
         for (int j = 0; j < sets.getLength(); j++) {
            Node set = sets.item(j);
            String name = "";
            ArrayList<Role> roles = new ArrayList<Role>();

            NodeList nodes = set.getChildNodes();
            for (int k = 0; k < nodes.getLength(); k++) {
               Node node = nodes.item(k);
               if ("name".equals(node.getNodeName())) {
                  name = node.getTextContent();
               }
               else if ("roleidx".equals(node.getNodeName())) {
                  int idx = Integer.parseInt(node.getTextContent());
                  roles.add(rolebank.get(idx));
               }
            }

            Set newScene = new Set(name, roles);
            setbank.add(newScene);
         }
      }
   }
}