package arez;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import static org.testng.Assert.*;

@SuppressWarnings( "NonJREEmulationClassesInClientCode" )
final class DiagnosticMessages
{
  @Nonnull
  private static final Map<Integer, DiagnosticMessage> c_messages = new HashMap<>();
  private static long c_loadTime;

  private DiagnosticMessages()
  {
  }

  @SuppressWarnings( "NonJREEmulationClassesInClientCode" )
  static void loadIfRequired()
    throws Exception
  {
    final File file = getMessageFilename();
    if ( file.exists() )
    {
      final long lastModified = file.lastModified();
      if ( c_loadTime != lastModified )
      {
        loadMessages( file );
        c_loadTime = lastModified;
      }
    }
    else
    {
      c_messages.clear();
      c_loadTime = 0;
    }
  }

  @Nonnull
  private static File getMessageFilename()
  {
    final String fixtureDir = System.getProperty( "arez.diagnostic_messages_file" );
    assertNotNull( fixtureDir,
                   "Expected System.getProperty( \"arez.diagnostic_messages_file\" ) to return location of diagnostic messages file" );
    return new File( fixtureDir );
  }

  private static void loadMessages( @Nonnull final File file )
    throws FileNotFoundException
  {
    c_messages.clear();
    final JsonReader reader = Json.createReader( new FileInputStream( file ) );
    final JsonArray top = reader.readArray();
    final int size = top.size();
    for ( int i = 0; i < size; i++ )
    {
      final JsonObject entry = top.getJsonObject( i );
      final int code = entry.getInt( "code" );
      final Guards.Type type = Guards.Type.valueOf( entry.getString( "type" ) );
      final String messagePattern = entry.getString( "messagePattern" );
      if ( c_messages.containsKey( code ) )
      {
        throw new IllegalStateException( "Failed to load diagnostic_messages.json as it is incorrectly " +
                                         "formatted with duplicate entries for code " + code );
      }
      c_messages.put( code, new DiagnosticMessage( code, type, messagePattern, true ) );
    }
  }

  static void saveIfRequired()
    throws Exception
  {
    if ( c_messages.values().stream().anyMatch( m -> !m.isLoadedFromFixture() ) )
    {
      final File file = getMessageFilename();
      final Map<String, Object> properties = new HashMap<>();
      properties.put( JsonGenerator.PRETTY_PRINTING, true );

      final JsonGeneratorFactory generatorFactory = Json.createGeneratorFactory( properties );
      try ( final FileOutputStream output = new FileOutputStream( file ) )
      {
        final JsonGenerator g = generatorFactory.createGenerator( output );
        g.writeStartArray();
        c_messages.values()
          .stream()
          .sorted( Comparator.comparingInt( DiagnosticMessage::getCode ) )
          .forEachOrdered( m -> {
            g.writeStartObject();
            g.write( "code", m.getCode() );
            g.write( "type", m.getType().name() );
            g.write( "messagePattern", m.getMessagePattern() );
            g.writeEnd();
          } );
        g.writeEnd();
        g.close();
      }
      formatJson( file );
    }
  }

  /**
   * Format the json file.
   * This is horribly inefficient but it is not called very often so ... meh.
   */
  private static void formatJson( @Nonnull final File file )
    throws IOException
  {
    final byte[] data = Files.readAllBytes( file.toPath() );
    final Charset charset = Charset.forName( "UTF-8" );
    final String jsonData = new String( data, charset );

    final String output =
      jsonData
        .replaceAll( "(?m)^ {4}\\{", "  {" )
        .replaceAll( "(?m)^ {4}}", "  }" )
        .replaceAll( "(?m)^ {8}\"", "    \"" )
        .replaceAll( "(?m)^\n\\[\n", "[\n" ) +
      "\n";
    Files.write( file.toPath(), output.getBytes( charset ) );
  }

  private static void recordDiagnosticMessage( final int code,
                                               @Nonnull final Guards.Type type,
                                               @Nonnull final String messagePattern )
  {
    c_messages.put( code, new DiagnosticMessage( code, type, messagePattern, false ) );

  }

  static void matchOrRecordDiagnosticMessage( final int code,
                                              @Nonnull final Guards.Type type,
                                              @Nonnull final String message )
  {
    final DiagnosticMessage m = c_messages.get( code );
    if ( null == m )
    {
      recordDiagnosticMessage( code, type, message );
    }
    else
    {
      final StringBuilder sb = new StringBuilder();

      String messagePattern = m.getMessagePattern();
      int lastOffset = 0;
      int offset;
      while ( -1 != ( offset = messagePattern.indexOf( "%s", lastOffset ) ) )
      {
        final String segment = messagePattern.substring( lastOffset, offset );
        sb.append( Pattern.quote( segment ) );
        sb.append( ".*" );
        lastOffset = offset + 2;
      }
      sb.append( Pattern.quote( messagePattern.substring( lastOffset ) ) );

      final Pattern pattern = Pattern.compile( sb.toString() );
      if ( !pattern.matcher( message ).matches() )
      {
        throw new IllegalStateException( "Failed to match " + type + " with code " + code + ".\n" +
                                         "Expected pattern:\n" + messagePattern + "\n\n" +
                                         "Actual Message:\n" + message );
      }
      assertEquals( m.getType(), type, "Failed to match type of diagnostic message with code " + code );
    }
  }
}