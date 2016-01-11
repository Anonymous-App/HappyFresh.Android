package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters
{
  public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN;
  public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
  public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER;
  public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY;
  public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
  public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
  public static final TypeAdapter<BigDecimal> BIG_DECIMAL;
  public static final TypeAdapter<BigInteger> BIG_INTEGER;
  public static final TypeAdapter<BitSet> BIT_SET;
  public static final TypeAdapterFactory BIT_SET_FACTORY;
  public static final TypeAdapter<Boolean> BOOLEAN;
  public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
  public static final TypeAdapterFactory BOOLEAN_FACTORY;
  public static final TypeAdapter<Number> BYTE;
  public static final TypeAdapterFactory BYTE_FACTORY;
  public static final TypeAdapter<Calendar> CALENDAR;
  public static final TypeAdapterFactory CALENDAR_FACTORY;
  public static final TypeAdapter<Character> CHARACTER;
  public static final TypeAdapterFactory CHARACTER_FACTORY;
  public static final TypeAdapter<Class> CLASS = new TypeAdapter()
  {
    public Class read(JsonReader paramAnonymousJsonReader)
      throws IOException
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
    }
    
    public void write(JsonWriter paramAnonymousJsonWriter, Class paramAnonymousClass)
      throws IOException
    {
      if (paramAnonymousClass == null)
      {
        paramAnonymousJsonWriter.nullValue();
        return;
      }
      throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + paramAnonymousClass.getName() + ". Forgot to register a type adapter?");
    }
  };
  public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
  public static final TypeAdapter<Currency> CURRENCY;
  public static final TypeAdapterFactory CURRENCY_FACTORY;
  public static final TypeAdapter<Number> DOUBLE;
  public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory()
  {
    public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      paramAnonymousTypeToken = paramAnonymousTypeToken.getRawType();
      if ((!Enum.class.isAssignableFrom(paramAnonymousTypeToken)) || (paramAnonymousTypeToken == Enum.class)) {
        return null;
      }
      paramAnonymousGson = paramAnonymousTypeToken;
      if (!paramAnonymousTypeToken.isEnum()) {
        paramAnonymousGson = paramAnonymousTypeToken.getSuperclass();
      }
      return new TypeAdapters.EnumTypeAdapter(paramAnonymousGson);
    }
  };
  public static final TypeAdapter<Number> FLOAT;
  public static final TypeAdapter<InetAddress> INET_ADDRESS;
  public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
  public static final TypeAdapter<Number> INTEGER;
  public static final TypeAdapterFactory INTEGER_FACTORY;
  public static final TypeAdapter<JsonElement> JSON_ELEMENT;
  public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
  public static final TypeAdapter<Locale> LOCALE;
  public static final TypeAdapterFactory LOCALE_FACTORY;
  public static final TypeAdapter<Number> LONG;
  public static final TypeAdapter<Number> NUMBER;
  public static final TypeAdapterFactory NUMBER_FACTORY;
  public static final TypeAdapter<Number> SHORT;
  public static final TypeAdapterFactory SHORT_FACTORY;
  public static final TypeAdapter<String> STRING;
  public static final TypeAdapter<StringBuffer> STRING_BUFFER;
  public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
  public static final TypeAdapter<StringBuilder> STRING_BUILDER;
  public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
  public static final TypeAdapterFactory STRING_FACTORY;
  public static final TypeAdapterFactory TIMESTAMP_FACTORY;
  public static final TypeAdapter<URI> URI;
  public static final TypeAdapterFactory URI_FACTORY;
  public static final TypeAdapter<URL> URL;
  public static final TypeAdapterFactory URL_FACTORY;
  public static final TypeAdapter<UUID> UUID;
  public static final TypeAdapterFactory UUID_FACTORY;
  
  static
  {
    BIT_SET = new TypeAdapter()
    {
      public BitSet read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        BitSet localBitSet = new BitSet();
        paramAnonymousJsonReader.beginArray();
        int i = 0;
        Object localObject = paramAnonymousJsonReader.peek();
        if (localObject != JsonToken.END_ARRAY)
        {
          boolean bool;
          switch (TypeAdapters.36.$SwitchMap$com$google$gson$stream$JsonToken[localObject.ordinal()])
          {
          default: 
            throw new JsonSyntaxException("Invalid bitset value type: " + localObject);
          case 1: 
            if (paramAnonymousJsonReader.nextInt() != 0) {
              bool = true;
            }
            break;
          }
          for (;;)
          {
            if (bool) {
              localBitSet.set(i);
            }
            i += 1;
            localObject = paramAnonymousJsonReader.peek();
            break;
            bool = false;
            continue;
            bool = paramAnonymousJsonReader.nextBoolean();
            continue;
            localObject = paramAnonymousJsonReader.nextString();
            try
            {
              int j = Integer.parseInt((String)localObject);
              if (j != 0) {}
              for (bool = true;; bool = false) {
                break;
              }
              paramAnonymousJsonReader.endArray();
            }
            catch (NumberFormatException paramAnonymousJsonReader)
            {
              throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + (String)localObject);
            }
          }
        }
        return localBitSet;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BitSet paramAnonymousBitSet)
        throws IOException
      {
        if (paramAnonymousBitSet == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.beginArray();
        int i = 0;
        if (i < paramAnonymousBitSet.length())
        {
          if (paramAnonymousBitSet.get(i)) {}
          for (int j = 1;; j = 0)
          {
            paramAnonymousJsonWriter.value(j);
            i += 1;
            break;
          }
        }
        paramAnonymousJsonWriter.endArray();
      }
    };
    BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
    BOOLEAN = new TypeAdapter()
    {
      public Boolean read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        if (paramAnonymousJsonReader.peek() == JsonToken.STRING) {
          return Boolean.valueOf(Boolean.parseBoolean(paramAnonymousJsonReader.nextString()));
        }
        return Boolean.valueOf(paramAnonymousJsonReader.nextBoolean());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
        throws IOException
      {
        if (paramAnonymousBoolean == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.value(paramAnonymousBoolean.booleanValue());
      }
    };
    BOOLEAN_AS_STRING = new TypeAdapter()
    {
      public Boolean read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Boolean.valueOf(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
        throws IOException
      {
        if (paramAnonymousBoolean == null) {}
        for (paramAnonymousBoolean = "null";; paramAnonymousBoolean = paramAnonymousBoolean.toString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousBoolean);
          return;
        }
      }
    };
    BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    BYTE = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          byte b = (byte)paramAnonymousJsonReader.nextInt();
          return Byte.valueOf(b);
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
    SHORT = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          short s = (short)paramAnonymousJsonReader.nextInt();
          return Short.valueOf(s);
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
    INTEGER = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          int i = paramAnonymousJsonReader.nextInt();
          return Integer.valueOf(i);
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
    ATOMIC_INTEGER = new TypeAdapter()
    {
      public AtomicInteger read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        try
        {
          paramAnonymousJsonReader = new AtomicInteger(paramAnonymousJsonReader.nextInt());
          return paramAnonymousJsonReader;
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, AtomicInteger paramAnonymousAtomicInteger)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousAtomicInteger.get());
      }
    }.nullSafe();
    ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
    ATOMIC_BOOLEAN = new TypeAdapter()
    {
      public AtomicBoolean read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        return new AtomicBoolean(paramAnonymousJsonReader.nextBoolean());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, AtomicBoolean paramAnonymousAtomicBoolean)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousAtomicBoolean.get());
      }
    }.nullSafe();
    ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
    ATOMIC_INTEGER_ARRAY = new TypeAdapter()
    {
      public AtomicIntegerArray read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        ArrayList localArrayList = new ArrayList();
        paramAnonymousJsonReader.beginArray();
        while (paramAnonymousJsonReader.hasNext()) {
          try
          {
            localArrayList.add(Integer.valueOf(paramAnonymousJsonReader.nextInt()));
          }
          catch (NumberFormatException paramAnonymousJsonReader)
          {
            throw new JsonSyntaxException(paramAnonymousJsonReader);
          }
        }
        paramAnonymousJsonReader.endArray();
        int j = localArrayList.size();
        paramAnonymousJsonReader = new AtomicIntegerArray(j);
        int i = 0;
        while (i < j)
        {
          paramAnonymousJsonReader.set(i, ((Integer)localArrayList.get(i)).intValue());
          i += 1;
        }
        return paramAnonymousJsonReader;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, AtomicIntegerArray paramAnonymousAtomicIntegerArray)
        throws IOException
      {
        paramAnonymousJsonWriter.beginArray();
        int i = 0;
        int j = paramAnonymousAtomicIntegerArray.length();
        while (i < j)
        {
          paramAnonymousJsonWriter.value(paramAnonymousAtomicIntegerArray.get(i));
          i += 1;
        }
        paramAnonymousJsonWriter.endArray();
      }
    }.nullSafe();
    ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
    LONG = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          long l = paramAnonymousJsonReader.nextLong();
          return Long.valueOf(l);
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    FLOAT = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Float.valueOf((float)paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    DOUBLE = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Double.valueOf(paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    NUMBER = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        switch (TypeAdapters.36.$SwitchMap$com$google$gson$stream$JsonToken[localJsonToken.ordinal()])
        {
        case 2: 
        case 3: 
        default: 
          throw new JsonSyntaxException("Expecting number, got: " + localJsonToken);
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new LazilyParsedNumber(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    NUMBER_FACTORY = newFactory(Number.class, NUMBER);
    CHARACTER = new TypeAdapter()
    {
      public Character read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
        if (paramAnonymousJsonReader.length() != 1) {
          throw new JsonSyntaxException("Expecting character, got: " + paramAnonymousJsonReader);
        }
        return Character.valueOf(paramAnonymousJsonReader.charAt(0));
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Character paramAnonymousCharacter)
        throws IOException
      {
        if (paramAnonymousCharacter == null) {}
        for (paramAnonymousCharacter = null;; paramAnonymousCharacter = String.valueOf(paramAnonymousCharacter))
        {
          paramAnonymousJsonWriter.value(paramAnonymousCharacter);
          return;
        }
      }
    };
    CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
    STRING = new TypeAdapter()
    {
      public String read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        if (localJsonToken == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        if (localJsonToken == JsonToken.BOOLEAN) {
          return Boolean.toString(paramAnonymousJsonReader.nextBoolean());
        }
        return paramAnonymousJsonReader.nextString();
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, String paramAnonymousString)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousString);
      }
    };
    BIG_DECIMAL = new TypeAdapter()
    {
      public BigDecimal read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          paramAnonymousJsonReader = new BigDecimal(paramAnonymousJsonReader.nextString());
          return paramAnonymousJsonReader;
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BigDecimal paramAnonymousBigDecimal)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousBigDecimal);
      }
    };
    BIG_INTEGER = new TypeAdapter()
    {
      public BigInteger read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          paramAnonymousJsonReader = new BigInteger(paramAnonymousJsonReader.nextString());
          return paramAnonymousJsonReader;
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BigInteger paramAnonymousBigInteger)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousBigInteger);
      }
    };
    STRING_FACTORY = newFactory(String.class, STRING);
    STRING_BUILDER = new TypeAdapter()
    {
      public StringBuilder read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new StringBuilder(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, StringBuilder paramAnonymousStringBuilder)
        throws IOException
      {
        if (paramAnonymousStringBuilder == null) {}
        for (paramAnonymousStringBuilder = null;; paramAnonymousStringBuilder = paramAnonymousStringBuilder.toString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousStringBuilder);
          return;
        }
      }
    };
    STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
    STRING_BUFFER = new TypeAdapter()
    {
      public StringBuffer read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new StringBuffer(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, StringBuffer paramAnonymousStringBuffer)
        throws IOException
      {
        if (paramAnonymousStringBuffer == null) {}
        for (paramAnonymousStringBuffer = null;; paramAnonymousStringBuffer = paramAnonymousStringBuffer.toString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousStringBuffer);
          return;
        }
      }
    };
    STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
    URL = new TypeAdapter()
    {
      public URL read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL) {
          paramAnonymousJsonReader.nextNull();
        }
        do
        {
          return null;
          paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
        } while ("null".equals(paramAnonymousJsonReader));
        return new URL(paramAnonymousJsonReader);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, URL paramAnonymousURL)
        throws IOException
      {
        if (paramAnonymousURL == null) {}
        for (paramAnonymousURL = null;; paramAnonymousURL = paramAnonymousURL.toExternalForm())
        {
          paramAnonymousJsonWriter.value(paramAnonymousURL);
          return;
        }
      }
    };
    URL_FACTORY = newFactory(URL.class, URL);
    URI = new TypeAdapter()
    {
      public URI read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL) {
          paramAnonymousJsonReader.nextNull();
        }
        for (;;)
        {
          return null;
          try
          {
            paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
            if ("null".equals(paramAnonymousJsonReader)) {
              continue;
            }
            paramAnonymousJsonReader = new URI(paramAnonymousJsonReader);
            return paramAnonymousJsonReader;
          }
          catch (URISyntaxException paramAnonymousJsonReader)
          {
            throw new JsonIOException(paramAnonymousJsonReader);
          }
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, URI paramAnonymousURI)
        throws IOException
      {
        if (paramAnonymousURI == null) {}
        for (paramAnonymousURI = null;; paramAnonymousURI = paramAnonymousURI.toASCIIString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousURI);
          return;
        }
      }
    };
    URI_FACTORY = newFactory(URI.class, URI);
    INET_ADDRESS = new TypeAdapter()
    {
      public InetAddress read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return InetAddress.getByName(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, InetAddress paramAnonymousInetAddress)
        throws IOException
      {
        if (paramAnonymousInetAddress == null) {}
        for (paramAnonymousInetAddress = null;; paramAnonymousInetAddress = paramAnonymousInetAddress.getHostAddress())
        {
          paramAnonymousJsonWriter.value(paramAnonymousInetAddress);
          return;
        }
      }
    };
    INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
    UUID = new TypeAdapter()
    {
      public UUID read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return UUID.fromString(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, UUID paramAnonymousUUID)
        throws IOException
      {
        if (paramAnonymousUUID == null) {}
        for (paramAnonymousUUID = null;; paramAnonymousUUID = paramAnonymousUUID.toString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousUUID);
          return;
        }
      }
    };
    UUID_FACTORY = newFactory(UUID.class, UUID);
    CURRENCY = new TypeAdapter()
    {
      public Currency read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        return Currency.getInstance(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Currency paramAnonymousCurrency)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousCurrency.getCurrencyCode());
      }
    }.nullSafe();
    CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
    TIMESTAMP_FACTORY = new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.getRawType() != Timestamp.class) {
          return null;
        }
        new TypeAdapter()
        {
          public Timestamp read(JsonReader paramAnonymous2JsonReader)
            throws IOException
          {
            paramAnonymous2JsonReader = (Date)this.val$dateTypeAdapter.read(paramAnonymous2JsonReader);
            if (paramAnonymous2JsonReader != null) {
              return new Timestamp(paramAnonymous2JsonReader.getTime());
            }
            return null;
          }
          
          public void write(JsonWriter paramAnonymous2JsonWriter, Timestamp paramAnonymous2Timestamp)
            throws IOException
          {
            this.val$dateTypeAdapter.write(paramAnonymous2JsonWriter, paramAnonymous2Timestamp);
          }
        };
      }
    };
    CALENDAR = new TypeAdapter()
    {
      private static final String DAY_OF_MONTH = "dayOfMonth";
      private static final String HOUR_OF_DAY = "hourOfDay";
      private static final String MINUTE = "minute";
      private static final String MONTH = "month";
      private static final String SECOND = "second";
      private static final String YEAR = "year";
      
      public Calendar read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        paramAnonymousJsonReader.beginObject();
        int i2 = 0;
        int i1 = 0;
        int n = 0;
        int m = 0;
        int k = 0;
        int j = 0;
        while (paramAnonymousJsonReader.peek() != JsonToken.END_OBJECT)
        {
          String str = paramAnonymousJsonReader.nextName();
          int i = paramAnonymousJsonReader.nextInt();
          if ("year".equals(str)) {
            i2 = i;
          } else if ("month".equals(str)) {
            i1 = i;
          } else if ("dayOfMonth".equals(str)) {
            n = i;
          } else if ("hourOfDay".equals(str)) {
            m = i;
          } else if ("minute".equals(str)) {
            k = i;
          } else if ("second".equals(str)) {
            j = i;
          }
        }
        paramAnonymousJsonReader.endObject();
        return new GregorianCalendar(i2, i1, n, m, k, j);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Calendar paramAnonymousCalendar)
        throws IOException
      {
        if (paramAnonymousCalendar == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.beginObject();
        paramAnonymousJsonWriter.name("year");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(1));
        paramAnonymousJsonWriter.name("month");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(2));
        paramAnonymousJsonWriter.name("dayOfMonth");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(5));
        paramAnonymousJsonWriter.name("hourOfDay");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(11));
        paramAnonymousJsonWriter.name("minute");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(12));
        paramAnonymousJsonWriter.name("second");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(13));
        paramAnonymousJsonWriter.endObject();
      }
    };
    CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
    LOCALE = new TypeAdapter()
    {
      public Locale read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(paramAnonymousJsonReader.nextString(), "_");
        paramAnonymousJsonReader = null;
        String str1 = null;
        String str2 = null;
        if (localStringTokenizer.hasMoreElements()) {
          paramAnonymousJsonReader = localStringTokenizer.nextToken();
        }
        if (localStringTokenizer.hasMoreElements()) {
          str1 = localStringTokenizer.nextToken();
        }
        if (localStringTokenizer.hasMoreElements()) {
          str2 = localStringTokenizer.nextToken();
        }
        if ((str1 == null) && (str2 == null)) {
          return new Locale(paramAnonymousJsonReader);
        }
        if (str2 == null) {
          return new Locale(paramAnonymousJsonReader, str1);
        }
        return new Locale(paramAnonymousJsonReader, str1, str2);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Locale paramAnonymousLocale)
        throws IOException
      {
        if (paramAnonymousLocale == null) {}
        for (paramAnonymousLocale = null;; paramAnonymousLocale = paramAnonymousLocale.toString())
        {
          paramAnonymousJsonWriter.value(paramAnonymousLocale);
          return;
        }
      }
    };
    LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
    JSON_ELEMENT = new TypeAdapter()
    {
      public JsonElement read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        switch (TypeAdapters.36.$SwitchMap$com$google$gson$stream$JsonToken[paramAnonymousJsonReader.peek().ordinal()])
        {
        default: 
          throw new IllegalArgumentException();
        case 3: 
          return new JsonPrimitive(paramAnonymousJsonReader.nextString());
        case 1: 
          return new JsonPrimitive(new LazilyParsedNumber(paramAnonymousJsonReader.nextString()));
        case 2: 
          return new JsonPrimitive(Boolean.valueOf(paramAnonymousJsonReader.nextBoolean()));
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return JsonNull.INSTANCE;
        case 5: 
          localObject = new JsonArray();
          paramAnonymousJsonReader.beginArray();
          while (paramAnonymousJsonReader.hasNext()) {
            ((JsonArray)localObject).add(read(paramAnonymousJsonReader));
          }
          paramAnonymousJsonReader.endArray();
          return (JsonElement)localObject;
        }
        Object localObject = new JsonObject();
        paramAnonymousJsonReader.beginObject();
        while (paramAnonymousJsonReader.hasNext()) {
          ((JsonObject)localObject).add(paramAnonymousJsonReader.nextName(), read(paramAnonymousJsonReader));
        }
        paramAnonymousJsonReader.endObject();
        return (JsonElement)localObject;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, JsonElement paramAnonymousJsonElement)
        throws IOException
      {
        if ((paramAnonymousJsonElement == null) || (paramAnonymousJsonElement.isJsonNull()))
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        if (paramAnonymousJsonElement.isJsonPrimitive())
        {
          paramAnonymousJsonElement = paramAnonymousJsonElement.getAsJsonPrimitive();
          if (paramAnonymousJsonElement.isNumber())
          {
            paramAnonymousJsonWriter.value(paramAnonymousJsonElement.getAsNumber());
            return;
          }
          if (paramAnonymousJsonElement.isBoolean())
          {
            paramAnonymousJsonWriter.value(paramAnonymousJsonElement.getAsBoolean());
            return;
          }
          paramAnonymousJsonWriter.value(paramAnonymousJsonElement.getAsString());
          return;
        }
        if (paramAnonymousJsonElement.isJsonArray())
        {
          paramAnonymousJsonWriter.beginArray();
          paramAnonymousJsonElement = paramAnonymousJsonElement.getAsJsonArray().iterator();
          while (paramAnonymousJsonElement.hasNext()) {
            write(paramAnonymousJsonWriter, (JsonElement)paramAnonymousJsonElement.next());
          }
          paramAnonymousJsonWriter.endArray();
          return;
        }
        if (paramAnonymousJsonElement.isJsonObject())
        {
          paramAnonymousJsonWriter.beginObject();
          paramAnonymousJsonElement = paramAnonymousJsonElement.getAsJsonObject().entrySet().iterator();
          while (paramAnonymousJsonElement.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)paramAnonymousJsonElement.next();
            paramAnonymousJsonWriter.name((String)localEntry.getKey());
            write(paramAnonymousJsonWriter, (JsonElement)localEntry.getValue());
          }
          paramAnonymousJsonWriter.endObject();
          return;
        }
        throw new IllegalArgumentException("Couldn't write " + paramAnonymousJsonElement.getClass());
      }
    };
    JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
  }
  
  private TypeAdapters()
  {
    throw new UnsupportedOperationException();
  }
  
  public static <TT> TypeAdapterFactory newFactory(TypeToken<TT> paramTypeToken, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.equals(this.val$type)) {
          return paramTypeAdapter;
        }
        return null;
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.getRawType() == this.val$type) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + this.val$type.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass1, final Class<TT> paramClass2, final TypeAdapter<? super TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        paramAnonymousGson = paramAnonymousTypeToken.getRawType();
        if ((paramAnonymousGson == this.val$unboxed) || (paramAnonymousGson == paramClass2)) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + paramClass2.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(Class<TT> paramClass, final Class<? extends TT> paramClass1, final TypeAdapter<? super TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        paramAnonymousGson = paramAnonymousTypeToken.getRawType();
        if ((paramAnonymousGson == this.val$base) || (paramAnonymousGson == paramClass1)) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + this.val$base.getName() + "+" + paramClass1.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <T1> TypeAdapterFactory newTypeHierarchyFactory(Class<T1> paramClass, final TypeAdapter<T1> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T2> TypeAdapter<T2> create(final Gson paramAnonymousGson, TypeToken<T2> paramAnonymousTypeToken)
      {
        paramAnonymousGson = paramAnonymousTypeToken.getRawType();
        if (!this.val$clazz.isAssignableFrom(paramAnonymousGson)) {
          return null;
        }
        new TypeAdapter()
        {
          public T1 read(JsonReader paramAnonymous2JsonReader)
            throws IOException
          {
            paramAnonymous2JsonReader = TypeAdapters.35.this.val$typeAdapter.read(paramAnonymous2JsonReader);
            if ((paramAnonymous2JsonReader != null) && (!paramAnonymousGson.isInstance(paramAnonymous2JsonReader))) {
              throw new JsonSyntaxException("Expected a " + paramAnonymousGson.getName() + " but was " + paramAnonymous2JsonReader.getClass().getName());
            }
            return paramAnonymous2JsonReader;
          }
          
          public void write(JsonWriter paramAnonymous2JsonWriter, T1 paramAnonymous2T1)
            throws IOException
          {
            TypeAdapters.35.this.val$typeAdapter.write(paramAnonymous2JsonWriter, paramAnonymous2T1);
          }
        };
      }
      
      public String toString()
      {
        return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T>
  {
    private final Map<T, String> constantToName = new HashMap();
    private final Map<String, T> nameToConstant = new HashMap();
    
    public EnumTypeAdapter(Class<T> paramClass)
    {
      try
      {
        Enum[] arrayOfEnum = (Enum[])paramClass.getEnumConstants();
        int k = arrayOfEnum.length;
        int i = 0;
        while (i < k)
        {
          Enum localEnum = arrayOfEnum[i];
          Object localObject1 = localEnum.name();
          Object localObject2 = (SerializedName)paramClass.getField((String)localObject1).getAnnotation(SerializedName.class);
          if (localObject2 != null)
          {
            String str = ((SerializedName)localObject2).value();
            localObject2 = ((SerializedName)localObject2).alternate();
            int m = localObject2.length;
            int j = 0;
            for (;;)
            {
              localObject1 = str;
              if (j >= m) {
                break;
              }
              localObject1 = localObject2[j];
              this.nameToConstant.put(localObject1, localEnum);
              j += 1;
            }
          }
          this.nameToConstant.put(localObject1, localEnum);
          this.constantToName.put(localEnum, localObject1);
          i += 1;
        }
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        throw new AssertionError("Missing field in " + paramClass.getName(), localNoSuchFieldException);
      }
    }
    
    public T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      return (Enum)this.nameToConstant.get(paramJsonReader.nextString());
    }
    
    public void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (paramT == null) {}
      for (paramT = null;; paramT = (String)this.constantToName.get(paramT))
      {
        paramJsonWriter.value(paramT);
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/gson/internal/bind/TypeAdapters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */