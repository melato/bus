package org.melato.bus.client;

public interface HelpStorage {
  /** Load a help item by name.
   * @param name
   * @param lang The language code (e.g. "el", "en").
   * @return The help item for the specific language, or for the default language.
   */
  HelpItem loadHelpByName(String name, String lang);
  /** Load a help item by internal node code.
   * @param node The node code.
   */
  HelpItem loadHelpByNode(String node);
}
