import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.util.ArrayList;

import org.w3c.dom.*;

import java.util.Hashtable;

public class ParseXML{

   private ArrayList<Set> setbank;
   private ArrayList<Scene> scenebank;
   private ArrayList<Role> rolebank;
   private ArrayList<Role> extrabank;

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

   public static void parseSceneCards(Document d, ArrayList<Scene> scenebank) {
      Element root = d.getDocumentElement();
      NodeList cards = root.getElementsByTagName("card");
      for (int i = 0; i < cards.getLength(); i++) {

         Node card = cards.item(i);
         String sceneName = parseStringAttribute(card, "name");
         String sceneImg = parseStringAttribute(card, "img");
         int sceneBudget = parseIntegerAttribute(card, "budget");
         int sceneNumber = -1;
         String sceneDesc = "";
         ArrayList<Role> sceneParts = new ArrayList<>();

         NodeList cardContents = card.getChildNodes();
         for (int j = 0; j < cardContents.getLength(); j++) {

            Node cardElement = cardContents.item(j);
            if ("scene".equals(cardElement.getNodeName())) {
               sceneNumber = parseIntegerAttribute(cardElement, "number");
               sceneDesc = cardElement.getTextContent();
            }
            else if ("part".equals(cardElement.getNodeName())) {
               parseRole(cardElement, sceneParts);
            } // Scene vs Part
         } // Card Contents

         scenebank.add(new Scene(sceneName, sceneImg, sceneBudget, sceneNumber, sceneDesc, sceneParts));

      } // Card
   }

   public static void parseBoard(Document d, ArrayList setbank, Hashtable<String, IArea> areabank) {
      Element root = d.getDocumentElement();
      NodeList elements = root.getChildNodes();
      for (int i = 0; i < elements.getLength(); i++) {

         Node element = elements.item(i);

         if ("set".equals(element.getNodeName())) {
            parseSet(element, setbank, areabank);
         }
         else if ("trailer".equals(element.getNodeName())) {
            parseTrailer(element, areabank);
         }
         else if ("office".equals(element.getNodeName())) {
            parseOffice(element, areabank);
         }
      } // Card
   }

   private static void parseTrailer(Node rootElement, Hashtable<String, IArea> areabank) {

      ArrayList<String> trailerNeighbors = new ArrayList<>();
      Rectangle trailerArea = new Rectangle();

      NodeList trailerContents = rootElement.getChildNodes();
      for (int i = 0; i < trailerContents.getLength(); i++) {

         Node trailerElement = trailerContents.item(i);
         if ("neighbors".equals(trailerElement.getNodeName())) {
            parseNeighbors(trailerElement, trailerNeighbors);
         }
         else if ("area".equals(trailerElement.getNodeName())) {
            parseArea(trailerElement, trailerArea);
         }
      }

      areabank.put("Trailer", new Trailer(trailerNeighbors, trailerArea));
   }

   private static void parseOffice(Node rootElement, Hashtable<String, IArea> areabank) {

      ArrayList<String> officeNeighbors = new ArrayList<>();
      Rectangle officeArea = new Rectangle();
      ArrayList<Upgrade> officeUpgrades = new ArrayList<>();

      NodeList officeContents = rootElement.getChildNodes();
      for (int i = 0; i < officeContents.getLength(); i++) {

         Node officeElement = officeContents.item(i);
         if ("neighbors".equals(officeElement.getNodeName())) {
            parseNeighbors(officeElement, officeNeighbors);
         }
         else if ("area".equals(officeElement.getNodeName())) {
            parseArea(officeElement, officeArea);
         }
         else if ("upgrade".equals(officeElement.getNodeName())) {
            parseUpgrades(officeElement, officeUpgrades);
         }
      }

      areabank.put("Office", new Office(officeNeighbors, officeArea, officeUpgrades));
   }

