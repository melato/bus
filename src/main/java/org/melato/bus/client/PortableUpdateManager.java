package org.melato.bus.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.melato.convert.xml.FieldHandler;
import org.melato.convert.xml.FieldWriter;
import org.melato.convert.xml.ReflectionHandler;
import org.melato.convert.xml.ReflectionWriter;
import org.melato.log.Log;
import org.melato.xml.XMLWriter;


/** Checks for and/or downloads database updates. */
public class PortableUpdateManager {
  public static final String INDEX_URL = "http://transit.melato.org/updates.xml";
  public static final String INSTALLED = "installed-files.xml";
  public static final String AVAILABLE = "available-files.xml";
  
  public static final String FILE_TAG = "file";
  public static final String UPDATES_TAG = "updates";
  
  public static final String ROUTES_ZIP = "ROUTES.zip";
  public static final String ROUTES_ENTRY = "ROUTES.db";
  
  private Map<String,UpdateFile> available = null;
  private Map<String,UpdateFile> installed = null;
  
  private File  filesDir;
  private File  tmpDir;
    
  public void setFilesDir(File filesDir) {
    this.filesDir = filesDir;
    this.tmpDir = filesDir;
  }

  /** Load the index from a local file.
   */
  private Map<String,UpdateFile> readIndex(String filename) {
    List<UpdateFile> list = new ArrayList<UpdateFile>();
    File file = new File(filesDir, filename);
    Map<String,UpdateFile> map = null;
    if ( ! file.exists())
      return map;
    try {
      ReflectionHandler<UpdateFile> reader = new FieldHandler<UpdateFile>(UpdateFile.class, list);    
      reader.parse(UPDATES_TAG + "/" + FILE_TAG, new FileInputStream(file));
      map = new HashMap<String,UpdateFile>();
      for( UpdateFile f: list ) {
        Log.info( filename + ": " + f);
        map.put(f.getName(), f);
      }
    } catch(Exception e) {
      file.delete();
    }
    return map;
  }
  
  private void writeIndex(Map<String,UpdateFile> index, String filename) {
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
      for( UpdateFile f: index.values()) {
        writer.write(f);
      }
      xml.tagEnd(UPDATES_TAG);
      xml.close();
      file.delete();
      tmpFile.renameTo( file );
    } finally {
      
    }
  }
  public void downloadAvailable() throws IOException {
    File file = new File(filesDir, AVAILABLE);
    URL url = new URL( INDEX_URL );
    Log.info( url );
    Log.info( file );
    Streams.copy(url, file);
  }
  
  /** Get an available update */
  public UpdateFile getAvailableUpdate(String name) {
    if ( available == null ) {
      available = readIndex(AVAILABLE);
      if ( available == null ) {
        try {
          downloadAvailable();
          available = readIndex(AVAILABLE);
        } catch (IOException e) {
          Log.info(e);
        }
        if (available == null) {
          available = Collections.emptyMap();
        }
      }
    }
    if ( installed == null ) {
      installed = readIndex(INSTALLED);
      if ( installed == null )
        installed = new HashMap<String,UpdateFile>();
    }
    UpdateFile availableFile = available.get(name);
    if ( availableFile != null ) {
      UpdateFile installedFile = installed.get(name);
      if ( installedFile != null ) {
        String version = installedFile.getVersion();
        if ( version != null && version.equals(availableFile.getVersion())) {
          return null;          
        }        
      }
    }
    return availableFile;
  }
  
  public void setInstalled(UpdateFile updateFile) {
    if ( installed == null ) {
      installed = readIndex(INSTALLED);
    }
    if ( installed == null ) {
      installed = new HashMap<String,UpdateFile>();
    }
    installed.put(updateFile.getName(), updateFile);
    writeIndex(installed, INSTALLED);
  }
  
  private void unzip( File zipFile, String entryName, File destFile ) throws ZipException, IOException {
    ZipFile zip = new ZipFile(zipFile);
    ZipEntry entry = new ZipEntry(entryName);
    Streams.copy(zip.getInputStream(entry), new FileOutputStream(destFile));    
  }
  public void updateRoutesDB(File databaseFile) {
    Log.info("updateRoutesDB");
    UpdateFile updateFile = getAvailableUpdate(ROUTES_ZIP);
    Log.info("updateFile =" + updateFile);
    if ( updateFile == null )
      return;
    File zipFile = new File(tmpDir, ROUTES_ZIP);
    File tmpFile = new File(tmpDir, databaseFile.getName() + ".tmp");
    Log.info("databaseFile =" + databaseFile);
    Log.info("zipFile =" + zipFile);
    URL url;
    try {
      url = new URL( updateFile.getUrl());
      Streams.copy(url, zipFile);
      unzip( zipFile, ROUTES_ENTRY, tmpFile);
      zipFile.delete();
      tmpFile.renameTo(databaseFile);
      setInstalled(updateFile);
    } catch (MalformedURLException e) {
      throw new RuntimeException( e );
    } catch (IOException e) {
      throw new RuntimeException( e );
    }
    
  }

}
