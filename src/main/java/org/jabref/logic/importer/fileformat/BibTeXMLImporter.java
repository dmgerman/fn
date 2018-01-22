begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBElement
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|Unmarshaller
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|datatype
operator|.
name|XMLGregorianCalendar
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|Importer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|bibtexml
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|bibtexml
operator|.
name|File
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|bibtexml
operator|.
name|Inbook
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|bibtexml
operator|.
name|Incollection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|FileType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Importer for the BibTeXML format.  *<p>  * check here for details on the format  * http://bibtexml.sourceforge.net/  */
end_comment

begin_class
DECL|class|BibTeXMLImporter
specifier|public
class|class
name|BibTeXMLImporter
extends|extends
name|Importer
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|BibTeXMLImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|START_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|START_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<(bibtex:)?file .*"
argument_list|)
decl_stmt|;
DECL|field|IGNORED_METHODS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|IGNORED_METHODS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"getClass"
argument_list|,
literal|"getAnnotate"
argument_list|,
literal|"getContents"
argument_list|,
literal|"getPrice"
argument_list|,
literal|"getSize"
argument_list|,
literal|"getChapter"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"BibTeXML"
return|;
block|}
annotation|@
name|Override
DECL|method|getFileType ()
specifier|public
name|FileType
name|getFileType
parameter_list|()
block|{
return|return
name|FileType
operator|.
name|BIBTEXML
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Importer for the BibTeXML format."
return|;
block|}
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader reader)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Our strategy is to look for the "<bibtex:file *" line.
name|String
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|START_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader reader)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|JAXBContext
name|context
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
literal|"org.jabref.logic.importer.fileformat.bibtexml"
argument_list|)
decl_stmt|;
name|Unmarshaller
name|unmarshaller
init|=
name|context
operator|.
name|createUnmarshaller
argument_list|()
decl_stmt|;
name|File
name|file
init|=
operator|(
name|File
operator|)
name|unmarshaller
operator|.
name|unmarshal
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Entry
argument_list|>
name|entries
init|=
name|file
operator|.
name|getEntry
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
name|entry
range|:
name|entries
control|)
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|getArticle
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getArticle
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getBook
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"book"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getBook
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getBooklet
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"booklet"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getBooklet
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getConference
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"conference"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getConference
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getInbook
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"inbook"
argument_list|)
expr_stmt|;
name|parseInbook
argument_list|(
name|entry
operator|.
name|getInbook
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getIncollection
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"incollection"
argument_list|)
expr_stmt|;
name|Incollection
name|incollection
init|=
name|entry
operator|.
name|getIncollection
argument_list|()
decl_stmt|;
if|if
condition|(
name|incollection
operator|.
name|getChapter
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|CHAPTER
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|incollection
operator|.
name|getChapter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|parse
argument_list|(
name|incollection
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getInproceedings
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"inproceedings"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getInproceedings
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getManual
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"manual"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getManual
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getMastersthesis
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"mastersthesis"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getMastersthesis
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getMisc
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"misc"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getMisc
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getPhdthesis
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"phdthesis"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getPhdthesis
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getProceedings
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"proceedings"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getProceedings
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getTechreport
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"techreport"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getTechreport
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getUnpublished
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"unpublished"
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|entry
operator|.
name|getUnpublished
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setCiteKey
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|bibEntry
operator|.
name|setField
argument_list|(
name|fields
argument_list|)
expr_stmt|;
name|bibItems
operator|.
name|add
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|JAXBException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error with XML parser configuration"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|ParserResult
operator|.
name|fromError
argument_list|(
name|e
argument_list|)
return|;
block|}
return|return
operator|new
name|ParserResult
argument_list|(
name|bibItems
argument_list|)
return|;
block|}
comment|/**      * We use a generic method and not work on the real classes, because they all have the same behaviour. They call all get methods      * that are needed and use the return value. So this will prevent writing similar methods for every type.      *<p>      * In this method, all<Code>get</Code> methods that entryType has will be used and their value will be put to fields,      * if it is not null. So for example if entryType has the method<Code>getAbstract</Code>, then      * "abstract" will be put as key to fields and the value of<Code>getAbstract</Code> will be put as value to fields.      * Some<Code>get</Code> methods shouldn't be mapped to fields, so<Code>getClass</Code> for example will be skipped.      *      * @param entryType This can be all possible BibTeX types. It contains all fields of the entry and their values.      * @param fields A map where the name and the value of all fields that the entry contains will be put.      */
DECL|method|parse (T entryType, Map<String, String> fields)
specifier|private
parameter_list|<
name|T
parameter_list|>
name|void
name|parse
parameter_list|(
name|T
name|entryType
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|Method
index|[]
name|declaredMethods
init|=
name|entryType
operator|.
name|getClass
argument_list|()
operator|.
name|getDeclaredMethods
argument_list|()
decl_stmt|;
for|for
control|(
name|Method
name|method
range|:
name|declaredMethods
control|)
block|{
try|try
block|{
if|if
condition|(
name|method
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"getYear"
argument_list|)
condition|)
block|{
name|putYear
argument_list|(
name|fields
argument_list|,
operator|(
name|XMLGregorianCalendar
operator|)
name|method
operator|.
name|invoke
argument_list|(
name|entryType
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
elseif|else
if|if
condition|(
name|method
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"getNumber"
argument_list|)
condition|)
block|{
name|putNumber
argument_list|(
name|fields
argument_list|,
operator|(
name|BigInteger
operator|)
name|method
operator|.
name|invoke
argument_list|(
name|entryType
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
elseif|else
if|if
condition|(
name|isMethodToIgnore
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
elseif|else
if|if
condition|(
name|method
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"get"
argument_list|)
condition|)
block|{
name|putIfValueNotNull
argument_list|(
name|fields
argument_list|,
name|method
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|"get"
argument_list|,
literal|""
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|method
operator|.
name|invoke
argument_list|(
name|entryType
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
decl||
name|InvocationTargetException
decl||
name|IllegalAccessException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not invoke method"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Returns whether the value of the given method name should be mapped or whether the method can be ignored.      *      * @param methodName The name of the method as String      * @return true if the method can be ignored, else false      */
DECL|method|isMethodToIgnore (String methodName)
specifier|private
name|boolean
name|isMethodToIgnore
parameter_list|(
name|String
name|methodName
parameter_list|)
block|{
return|return
name|IGNORED_METHODS
operator|.
name|contains
argument_list|(
name|methodName
argument_list|)
return|;
block|}
comment|/**      * Inbook needs a special Treatment, because<Code>inbook.getContent()</Code> returns a list of<Code>JAXBElements</Code>.      * The other types have just<Code>get</Code> methods, which return the values as Strings.      */
DECL|method|parseInbook (Inbook inbook, Map<String, String> fields)
specifier|private
name|void
name|parseInbook
parameter_list|(
name|Inbook
name|inbook
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|List
argument_list|<
name|JAXBElement
argument_list|<
name|?
argument_list|>
argument_list|>
name|content
init|=
name|inbook
operator|.
name|getContent
argument_list|()
decl_stmt|;
for|for
control|(
name|JAXBElement
argument_list|<
name|?
argument_list|>
name|element
range|:
name|content
control|)
block|{
name|String
name|localName
init|=
name|element
operator|.
name|getName
argument_list|()
operator|.
name|getLocalPart
argument_list|()
decl_stmt|;
name|Object
name|elementValue
init|=
name|element
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|elementValue
operator|instanceof
name|String
condition|)
block|{
name|String
name|value
init|=
operator|(
name|String
operator|)
name|elementValue
decl_stmt|;
name|putIfValueNotNull
argument_list|(
name|fields
argument_list|,
name|localName
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|elementValue
operator|instanceof
name|BigInteger
condition|)
block|{
name|BigInteger
name|value
init|=
operator|(
name|BigInteger
operator|)
name|elementValue
decl_stmt|;
if|if
condition|(
name|FieldName
operator|.
name|NUMBER
operator|.
name|equals
argument_list|(
name|localName
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|CHAPTER
operator|.
name|equals
argument_list|(
name|localName
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|CHAPTER
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|elementValue
operator|instanceof
name|XMLGregorianCalendar
condition|)
block|{
name|XMLGregorianCalendar
name|value
init|=
operator|(
name|XMLGregorianCalendar
operator|)
name|elementValue
decl_stmt|;
if|if
condition|(
name|FieldName
operator|.
name|YEAR
operator|.
name|equals
argument_list|(
name|localName
argument_list|)
condition|)
block|{
name|putYear
argument_list|(
name|fields
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Unexpected field was found"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Unexpected field was found"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|putYear (Map<String, String> fields, XMLGregorianCalendar year)
specifier|private
name|void
name|putYear
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|,
name|XMLGregorianCalendar
name|year
parameter_list|)
block|{
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|year
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|putNumber (Map<String, String> fields, BigInteger number)
specifier|private
name|void
name|putNumber
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|,
name|BigInteger
name|number
parameter_list|)
block|{
if|if
condition|(
name|number
operator|!=
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|number
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|putIfValueNotNull (Map<String, String> fields, String key, String value)
specifier|private
name|void
name|putIfValueNotNull
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|,
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