   private static void parseSet(Node rootElement, ArrayList<Set> setbank, Hashtable<String, IArea> areabank) {

      String setName = parseStringAttribute(rootElement, "name");
      ArrayList<String> setNeighborStrings = new ArrayList<>();
      Rectangle setArea = new Rectangle();
      ArrayList<Integer> setTakeNums = new ArrayList<>();
      ArrayList<Rectangle> setTakeAreas = new ArrayList<>();
      ArrayList<Role> setParts = new ArrayList<>();

      NodeList setContents = rootElement.getChildNodes();
      for (int j = 0; j < setContents.getLength(); j++) {

         Node setElement = setContents.item(j);
         if ("neighbors".equals(setElement.getNodeName())) {
            parseNeighbors(setElement, setNeighborStrings);
         }
         else if ("area".equals(setElement.getNodeName())) {
            parseArea(setElement, setArea);
         }
         else if ("takes".equals(setElement.getNodeName())) {
            parseTakes(setElement, setTakeNums, setTakeAreas);
         }
         else if ("parts".equals(setElement.getNodeName())) {
            parseRoles(setElement, setParts);
         }
      }

      Set set = new Set(setName, setNeighborStrings, setArea, setTakeNums, setTakeAreas, setParts);
      setbank.add(set);
      areabank.put(setName, set);
   }

   private static void parseUpgrades(Node rootElement, ArrayList<Upgrade> finalUpgrades) {
      NodeList upgrades = rootElement.getChildNodes();
      for (int k = 0; k < upgrades.getLength(); k++) {
         Node upgrade = upgrades.item(k);

         int level = parseIntegerAttribute(upgrade, "level");
         String currency = parseStringAttribute(upgrade, "currency");
         int amount = parseIntegerAttribute(upgrade, "amt");
         Rectangle area = new Rectangle();

         Node areaNode = upgrade.getFirstChild();
         parseArea(areaNode, area);

         finalUpgrades.add(new Upgrade(level, currency, amount, area));
      }
   }

   private static void parseNeighbors(Node rootElement, ArrayList<String> finalNeighbors) {
      NodeList neighbors = rootElement.getChildNodes();
      for (int k = 0; k < neighbors.getLength(); k++) {
         Node neighbor = neighbors.item(k);
         if ("neighbor".equals(neighbor.getNodeName())) {
            finalNeighbors.add(parseStringAttribute(neighbor, "name"));
         }
      }
   }

   private static void parseTakes(Node rootElement, ArrayList<Integer> takeNums, ArrayList<Rectangle> takeAreas) {
      NodeList takes = rootElement.getChildNodes();
      for (int k = 0; k < takes.getLength(); k++) {
         Node take = takes.item(k);
         if ("take".equals(take.getNodeName())) {
            takeNums.add(parseIntegerAttribute(take, "number"));
            Rectangle takeArea = new Rectangle();
            parseArea(take.getFirstChild(), takeArea);
            takeAreas.add(takeArea);
         }

      }
   }

   private static void parseRoles(Node rootElement, ArrayList<Role> finalRoles) {
      NodeList roles = rootElement.getChildNodes();
      for (int k = 0; k < roles.getLength(); k++) {
         Node neighbor = roles.item(k);
         if ("part".equals(neighbor.getNodeName())) {
            parseRole(neighbor, finalRoles);
         }
      }
   }

   private static void parseRole(Node rootElement, ArrayList<Role> parts) {

      String partName = parseStringAttribute(rootElement, "name");
      int partLevel = parseIntegerAttribute(rootElement, "level");
      Rectangle partArea = new Rectangle();
      String partLine = "";

      NodeList partContents = rootElement.getChildNodes();
      for (int k = 0; k < partContents.getLength(); k++) {

         Node partElement = partContents.item(k);
         if ("area".equals(partElement.getNodeName())) {
            parseArea(partElement, partArea);
         }
         else if ("line".equals(partElement.getNodeName())) {
            partLine = partElement.getTextContent();
         }
      }

      parts.add(new Role(partName, partLine, partLevel, partArea));
   }

   private static void parseArea(Node rootElement, Rectangle area) {
      area.x = parseIntegerAttribute(rootElement, "x");
      area.y = parseIntegerAttribute(rootElement, "y");
      area.height = parseIntegerAttribute(rootElement, "h");
      area.width = parseIntegerAttribute(rootElement, "w");
   }

