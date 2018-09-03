package arez;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FlagsTest
  extends AbstractArezTest
{
  @Test
  public void isActive()
    throws Exception
  {
    assertFalse( Flags.isActive( Flags.STATE_DISPOSED ) );
    assertFalse( Flags.isActive( Flags.STATE_DISPOSING ) );
    assertFalse( Flags.isActive( Flags.STATE_INACTIVE ) );
    assertTrue( Flags.isActive( Flags.STATE_UP_TO_DATE ) );
    assertTrue( Flags.isActive( Flags.STATE_POSSIBLY_STALE ) );
    assertTrue( Flags.isActive( Flags.STATE_STALE ) );
  }

  @Test
  public void isNotActive()
    throws Exception
  {
    assertTrue( Flags.isNotActive( Flags.STATE_DISPOSED ) );
    assertTrue( Flags.isNotActive( Flags.STATE_DISPOSING ) );
    assertTrue( Flags.isNotActive( Flags.STATE_INACTIVE ) );
    assertFalse( Flags.isNotActive( Flags.STATE_UP_TO_DATE ) );
    assertFalse( Flags.isNotActive( Flags.STATE_POSSIBLY_STALE ) );
    assertFalse( Flags.isNotActive( Flags.STATE_STALE ) );
  }

  @Test
  public void getState()
    throws Exception
  {
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_DISPOSED ), Flags.STATE_DISPOSED );
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_DISPOSING ), Flags.STATE_DISPOSING );
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_INACTIVE ), Flags.STATE_INACTIVE );
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_UP_TO_DATE ), Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_POSSIBLY_STALE ), Flags.STATE_POSSIBLY_STALE );
    assertEquals( Flags.getState( Flags.PRIORITY_NORMAL | Flags.STATE_STALE ), Flags.STATE_STALE );
  }

  @Test
  public void setState()
    throws Exception
  {
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_DISPOSED ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_DISPOSED );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_DISPOSING ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_DISPOSING );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_DISPOSING ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_DISPOSING );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_INACTIVE ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_INACTIVE );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_UP_TO_DATE ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_POSSIBLY_STALE ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_POSSIBLY_STALE );
    assertEquals( Flags.setState( Flags.PRIORITY_NORMAL |
                                  Flags.READ_WRITE |
                                  Flags.STATE_UP_TO_DATE,
                                  Flags.STATE_STALE ),
                  Flags.PRIORITY_NORMAL | Flags.READ_WRITE | Flags.STATE_STALE );
  }

  @Test
  public void getLeastStaleObserverState()
    throws Exception
  {
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_DISPOSED ),
                  Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_DISPOSING ),
                  Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_INACTIVE ),
                  Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_UP_TO_DATE ),
                  Flags.STATE_UP_TO_DATE );
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_POSSIBLY_STALE ),
                  Flags.STATE_POSSIBLY_STALE );
    assertEquals( Flags.getLeastStaleObserverState( Flags.PRIORITY_NORMAL | Flags.STATE_STALE ), Flags.STATE_STALE );
  }

  @Test
  public void getStateName()
    throws Exception
  {
    assertEquals( Flags.getStateName( Flags.STATE_DISPOSED ), "DISPOSED" );
    assertEquals( Flags.getStateName( Flags.STATE_DISPOSING ), "DISPOSING" );
    assertEquals( Flags.getStateName( Flags.STATE_INACTIVE ), "INACTIVE" );
    assertEquals( Flags.getStateName( Flags.STATE_UP_TO_DATE ), "UP_TO_DATE" );
    assertEquals( Flags.getStateName( Flags.STATE_POSSIBLY_STALE ), "POSSIBLY_STALE" );
    assertEquals( Flags.getStateName( Flags.STATE_STALE ), "STALE" );
    // State value should have been passed in
    assertEquals( Flags.getStateName( Flags.PRIORITY_NORMAL | Flags.STATE_STALE ), "UNKNOWN(100663302)" );
  }

  @Test
  public void isPrioritySpecified()
    throws Exception
  {
    assertTrue( Flags.isPrioritySpecified( Flags.PRIORITY_HIGHEST ) );
    assertTrue( Flags.isPrioritySpecified( Flags.PRIORITY_HIGH ) );
    assertTrue( Flags.isPrioritySpecified( Flags.PRIORITY_NORMAL ) );
    assertTrue( Flags.isPrioritySpecified( Flags.PRIORITY_LOW ) );
    assertTrue( Flags.isPrioritySpecified( Flags.PRIORITY_LOWEST ) );
    assertFalse( Flags.isPrioritySpecified( 0 ) );
    assertFalse( Flags.isPrioritySpecified( Flags.READ_WRITE ) );
  }

  @Test
  public void getPriority()
    throws Exception
  {
    assertEquals( Flags.getPriority( Flags.PRIORITY_HIGHEST | Flags.STATE_INACTIVE ), 0 );
    assertEquals( Flags.getPriority( Flags.PRIORITY_HIGH | Flags.STATE_INACTIVE ), 1 );
    assertEquals( Flags.getPriority( Flags.PRIORITY_NORMAL | Flags.STATE_INACTIVE ), 2 );
    assertEquals( Flags.getPriority( Flags.PRIORITY_LOW | Flags.STATE_INACTIVE ), 3 );
    assertEquals( Flags.getPriority( Flags.PRIORITY_LOWEST | Flags.STATE_INACTIVE ), 4 );
  }

  @Test
  public void isTransactionModeSpecified()
    throws Exception
  {
    assertTrue( Flags.isTransactionModeSpecified( Flags.READ_ONLY ) );
    assertTrue( Flags.isTransactionModeSpecified( Flags.READ_WRITE ) );
    assertFalse( Flags.isTransactionModeSpecified( 0 ) );
    assertFalse( Flags.isTransactionModeSpecified( Flags.PRIORITY_LOWEST ) );
  }

  @Test
  public void optionsAreUnique()
    throws Exception
  {
    final HashMap<String, Integer> flags = new HashMap<>();
    for ( final Field field : Flags.class.getDeclaredFields() )
    {
      final String name = field.getName();
      if ( !name.endsWith( "_MASK" ) &&
           !name.endsWith( "_SHIFT" ) &&
           !Modifier.isPrivate( field.getModifiers() ) &&
           !field.isSynthetic() )
      {
        final int value = (Integer) field.get( null );
        flags.put( name, value );
      }
    }
    final ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>( flags.entrySet() );
    final int size = entries.size();
    for ( int i = 0; i < size; i++ )
    {
      final Map.Entry<String, Integer> entry = entries.get( i );
      final String name = entry.getKey();
      final int value = entry.getValue();

      if ( ( ( Flags.CONFIG_FLAGS_MASK | Flags.RUNTIME_FLAGS_MASK ) & value ) != value )
      {
        fail( "Flag named " + name + " in class " + Flags.class.getName() + " is not within " +
              "expected configuration mask. Update mask or configuration value." );
      }

      for ( int j = i + 1; j < size; j++ )
      {
        final Map.Entry<String, Integer> innerEntry = entries.get( j );
        final int innerValue = innerEntry.getValue();
        if ( innerValue == value )
        {
          fail( "Flags in class " + Flags.class.getName() + " are not unique. Flag named " + name +
                " and flag named " + innerEntry.getKey() + " have the same value: " + value );
        }
      }
    }
  }
}
