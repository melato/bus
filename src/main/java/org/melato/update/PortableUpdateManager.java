package org.melato.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.melato.convert.xml.FieldHandler;
import org.melato.convert.xml.FieldWriter;
import org.melato.convert.xml.ReflectionHandler;
import org.melato.convert.xml.ReflectionWriter;
import org.melato.log.Log;
import org.melato.progress.ProgressGenerator;
import org.melato.xml.XMLWriter;


/** Checks for updates to various files and can download them. */
public class PortableUpdateManager {
  public static final String INSTALLED = "installed-files.xml";
  public static final String AVAILABLE = "available-files.xml";
  
  public static final String FILE_TAG = "file";
  public static final String UPDATES_TAG = "updates";
  
  private List<UpdateFile> availableFiles = null;
  private List<UpdateFile> installedFiles = null;
  
  private File  filesDir;
  private URL   indexUrl;
  private File  tmpDir;
    
  
  public PortableUpdateManager(URL indexUrl, File filesDir) {
    super();
    this.indexUrl = indexUrl;
    this.filesDir = filesDir;
  }

  public PortableUpdateManager(String indexUrl, File filesDir) {
    super();
    try {
      this.indexUrl = new URL(indexUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    }
    this.filesDir = filesDir;
  }

  public void setFilesDir(File filesDir) {
    this.filesDir = filesDir;
    this.tmpDir = filesDir;
  }

  /** Load the index from a local file.
   */
  private boolean readIndex(File file, Collection<UpdateFile> collector) {
    if ( file.exists()) {
      try {
        ReflectionHandler<UpdateFile> reader = new FieldHandler<UpdateFile>(UpdateFile.class, collector);    
        reader.parse(UPDATES_TAG + "/" + FILE_TAG, new FileInputStream(file));
        return true;
      } catch(Exception e) {
        file.delete();
      }
    }
    return false;
  }
  
  /** Load the index from a local file.
   */
  private boolean readIndex(String filename, Collection<UpdateFile> collector) {
    File file = new File(filesDir, filename);
    return readIndex(file, collector);
  }
  
  private void writeIndex(Collection<UpdateFile> index, String filename) {
    File file = new File(filesDir, filename);
    File tmpFile = new File(filesDir, filename + ".tmp");
    XMLWriter xml;
    try {
      xml = new XMLWriter(new FileOutputStream(tmpFile));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException( e );
    } catch (FileNotFoundException e) {
      throw new RuntimeException( e );
    }
    try {
      xml.printHeader();
      xml.tagOpen(UPDATES_TAG);
      ReflectionWriter<UpdateFile> writer = new FieldWriter<UpdateFile>(UpdateFile.class, xml, FILE_TAG);
      for( UpdateFile f: index) {
        writer.write(f);
      }
      xml.tagEnd(UPDATES_TAG);
      xml.close();
      file.delete();
      tmpFile.renameTo( file );
    } finally {
      
    }
  }
  
  private URL getURL(String url) throws MalformedURLException {
    return new URL( indexUrl, url);
  }
  
  private void downloadAvailable() throws IOException {
    File file = new File(filesDir, AVAILABLE);
    Log.info( indexUrl );
    Streams.copy(indexUrl, file);
  }
  
  /** If the list of available updates is empty, check once a week. */
  private int DEFAULT_FREQUENCY_HOURS = 24 * 7;
  
  private boolean needsRefresh(Collection<UpdateFile> list, long lastUpdateTime) {
    long now = System.currentTimeMillis();
    boolean hasFiles = false;
    for(UpdateFile f: list) {
      hasFiles = true;
      long expires = lastUpdateTime + f.getFrequencyHours() * 3600 * 1000L;
      if ( expires < now )
        return true;
    }
    if ( ! hasFiles ) {
      return lastUpdateTime + DEFAULT_FREQUENCY_HOURS * 3600 * 1000L < now;
    }
    return false;
  }
  /**
   * Update our cache of available updates and load it in memory.
   * The AVAILABLE cache file will be updated if:
   * <ul>
   * <li>It does not exist.
   * <li>Its age is older than the frequencyHours field of any available update. 
   * <ul>
   * In all cases, the available updates will be not-null.
   */
  private void initAvailable() {
    if ( availableFiles == null ) {
      availableFiles = new ArrayList<UpdateFile>();
      File file = new File(filesDir, AVAILABLE);
      if ( ! file.exists() || needsRefresh(availableFiles, file.lastModified())) {
        try {
          downloadAvailable();
        } catch (IOException e) {
          Log.info(e);
        }
      }
      if ( file.exists() ) {
        readIndex(AVAILABLE, availableFiles);
      }
    }
  }
  
  private void initInstalled() {    
    if ( installedFiles == null ) {
      installedFiles = new ArrayList<UpdateFile>();
      readIndex(INSTALLED, installedFiles);
    }
  }
  
  private UpdateFile findFile(Collection<UpdateFile> list, String name) {
    for(UpdateFile f: list) {
      if ( name.equals(f.getName())) {
        return f;
      }
    }
    return null;
  }

  public static boolean needsUpdate(UpdateFile installed, UpdateFile available) {
    if ( available == null )
      return false;
    if ( installed == null )
      return true;
    String version = available.getVersion();
    if ( version == null ) {
      return false;
    }
    if ( installed == null || ! version.equals(installed.getVersion())) {
      return true;
    }
    return false;
  }
  
  /**
   * Return a list of files that have available updates.
   * @return
   */
  public List<UpdateFile> getAvailableUpdates() {
    initAvailable();
    initInstalled();
    List<UpdateFile> updates = new ArrayList<UpdateFile>();
    for(UpdateFile available: availableFiles ) {
      String version = available.getVersion();
      if ( version == null ) {
        System.err.println( "Missing version from available update: " + available.getName());
        continue;
      }
      UpdateFile installed = findFile(installedFiles, available.getName());
      if ( installed == null || ! version.equals(installed.getVersion())) {
        updates.add(available);
      }
    }
    return updates;
  }
  
  public void setInstalled(UpdateFile updateFile) {
    initInstalled();
    String name = updateFile.getName();
    boolean found = false;
    for( int i = 0; i < installedFiles.size(); i++ ) {
      if ( name.equals( installedFiles.get(i).getName())) {
        installedFiles.set(i, updateFile);
        found = true;
        break;        
      }
    }
    if ( ! found )
      installedFiles.add(updateFile);
    writeIndex(installedFiles, INSTALLED);
  }
  
  private void unzip( File zipFile, String entryName, File destFile ) throws ZipException, IOException {
    ZipFile zip = new ZipFile(zipFile);
    ZipEntry entry = new ZipEntry(entryName);
    Streams.copy(zip.getInputStream(entry), new FileOutputStream(destFile));    
  }
  
  public void updateFile(UpdateFile updateFile, File destinationFile) {
    File tmpFile = new File(tmpDir, destinationFile.getName() + ".tmp");
    Log.info("update file: " + destinationFile);
    URL url;
    try {
      url = getURL(updateFile.getUrl());
      int size = updateFile.getSize();
      if ( size != 0 ) {
        ProgressGenerator.get().setLimit(size);
      }        
      Streams.copy(url, tmpFile);
      tmpFile.renameTo(destinationFile);
      setInstalled(updateFile);
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    } catch (IOException e) {
      throw new RuntimeException( e );
    }    
  }

  public void updateZipedFile(UpdateFile updateFile, String zipEntry, File destinationFile) {
    Log.info("update zip file: " + destinationFile);
    File zipFile = new File(tmpDir, updateFile.getName());
    File tmpFile = new File(tmpDir, destinationFile.getName() + ".tmp");
    URL url;
    try {
      url = getURL(updateFile.getUrl());
      int size = updateFile.getSize();
      if ( size != 0 ) {
        ProgressGenerator.get().setLimit(size);
      }        
      Streams.copy(url, zipFile);
      unzip( zipFile, zipEntry, tmpFile);
      zipFile.delete();
      tmpFile.renameTo(destinationFile);
      setInstalled(updateFile);
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    } catch (IOException e) {
      throw new RuntimeException( e );
    }    
  }

}