   private static String parseStringAttribute(Node rootElement, String itemName) {
      return rootElement.getAttributes().getNamedItem(itemName).getTextContent();
   }

   private static Integer parseIntegerAttribute(Node rootElement, String itemName) {
      return Integer.parseInt(rootElement.getAttributes().getNamedItem(itemName).getTextContent());
   }
}

//   public static void parseSetDataTo (Document d, ArrayList<Set> setbank, ArrayList<Scene> scenebank, ArrayList<Role> rolebank) {
//
//      Element root = d.getDocumentElement();
//      NodeList setbanks = root.getElementsByTagName("setbank"); // rolebank or extrabank
//      for (int i = 0; i < setbanks.getLength(); i++) {
//         NodeList sets = setbanks.item(i).getChildNodes();
//         for (int j = 0; j < sets.getLength(); j++) {
//            Node set = sets.item(j);
//            String name = "";
//            int maxShots = 2;
//            ArrayList<Role> roles = new ArrayList<Role>();
//
//            NodeList nodes = set.getChildNodes();
//            for (int k = 0; k < nodes.getLength(); k++) {
//               Node node = nodes.item(k);
//               if ("name".equals(node.getNodeName())) {
//                  name = node.getTextContent();
//               }
//               else if ("maxShots".equals(node.getNodeName())) {
//                  maxShots = Integer.parseInt(node.getTextContent());
//               }
//               else if ("roleidx".equals(node.getNodeName())) {
//                  int idx = Integer.parseInt(node.getTextContent());
//                  roles.add(rolebank.get(idx));
//               }
//            }
//
//            Set newScene = new Set(name, maxShots, roles);
//            setbank.add(newScene);
//         }
//      }
//   }

//   public static void parseRoleDataTo (Document d, ArrayList<Role> rolebank, String type) {
//
//      Element root = d.getDocumentElement();
//      NodeList rolebanks = root.getElementsByTagName(type); // rolebank or extrabank
//      for (int i = 0; i < rolebanks.getLength(); i++) {
//         NodeList roles = rolebanks.item(i).getChildNodes();
//         for (int j = 0; j < roles.getLength(); j++) {
//            Node role = roles.item(j);
//            String name = "";
//            String desc = "";
//            int rank = 0;
//
//            NodeList nodes = role.getChildNodes();
//            for (int k = 0; k < nodes.getLength(); k++) {
//               Node node = nodes.item(k);
//               if ("name".equals(node.getNodeName())) {
//                  name = node.getTextContent();
//               }
//               else if ("desc".equals(node.getNodeName())) {
//                  desc = node.getTextContent();
//               }
//               else if ("rank".equals(node.getNodeName())) {
//                  rank = Integer.parseInt(node.getTextContent());
//               }
//            }
//
//            Role newRole = new Role(name, desc, rank);
//            rolebank.add(newRole);
//         }
//      }
//   }
//
//   public static void parseSceneDataTo (Document d, ArrayList<Scene> scenebank, ArrayList<Role> rolebank) {
//
//      Element root = d.getDocumentElement();
//      NodeList scenebanks = root.getElementsByTagName("scenebank"); // rolebank or extrabank
//      for (int i = 0; i < scenebanks.getLength(); i++) {
//         NodeList scenes = scenebanks.item(i).getChildNodes();
//         for (int j = 0; j < scenes.getLength(); j++) {
//            Node scene = scenes.item(j);
//            String name = "";
//            String desc = "";
//            ArrayList<Role> roles = new ArrayList<Role>();
//
//            NodeList nodes = scene.getChildNodes();
//            for (int k = 0; k < nodes.getLength(); k++) {
//               Node node = nodes.item(k);
//               if ("name".equals(node.getNodeName())) {
//                  name = node.getTextContent();
//               }
//               else if ("desc".equals(node.getNodeName())) {
//                  desc = node.getTextContent();
//               }
//               else if ("roleidx".equals(node.getNodeName())) {
//                  int idx = Integer.parseInt(node.getTextContent());
//                  roles.add(rolebank.get(idx));
//               }
//            }
//
//            Scene newScene = new Scene(name, desc, roles);
//            scenebank.add(newScene);
//         }
//      }
//   }
//}